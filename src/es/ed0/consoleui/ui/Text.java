/**
 * Created by Ed0 in 23 feb. 2019
 */
package es.ed0.consoleui.ui;

/**
 * Console ui that prints simple text. The only use of this object is to wrap the string class and 
 * have a common console component
 */
public class Text extends Component {

	private String text;
	
	/**
	 * Create a new Text component with the text in the given string
	 * @param text
	 */
	public Text(String text) {
		this.text = text;
	}
	
	@Override
	protected void print(StringBuilder sb) {
		sb.append(text);
	}

}
