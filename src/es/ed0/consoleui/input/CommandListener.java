/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.input;

/**
 * Input listener that listens for command entered by the user
 */
public interface CommandListener extends InputListener {
	/**
	 * Triggered when a user writes a valid command
	 * @param command entered command
	 * @param args command arguments, this is, all the text entered after the command separated by spaces
	 * @return whether the input has been processed or not. If true is returned the remaining listeners will not be triggered
	 */
	public boolean onCommand(String command, String[] args);
}
