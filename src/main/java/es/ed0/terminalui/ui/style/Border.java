package es.ed0.terminalui.ui.style;

import es.ed0.terminalui.ui.style.BorderStyle.BorderPiece;

public class Border {
	
	public final static int TEXT_POS_TOP = 0, TEXT_POS_BOTTOM = 1;
	
	private BorderStyle style;
	private String text;
	private double textBias;
	private int borderTextPos;
	
	public Border (BorderStyle style) {
		this(style, "", 0, TEXT_POS_TOP);
	}
	
	public Border (BorderStyle style, String text) {
		this(style, text, 0, TEXT_POS_TOP);
	}
	
	public Border (BorderStyle style, String text, double textBias) {
		this(style, text, textBias, TEXT_POS_TOP);
	}
	
	public Border (BorderStyle style, String text, double textBias, int borderTextPos) {
		this.style = style;
		this.text = text;
		this.textBias = textBias;
		this.borderTextPos = borderTextPos;
	}
	
	public void drawBorderTop(StringBuilder sb, int width) {
		int lenBefore = sb.length();
		sb.append(style.getPiece(BorderPiece.sd));
		this.drawBorderHorizontal(sb,  width, this.borderTextPos == TEXT_POS_TOP);
		sb.append(style.getPiece(BorderPiece.as));
		if (sb.length() != lenBefore)
			sb.append("\n");
	}
	
	public void drawBorderBottom(StringBuilder sb, int width) {
		int lenBefore = sb.length();
		sb.append(style.getPiece(BorderPiece.wd));
		this.drawBorderHorizontal(sb,  width, this.borderTextPos == TEXT_POS_BOTTOM);
		sb.append(style.getPiece(BorderPiece.wa));
		if (sb.length() != lenBefore)
			sb.append("\n");
	}
	
	public void drawBorderHorizontal(StringBuilder sb, int width, boolean borderText) {
		int textWidth = this.text.length();
		if (!borderText || textWidth < 1) { // no border text
			for (int i = 0; i < width; i++) sb.append(style.getPiece(BorderPiece.ad));
		} else {

			if (textWidth > width) textWidth = width;
			
			int nonTextWidth = width - textWidth;
			int nonTextWidthBefore = nonTextWidth;
			if (this.textBias < 0) {
				int textBiasPos = (int) (this.textBias + 1) * -1;
				if (textBiasPos > nonTextWidthBefore) textBiasPos = nonTextWidthBefore;
				nonTextWidthBefore -= textBiasPos;
			} else if (this.textBias >= 1) {// value bigger than one
				if (this.textBias < nonTextWidth)
					nonTextWidthBefore = (int) this.textBias;
			} else { // value is between 0 and 1
				nonTextWidthBefore *= this.textBias;
			}
			int nonTextWidthAfter = nonTextWidth - nonTextWidthBefore;
			
			for (int b = 0; b < nonTextWidthBefore; b++) sb.append(style.getPiece(BorderPiece.ad));
			for (int i = 0; i < textWidth; i++) sb.append(this.text.charAt(i));
			for (int b = 0; b < nonTextWidthAfter; b++)  sb.append(style.getPiece(BorderPiece.ad));
			
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
	
	/**
	 * Set the alignment of the text in the border, this will depend on the value given<br>
	 * <b>0 to 0.99:</b> The text will align proportionally to the width of the border where 0 is aligned to the left 
	 * and 1 is aligned to the right<br>
	 * <b>1 or bigger:</b> The text will be positioned the exact amount of characters from the left<br>
	 * <b>-1 or smaller:</b> The text will be positioned the given amount of characters from the right,
	 * having that -1 is 0 characters, -2 is 1 character, etc <br>
	 * @param textBias
	 */
	public void setTextBias(double textBias) {
		this.textBias = textBias;
	}

	public int getBorderTextPos() {
		return borderTextPos;
	}
	
	/**
	 * Set position of border text
	 * @param borderTextPos one of Border.TEXT_POS_TOP or Border.TEXT_POS_BOTTOM
	 */
	public void setBorderTextPos(int borderTextPos) {
		this.borderTextPos = borderTextPos;
	}

	
	
}
