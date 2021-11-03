/**
 * Created by Ed0 in 23 feb. 2019
 */
package es.ed0.terminalui.ui;

import es.ed0.terminalui.util.StringUtils;
import es.ed0.terminalui.ui.style.Border;
import es.ed0.terminalui.ui.style.BorderStyle.BorderPiece;

/**
 * Console ui that prints simple text. 
 * The main use of this object is to wrap the string class with a common terminal component 
 * or to create simple panels using borders with text
 */
public class Text extends Component {

	private String text;
	
	public Text(Object obj) {
		this(obj.toString());
	}
	
	/**
	 * Create a new Text component with the text in the given string.
	 * A null String will result in the text "null"
	 * @param text
	 */
	public Text(String text) {
		setText(text);
	}
	
	@Override
	protected void print(StringBuilder sb) {
		String[] lines = new String[] { this.text };
		int maxWidth = this.getMaxWidth();
		if (maxWidth > -1) {
			lines = StringUtils.align(StringUtils.wordWrap(this.text, maxWidth), maxWidth, getAlignment());
		} else {
			maxWidth = this.text.length();
		}
		
		final Border b = getBorder();
		final int borderWidth = maxWidth + padding[3] + padding[1];
		
		final StringBuilder verticalPad = new StringBuilder();
		if (b != null) verticalPad.append(b.getStyle().getPiece(BorderPiece.ws));
		for (int w = 0; w < borderWidth; w++)
			verticalPad.append(this.getPaddingChar());
		if (b != null) verticalPad.append(b.getStyle().getPiece(BorderPiece.ws));
		if (verticalPad.length() != 0) verticalPad.append("\n");
		
		// top border
		if (b != null) b.drawBorderTop(sb, borderWidth);

		// top padding
		for (int p = 0; p < this.padding[0]; p++) {
			sb.append(verticalPad);
		}
		
		// print text lines
		for (int h = 0; h < lines.length; h++) {
			//left padding
			if (b != null) sb.append(b.getStyle().getPiece(BorderPiece.ws));
			for (int p = 0; p < this.padding[3]; p++)
				sb.append(this.getPaddingChar());
			
			//content
			sb.append(lines[h]);
				
			//right padding
			for (int p = 0; p < this.padding[1]; p++)
				sb.append(this.getPaddingChar());
			if (b != null) sb.append(b.getStyle().getPiece(BorderPiece.ws));
			
			sb.append("\n");
		}
		// bottom padding
		for (int p = 0; p < this.padding[2]; p++) {
			sb.append(verticalPad);
		}
		// bottom border
		if (b != null) b.drawBorderBottom(sb, borderWidth);
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text == null ? "null" : text;
	}

	public void append(String text) {
		this.text += text;
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
