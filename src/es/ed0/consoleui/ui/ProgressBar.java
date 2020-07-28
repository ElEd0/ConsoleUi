/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;

import es.ed0.consoleui.ui.style.Alignment;
import es.ed0.consoleui.ui.style.BorderStyle;
import es.ed0.consoleui.ui.style.BorderStyle.BorderPiece;

/**
 * 
 */
public class ProgressBar extends Component {
	
	
	private BorderStyle style;
	private int max;
	private int value;
	private String progressChar = "*", unprogressChar = "-";
	private int progressHeight = 1;
	
	public ProgressBar(int max) {
		this(max, 0);
	}
	public ProgressBar(int max, int value) {
		this(max, value, BorderStyle.sql);
	}
	public ProgressBar(int max, int value, BorderStyle style) {
		this.max = max;
		setValue(value);
		this.style = style;
		this.padding = new int[] {0, 1, 0, 1};
		align = Alignment.left;
	}


	@Override
	protected void print(StringBuilder sb) {
		final int borderWidth = max + padding[3] + padding[1];
		// top border
		sb.append(style.getPiece(BorderPiece.ds));
		for (int i = 0; i < borderWidth; i++) sb.append(style.getPiece(BorderPiece.da));
		sb.append(style.getPiece(BorderPiece.sa)).append("\n");
		
		// top padding
		for (int p = 0; p < this.padding[0]; p++) {
			sb.append(style.getPiece(BorderPiece.ws));
			for (int w = 0; w < borderWidth; w++)
				sb.append(" ");
			sb.append(style.getPiece(BorderPiece.ws)).append("\n");
		}
		//progress bar
		for (int h = 0; h < this.progressHeight; h++) {
			
			sb.append(style.getPiece(BorderPiece.ws));
			for (int p = 0; p < this.padding[3]; p++)
				sb.append(" ");
			
			switch (this.align) {
			case left: case center:
				for (int i = 0; i < value; i++) sb.append(progressChar);
				for (int i = 0; i < (max - value); i++) sb.append(unprogressChar);
				break;
			case right:
				for (int i = 0; i < (max - value); i++) sb.append(unprogressChar);
				for (int i = 0; i < value; i++) sb.append(progressChar);
				break;
			}
			for (int p = 0; p < this.padding[1]; p++)
				sb.append(" ");
			sb.append(style.getPiece(BorderPiece.ws)).append("\n");
		
		}
		// bottom padding
		for (int p = 0; p < this.padding[2]; p++) {
			sb.append(style.getPiece(BorderPiece.ws));
			for (int w = 0; w < borderWidth; w++)
				sb.append(" ");
			sb.append(style.getPiece(BorderPiece.ws)).append("\n");
		}
		// bottom border
		sb.append(style.getPiece(BorderPiece.wd));
		for (int i = 0; i < borderWidth; i++) sb.append(style.getPiece(BorderPiece.da));
		sb.append(style.getPiece(BorderPiece.wa));
	}
	
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value > max ? max : value;
	}
	public String getProgressChar() {
		return progressChar;
	}
	public void setProgressChar(String progressChar) {
		this.progressChar = progressChar;
	}
	public String getUnprogressChar() {
		return unprogressChar;
	}
	public void setUnprogressChar(String unprogressChar) {
		this.unprogressChar = unprogressChar;
	}

	public int getProgressHeight() {
		return progressHeight;
	}
	public void setProgressHeight(int progressHeight) {
		this.progressHeight = progressHeight;
	}
	
}
