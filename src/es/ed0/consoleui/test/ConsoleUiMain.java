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
import es.ed0.consoleui.input.LineListener;
import es.ed0.consoleui.ui.Canvas;
import es.ed0.consoleui.ui.Component;
import es.ed0.consoleui.ui.EntryTable;
import es.ed0.consoleui.ui.Panel;
import es.ed0.consoleui.ui.ProgressBar;
import es.ed0.consoleui.ui.Text;
import es.ed0.consoleui.ui.TreeView;
import es.ed0.consoleui.ui.TreeView.TreeViewPopulator;
import es.ed0.consoleui.ui.style.Alignment;
import es.ed0.consoleui.ui.style.Border;
import es.ed0.consoleui.ui.style.BorderStyle;
import es.ed0.consoleui.ui.EntryTable.TablePopulator;
import es.ed0.consoleui.ui.Grid;

/**
 * 
 */
public class ConsoleUiMain implements LineListener, CommandListener {

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
		
		Text t = new Text("hola texto de prueba lolaasd asd sd asd  asd  sdsdsd  sd sd  ");
		t.setPadding(1, 3, 1, 3);
		t.setBorder(new Border(BorderStyle.unicode, "| border tt |", 0.05, 0));
		
		ui.print(t);
		
		//if (true) return;
		
		Canvas can = new Canvas(50, 50);
		can.setBorder(new Border(BorderStyle.unicode));
		
		
		can.setPadding(0);
		can.fill('*');
		for (int i = 0; i < can.getWidth(); i++) {
			can.draw(i, 0, (i + "").charAt(0));
		}
		for (int i = 0; i < can.getHeight(); i++) {
			can.draw(0, i, (i + "").charAt(0));
		}
		//can.drawRectangle(2, 2, 10, 10, '0', false);
		//ui.print(can);
		
		//can.fill('*');
		//can.drawRectangle(2, 2, 10, 10, '0', true);
		//ui.print(can);
		/*
		can.drawLine(2, 2, 16, 2, '0');
		can.drawLine(16, 2, 16, 16, '0');
		can.drawLine(16, 16, 2, 16, '0');
		can.drawLine(2, 16, 2, 2, '0');
		can.drawLine(2, 2, 16, 16, 'O');
		can.drawLine(16, 2, 2, 16, 'O');
		*/
		
		can.drawCircle(25, 25, 8, 'O', true);
		
		
		ui.print(can);

		if (true)return;
		
		
		
		TreeView<File> tv = new TreeView<File>(BorderStyle.hollow, new File("C:\\Users\\us3r\\Apache24"), new TreeViewPopulator<File>() {

			@Override
			public String getName(File item) {
				return item.getName();
			}

			@Override
			public File[] getChildren(File item) {
				if (item.isDirectory()) {
					return item.listFiles();
				} else {
					return new File[] {};
				}
			}
			
		});
		//tv.setPaddingChar(BorderStyle.hollow.getPiece(BorderPiece.da) + " ");
		tv.setPadding(0, 0, 0, 1);
		tv.setLeftMargin(6);
		
		ui.print(tv);

		
		
		ProgressBar pb = new ProgressBar(20, 2, BorderStyle.sql);
		pb.setValue(11);
		//pb.setPadding(1, 0,0, 0);
		pb.setAlign(Alignment.left);
		pb.setProgressHeight(1);
		ui.print(pb);

		ui.addCommands("help", "toggle", "yey", "y este");

//		ui.addInputListener(this);
//		ui.addInputListener(new CommandListener() {
//			@Override
//			public boolean onCommand(String command, String[] args) {
//				ui.println("Entered command " + command);
//				ui.println("With arguments: ");
//				for (String s : args)
//					ui.print(s + ", ");
//				
//				if(command.equals("y-este"))
//					ui.println(ui.promptInput("Como quieres tu meme?"));
//				return false;
//			}
//		});
		
		ui.setLineSpacing(3);
		
		Panel inside = new Panel("Esto es un panel de prubea tt", new Text("okei tt"));
		
		
		Panel panel = new Panel("Esto es un panel de prubea tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt", new Text(
				"erase una vez en un lugar de a saber donde que ocurrio una movida to tocha. "
				+ "erase una vez en un lugar de a saber donde que ocurrio una movida to tocha.\n "
				+ "erase una vez en un lugar de a saber donde que ocurrio una movida to tocha.", true));
		//panel.add(new Separator(50));
		//panel.add("firmado por el ed0\n");
		//panel.add(new Separator(50));
		//panel.add(inside);
		
		//panel.setPadding(1, 3, 1, 3);
		//panel.setTileMaxWidth(50);
		panel.setContentAlign(Alignment.right);
		panel.setHeaderAlign(Alignment.right);
		
		
		//ui.print(panel);
		//ui.print(inside);
		
		//if (true) return;
		
		List<Pojo> pojos = Arrays.asList(new Pojo[] {
				new Pojo("hi", "wadup", 12), new Pojo("holita", "surnamejesadasdsaddasdddddddddddddddddddddddddddddddddddasaadsasddssadadj", 1900),
				new Pojo("lluco", "putoamo", 24), new Pojo("juanelea", "blepblep", 10),
		});
		
		EntryTable<Pojo> table = new EntryTable<Pojo>(BorderStyle.hollow, pojos, "name", "surname", "age");
		table.setTablePopulator(new TablePopulator<Pojo>() {
			@Override
			public ArrayList<Component> getViewForRow(int i, Pojo entry) {
				//System.out.println(i);
				final ArrayList<Component> row = new ArrayList<Component>();
				row.add(new Text(entry.name));
				row.add(new Text(entry.surname));
				row.add(new Text(entry.age));
				return row;
			}
		});
		table.setAlign(Alignment.center);
		//table.setEnumerate(true);
		table.removeCol(1);
		//ui.println(table.getRowCount());
		table.removeRow(2);
		//ui.println(table.getRowCount());
		table.setEnumerate(true);
		
		Panel tablePanel = new Panel(BorderStyle.unicode, "mira que guapa la tabla", table);
		tablePanel.setHeaderAlign(Alignment.center);
		//tablePanel.setTileMaxWidth(-1);
		
		Text longText = new Text("hola muy buena tardes a todos os queria presentar este texto "
				+ "muy largo que se va a tener que dividir en partes pq sinoxd");
		
		
		tablePanel.setTileMaxWidth(20);
		tablePanel.setPadding(0);
		
		ui.print(tablePanel);
		
		if (true)return;
		
		Panel newPanel = new Panel(BorderStyle.unicode, "mira que guapa la tabla", longText);
		newPanel.setTileMaxWidth(20);
		ui.print(newPanel);
		
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
//		ui.print(new Panel(BorderStyle.unicode, files));
		
		Grid grid = new Grid(3, 3);
		
		grid.add(new Text("celda 4 loco"), 0, 1);
		grid.add(new Text("celda 2 loco"), 1, 0);
		grid.add(new Text("celda 1 loco"), 0, 0);
		table.setEnumerate(false);
		grid.add(tablePanel, 2, 1);
		grid.add(new Text("celda 3 loco"), 2, 0);
		grid.add(new Text("celda 5 loco\nasdaddadass asadsdas dasd asda sdadds dad sad dadsasd asd aco"), 1, 1);
		grid.add(new Text("celda 7 loco"), 2, 2);
		grid.add(new Text("amo a aser esto un poco + grandesito"), 0, 2);
		
		grid.setPadding(1);

		grid.setAlign(Alignment.right, 0, 0);
		grid.setAlign(Alignment.left, 1, 0);
		grid.setAlign(Alignment.left, 2, 0);
		grid.setAlign(Alignment.left, 0, 1);
		grid.setAlign(Alignment.right, 0, 2);
		grid.setAlign(Alignment.left, 2, 2);
		
		ui.print(grid);
		
		
		Grid grid2 = new Grid(1, 2);
		
		
		grid2.add(new Text("Esto es como un panel"));
		grid2.add(new ProgressBar(25, 30));
		
		ui.print(grid2);
		
	}

	/* (non-Javadoc)
	 * @see es.ed0.consoleui.input.LineListener#onLine(java.lang.String)
	 */
	@Override
	public boolean onLine(String line) {
		System.out.println("entered line " + line);
		return true;
	}

	/* (non-Javadoc)
	 * @see es.ed0.consoleui.input.CommandListener#onCommand(java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(String command, String[] args) {
		System.out.println("entered command " + command + " with " + args.length + " args");
		return true;
	}

}
