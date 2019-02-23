/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;

/**
 * Abstract class that all console ui elements must extend. 
 * Handles basic shared functionality like tabulation
 */
public abstract class Component {
	
	public String toString() {
		final StringBuilder content = new StringBuilder();
		print(content);
		if (tabulation != 0) {
			final StringBuilder tab = new StringBuilder();
			for (int t = 0; t < tabulation; t++) tab.append("\t");
			final String aux = content.toString();
			content.setLength(0);
			content.append(tab.toString());
			content.append(aux.replaceAll("\n", "\n" + tab.toString()));
		}
		return content.toString();
	}
	
	protected int tabulation = 0;
	
	/**
	 * Sets the tabulation for this component, this is, how many \t will be printed before the component
	 * @param tabs
	 */
	public void setTabulation(int tabs) {
		this.tabulation = tabs;
	}
	
	/**
	 * Called when a component needs to be printed. The extending class must define the body and append its 
	 * contents as string lines in the given string builder
	 * @param sb StringBuilder to append the body to
	 */
	protected abstract void print(StringBuilder sb);
	
}
