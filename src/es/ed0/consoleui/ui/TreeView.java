/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;

import java.util.ArrayList;

/**
 * 
 */
public class TreeView<T> extends Component {

	public static interface TreeViewPopulator<T> {
		public String getName(T item);
		public T[] getChildren(T item);
	}
	
	private static final String MARKER_AD = "─";
	private static final String MARKER_WDS = "┣";
	private static final String MARKER_WD = "┗";
	private static final String MARKER_WS = "┃";
	private static final String MARKER_SPACE = " ";
	
	
	private T root;
	private TreeViewPopulator<T> populator;
	private String[] marks;
	private ArrayList<TreeView<T>> sons;
	
	public TreeView(T root, TreeViewPopulator<T> populator) {
		this(root, populator, new String[] { MARKER_AD });
	}
	
	private TreeView(T root, TreeViewPopulator<T> populator, String[] marks) {
		this.populator = populator;
		this.marks = marks;
		this.sons = new ArrayList<TreeView<T>>();
		setRoot(root);		
	}
	
	@Override
	protected void print(StringBuilder sb) {
		for (String m : marks)
			sb.append(m).append(MARKER_SPACE);
		
		sb.append(populator.getName(root)).append("\n");
		for (TreeView<T> son : sons)
			son.print(sb);
	}

	public T getRoot() {
		return root;
	}

	public void setRoot(T root) {
		this.root = root;
		sons.clear();
		final T[] newSons = populator.getChildren(root);
		if (newSons != null)
			for (int i = 0; i < newSons.length; i++) {
				String[] newMarks = new String[marks.length + 1];
				for (int m = 0; m < marks.length; m++) {
					switch (marks[m]) {
					case MARKER_AD: case MARKER_WD: case MARKER_SPACE: newMarks[m] = MARKER_SPACE; break;
					case MARKER_WDS: case MARKER_WS: newMarks[m] = MARKER_WS; break;
					}
				}
				newMarks[newMarks.length - 1] = (i == newSons.length - 1 ? MARKER_WD : MARKER_WDS);
				sons.add(new TreeView<T>(newSons[i], populator, newMarks));
			}
	}

	public ArrayList<TreeView<T>> getSons() {
		return sons;
	}
	
	

}
