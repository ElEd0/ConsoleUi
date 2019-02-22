/**
 * Created by Ed0 in 16 feb. 2019
 */
package es.ed0.consoleui.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.ed0.consoleui.ui.BorderStyle.BorderPiece;

public class EntryTable<T> extends Component {

	public interface TablePopulator<T> {
		public ArrayList<String> getViewForRow(int i, T entry);
	}
	
	// TODO styles
	
	private String[] cols;
	private ArrayList<Integer> colWidths, rowHeights;
	private Alignment colAlign;
	private BorderStyle style;
	
	private ArrayList<ArrayList<String>> rows;
	private ArrayList<T> entries;
	private TablePopulator<T> populator;
	
	private int colMargin = 1;
	private boolean enumerate = false;
	
	public EntryTable(List<T> entries, String... cols) {
		this(BorderStyle.sql, entries, cols);
	}
	
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
			final ArrayList<String> views = populator.getViewForRow(i, entries.get(i));
			if (enumerate)
				views.add(0, (i + 1) + "");
			rows.add(views);
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
		
		sb.append(printTopLine());
		sb.append(printData(Arrays.asList(cols)));
		for (int i = 0; i < rows.size(); i++) {
			sb.append(printMiddleLine());
			sb.append(printData(rows.get(i)));
		}
		sb.append(printBottomLine());
		
	}
	
	private String printTopLine() {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < colWidths.size(); i++) {
			if (i == 0) sb.append(style.getPiece(BorderPiece.ds));
			else  sb.append(style.getPiece(BorderPiece.dsa));
			for (int m = 0; m < (colWidths.get(i) + (colMargin * 2)); m++)
				sb.append(style.getPiece(BorderPiece.da));
		}
		sb.append(style.getPiece(BorderPiece.sa)).append("\n");
		return sb.toString();
	}
	
	private String printMiddleLine() {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < colWidths.size(); i++) {
			if (i == 0) sb.append(style.getPiece(BorderPiece.wds));
			else  sb.append(style.getPiece(BorderPiece.wdsa));
			for (int m = 0; m < (colWidths.get(i) + (colMargin * 2)); m++)
				sb.append(style.getPiece(BorderPiece.da));
		}
		sb.append(style.getPiece(BorderPiece.wsa)).append("\n");
		return sb.toString();
	}
	
	private String printBottomLine() {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < colWidths.size(); i++) {
			if (i == 0) sb.append(style.getPiece(BorderPiece.wd));
			else  sb.append(style.getPiece(BorderPiece.wda));
			for (int m = 0; m < (colWidths.get(i) + (colMargin * 2)); m++)
				sb.append(style.getPiece(BorderPiece.da));
		}
		sb.append(style.getPiece(BorderPiece.wa)).append("\n");
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
