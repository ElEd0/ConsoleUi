/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 */
public class Panel extends Component {
	
	
	private int[] padding;
	private int longestWidth = 0;
	private int maxWidth = 60;
	
	private String header, content;
	private Alignment headerAlign = Alignment.center, contentAlign = Alignment.left;

	public Panel(Component component) {
		this(null, component.toString());
	}

	public Panel(String header, Component component) {
		this(header, component.toString());
	}
	
	public Panel(String content) {
		this(null, content);
	}
	
	public Panel(String header, String content) {
		this.header = header;
		this.content = content;
		padding = new int[] {1, 1, 1, 1};
	}
	
	public void add(Component component) {
		add(component.toString());
	}
	
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
		final Separator sep = new Separator(longestWidth + padding[1] + padding[3], "-");
		sep.setNewLine(false);
		
		sb.append("+").append(sep).append("+\n");
		
		if (headerLines.size() > 0) {
			sb.append(printData(headerLines, headerAlign));
			sb.append("+").append(sep).append("+\n");
		}
		// top padding
		for (int t = 0; t < padding[0]; t++)
			sb.append(printData(Arrays.asList(new String[] {""}), Alignment.right));
		
		sb.append(printData(contentLines, contentAlign));		

		// bottom padding
		for (int t = 0; t < padding[2]; t++)
			sb.append(printData(Arrays.asList(new String[] {""}), Alignment.right));
		
		sb.append("+").append(sep).append("+\n");
		
	}
	
	private ArrayList<String> createLines(String text) {
		final ArrayList<String> result = new ArrayList<String>();
		if (text != null)
			for (String l : text.split("\n")) {
				l = l.trim();
				// if line is longer than maxwidth crop it into pieces of max width size, from spaces ' ' if possible
				if (l.length() > maxWidth) {
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
			res.append("|");
			for (int l = 0; l < paddingLeft; l++) res.append(" ");
			res.append(line);
			for (int r = 0; r < paddingRight; r++) res.append(" ");
			res.append("|\n");
		}
		return res.toString();
	}
	
	public void setPadding(int padding) {
		setPadding(padding, padding, padding, padding);
	}
	
	public void setPadding(int top, int right, int bottom, int left) {
		padding[0] = top;
		padding[1] = right;
		padding[2] = bottom;
		padding[3] = left;
	}
	
	public void setPaddingTop(int padding) {
		this.padding[0] = padding;
	}
	public void setPaddingRight(int padding) {
		this.padding[1] = padding;
	}
	public void setPaddingBottom(int padding) {
		this.padding[2] = padding;
	}
	public void setPaddingLeft(int padding) {
		this.padding[3] = padding;
	}
	public int getMaxWidth() {
		return maxWidth;
	}
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}
	public void setHeaderAlign(Alignment headerAlign) {
		this.headerAlign = headerAlign;
	}

	public void setContentAlign(Alignment contentAlign) {
		this.contentAlign = contentAlign;
	}


}
