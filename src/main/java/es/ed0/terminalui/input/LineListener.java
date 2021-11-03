/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.terminalui.input;

/**
 * Input listener that will be triggered whenever the user enters text
 */
public interface LineListener extends InputListener {
	/**
	 * Called when a new line of text is entered in to the terminal
	 * @param line entered line
	 * @return whether the input has been processed or not. If true is returnes the remaining listeners will not be triggered
	 */
	public boolean onLine(String line);
}
