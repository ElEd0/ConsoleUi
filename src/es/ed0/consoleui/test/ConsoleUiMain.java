/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.ed0.consoleui.ConsoleUi;
import es.ed0.consoleui.input.CommandListener;
import es.ed0.consoleui.ui.Alignment;
import es.ed0.consoleui.ui.BorderStyle;
import es.ed0.consoleui.ui.EntryTable;
import es.ed0.consoleui.ui.Panel;
import es.ed0.consoleui.ui.ProgressBar;
import es.ed0.consoleui.ui.Separator;
import es.ed0.consoleui.ui.Text;
import es.ed0.consoleui.ui.TreeView;
import es.ed0.consoleui.ui.TreeView.TreeViewPopulator;
import es.ed0.consoleui.ui.EntryTable.TablePopulator;

/**
 * 
 */
public class ConsoleUiMain {

	public static void main(String[] args) {
		new ConsoleUiMain();
	}
	
	private ConsoleUi ui;
	
	static class Pojo {
		public String name, surname;
		public int age;
		public Pojo(String name, String surname, int age) {
			this.name = name;
			this.surname = surname;
			this.age = age;
		}
	}
	
	private ConsoleUiMain() {
		ui = new ConsoleUi();
		
		
		ui.addCommands("help", "toggle", "yey", "y este");
		
		ui.addInputListener(new CommandListener() {
			@Override
			public boolean onCommand(String command, String[] args) {
				ui.println("Entered command " + command);
				ui.println("With arguments: ");
				for (String s : args)
					ui.print(s + ", ");
				
				if(command.equals("y-este"))
					ui.println(ui.promptInput("Como quieres tu meme?"));
				return true;
			}
		});
		
		ui.setLineSpacing(3);
		
		Panel inside = new Panel("menuda movida tron", new Text("okei tt"));
		
		Panel panel = new Panel("Esto es un panel de prubea tt", new Text(
				"erase una vez en un lugar de a saber donde que ocurrio una movida to tocha. "
				+ "erase una vez en un lugar de a saber donde que ocurrio una movida to tocha.\n "
				+ "erase una vez en un lugar de a saber donde que ocurrio una movida to tocha."));
		panel.add(new Separator(50));
		panel.add("firmado por el ed0");
		panel.add(new Separator(50));
		panel.add(inside);
		
		panel.setPadding(0, 3, 0, 3);
		panel.setMaxWidth(50);
		panel.setContentAlign(Alignment.right);
		
		panel.setTabulation(2);
		
		ui.print(panel);
		
		List<Pojo> pojos = Arrays.asList(new Pojo[] {
				new Pojo("hi", "wadup", 12), new Pojo("holita", "surnamejesadasdsaddasdddddddddddddddddddddddddddddddddddasaadsasddssadadj", 1900),
				new Pojo("sandi", "gominola", 24), new Pojo("juanelea", "blepblep", 10),
		});
		
		EntryTable<Pojo> table = new EntryTable<Pojo>(BorderStyle.hollow, pojos, "name", "surname", "age");
		table.setTablePopulator(new TablePopulator<Pojo>() {
			@Override
			public ArrayList<String> getViewForRow(int i, Pojo entry) {
				final ArrayList<String> row = new ArrayList<String>();
				row.add(entry.name);
				row.add(entry.surname);
				row.add(entry.age + "");
				return row;
			}
		});
		table.setColAlign(Alignment.center);
		table.setEnumerate(true);
		table.setEnumerate(false);
		table.setTabulation(1);
		
		Panel tablePanel = new Panel(BorderStyle.unicode, "mira que guapa la tabla", table);
		tablePanel.setMaxWidth(-1);
		
		ui.print(tablePanel);
		
		ui.printList(Arrays.asList(new String[] { "entry1", "entry2", "entry678" }));
		
		
//		TreeView<File> files = new TreeView<File>(BorderStyle.unicode, new File("D:/Backups"), new TreeViewPopulator<File>() {
//			@Override
//			public String getName(File item) {
//				return item.getName();
//			}
//			@Override
//			public File[] getChildren(File item) {
//				return item.listFiles();
//			}
//		});
//		
//		ui.print(files);
		
		ProgressBar bar = new ProgressBar(20, 20, BorderStyle.sql);
		
		bar.setMax(25);
		bar.setValue(17);
		
		
		ui.print(bar);
		
		
		
		
	}

}
