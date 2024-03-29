/**
 * Created by Ed0 in 16 feb. 2019
 */
package es.ed0.terminalui.ui;

import java.util.ArrayList;
import java.util.List;

import es.ed0.terminalui.ui.style.BorderStyle;

/**
 * Creates a terminal ui Table in which each row represents an object of type <code>T</code>. 
 * @param <T> An object that represents a row entry
 */
public class EntryTable<T> extends Grid {

	/**
	 * Interface required to inflate the EntryTable with data. 
	 * The user must specify the data for each column for every entry
	 * @param <T> An object that represents a row entry
	 */
	public interface TablePopulator<T> {
		/**
		 * Retrieve the data to insert into the table for the given entry 
		 * <code>entry</code> with index <code>index</code>
		 * @param index entry index in the list
		 * @param entry row entry
		 * @return ArrayList containing one component for every column
		 */
		ArrayList<Component> getViewForRow(int index, T entry);
	}
	
	private String[] cols;
	private ArrayList<T> entries;
	private TablePopulator<T> populator;
	
	private boolean enumerate = false;
	private String enumerationLabel = null;
	
	/**
	 * Create a new EntryTable with the given columns and entries (rows). 
	 * A {@link TablePopulator} is required to handle the table creation, you can do this via 
	 * {@link #setTablePopulator(TablePopulator)}
	 * @param entries Entry per row
	 * @param cols table columns
	 */
	public EntryTable(List<T> entries, String... cols) {
		this(BorderStyle.SQL, entries, cols);
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
		super(style, cols.length, entries.size() + 1);
		this.entries = new ArrayList<T>(entries);
		this.setCols(cols);
	}
	
	
	protected void print(StringBuilder sb) {
		if (populator == null)
			return;
		// add column header
		for (int c = 0; c < cols.length; c++)
			super.add(new Text(cols[c]), c, 0);
		
		// populate table
		for (int i = 0; i < entries.size(); i++) {
			List<Component> views = populator.getViewForRow(i, entries.get(i));
			if (enumerate)
				views.add(0, new Text(i + 1));
			for (int v = 0; v < cols.length; v++)
				super.add(views.get(v), v, i + 1);
			
		}
		
		super.print(sb);
	}
	
	public void removeEntry(int index) {
		if (index < getEntryCount())
			this.entries.remove(index);
	}

	public int getEntryCount() {
		return this.entries.size();
	}
	
	public void setEnumerate(boolean enumerate) {
		if (this.enumerate == enumerate)
			return;
		this.enumerate = enumerate;
		if (enumerate) {
			final String[] newCols = new String[cols.length + 1];
			newCols[0] = enumerationLabel == null ? "N#" : enumerationLabel;
			for (int c = 0; c < cols.length; c++)
				newCols[c + 1] = cols[c];
			setCols(newCols);
		} else {
			final String[] newCols = new String[cols.length - 1];
			for (int c = 1; c < cols.length; c++)
				newCols[c - 1] = cols[c];
			setCols(newCols);
			super.removeCol(cols.length);
		}
	}

	public void setEnumerationLabel(String enumerationLabel) {
		this.enumerationLabel = enumerationLabel;
		if (enumerate) {
			cols[0] = enumerationLabel == null ? "N#" : enumerationLabel;
		}
	}

	public String[] getCols() { return cols; }

	public void setCols(String[] cols) {
		this.cols = cols;
	}

	public ArrayList<T> getEntries() { return entries; }

	public void setEntries(List<T> entries) { this.entries = new ArrayList<T>(entries); }

	public TablePopulator<T> getTablePopulator() { return populator; }

	public void setTablePopulator(TablePopulator<T> populator) { this.populator = populator; }

	
}
