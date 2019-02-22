/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;

/**
 * 
 */
public abstract class Component {
	
	public String toString() {
		final StringBuilder content = new StringBuilder();
		print(content);
		return content.toString();
	}
	
	protected abstract void print(StringBuilder sb);
	
}
