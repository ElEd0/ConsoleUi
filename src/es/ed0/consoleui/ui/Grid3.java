package es.ed0.consoleui.ui;

import es.ed0.consoleui.ui.style.Alignment;
import es.ed0.consoleui.ui.style.BorderStyle;

public class Grid3 extends Component {

	
	public static class GridCell {
		
		public Component component;
		public String[] printedLines;
		private int tileMinWidth = 0;
		private int tileMaxWidth = 0;
		
		public GridCell(Component component) {
			this.component = component;
		}
		
		public void printComponent() {
			StringBuilder sb = new StringBuilder();
			component.print(sb);
			this.printedLines = sb.toString().split("\n"); 
		}
		
		
	}
	
	
	private BorderStyle style;

	public Grid3() {
		this(BorderStyle.unicode, 1, 1);
	}
	
	public Grid3(int cols, int rows) {
		this(BorderStyle.unicode, cols, rows);
	}
	
	public Grid3(BorderStyle style, int cols, int rows) {
		super(Alignment.center, new int[] {0, 1, 0, 1});
		this.style = style;
	}
	
		
	@Override
	protected void print(StringBuilder sb) {
		
		
	}

}
