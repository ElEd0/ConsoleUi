/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.terminalui.ui;

import es.ed0.terminalui.ui.style.Alignment;
import es.ed0.terminalui.ui.style.Border;

/**
 * Abstract class that all terminal ui elements must extend. 
 * Handles basic shared functionality like tabulation, padding, borders, etc
 */
public abstract class Component {
	
	public String toString() {
		final StringBuilder content = new StringBuilder();
		this.print(content);
		if (leftMargin != 0) {
			final StringBuilder tabB = new StringBuilder();
			for (int t = 0; t < leftMargin; t++) tabB.append(" ");
			final String tab = tabB.toString();
			final String aux = content.toString();
			content.setLength(0);
			content.append(tab);
			content.append(aux.replaceAll("\n", "\n" + tab));
		}
		if (isNewLine())
			content.append("\n");
		return content.toString();
	}
	
	private boolean newLine = true;
	private int leftMargin = 0;
	protected int[] padding = { 0, 0, 0, 0 };
	protected char paddingChar = ' ';
	protected Alignment align = Alignment.left;
	protected Border border;
	protected int maxWidth = -1;
	
	public int getLeftMargin() {
		return leftMargin;
	}

	/**
	 * Sets the left spacing for this component, this is, how many space characters will be printed before the component
	 * @param leftMargin
	 */
	public void setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
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
		this.setPadding(new int[] {top, right, bottom, left});
	}
	/**
	 * Set padding independently for all 4 sides (top, right, bottom and left)
	 * @param pad
	 */
	public void setPadding(int[] pad) {
		if (pad.length >= 4)
			this.padding = pad;
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
	 * Set padding from right and left
	 * @param padding
	 */
	public void setPaddingHorizontal(int padding) {
		this.setPaddingLeft(padding);
		this.setPaddingRight(padding);
	}

	/**
	 * Set padding from top and bottom
	 * @param padding
	 */
	public void setPaddingVertical(int padding) {
		this.setPaddingTop(padding);
		this.setPaddingBottom(padding);
	}

	/**
	 * Gets the current padding, in order: top, right, bottom, left
	 * @return
	 */
	public int[] getPadding() {
		return this.padding;
	}
	
	/**
	 * Returns current character used as padding
	 * @return String containing character
	 */
	public char getPaddingChar() {
		return paddingChar;
	}
	/**
	 * Sets the character used as padding
	 * @param paddingChar
	 */
	public void setPaddingChar(char paddingChar) {
		this.paddingChar = paddingChar;
	}
	/**
	 * Returns the current alignment
	 * @return
	 */
	public Alignment getAlignment() {
		return align;
	}
	/**
	 * Sets the alignment of the content inside its frame
	 * @param align
	 */
	public void setAlignment(Alignment align) {
		this.align = align;
	}
	
	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	/**
	 * Called when a component needs to be printed. The extending class must define the body and append its 
	 * contents as string lines in the given string builder
	 * @param sb StringBuilder to append the body to
	 */
	protected abstract void print(StringBuilder sb);
	
}
