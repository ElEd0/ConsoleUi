/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.terminalui.ui;

import es.ed0.terminalui.ui.style.Border;
import es.ed0.terminalui.ui.style.BorderStyle.BorderPiece;

/**
 * Component that displays progress based on a max and current value. 
 * The progress is shown by the amount of {@link #getProgressChar()} and {@link #getUnprogressChar()}
 */
public class ProgressBar extends Component {
	
	
	private int max;
	private int value;
	private String progressChar = "*", unprogressChar = "-";
	private int progressHeight = 1;
	
	public ProgressBar(int max) {
		this(max, 0);
	}
	
	public ProgressBar(int max, int value) {
		this.max = max;
		setValue(value);
	}


	@Override
	protected void print(StringBuilder sb) {
		final Border b = getBorder();
		final int borderWidth = this.max + padding[3] + padding[1];
		
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

		//progress bar
		for (int h = 0; h < this.progressHeight; h++) {
			//left padding
			sb.append(b.getStyle().getPiece(BorderPiece.ws));
			for (int p = 0; p < this.padding[3]; p++)
				sb.append(this.getPaddingChar());
			
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
	
	/**
	 * Get the current progress percent
	 * @return
	 */
	public double getProgress() {
		return (value * 100f) / max;
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
