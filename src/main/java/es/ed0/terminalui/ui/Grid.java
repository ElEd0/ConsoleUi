package es.ed0.terminalui.ui;

import java.util.ArrayList;

import es.ed0.terminalui.util.StringUtils;
import es.ed0.terminalui.ui.style.Alignment;
import es.ed0.terminalui.ui.style.BorderStyle;
import es.ed0.terminalui.ui.style.BorderStyle.BorderPiece;

/**
 * Terminal ui component that presents children components in a two-dimensional grid
 */
public class Grid extends Component {

	/**
	 * A cell which contains a component inside a {@link Grid}.
	 * The cell also implements the Component class so common properties like padding or margin
	 * are available.
	 */
	public static class GridCell extends Component {
		
		private Component component;
		private String[] printedLines;
		private int minWidth = -1;
		
		public GridCell(Component component) {
			this.component = component;
		}
		
		public void printComponent() {
			StringBuilder sb = new StringBuilder();
			this.print(sb);
			this.printedLines = sb.toString().split("\n");
		}
		
		public int getWidth() {
			if (this.printedLines == null)
				return 0;
			int max = 0;
			for (String line : this.printedLines) {
				final int len = line.length();
				if (len > max) max = len;
			}
			return max;
		}
		
		public int getHeight() {
			if (this.printedLines == null)
				return 0;
			return this.printedLines.length;
		}

		@Override
		protected void print(StringBuilder sb) {
			component.setNewLine(false);
			component.print(sb);
		}
	}
	
	private final ArrayList<ArrayList<GridCell>> cells;
	int[] colWidths;
	int[] rowHeights;
	
	private BorderStyle style;

	public Grid() {
		this(BorderStyle.UNICODE, 1, 1);
	}
	
	public Grid(int cols, int rows) {
		this(BorderStyle.UNICODE, cols, rows);
	}
	
	public Grid(BorderStyle style, int cols, int rows) {
		this.style = style;
		
		this.cells = new ArrayList<ArrayList<GridCell>>(rows);
		for (int r = 0; r < rows; r++) {
			this.cells.add(new ArrayList<GridCell>(cols));
			for (int c = 0; c < cols; c++) {
				this.cells.get(r).add(null);
			}
		}
	}

	@Override
	protected void print(StringBuilder sb) {

		// CALCULATE WIDTH AND HEIGHTS FOR EACH CELL
		this.colWidths = new int[getColCount()];
		this.rowHeights = new int[getRowCount()];

		for (int r = 0; r < cells.size(); r++) {
			for (int c = 0; c < cells.get(r).size(); c++) {
				
				GridCell cell = cells.get(r).get(c);
				if (cell != null) {
					cell.printComponent();
					final int cellWidth = cell.getWidth(), cellHeight = cell.getHeight();

					//System.out.println("CELL: '" + cell.printedLines[0] + "' WIDTH: " + cellWidth);

					if (cellWidth > colWidths[c]) {
						colWidths[c] = cellWidth;
					}
					if (cellHeight > rowHeights[r]) {
						rowHeights[r] = cellHeight;
					}
				}
				
			}
		}
		
		// PRINT CELLS
		for (int r = 0; r < cells.size(); r++) {
			
			// PRINT TOP BORDER
			if (r == 0) {
				sb.append(printBorder(BorderPiece.sd, BorderPiece.asd, BorderPiece.as, BorderPiece.ad));
			} else {
				sb.append(printBorder(BorderPiece.wsd, BorderPiece.wasd, BorderPiece.was, BorderPiece.ad_in));
			}
			
			// STORE AND ALIGN ALL CELLS IN ROW
			ArrayList<GridCell> cellsInRow = new ArrayList<GridCell>();
			int rowHeight = rowHeights[r];
			for (int c = 0; c < cells.get(r).size(); c++) {
				GridCell cell = cells.get(r).get(c);
				if (cell == null) {
					cell = new GridCell(new Text(""));
					cell.printComponent();
				}
				cell.printedLines = StringUtils.align(cell.printedLines, colWidths[c], cell.getAlignment());
				cellsInRow.add(cell);
			}
			
			// PRINT EVERY LINE IN EVERY CELL OF THE ROW
			for (int rr = 0; rr < rowHeight; rr++) {
				
				for (int c = 0; c < cellsInRow.size(); c++) {
					GridCell cell = cellsInRow.get(c);
					String line = cell.getHeight() <= rr ? StringUtils.repeatString(" ", colWidths[c]) : cell.printedLines[rr];

					sb.append(style.getPiece(c == 0 ? BorderPiece.ws : BorderPiece.ws_in)).append(line);
				}
				
				sb.append(style.getPiece(BorderPiece.ws)).append("\n");
				
			}
			
		}
		sb.append(printBorder(BorderPiece.wd, BorderPiece.wad, BorderPiece.wa, BorderPiece.ad));
		
	}

	private String printBorder(BorderPiece left, BorderPiece center, BorderPiece right, BorderPiece fill) {
		final StringBuilder sb = new StringBuilder();
		sb.append(style.getPiece(left));
		for (int i = 0; i < colWidths.length; i++) {
			if (i != 0) sb.append(style.getPiece(center));

			for (int m = 0; m < colWidths[i]; m++)
				sb.append(style.getPiece(fill));
		}
		sb.append(style.getPiece(right));

		// TODO change or remove dis V
		if (style != BorderStyle.NONE)
			sb.append("\n");
		return sb.toString();
	}
	

	public void add(Component component, int col, int row) {
		while (row >= cells.size())
			cells.add(new ArrayList<GridCell>());
		while (col >= this.cells.get(row).size()) {
			this.cells.get(row).add(null);
		}
		//this.cells.get(row).ensureCapacity(col + 1);
		this.cells.get(row).set(col, new GridCell(component));
	}

	public void add(Component component) {
		//  find the first empty position
		int r = 0, c = 0;
		boolean brk = false;
		for (int rs = cells.size(); r < rs; r++) {
			for (c = 0; c < cells.get(r).size(); c++) {
				if (cells.get(r).get(c) == null) {
					brk = true;
					break;
				}
			}
			if (brk) break;
		}
		add(component, c, r);
	}

	public void remove(int col, int row) {
		add(null, col, row);
	}
	
	public void removeCol(int col) {
		if (cells.size() > 0 && col < cells.get(0).size())
			for (int r = 0; r < cells.size(); r++)
				cells.get(r).remove(col);
	}
	
	public void removeRow(int row) {
		if (row < cells.size())
			cells.remove(row);
	}
	
	public int getColCount() {
		return cells.size() == 0 ? 0 : cells.get(0).size();
	}

	public int getRowCount() {
		return this.cells.size();
	}

	public void setColCount(int cols) {
		for (ArrayList<GridCell> row : cells) {
			int curr = row.size();
			if (cols > curr) {
				row.ensureCapacity(cols);
			} else if (cols < curr) {
				while ((curr = row.size()) != cols) {
					row.remove(curr - 1);
				}
			}
		}
	}

	public void setRowCount(int rows) {
		int curr = this.cells.size();
		if (rows > curr) {
			while (rows != cells.size())
				cells.add(new ArrayList<GridCell>());
		} else if (rows < curr) {
			while ((curr = this.cells.size()) != rows) {
				this.cells.remove(curr - 1);
			}
		}
	}

	@Override
	public void setAlignment(Alignment alignment) {
		super.setAlignment(alignment);
		for (ArrayList<GridCell> cs : cells) {
			if (cs != null) {
				for (GridCell cell : cs) {
					if (cell != null) cell.setAlignment(alignment);
				}
			}
		}
	}

	public void setAlignment(Alignment alignment, int col, int row) {
		if (row > cells.size() || col > cells.get(row).size()) return;
		this.cells.get(row).get(col).setAlignment(alignment);
	}
}
