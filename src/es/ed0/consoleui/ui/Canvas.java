package es.ed0.consoleui.ui;

import es.ed0.consoleui.ui.style.Alignment;
import es.ed0.consoleui.ui.style.BorderStyle;
import es.ed0.consoleui.ui.style.BorderStyle.BorderPiece;

/**
 * Canvas Component to draw any character in any position of a custom sized matrix
 */
public class Canvas extends Component {

	private BorderStyle style;
	private int width, height;
	private char[][] content;
	

	public Canvas(int width, int height) {
		this(BorderStyle.unicode, width, height);
	}
	
	public Canvas(BorderStyle style, int width, int height) {
		this(style, new char[width][height]);
	}

	public Canvas(BorderStyle style, char[][] content) {
		super(Alignment.left, new int[] {0, 0, 0, 0});
		this.style = style;
		this.content = content;
		this.width = content.length;
		this.height = (this.width > 0 ? content[0].length : 0);
	}

	@Override
	protected void print(StringBuilder sb) {
		final int borderWidth = this.width + padding[3] + padding[1];
		// top border
		sb.append(style.getPiece(BorderPiece.ds));
		for (int i = 0; i < borderWidth; i++) sb.append(style.getPiece(BorderPiece.da));
		sb.append(style.getPiece(BorderPiece.sa)).append("\n");
		
		// top padding
		for (int p = 0; p < this.padding[0]; p++) {
			sb.append(style.getPiece(BorderPiece.ws));
			for (int w = 0; w < borderWidth; w++)
				sb.append(" ");
			sb.append(style.getPiece(BorderPiece.ws)).append("\n");
		}
		
		for (int h = 0; h < this.height; h++) {
			//left padding
			sb.append(style.getPiece(BorderPiece.ws));
			for (int p = 0; p < this.padding[3]; p++)
				sb.append(" ");
			
			//content
			for (int w = 0; w < this.width; w++)
				sb.append(this.content[w][h]);
				
			//right padding
			for (int p = 0; p < this.padding[1]; p++)
				sb.append(" ");
			sb.append(style.getPiece(BorderPiece.ws)).append("\n");
		
		}
		// bottom padding
		for (int p = 0; p < this.padding[2]; p++) {
			sb.append(style.getPiece(BorderPiece.ws));
			for (int w = 0; w < borderWidth; w++)
				sb.append(" ");
			sb.append(style.getPiece(BorderPiece.ws)).append("\n");
		}
		// bottom border
		sb.append(style.getPiece(BorderPiece.wd));
		for (int i = 0; i < borderWidth; i++) sb.append(style.getPiece(BorderPiece.da));
		sb.append(style.getPiece(BorderPiece.wa));
	}
	
	public char getCharAt(int x, int y) {
		return this.content[x][y];
	}
	public void draw(int x, int y, char c) {
		this.content[x][y] = c;
	}
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
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param toY
	 * @param c
	 */
	public void drawLine(int fromX, int fromY, int toX, int toY, char c) {
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
		final int xDiff = x1 - x0, yDiff = y1 - y0;
		
		if (xDiff == 0) {
			for (int h = y0; h < y1; h++) {
				draw(x0, h, c);
			}
		} else if (yDiff == 0) {
			for (int w = x0; w < x1; w++) {
				draw(w, y0, c);
			}
		} else {//algorithm

			final int a = yDiff * 2, b = a - xDiff * 2, p = a - xDiff;

			for (int w = x0; w < x1; w++) {
				for (int h = y0; h < y1; h++) {
					draw(w, h, c);
				}
			}
			
		}
		
	}
	
	public void fill(char c) {
		for (int w = 0; w < width; w++)
			for (int h = 0; h < height; h++)
				this.content[w][h] = c;
	}

	public BorderStyle getStyle() {
		return style;
	}

	public void setStyle(BorderStyle style) {
		this.style = style;
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
	
	

}
