/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;


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
		super(style, 1, (header == null ? 1 : 2));
		if (header != null) {
			this.header = new Text(header);
			super.add(this.header);
			this.setHeaderAlign(Alignment.center);
		}
		this.content = component;
		this.content.setTabulation(0);
		super.add(content);
		this.setContentAlign(Alignment.left);
	}
	
	
	/**
	 * Set the alignment for the header text
	 * @param headerAlign
	 */
	public void setHeaderAlign(Alignment headerAlign) {
		super.setAlign(headerAlign, 0, 0);
	}
	/**
	 * Set the alignment for the body content
	 * @param contentAlign
	 */
	public void setContentAlign(Alignment contentAlign) {
		super.setAlign(contentAlign);
	}


}
