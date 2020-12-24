/**
 * Created by Ed0 in 23 feb. 2019
 */
package es.ed0.consoleui.ui;

import es.ed0.consoleui.StringUtils;
import es.ed0.consoleui.ui.style.Alignment;
import es.ed0.consoleui.ui.style.Border;
import es.ed0.consoleui.ui.style.BorderStyle.BorderPiece;

/**
 * Console ui that prints simple text. 
 * The main use of this object is to wrap the string class with a common console component
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
		super(Alignment.left, new int[] { 0, 0, 0, 0 });
		setText(text);
	}
	
	
	@Override
	protected void print(StringBuilder sb) {
		String[] lines = new String[] { this.text };
		int maxWidth = this.getMaxWidth();
		if (maxWidth > -1) {
			lines = StringUtils.align(StringUtils.wordWrap(this.text, maxWidth), maxWidth, 0);
		} else {
			maxWidth = this.text.length();
		}
		
		final Border b = getBorder();
		final int borderWidth = maxWidth + padding[3] + padding[1];
		
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
		
		// print text lines
		for (int h = 0; h < lines.length; h++) {
			//left padding
			sb.append(b.getStyle().getPiece(BorderPiece.ws));
			for (int p = 0; p < this.padding[3]; p++)
				sb.append(this.getPaddingChar());
			
			//content
			sb.append(lines[h]);
				
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
		this.text = text == null ? "null" : text;
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
