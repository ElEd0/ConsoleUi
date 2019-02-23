/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ed0.consoleui.input.CommandListener;
import es.ed0.consoleui.input.InputListener;
import es.ed0.consoleui.input.LineListener;
import es.ed0.consoleui.ui.Component;
import es.ed0.consoleui.ui.Separator;

/**
 * Main class that controls all the console input and output. Prints text and components and handles input commands.
 */
public class ConsoleUi {

	/**
	 * TODO:
	 * - entry table +
	 * - prompt +
	 * - commandhandler +
	 * - loading bar +
	 * - y/n prompt +
	 * - line +
	 * - list + 
	 * - banners*
	 * - hierarchy view +
	 * - line margin +
	 * - set output stream +
	 * - panel (padding, etc) +
	 * - grid - wip
	 */
	
	private Scanner t;
	private PrintStream out;
	private int lastPrintLength = 10;
	private int lineSpacing = 1;
	
	private ArrayList<InputListener> listeners;
	private ArrayList<String> commands;
	private Thread inputThread;
	
	
	public ConsoleUi() {
		t = new Scanner(System.in);
		out = System.out;
		commands = new ArrayList<String>();
		listeners = new ArrayList<InputListener>();
	}
	
	public void startInputThread() {
		if (inputThread != null) {
			inputThread.interrupt();
		}
		inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					dispatchInput(t.nextLine());
				}
			}
		});
		
		inputThread.start();
	}
	
	public void stopInputThread() {
		if (inputThread != null) {
			inputThread.interrupt();
			inputThread = null;
		}
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
					if (c.equals(coma[0])) {
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
	 * Prompt the given message and wait for a response. Note that this will enter in conflict with the
	 * main input handler (if activated)
	 * @param message Prompt message
	 * @return User input answer
	 */
	public String promptInput(String message) {
		out.print(message + "\t");
		final String res = t.nextLine();
		return res;
	}
	
	/**
	 * Prompt the given message and wait for a yes/no response. Note that this will enter in conflict with the
	 * main input handler (if activated)
	 * @param message Prompt message
	 * @return true if user entered "y" or "yes". Case insensitive
	 */
	public boolean promptAccept(String message) {
		switch (promptInput(message).toLowerCase()) {
		case "y": case "yes": return true;
		default: return false;
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
	 * Print a console ui component to the console
	 * @param com component to print
	 */
	public void print(Component com) {
		println(com.toString());
	}
	
	/**
	 * Prints the given text to the console
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
	 * Prints the given text to the console and appends a line jump
	 * @param line
	 */
	public void println(String line) {
		print(line + "\n");
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
	 * @param streamnew InputStream
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
	 * @param command command
	 */
	public void addCommands(String... commands) {
		for (String c : commands)
			addCommand(c);
	}
	/**
	 * Add an inputListener to listen for user keyboard input. This will activate a thread that will constantly 
	 * check for user input and it will dispatch it to the available inputListeners.<br>
	 * Note this will enter in conflict with the prompt for input methods unless called while the user input is being processed. 
	 * This means you cannot call {@link #promptInput(String)} or {@link #promptAccept(String)} while listening for input, but you can 
	 * still use them when the input listener is triggered.<br>
	 * If a CommandListener is given, commands must be set using {@link #addCommand(String)} or {@link #addCommands(String...)} 
	 * to trigger the {@link CommandListener#onCommand(String, String[])} method
	 * @param listener listener to add
	 */
	public void addInputListener(InputListener listener) {
		listeners.add(listener);
		if (inputThread == null)
			startInputThread();
	}
	
	/**
	 * Removes the given listener from the available listeners list
	 * @param listener
	 */
	public void removeInputListener(InputListener listener) {
		listeners.remove(listener);
	}
	
	
}
