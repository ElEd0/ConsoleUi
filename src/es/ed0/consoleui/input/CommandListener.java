/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.input;

/**
 * 
 */
public interface CommandListener extends InputListener {
	public boolean onCommand(String command, String[] args);
}
