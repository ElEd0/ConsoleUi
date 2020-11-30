package es.ed0.consoleui.ui.style;

import es.ed0.consoleui.ui.style.BorderStyle.BorderPiece;

public class Border {
	
	private BorderStyle style;
	private String text;
	private double textBias;
	private int borderTextPos;
	
	public Border (BorderStyle style) {
		this(style, "", 0);
	}
	
	public Border (BorderStyle style, String text) {
		this(style, text, 0);
	}
	
	public Border (BorderStyle style, String text, double textBias) {
		this(style, text, 0, 0);
	}
	
	public Border (BorderStyle style, String text, double textBias, int borderTextPos) {
		this.style = style;
		this.text = text;
		this.textBias = textBias > 1 ? 1 : textBias;
		this.borderTextPos = borderTextPos;
	}
	
	public void drawBorderTop(StringBuilder sb, int width) {
		sb.append(style.getPiece(BorderPiece.ds));
		this.drawBorderHorizontal(sb,  width, this.borderTextPos == 0);
		sb.append(style.getPiece(BorderPiece.sa)).append("\n");
	}
	
	public void drawBorderBottom(StringBuilder sb, int width) {
		sb.append(style.getPiece(BorderPiece.wd));
		this.drawBorderHorizontal(sb,  width, this.borderTextPos == 1);
		sb.append(style.getPiece(BorderPiece.wa)).append("\n");
	}
	
	public void drawBorderHorizontal(StringBuilder sb, int width, boolean borderText) {
		int textWidth = this.text.length();
		if (!borderText || textWidth < 1) { // no border text
			for (int i = 0; i < width; i++) sb.append(style.getPiece(BorderPiece.da));
		} else {

			if (textWidth > width) textWidth = width;
			
			int nonTextWidth = width - textWidth;
			int nonTextWidthBefore = (int) (nonTextWidth * this.textBias);
			int nonTextWidthAfter = nonTextWidth - nonTextWidthBefore;
			
			for (int b = 0; b < nonTextWidthBefore; b++) sb.append(style.getPiece(BorderPiece.da));
			for (int i = 0; i < textWidth; i++) sb.append(this.text.charAt(i));
			for (int b = 0; b < nonTextWidthAfter; b++)  sb.append(style.getPiece(BorderPiece.da));
			
		}
		
	}
	

	public BorderStyle getStyle() {
		return style;
	}

	public void setStyle(BorderStyle style) {
		this.style = style;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getTextBias() {
		return textBias;
	}

	public void setTextBias(double textBias) {
		this.textBias = textBias > 1 ? 1 : textBias;
	}

	public int getBorderTextPos() {
		return borderTextPos;
	}
	
	/**
	 * Set position of border text
	 * @param borderTextPos 0 = top border, 1 = bottom border
	 */
	public void setBorderTextPos(int borderTextPos) {
		this.borderTextPos = borderTextPos;
	}

	
	
}
