/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.terminalui.ui;

import es.ed0.terminalui.ui.style.Alignment;
import es.ed0.terminalui.ui.style.BorderStyle;

/**
 * Console ui element that prints a panel and an optional header
 */
public class Panel extends Grid {
	
	private Text header;
	private Component content;

	/**
	 * Create a new Panel with the given component as body
	 * @param component body
	 */
	public Panel(Component component) {
		this(BorderStyle.SQL, null, component);
	}
	/**
	 * Create a new Panel with the given header and body
	 * @param header header text
	 * @param component body
	 */
	public Panel(String header, Component component) {
		this(BorderStyle.SQL, header, component);
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
		super(style, 1, (header == null ? 1 : 2));
		if (header != null) {
			this.header = new Text(header);
			super.add(this.header, 0, 0);
			this.setHeaderAlignment(Alignment.center);
		}
		this.content = component;
		this.content.setLeftMargin(0);
		super.add(content, 0, 1);
		this.setContentAlignment(Alignment.left);
	}
	
	
	/**
	 * Set the alignment for the header text
	 * @param headerAlign
	 */
	public void setHeaderAlignment(Alignment headerAlign) {
		super.setAlignment(headerAlign, 0, 0);
	}
	/**
	 * Set the alignment for the body content
	 * @param contentAlign
	 */
	public void setContentAlignment(Alignment contentAlign) {
		super.setAlignment(contentAlign);
	}


}
