/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;

import java.util.ArrayList;

/**
 * 
 */
public class Grid extends Component {

	private ArrayList<ArrayList<Component>> grid;
	
	public Grid() {
		this(1, 1);
	}
	public Grid(int cols, int rows) {
		this.grid = new ArrayList<ArrayList<Component>>(cols);
		for (int r = 0; r < cols; r++)
			grid.add(new ArrayList<Component>(rows));
	}
	
	public void add(Component component) {
		//  find the first empty position
		int x = 0, y = 0;
		boolean brk = false;
		for (int c = 0; c < grid.size(); c++) {
			for (int r = 0; r < grid.get(c).size(); r++)
				if (grid.get(c).get(r) == null) {
					x = c;
					y = r;
					brk = true;
					break;
				}
			if (brk)
				break;
		}
		add(component, x, y);
	}
	
	public void add(Component component, int col, int row) {
		try {
			this.grid.get(col).remove(row);
			this.grid.get(col).add(row, component);
		} catch (IndexOutOfBoundsException e) {
			
		}
	}
	
	public void remove(int col, int row) {
		add(null, col, row);
	}
	
	
	@Override
	protected void print(StringBuilder sb) {
		
		
		
	}

}
