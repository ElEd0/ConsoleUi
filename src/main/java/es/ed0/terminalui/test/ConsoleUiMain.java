/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.terminalui.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.ed0.terminalui.TerminalUi;
import es.ed0.terminalui.input.CommandListener;
import es.ed0.terminalui.input.LineListener;
import es.ed0.terminalui.ui.Canvas;
import es.ed0.terminalui.ui.Component;
import es.ed0.terminalui.ui.EntryTable;
import es.ed0.terminalui.ui.Panel;
import es.ed0.terminalui.ui.ProgressBar;
import es.ed0.terminalui.ui.Text;
import es.ed0.terminalui.ui.style.Alignment;
import es.ed0.terminalui.ui.style.Border;
import es.ed0.terminalui.ui.style.BorderStyle;
import es.ed0.terminalui.ui.EntryTable.TablePopulator;
import es.ed0.terminalui.ui.Grid;

/**
 * 
 */
public class ConsoleUiMain implements LineListener, CommandListener {

	public static void main(String[] args) {
		new ConsoleUiMain();
	}
	
	private TerminalUi ui;
	
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
		ui = new TerminalUi();

		
		//if (true) return;
		// DONT DELETE THIS, NEEDED FOR TESTING LOL
		
		Text t = new Text("hola texto de prueba lolaasd asd sd asd asd sdsdsd sd sd ");
		t.setPadding(1, 3, 1, 3);
		t.setBorder(new Border(BorderStyle.UNICODE, "| border tt |", -4, 0));
		t.setMaxWidth(10);
		
		
		Grid grid1 = new Grid(BorderStyle.UNICODE, 4, 2);
		
		Text text1 = new Text("text with border");
		text1.setBorder(new Border(BorderStyle.SQL, "title", 0.8d));
		
		ui.print(t);
	
		
		//*
		grid1.add(new Text("fir\nst 1\nlolo"), 0, 0);
		grid1.add(new Text("first 2"), 1, 0);
		grid1.add(text1, 2, 0);
		grid1.add(new Text("first 4"), 3, 0);
		grid1.add(new Text("second 1"), 0, 1);
		grid1.add(new Text("second 222222 22222 lololo brbbr"), 1, 1);
		//*/
		

		
		ui.print(grid1);

		
		
		
		if (true) return;
		
		Canvas can = new Canvas(50, 50);
		can.setBorder(new Border(BorderStyle.UNICODE));
		
		
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
		
		
		//ui.print(can);

		//if (true)return;
		
		
		//ui.print(tv);
 
		//if (true) return;
		
		
		ProgressBar pb = new ProgressBar(20, 2);
		pb.setValue(11);
		//pb.setPadding(1, 0,0, 0);
		pb.setAlignment(Alignment.left);
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
				+ "erase una vez en un lugar de a saber donde que ocurrio una movida to tocha."));
		//panel.add(new Separator(50));
		//panel.add("firmado por el ed0\n");
		//panel.add(new Separator(50));
		//panel.add(inside);
		
		//panel.setPadding(1, 3, 1, 3);
		//panel.setTileMaxWidth(50);
		panel.setContentAlignment(Alignment.right);
		panel.setHeaderAlignment(Alignment.right);
		
		
		ui.print(panel);
		ui.print(inside);
		
		if (true) return;
		
		List<Pojo> pojos = Arrays.asList(new Pojo[] {
				new Pojo("hi", "wadup", 12), new Pojo("holita", "surnamejesadasdsaddasdddddddddddddddddddddddddddddddddddasaadsasddssadadj", 1900),
				new Pojo("lluco", "putoamo", 24), new Pojo("juanelea", "blepblep", 10),
		});
		
		EntryTable<Pojo> table = new EntryTable<Pojo>(BorderStyle.UNICODE_DOUBLE, pojos, "name", "surname", "age");
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
		table.setAlignment(Alignment.center);
		//table.setEnumerate(true);
		table.removeCol(1);
		//ui.println(table.getRowCount());
		table.removeRow(2);
		//ui.println(table.getRowCount());
		table.setEnumerate(true);
		
		ui.print(table);
		
		if (true)return;
		
		
		Panel tablePanel = new Panel(BorderStyle.UNICODE, "mira que guapa la tabla", table);
		tablePanel.setHeaderAlignment(Alignment.center);
		//tablePanel.setTileMaxWidth(-1);
		
		Text longText = new Text("hola muy buena tardes a todos os queria presentar este texto "
				+ "muy largo que se va a tener que dividir en partes pq sinoxd");
		

		tablePanel.setPadding(0);
		
		ui.print(tablePanel);

		
	}

	@Override
	public boolean onLine(String line) {
		System.out.println("entered line " + line);
		return true;
	}
	
	@Override
	public boolean onCommand(String command, String[] args) {
		System.out.println("entered command " + command + " with " + args.length + " args");
		return true;
	}

}
