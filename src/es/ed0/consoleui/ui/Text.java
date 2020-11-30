/**
 * Created by Ed0 in 23 feb. 2019
 */
package es.ed0.consoleui.ui;

import es.ed0.consoleui.ui.style.Alignment;
import es.ed0.consoleui.ui.style.Border;
import es.ed0.consoleui.ui.style.BorderStyle.BorderPiece;

/**
 * Console ui that prints simple text. The only use of this object is to wrap the string class and 
 * have a common console component
 */
public class Text extends Component {

	private String text;
	
	public Text(Object obj) {
		this(obj.toString(), false);
	}
	/**
	 * Create a new Text component with the text in the given string.
	 * A null String will result in the text "null"
	 * @param text
	 */
	public Text(String text) {
		this(text, false);
	}
	/**
	 * Create a new Text component with the text in the given string.
	 * A null String will result in the text "null"
	 * @param text text
	 * @param newLine append a line break at the end (\n). This can be changed in {@link Component#setNewLine(boolean)}
	 */
	public Text(String text, boolean newLine) {
		super(Alignment.left, new int[] {0, 0, 0, 0});
		this.text = text == null ? "null" : text;
		setNewLine(newLine);
	}
	
	@Override
	protected void print(StringBuilder sb) {
		final Border b = getBorder();
		final int borderWidth = this.text.length() + padding[3] + padding[1];
		
		final StringBuilder verticalPad = new StringBuilder();
		verticalPad.append(b.getStyle().getPiece(BorderPiece.ws));
		for (int w = 0; w < borderWidth; w++)
			verticalPad.append(this.getPaddingChar());
		verticalPad.append(b.getStyle().getPiece(BorderPiece.ws)).append("\n");
		
		// top border
		b.drawBorderTop(sb, borderWidth);

		// top padding
		for (int p = 0; p < this.padding[0]; p++) {
			sb.append(verticalPad);
		}
		// if text can handle various heights in the future...
		for (int h = 0; h < 1; h++) {
			//left padding
			sb.append(b.getStyle().getPiece(BorderPiece.ws));
			for (int p = 0; p < this.padding[3]; p++)
				sb.append(this.getPaddingChar());
			
			//content
			sb.append(this.text);
				
			//right padding
			for (int p = 0; p < this.padding[1]; p++)
				sb.append(this.getPaddingChar());
			sb.append(b.getStyle().getPiece(BorderPiece.ws)).append("\n");
		
		}
		// bottom padding
		for (int p = 0; p < this.padding[2]; p++) {
			sb.append(verticalPad);
		}
		// bottom border
		b.drawBorderBottom(sb, borderWidth);
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public void append(String text) {
		this.text = new StringBuilder(this.text).append(text).toString();
	}
	
	public void append(Component comp) {
		append(comp.toString());
	}
	
	public void appendln(String text) {
		append(text + "\n");
	}

	public void appendln() {
		append("\n");
	}
}
