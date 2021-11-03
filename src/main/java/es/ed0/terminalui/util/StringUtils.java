package es.ed0.terminalui.util;

import es.ed0.terminalui.ui.style.Alignment;

import java.util.ArrayList;
import java.util.StringTokenizer;

public abstract class StringUtils {

	/**
	 * Align lines to the selected direction, fills with empty spaces the remaining space for each line
	 * so the result will contain strings of identical length <code>maxWidth</code>
	 * @param lines
	 * @param maxWidth
	 * @param alignment
	 * @return
	 */
	public static String[] align(String[] lines, int maxWidth, Alignment alignment) {
		// 0 = left, 1 = center, 2 = right, 3 = justified
		ArrayList<String> result = new ArrayList<String>(lines.length);
		for (String line : lines) {
			StringBuilder lineBuilder = new StringBuilder();
			//line = line.trim();
			if (line.length() < maxWidth) {
				int diff = maxWidth - line.length();
				
				switch (alignment) {
				case left: lineBuilder.append(line).append(repeatString(" ", diff)); break;
				case center:
					int leftPad = diff / 2, rightPad = diff - leftPad;
					lineBuilder.append(repeatString(" ", leftPad))
						.append(line)
						.append(repeatString(" ", rightPad));
					
					break;
				case right:
					lineBuilder.append(repeatString(" ", diff)).append(line);
					break;
				case justified:
					// unimplemented
					lineBuilder.append(line);
					break;
				}
			} else {
				lineBuilder.append(line);
			}
			result.add(lineBuilder.toString());
		}
		
		return result.toArray(new String[0]);
	}

	public static String[] wordWrap(String str, int width) {
		ArrayList<String> lines = new ArrayList<String>();
		String[] words = split(str, new char[] { ' ', '-', '\t' });

	    int curLineLength = 0;
	    StringBuilder strBuilder = new StringBuilder();
	    for (int i = 0; i < words.length; i += 1) {
	    	String word = words[i];
	        // If adding the new word to the current line would be too long,
	        // then put it on a new line (and split it up if it's too long).
	        if (curLineLength + word.length() > width) {
	            // Only move down to a new line if we have text on the current line.
	            // Avoids situation where wrapped whitespace causes empty lines in text.
	            if (curLineLength > 0) {
	            	lines.add(strBuilder.toString());
	            	strBuilder.setLength(0);
	                curLineLength = 0;
	            }

	            // If the current word is too long to fit on a line even on it's own then
	            // split the word up.
	            while (word.length() > width) {
	                strBuilder.append(word.substring(0, width - 2)).append("-");
	                word = word.substring(width - 2);

	            	lines.add(strBuilder.toString());
	            	strBuilder.setLength(0);
	            }

	            // Remove leading whitespace from the word so the new line starts flush to the left.
	            word = word.trim();
	        }
	        strBuilder.append(word);
	        curLineLength += word.length();
	    }
	    // add last line
	    if (strBuilder.length() > 0) {
        	lines.add(strBuilder.toString());
	    }

	    return lines.toArray(new String[0]);
	}

	public static String[] split(String str, char[] splitChars) {
	    ArrayList<String> parts = new ArrayList<String>();
	    int startIndex = 0;
	    while (true) {
	        int index = indexOfAny(str, splitChars, startIndex);

	        if (index == -1) {
	            parts.add(str.substring(startIndex));
	            return parts.toArray(new String[0]);
	        }

	        String word = str.substring(startIndex, index);
	        char nextChar = str.substring(index, index + 1).charAt(0);
	        // Dashes and the likes should stick to the word occurring before it. Whitespace doesn't have to.
	        if (nextChar == ' ') {
	            parts.add(word);
	            parts.add(String.valueOf(nextChar));
	        } else {
	            parts.add(word + nextChar);
	        }

	        startIndex = index + 1;
	    }
	}
	
	public static int indexOfAny(String str, char[] searchChars, int startIndex) {
		char[] strChars = str.toCharArray();
		int strLength = strChars.length;
		if (startIndex >= strLength) return -1;
		for (int i = startIndex; i < strLength; i++) {
			char c = strChars[i];
			for (char s : searchChars) {
				if (s == c) return i;
			}
		}
		return -1;
	}
	
	/**
	 * Regex-free split function
	 * @param source
	 * @param delimiter
	 * @return
	 */
	public static String[] split(String source, String delimiter) {
		StringTokenizer st = new StringTokenizer(source, delimiter);
		int tokenCount = st.countTokens();
		String[] res = new String[tokenCount];
		for (int i = 0; i < tokenCount; i++) {
			res[i] = st.nextToken();
		}
		return res;
	}

	/**
	 * Outputs a string formed by the concatenation of the input <code>piece</code> string
	 * <code>times</code> amount of times
	 * @param piece
	 * @param times
	 * @return
	 */
	public static String repeatString(String piece, int times) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < times; i++) sb.append(piece);
		return sb.toString();
	}
	
	public static String concat(Object... objects) {
		final StringBuilder sb = new StringBuilder();
		for (Object obj : objects) sb.append(obj);
		return sb.toString();
	}
	
	
}
