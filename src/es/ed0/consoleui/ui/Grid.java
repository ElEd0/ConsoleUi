/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.ed0.consoleui.ui.style.Alignment;
import es.ed0.consoleui.ui.style.BorderStyle;
import es.ed0.consoleui.ui.style.BorderStyle.BorderPiece;

/**
 * 
 */
public class Grid extends Component {

	private ArrayList<ArrayList<Component>> rows;
	private ArrayList<Integer> colWidths, rowHeights;
	private int tileMinWidth = 0; // borders do not take in account
	private int tileMaxWidth = 50; // borders do not take in account
	private BorderStyle style;
	
	private HashMap<String, Alignment> alignOverrides;
	
	public Grid() {
		this(BorderStyle.unicode, 1, 1);
	}
	public Grid(int cols, int rows) {
		this(BorderStyle.unicode, cols, rows);
	}
	public Grid(BorderStyle style, int cols, int rows) {
		super(Alignment.center, new int[] {0, 1, 0, 1});
		this.style = style;
		this.alignOverrides = new HashMap<String, Alignment>();
		this.rows = new ArrayList<ArrayList<Component>>(rows);
		for (int r = 0; r < rows; r++) {
			this.rows.add(new ArrayList<Component>(cols));
			for (int c = 0; c < cols; c++)
				this.rows.get(r).add(new Text(""));
		}
	}
	
	@Override
	protected void print(StringBuilder sb) {

		// virtual empty row used to print top and bottom padding for each row
		final ArrayList<ArrayList<String>> paddingRow = new ArrayList<ArrayList<String>>(rows.get(0).size());
		
		rowHeights = new ArrayList<Integer>(rows.size());
		colWidths = new ArrayList<Integer>(rows.get(0).size());

		for (int r = 0; r < rows.size(); r++) {
			for (int c = 0; c < rows.get(r).size(); c++) {
				// we reuse the first iteration of the column loop to populate the virtual padding row once
				if (r == 0)
					paddingRow.add(new ArrayList<String>());
				
				if (c > colWidths.size() - 1)
					colWidths.add(c, this.tileMinWidth);
				
				if (colWidths.get(c) == -1)
					continue;
				
				int currWidth = colWidths.get(c);
				int width = rows.get(r).get(c).getWidth();
				
				if (rows.get(r).get(c) instanceof Text) {
					if (width > this.tileMaxWidth)
						width = this.tileMaxWidth;
					if (width > currWidth)
						colWidths.set(c, width);
				} else {
					colWidths.set(c, width);
				}
				
			}
		}

		final ArrayList<ArrayList<ArrayList<String>>> linesPerColPerRow = new ArrayList<>(); // arraylist of rows
		
		for (int r = 0; r < rows.size(); r++) {
			
			final ArrayList<Component> cols = rows.get(r);
			int heighest = 0;
			linesPerColPerRow.add(new ArrayList<ArrayList<String>>()); // add a row
			
			for (int c = 0; c < cols.size(); c++) {
				
				ArrayList<String> lines = wrapTextLines(cols.get(c).toString(), colWidths.get(c));
				linesPerColPerRow.get(r).add(lines);
				
				int lineHeight = lines.size() == 0 ? 1 : lines.size();
				if (lineHeight > heighest)
					heighest = lineHeight;
			}
			rowHeights.add(heighest);
		}
		
		
		
		sb.append(printLine(BorderPiece.ds, BorderPiece.dsa, BorderPiece.sa));
		for (int i = 0; i < linesPerColPerRow.size(); i++) {
			// top padding
			sb.append(printData(paddingRow, padding[0], -1));
			sb.append(printData(linesPerColPerRow.get(i), rowHeights.get(i), i));
			sb.append(printData(paddingRow, padding[2], -1));
			
			if (i != linesPerColPerRow.size() - 1)
				sb.append(printLine(BorderPiece.wds, BorderPiece.wdsa, BorderPiece.wsa));
		}
		sb.append(printLine(BorderPiece.wd, BorderPiece.wda, BorderPiece.wa));
		
		// remove last \n
		if (sb.charAt(sb.length() - 1) == '\n')
			sb.setLength(sb.length() - 1);
		
	}

	private String printLine(BorderPiece left, BorderPiece center, BorderPiece right) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < colWidths.size(); i++) {
			if (i == 0) sb.append(style.getPiece(left));
			else  sb.append(style.getPiece(center));
			for (int m = 0; m < (colWidths.get(i) + padding[1] + padding[3]); m++)
				sb.append(style.getPiece(BorderPiece.da));
		}
		sb.append(style.getPiece(right));
		if (style != BorderStyle.none)
			sb.append("\n");
		return sb.toString();
	}
	
	
	private String printData(List<ArrayList<String>> data, int rowHeight, int rowIndex) {
		final StringBuilder sb = new StringBuilder();
		for (int rh = 0; rh < rowHeight; rh++) {
			for (int i = 0; i < data.size(); i++) {
				final Alignment align = this.alignOverrides.containsKey(i + "-" + rowIndex) ?
						this.alignOverrides.get(i + "-" + rowIndex) : this.align;
				final ArrayList<String> lines = data.get(i);
				final String line = (rh >= lines.size() ? "" : lines.get(rh));
				int paddingRight = padding[1], paddingLeft = padding[3];
				int diff = colWidths.get(i) - line.length();
				switch (align) {
				case right: paddingLeft += diff; break;
				case left: paddingRight += diff; break;
				case center:
					int sidePad = diff / 2;
					paddingRight += sidePad;
					paddingLeft += sidePad;
					if (diff % 2 != 0)
						paddingRight++;
					break;
				}
				sb.append(style.getPiece(BorderPiece.ws));
				for (int m = 0; m < paddingLeft; m++) sb.append(" ");
				sb.append(line);
				for (int m = 0; m < paddingRight; m++) sb.append(" ");
			}
			sb.append(style.getPiece(BorderPiece.ws)).append("\n");
		}
		return sb.toString();
	}

	public static ArrayList<String> wrapTextLines(String text, int maxWidth) {
		final ArrayList<String> lines = new ArrayList<String>();
		for (String line : text.split("\n")) {
			if (maxWidth > 0 && line.length() > maxWidth) {
				StringBuilder newLine = new StringBuilder();
				int lastSpace = 0;
				int count = 0;
				for (int i = 0; i < line.length(); i++) {
					if (line.charAt(i) == ' ')
						lastSpace = i;
					newLine.append(line.charAt(i));
					if (count == maxWidth - 1) {
						if (lastSpace == 0)
							newLine.append("\n");
						else
							newLine.setCharAt(lastSpace, '\n');
						count = 0;
					} else {
						count++;
					}
				}
				lines.addAll(wrapTextLines(newLine.toString(), maxWidth));
			} else {
				lines.add(line);
			}
		}
		return lines;
	}
	
	public void add(Component component) {
		//  find the first empty position
		int x = 0, y = 0;
		boolean brk = false;
		for (int r = 0; r < rows.size(); r++) {
			for (int c = 0; c < rows.get(r).size(); c++)
				if (rows.get(r).get(c).toString().length() == 0) {
					x = c;
					y = r;
					brk = true;
					break;
				}
			if (brk)
				break;
		}
		add(component, x, y);
	}
	
	public void add(Component component, int col, int row) {
		while (row >= rows.size())
			rows.add(new ArrayList<Component>());
		while (col >= rows.get(row).size())
			for (int r = 0; r < rows.size(); r++)
				rows.get(r).add(new Text(""));
		
		this.rows.get(row).set(col, component);
	}
	
	public void remove(int col, int row) {
		add(new Text(""), col, row);
	}
	
	public void removeCol(int col) {
		if (rows.size() > 0 && col < rows.get(0).size())
			for (int r = 0; r < rows.size(); r++)
				rows.get(r).remove(col);
	}
	
	public void removeRow(int row) {
		if (row < rows.size())
			rows.remove(row);
	}
	
	public int getColCount() {
		return rows.size() == 0 ? 0 : rows.get(0).size();
	}

	public int getRowCount() {
		return rows.size();
	}
	
	public void setAlign(Alignment value, int col, int row) {
		this.alignOverrides.put(col + "-" + row, value);
	}
	
	public void clearAlign() {
		this.alignOverrides.clear();
		this.align = Alignment.center;
	}
	
	public int getTileMinWidth() {
		return tileMinWidth;
	}
	public void setTileMinWidth(int tileMinWidth) {
		this.tileMinWidth = tileMinWidth;
	}
	public int getTileMaxWidth() {
		return tileMaxWidth;
	}
	public void setTileMaxWidth(int tileMaxWidth) {
		this.tileMaxWidth = tileMaxWidth;
	}
	public BorderStyle getBorderStyle() {
		return style;
	}
	public void setBorderStyle(BorderStyle style) {
		this.style = style;
	}

}
