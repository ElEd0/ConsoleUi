/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui;

import java.io.File;
import java.util.ArrayList;

import es.ed0.consoleui.ui.style.BorderStyle;
import es.ed0.consoleui.ui.style.BorderStyle.BorderPiece;

/**
 * Console ui element that prints a Tree view showing a recursive hierarchy
 * @param <T> Recursive element type. This is an object that contains both a representative name and 
 * a list of objects of the same type. Ie: The class {@link File} contains a representative name 
 * {@link File#getName()} and a list of files inside itself {@link File#listFiles()}
 */
public class TreeView<T> extends Component {

	/**
	 * Interface required to populated a TreeView
	 * @param <T> TreeView object type
	 */
	public static interface TreeViewPopulator<T> {
		/**
		 * Returns the text that will be printed to represent the node for the entry <code>item</code>
		 * @param item item to print
		 * @return text to print for this item
		 */
		public String getName(T item);
		/**
		 * Return a list of all the entries of the object type inside the entr <code>item</code>
		 * @param item parent entry
		 * @return items inside the entry, to be printed in their respective node
		 */
		public T[] getChildren(T item);
	}
	
	
	private T root;
	private TreeViewPopulator<T> populator;
	private ArrayList<TreeView<T>> sons;
	private BorderPiece[] marks;
	
	private boolean opened = true;
	private BorderStyle style;
	
	public TreeView(T root, TreeViewPopulator<T> populator) {
		this(BorderStyle.unicode, root, populator, new BorderPiece[] { BorderPiece.da });
	}
	
	public TreeView(BorderStyle style, T root, TreeViewPopulator<T> populator) {
		this(style, root, populator, new BorderPiece[] { BorderPiece.da });
	}
	
	private TreeView(BorderStyle style, T root, TreeViewPopulator<T> populator, BorderPiece[] marks) {
		this.populator = populator;
		this.marks = marks;
		this.style = style;
		this.sons = new ArrayList<TreeView<T>>();
		setRoot(root);		
	}
	
	@Override
	protected void print(StringBuilder sb) {
		for (BorderPiece m : marks)
			sb.append(style.getPiece(m)).append(" ");
		
		sb.append(populator.getName(root)).append("\n");
		if (isOpened())
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
				BorderPiece[] newMarks = new BorderPiece[marks.length + 1];
				for (int m = 0; m < marks.length; m++) {
					switch (marks[m]) {
					case wds: case ws: newMarks[m] = BorderPiece.ws; break;
					case da: case wd: case none: default: newMarks[m] = BorderPiece.none; break;
					}
				}
				newMarks[newMarks.length - 1] = (i == newSons.length - 1 ? BorderPiece.wd : BorderPiece.wds);
				sons.add(new TreeView<T>(style, newSons[i], populator, newMarks));
			}
	}

	public ArrayList<TreeView<T>> getSons() {
		return sons;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	
	

}
