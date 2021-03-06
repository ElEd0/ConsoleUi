/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;

import es.ed0.consoleui.ui.style.Alignment;
import es.ed0.consoleui.ui.style.BorderStyle;
import es.ed0.consoleui.ui.style.BorderStyle.BorderPiece;

/**
 * Console ui component that prints a separator line
 */
public class Separator extends Component {
	
	private int width;
	private String style;
	
	/**
	 * Create a new separator with the given width. The character "=" will be used
	 * @param width
	 */
	public Separator(int width) {
		this(width, "=");
	}
	public Separator(int width, BorderStyle style) {
		this(width, style.getPiece(BorderPiece.da));
	}
	/**
	 * Create a new Separator using given width and style
	 * @param width
	 * @param style
	 */
	public Separator(int width, String style) {
		super(Alignment.left, new int[] {0, 0, 0, 0});
		this.width = width;
		this.style = style;
	}
	
	@Override
	protected void print(StringBuilder sb) {
		for (int i = 0; i < width; i++)
			sb.append(style);
	}
	
}
