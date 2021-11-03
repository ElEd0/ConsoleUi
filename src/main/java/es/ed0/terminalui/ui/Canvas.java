package es.ed0.terminalui.ui;

import es.ed0.terminalui.ui.style.Border;
import es.ed0.terminalui.ui.style.BorderStyle.BorderPiece;

/**
 * Canvas Component to draw any character in any position of a custom sized matrix
 */
public class Canvas extends Component {

	private int width, height;
	private char[][] content;
	private boolean dynamicSize = false;
	

	public Canvas(int width, int height) {
		this(new char[width][height]);
	}

	public Canvas(char[][] content) {
		this.content = content;
		this.width = content.length;
		this.height = (this.width > 0 ? content[0].length : 0);
	}

	@Override
	protected void print(StringBuilder sb) {
		final Border b = getBorder();
		final int borderWidth = this.width + padding[3] + padding[1];
		// border top
		b.drawBorderTop(sb, borderWidth);
		
		// top padding
		for (int p = 0; p < this.padding[0]; p++) {
			sb.append(b.getStyle().getPiece(BorderPiece.ws));
			for (int w = 0; w < borderWidth; w++)
				sb.append(this.getPaddingChar());
			sb.append(b.getStyle().getPiece(BorderPiece.ws)).append("\n");
		}
		
		for (int h = 0; h < this.height; h++) {
			//left padding
			sb.append(b.getStyle().getPiece(BorderPiece.ws));
			for (int p = 0; p < this.padding[3]; p++)
				sb.append(this.getPaddingChar());
			
			//content
			for (int w = 0; w < this.width; w++)
				sb.append(this.content[w][h]);
				
			//right padding
			for (int p = 0; p < this.padding[1]; p++)
				sb.append(this.getPaddingChar());
			sb.append(b.getStyle().getPiece(BorderPiece.ws)).append("\n");
		
		}
		// bottom padding
		for (int p = 0; p < this.padding[2]; p++) {
			sb.append(b.getStyle().getPiece(BorderPiece.ws));
			for (int w = 0; w < borderWidth; w++)
				sb.append(this.getPaddingChar());
			sb.append(b.getStyle().getPiece(BorderPiece.ws)).append("\n");
		}
		// bottom border
		b.drawBorderBottom(sb, borderWidth);
		
	}
	
	public char getCharAt(int x, int y) {
		return this.content[x][y];
	}

	public void draw(int x, int y, char c) {
		//System.out.println(x + " : " + y);
		this.content[x][y] = c;
	}
	/**
	 * Draws a hollow or filled rectangle given the start and end coords
	 * @param fromX start x 
	 * @param fromY start y
	 * @param toX end x
	 * @param toY end y
	 * @param c char to draw
	 * @param hollow true for hollow
	 */
	public void drawRectangle(int fromX, int fromY, int toX, int toY, char c, boolean hollow) {
		int x0 = fromX, y0 = fromY, x1 = toX, y1 = toY;
		if (fromX > toX) {
			final int aux = x1;
			x1 = x0;
			x0 = aux;
		}
		if (fromY > toY) {
			final int aux = y1;
			y1 = y0;
			y0 = aux;
		}

		if (hollow) {
			for (int w = x0; w < x1; w++) {
				draw(w, y0, c);
				draw(w, y1 - 1, c);
			}
			for (int h = y0; h < y1; h++) {
				draw(x0, h, c);
				draw(x1 - 1, h, c);
			}
		} else {
			for (int w = x0; w < x1; w++)
				for (int h = y0; h < y1; h++) {
					draw(w, h, c);
				}
		}
		
	}
	
	/**
	 * Draws a "straight" line in the canvas from one point to another 
	 * making use of Bresenham's line algorithm
	 * @param fromX start x
	 * @param fromY start y
	 * @param toX end x
	 * @param toY end y
	 * @param c char to draw
	 */
	public void drawLine(int fromX, int fromY, int toX, int toY, char c) {
		final int x0 = fromX, y0 = fromY, x1 = toX, y1 = toY;
		// 2 8 8 2
		final int ix = x0 < x1 ? 1 : -1, iy = y0 < y1 ? 1 : -1;
		final int dx = x0 < x1 ? x1 - x0 : x0 - x1, dy = y0 < y1 ? y1 - y0 : y0 - y1;
		int x = x0, y = y0;
		int p = 2 * dy - dx;
		
		// straight vertical line exception
		if (x == x1) {
			for (int i = y0; i != y1; i += iy) 
				draw(x, i, c);
		} else {// algorithm for diagonal and horizontal lines
			while (x != x1 || y != y1) {
				
				draw(x, y, c);
				
				if (p >= 0) {
					if (y != y1)
						y += iy;
					p = p + 2 * dy - 2 * dx;
				} else {
					p = p + 2 * dy;
				}
				if (x != x1)
					x += ix;
			}
		}
		
	}
	
	/**
	 * Draws a hollow or filled circle of given radius and center 
	 * using Bresenham's (midpoint) circle algorithm
	 * @param centerX center x
	 * @param centerY center y
	 * @param radius radius
	 * @param c char to draw
	 * @param hollow true for hollow
	 */
	public void drawCircle(int centerX, int centerY, int radius, char c, boolean hollow) {
		int x = 0, y = radius;
	    int d = 3 - 2 * radius; 
	    drawCircle(centerX, centerY, x, y, c, hollow);
	    while (y >= x) {
			// for each pixel we will draw all eight pixels 
			x++;
			// check for decision parameter and correspondingly update d, x, y 
			if (d > 0) {
				y--;
				d = d + 4 * (x - y) + 10; 
			} else {
				d = d + 4 * x + 6; 
			}
			drawCircle(centerX, centerY, x, y, c, hollow);
		} 
	    
	}
	
	private void drawCircle(int xc, int yc, int x, int y, char c, boolean hollow) {
		if (hollow) {
			draw(xc + x, yc + y, c);
			draw(xc - x, yc + y, c);
			draw(xc + x, yc - y, c);
			draw(xc - x, yc - y, c);
			draw(xc + y, yc + x, c);
			draw(xc - y, yc + x, c);
			draw(xc + y, yc - x, c);
			draw(xc - y, yc - x, c);
		} else {
			drawLine(xc - x, yc + y, xc + x, yc + y, c);
			drawLine(xc - x, yc - y, xc + x, yc - y, c);
			drawLine(xc - y, yc + x, xc + y, yc + x, c);
			drawLine(xc - y, yc - x, xc + y, yc - x, c);
		}
	}
	
	public void fill(char c) {
		for (int w = 0; w < width; w++)
			for (int h = 0; h < height; h++)
				this.content[w][h] = c;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isDynamicSize() {
		return dynamicSize;
	}

	public void setDynamicSize(boolean dynamicSize) {
		this.dynamicSize = dynamicSize;
	}
}
