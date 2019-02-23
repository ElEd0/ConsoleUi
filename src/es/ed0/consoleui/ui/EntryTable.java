/**
 * Created by Ed0 in 16 feb. 2019
 */
package es.ed0.consoleui.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.ed0.consoleui.ui.BorderStyle.BorderPiece;
/**
 * Creates a console ui Table in which each row is represented by a object of type <code>T</code>. 
 * @param <T> An object that represents a row entry
 */
public class EntryTable<T> extends Component {

	/**
	 * Interface required to inflate the EntryTable with data. 
	 * The user must specify the data for each column for every entry
	 * @param <T> An object that represents a row entry
	 */
	public interface TablePopulator<T> {
		/**
		 * Retrieve the data to insert into the table for the given entry 
		 * <code>entry</code> with index <code>index</code>
		 * @param index entry index inside the entry list
		 * @param entry row entry
		 * @return ArrarList containing one string for every column
		 */
		public ArrayList<String> getViewForRow(int index, T entry);
	}
	
	private String[] cols;
	private ArrayList<Integer> colWidths, rowHeights;
	private Alignment colAlign;
	private BorderStyle style;
	
	private ArrayList<ArrayList<String>> rows;
	private ArrayList<T> entries;
	private TablePopulator<T> populator;
	
	private int colMargin = 1;
	private boolean enumerate = false;
	
	/**
	 * Create a new EntryTable with the given columns and entries (rows). 
	 * A {@link TablePopulator} is required to handle the table creation, you can do this via 
	 * {@link #setTablePopulator(TablePopulator)}
	 * @param entries Entry per row
	 * @param cols table columns
	 */
	public EntryTable(List<T> entries, String... cols) {
		this(BorderStyle.sql, entries, cols);
	}

	/**
	 * Create a new EntryTable with the given columns, entries (rows) and Border style. 
	 * A {@link TablePopulator} is required to handle the table creation, you can do this via 
	 * {@link #setTablePopulator(TablePopulator)}
	 * @param style Border style for the table
	 * @param entries Entry per row
	 * @param cols table columns
	 */
	public EntryTable(BorderStyle style, List<T> entries, String... cols) {
		this.entries = new ArrayList<T>(entries);
		this.cols = cols;
		this.style = style;
		this.colAlign = Alignment.center;
	}
	
	
	protected void print(StringBuilder sb) {
		if (populator == null)
			return;
		
		// get rows from populator
		rows = new ArrayList<>();
		rowHeights = new ArrayList<Integer>();
		for (int i = 0; i < entries.size(); i++) {
			List<String> views = populator.getViewForRow(i, entries.get(i));
			if (enumerate)
				views.add(0, (i + 1) + "");
			
			// correct size
			if (views.size() < cols.length)
				for (int v = 0; v < (cols.length - views.size()); v++)
					views.add("");
			else if (views.size() > cols.length)
				views = views.subList(0, cols.length);
				
			rows.add(new ArrayList<>(views));
			int heighest = 1;
			for (String data : views) {
				int lineHeight = (data.length() - data.replaceAll("\n", "").length()) + 1;
				if (lineHeight > heighest)
					heighest = lineHeight;
			}
			rowHeights.add(heighest);
		}

		// calculate max column widths
		colWidths = new ArrayList<Integer>(cols.length);
		for (String s : cols) 
			colWidths.add(s.length());
		
		for (int i = 0; i < colWidths.size(); i++) {
			for (int r = 0; r < rows.size(); r++) {
				int valueLength = rows.get(r).get(i).length();
				if (valueLength > colWidths.get(i)) {
					colWidths.remove(i);
					colWidths.add(i, valueLength);
				}
			}
		}
		
		sb.append(printLine(BorderPiece.ds, BorderPiece.dsa, BorderPiece.sa));
		sb.append(printData(Arrays.asList(cols)));
		for (int i = 0; i < rows.size(); i++) {
			sb.append(printLine(BorderPiece.wds, BorderPiece.wdsa, BorderPiece.wsa));
			sb.append(printData(rows.get(i)));
		}
		sb.append(printLine(BorderPiece.wd, BorderPiece.wda, BorderPiece.wa));
		
	}
	
	private String printLine(BorderPiece left, BorderPiece center, BorderPiece right) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < colWidths.size(); i++) {
			if (i == 0) sb.append(style.getPiece(left));
			else  sb.append(style.getPiece(center));
			for (int m = 0; m < (colWidths.get(i) + (colMargin * 2)); m++)
				sb.append(style.getPiece(BorderPiece.da));
		}
		sb.append(style.getPiece(right)).append("\n");
		return sb.toString();
	}
	
	private String printData(List<String> data) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.size(); i++) {
			int paddingRight = colMargin, paddingLeft = colMargin;
			int diff = colWidths.get(i) - data.get(i).length();
			switch (this.colAlign) {
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
			sb.append(data.get(i));
			for (int m = 0; m < paddingRight; m++) sb.append(" ");
		}
		sb.append(style.getPiece(BorderPiece.ws)).append("\n");
		return sb.toString();
	}
	
	public void setEnumerate(boolean enumerate) {
		if (this.enumerate == enumerate)
			return;
		this.enumerate = enumerate;
		if (enumerate) {
			final String[] newCols = new String[getCols().length + 1];
			newCols[0] = "nº";
			for (int c = 0; c < cols.length; c++)
				newCols[c + 1] = cols[c];
			setCols(newCols);			
		} else {
			final String[] newCols = new String[getCols().length - 1];
			for (int c = 1; c < cols.length; c++)
				newCols[c - 1] = cols[c];
			setCols(newCols);
		}
	}
	
	public void setColAlign(Alignment align) { this.colAlign = align; }

	public String[] getCols() { return cols; }

	public void setCols(String[] cols) { this.cols = cols; }

	public ArrayList<T> getEntries() { return entries; }

	public void setEntries(List<T> entries) { this.entries = new ArrayList<T>(entries); }

	public TablePopulator<T> getTablePopulator() { return populator; }

	public void setTablePopulator(TablePopulator<T> populator) { this.populator = populator; }

	public int getColMargin() { return colMargin; }

	public void setColMargin(int colMargin) { this.colMargin = colMargin; }
	
	
}
