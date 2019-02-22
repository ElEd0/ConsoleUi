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
 * 
 */
public class ConsoleUi {

	/**
	 * TODO:
	 * - entry table +
	 * - prompt +
	 * - commandhandler +
	 * - loading bar
	 * - y/n prompt +
	 * - line +
	 * - list + 
	 * - banners*
	 * - hierarchy view
	 * - line margin +
	 * - set output stream +
	 * - panel (padding, etc) +
	 * - grid - wip
	 */
	
	private Scanner t;
	private PrintStream out;
	private InputStream in;
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
	
	public String promptInput(String message) {
		out.print(message + "\t");
		final String res = t.nextLine();
		return res;
	}
	
	public boolean promptAccept(String message) {
		switch (promptInput(message).toLowerCase()) {
		case "y": case "yes": return true;
		default: return false;
		}
	}
	
	public void printList(List<?> list) {
		final StringBuilder sb = new StringBuilder();
		for (Object obj : list)
			sb.append(" - ").append(obj.toString()).append("\n");
		println(sb.toString());
	}
	
	public void print(Component com) {
		println(com.toString());
	}
	
	public void print(String line) {
		out.print(line);
		lastPrintLength = line.length();
		if (line.endsWith("\n"))
			for (int i = 0; i < lineSpacing - 1; i++)
				out.println();
	}
	
	public void println(String line) {
		print(line + "\n");
	}
	
	public void println() {
		print("\n");
	}
	
	public void separator(int length) {
		print(new Separator(length));
	}
	
	public void separator() {
		print(new Separator(lastPrintLength));
	}
	
	public void setOutputStream(PrintStream stream) {
		this.out = stream;
	}
	
	public void setInputStream(InputStream stream) {
		this.in = stream;
		t.close();
		t = new Scanner(stream);
	}

	public int getLineSpacing() {
		return lineSpacing;
	}

	public void setLineSpacing(int lineSpacing) {
		this.lineSpacing = lineSpacing;
	}
	
	public void addCommand(String command) {
		commands.add(command.replace(" ", "-"));
	}
	
	public void addCommands(String... commands) {
		for (String c : commands)
			addCommand(c);
	}

	public void addInputListener(InputListener listener) {
		listeners.add(listener);
		if (inputThread == null)
			startInputThread();
	}
	
	public void removeInputListener(InputListener listener) {
		listeners.remove(listener);
	}
	
	
}
