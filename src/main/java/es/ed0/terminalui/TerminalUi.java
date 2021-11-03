/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.terminalui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ed0.terminalui.input.CommandListener;
import es.ed0.terminalui.input.InputListener;
import es.ed0.terminalui.input.LineListener;
import es.ed0.terminalui.ui.Component;
import es.ed0.terminalui.ui.Separator;

/**
 * Main class that controls all the terminal input and output. Prints text and components and handles input commands.
 */
public class TerminalUi {

	/**
	 * TODO:
	 * - entry table +
	 * - prompt +
	 * - commandhandler +
	 * - loading bar +
	 * - y/n prompt +
	 * - line +
	 * - list + 
	 * - hierarchy view (tree view) +
	 * - line margin +
	 * - set output stream +
	 * - panel (padding, etc) +
	 * - grid - wip
	 * - fix grid omg, remove getWidth -> cannot call toString twice
	 * - canvas drawAt(x, y) +
	 * - canvas draw circle +
	 * - canvas line +
	 * - better borders, with title
	 * - graphs (?)
	 * - progress last progress char
	 * - \r repaint console
	 * - prompt options (int)
	 */
	
	private Scanner t;
	private PrintStream out;
	private int lastPrintLength = 10;
	private int lineSpacing = 1;
	
	private final ArrayList<InputListener> listeners;
	private final ArrayList<String> commands;
	private boolean acceptAnythingAsCommand = false;
	
	
	public TerminalUi() {
		t = new Scanner(System.in);
		out = System.out;
		commands = new ArrayList<String>();
		listeners = new ArrayList<InputListener>();
	}
	
	private void dispatchInput(String line) {
		if (line == null || line.equals(""))
			return;
		boolean brk = false;
		for (InputListener li : listeners) {
			if (li instanceof LineListener) {
				brk = ((LineListener) li).onLine(line);
			} else if (li instanceof CommandListener) {
				
				final String[] coma = line.split(" ");
				final String[] as = new String[coma.length - 1];
				for (int c = 0; c < as.length; c++)
					as[c] = coma[c + 1];
					
				for (String c : commands) {
					if (this.acceptAnythingAsCommand || c.equals(coma[0])) {
						brk = ((CommandListener) li).onCommand(c, as);
						break;
					}
				}
			}
			if (brk)
				break;
		}
	}
	/**
	 * It will activate the user input and send the next entered line or command to the available listeners
	 * @param message
	 */
	public void promptInput(String message) {
		print(message + " ");
		dispatchInput(t.nextLine());
	}
	
	/**
	 * Prompt the given message and wait for a response.
	 * @param message Prompt message
	 * @return User input answer
	 */
	public String promptText(String message) {
		out.print(message);
		return t.nextLine();
	}
	
	/**
	 * Prompt the given message and wait for a yes/no response.
	 * @param message Prompt message
	 * @return true if user entered "y" or "yes". Case insensitive
	 */
	public boolean promptAccept(String message) {
		switch (promptText(message).toLowerCase()) {
			case "y": case "yes": return true;
			default: return false;
		}
	}

	/**
	 * Promt the given message and wait for a numeric response
	 * @param message Prompt message
	 * @return The input as an int, or -1 if the input could not be parsed as an int
	 */
	public int promptOption(String message) {
		out.print(message);
		try {
			return t.nextInt();
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * Prints the elements in the given list. It will do so by calling the toString() method for each object<br>
	 * Ie:<br>
	 *  - element1<br>
	 *  - element2<br>
	 *  - element3<br>
	 * @param list
	 */
	public void printList(List<?> list) {
		final StringBuilder sb = new StringBuilder();
		for (Object obj : list)
			sb.append(" - ").append(obj.toString()).append("\n");
		println(sb.toString());
	}
	
	/**
	 * Print a terminal ui component to the console
	 * @param com component to print
	 */
	public void print(Component com) {
		println(com.toString());
	}
	
	/**
	 * Prints the given text to the terminal
	 * @param line
	 */
	public void print(String line) {
		out.print(line);
		if (!line.equals("\n"))
			lastPrintLength = line.length();
		if (line.endsWith("\n"))
			for (int i = 0; i < lineSpacing - 1; i++)
				out.println();
	}

	/**
	 * Prints the given object to the terminal
	 * @param obj
	 */
	public void print(Object obj) {
		this.print(obj.toString());
	}
	/**
	 * Prints the given text to the terminal and appends a line jump
	 * @param line
	 */
	public void println(String line) {
		print(line + "\n");
	}
	/**
	 * Prints the given object to the terminal and appends a line jump
	 * @param obj
	 */
	public void println(Object obj) {
		println(obj.toString());
	}
	
	/**
	 * Prints a line jump
	 */
	public void println() {
		print("\n");
	}
	
	/**
	 * Prints a separator with the same length as the last printed text line
	 */
	public void separator() {
		print(new Separator(lastPrintLength));
	}
	
	/**
	 * Sets the printing output stream. Default is System.out
	 * @param stream new PrintStream
	 */
	public void setOutputStream(PrintStream stream) {
		this.out = stream;
	}
	
	/**
	 * Sets the input stream. Default is System.in
	 * @param stream InputStream
	 */
	public void setInputStream(InputStream stream) {
		t.close();
		t = new Scanner(stream);
	}

	/**
	 * Return the current line spacing. Default is 1
	 * @return line spacing
	 */
	public int getLineSpacing() {
		return lineSpacing;
	}

	/**
	 * Set the line spacing. The line jumps between print and print, this includes println prints. Default is 1
	 * @param lineSpacing new linespacing
	 */
	public void setLineSpacing(int lineSpacing) {
		this.lineSpacing = lineSpacing;
	}
	
	/**
	 * Add a recognizable command to the list of commands that trigger the onCommand() method in any declared CommandListener.<br>
	 * Spaces are not allowed and will be replaced with "-"
	 * @param command command
	 */
	public void addCommand(String command) {
		commands.add(command.replace(" ", "-"));
	}
	/**
	 * Add commands to the list of commands that trigger the onCommand() method in any declared CommandListener.<br>
	 * Spaces are not allowed and will be replaced with "-"
	 * @param commands command or commands
	 */
	public void addCommands(String... commands) {
		for (String c : commands)
			addCommand(c);
	}
	/**
	 * If set to true the command listeners will be triggered by any string without 
	 * the need of adding them as valid commands via {@link #addCommand(String)}
	 * @param accept
	 */
	public void acceptAnythingAsCommand(boolean accept) {
		this.acceptAnythingAsCommand = accept;
	}
	
	/**
	 * Add an inputListener to listen for user keyboard input.
	 * If a CommandListener is given, commands must be set using {@link #addCommand(String)} or {@link #addCommands(String...)} 
	 * to trigger the {@link CommandListener#onCommand(String, String[])} method. 
	 * Alternatively one can set to true acceptAnythingAsCommand by calling {@link #acceptAnythingAsCommand(boolean)}
	 * @param listener listener to add
	 */
	public void addInputListener(InputListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Removes the given listener from the available listeners list
	 * @param listener
	 */
	public void removeInputListener(InputListener listener) {
		listeners.remove(listener);
	}
	
	
}
