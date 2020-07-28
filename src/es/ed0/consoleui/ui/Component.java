/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;

import es.ed0.consoleui.ui.style.Alignment;

/**
 * Abstract class that all console ui elements must extend. 
 * Handles basic shared functionality like tabulation
 */
public abstract class Component {
	
	public String toString() {
		final StringBuilder content = new StringBuilder();
		print(content);
		if (tabulation != 0) {
			final StringBuilder tab = new StringBuilder();
			for (int t = 0; t < tabulation; t++) tab.append("\t");
			final String aux = content.toString();
			content.setLength(0);
			content.append(tab.toString());
			content.append(aux.replaceAll("\n", "\n" + tab.toString()));
		}
		if (isNewLine())
			content.append("\n");
		return content.toString();
	}
	
	private int tabulation = 0;
	private boolean newLine = true;
	protected int[] padding;
	protected Alignment align;
	
	/**
	 * Sets the tabulation for this component, this is, how many \t will be printed before the component
	 * @param tabs
	 */
	public void setTabulation(int tabs) {
		this.tabulation = tabs;
	}
	/**
	 * True if this component will append a line jump at its end
	 * @return
	 */
	public boolean isNewLine() {
		return newLine;
	}

	/**
	 * Set whether the component will jump line after being printed
	 * @param newLine jump line
	 */
	public void setNewLine(boolean newLine) {
		this.newLine = newLine;
	}

	public void setPadding(int padding) {
		this.setPadding(padding, padding, padding, padding);
	}
	/**
	 * Set padding independently for all 4 sides (top, right, bottom and left)
	 * @param top
	 * @param right
	 * @param bottom
	 * @param left
	 */
	public void setPadding(int top, int right, int bottom, int left) {
		padding[0] = top;
		padding[1] = right;
		padding[2] = bottom;
		padding[3] = left;
	}
	/**
	 * Set padding from top
	 * @param padding
	 */
	public void setPaddingTop(int padding) {
		this.padding[0] = padding;
	}
	/**
	 * Set padding from right
	 * @param padding
	 */
	public void setPaddingRight(int padding) {
		this.padding[1] = padding;
	}
	/**
	 * Set padding from bottom
	 * @param padding
	 */
	public void setPaddingBottom(int padding) {
		this.padding[2] = padding;
	}
	/**
	 * Set padding from left
	 * @param padding
	 */
	public void setPaddingLeft(int padding) {
		this.padding[3] = padding;
	}
	/**
	 * Gets the current padding, in order: top, right, bottom, left
	 * @return
	 */
	public int[] getPadding() {
		return this.padding;
	}

	public Alignment getAlign() {
		return align;
	}
	/**
	 * Sets the alignment of the bar inside the frame
	 * @param align
	 */
	public void setAlign(Alignment align) {
		this.align = align;
	}
	
	public int getWidth() {
		final String toStr = toString();
		final int index = toStr.indexOf('\n');
		return index == -1 ? toStr.length() : index;
	}

	/**
	 * Called when a component needs to be printed. The extending class must define the body and append its 
	 * contents as string lines in the given string builder
	 * @param sb StringBuilder to append the body to
	 */
	protected abstract void print(StringBuilder sb);
	
}
