/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;

/**
 * 
 */
public class Separator extends Component {
	
	private boolean newLine = true;
	private int width;
	private String style;
	
	public Separator(int width) {
		this(width, "=");
	}
	
	public Separator(int width, String style) {
		this.width = width;
		this.style = style;
	}
	
	@Override
	protected void print(StringBuilder sb) {
		for (int i = 0; i < width; i++)
			sb.append(style);
		if (getNewLine())
			sb.append("\n");
	}
	
	public void setNewLine(boolean newLine) { this.newLine = newLine; }
	public boolean getNewLine() { return newLine; }
}
