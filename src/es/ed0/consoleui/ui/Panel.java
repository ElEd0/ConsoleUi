/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.ed0.consoleui.ui.BorderStyle.BorderPiece;

/**
 * Console ui element that prints a panel and an optional header
 */
public class Panel extends Component {
	
	
	private int[] padding;
	private int longestWidth = 0;
	private int maxWidth = 60;
	
	private String header, content;
	private Alignment headerAlign = Alignment.center, contentAlign = Alignment.left;
	private BorderStyle style;

	/**
	 * Create a new Panel with the given component as body
	 * @param component body
	 */
	public Panel(Component component) {
		this(BorderStyle.sql, null, component);
	}
	/**
	 * Create a new Panel with the given header and body
	 * @param header header text
	 * @param component body
	 */
	public Panel(String header, Component component) {
		this(BorderStyle.sql, header, component);
	}

	/**
	 * Create a new Panel with the given component as body
	 * @param component body
	 */
	public Panel(BorderStyle style, Component component) {
		this(style, null, component);
	}
	/**
	 * Create a new Panel with the given header and body
	 * @param header header text
	 * @param component body
	 */
	public Panel(BorderStyle style, String header, Component component) {
		this.style = style;
		this.header = header;
		this.content = component.toString();
		padding = new int[] {1, 1, 1, 1};
	}
	/**
	 * Add a component to this panels body
	 * @param component
	 */
	public void add(Component component) {
		add(component.toString());
	}

	/**
	 * Add a text to this panels body
	 * @param component
	 */
	public void add(String text) {
		final StringBuilder s = new StringBuilder(content);
		if (!content.endsWith("\n"))
			s.append("\n");
		s.append(text);
		content = s.toString();
	}
	
	protected void print(StringBuilder sb) {
		final ArrayList<String> headerLines = createLines(header),
				contentLines = createLines(content);
		
		// max line width + padding right + padding left
		final Separator sep = new Separator(longestWidth + padding[1] + padding[3], style.getPiece(BorderPiece.da));
		sep.setNewLine(false);
		
		sb.append(style.getPiece(BorderPiece.ds)).append(sep).append(style.getPiece(BorderPiece.sa)).append("\n");
		
		if (headerLines.size() > 0) {
			sb.append(printData(headerLines, headerAlign));
			sb.append(style.getPiece(BorderPiece.wds)).append(sep).append(style.getPiece(BorderPiece.wsa)).append("\n");
		}
		// top padding
		for (int t = 0; t < padding[0]; t++)
			sb.append(printData(Arrays.asList(new String[] {""}), Alignment.right));
		
		sb.append(printData(contentLines, contentAlign));		

		// bottom padding
		for (int t = 0; t < padding[2]; t++)
			sb.append(printData(Arrays.asList(new String[] {""}), Alignment.right));

		sb.append(style.getPiece(BorderPiece.wd)).append(sep).append(style.getPiece(BorderPiece.wa)).append("\n");
		
	}
	
	private ArrayList<String> createLines(String text) {
		final ArrayList<String> result = new ArrayList<String>();
		if (text != null)
			for (String l : text.split("\n")) {
				l = l.trim();
				// if line is longer than maxwidth crop it into pieces of max width size, from spaces ' ' if possible
				if (maxWidth > 0 && l.length() > maxWidth) {
					StringBuilder newLine = new StringBuilder();
					int lastSpace = 0;
					int count = 0;
					for (int i = 0; i < l.length(); i++) {
						if (l.charAt(i) == ' ')
							lastSpace = i;
						newLine.append(l.charAt(i));
						if (count == maxWidth - 1) {
							if (lastSpace == 0)
								newLine.append("\n");
							else
								newLine.setCharAt(lastSpace, '\n');
							count = 0;
						}
						count++;
					}
					result.addAll(createLines(newLine.toString()));
				} else {
					result.add(l);
					if (l.length() > longestWidth)
						longestWidth = l.length();
				}
			}		
		return result;
	}
	
	private String printData(List<String> data, Alignment align) {
		final StringBuilder res = new StringBuilder();
		for (String line : data) {
			int paddingRight = padding[1], paddingLeft = padding[3];
			int diff = longestWidth - line.length();
			switch (align) {
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
			res.append(style.getPiece(BorderPiece.ws));
			for (int l = 0; l < paddingLeft; l++) res.append(" ");
			res.append(line);
			for (int r = 0; r < paddingRight; r++) res.append(" ");
			res.append(style.getPiece(BorderPiece.ws)).append("\n");
		}
		return res.toString();
	}
	
	/**
	 * Set padding for all 4 sides (top, right, bottom and left)
	 * @param padding
	 */
	public void setPadding(int padding) {
		setPadding(padding, padding, padding, padding);
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
	public int getMaxWidth() {
		return maxWidth;
	}
	/**
	 * Set max width for this panel, borders and padding are not included. 
	 * Text will be wrapped to match this max width. Set to -1 for a dynamic width
	 * @param maxWidth
	 */
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}
	/**
	 * Set the alignment for the header text
	 * @param headerAlign
	 */
	public void setHeaderAlign(Alignment headerAlign) {
		this.headerAlign = headerAlign;
	}
	/**
	 * Set the alignment for the body content
	 * @param contentAlign
	 */
	public void setContentAlign(Alignment contentAlign) {
		this.contentAlign = contentAlign;
	}


}
