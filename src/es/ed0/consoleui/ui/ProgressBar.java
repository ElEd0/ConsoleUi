/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;

import es.ed0.consoleui.ui.BorderStyle.BorderPiece;

/**
 * 
 */
public class ProgressBar extends Component {
	
	
	private BorderStyle style;
	private int max;
	private int value;
	private String progressChar = "*";
	
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
	}


	@Override
	protected void print(StringBuilder sb) {
		sb.append(style.getPiece(BorderPiece.ds));
		for (int i = 0; i < max + 2; i++) sb.append(style.getPiece(BorderPiece.da));
		sb.append(style.getPiece(BorderPiece.sa)).append("\n");
		
		sb.append(style.getPiece(BorderPiece.ws)).append(" ");
		for (int i = 0; i < value; i++) sb.append(progressChar);
		for (int i = 0; i < (max - value); i++) sb.append(" ");
		sb.append(" ").append(style.getPiece(BorderPiece.ws)).append("\n");
		
		sb.append(style.getPiece(BorderPiece.wd));
		for (int i = 0; i < max + 2; i++) sb.append(style.getPiece(BorderPiece.da));
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
	
	public void setProgressChar(char c) {
		progressChar = String.valueOf(c);
	}

}
