/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.terminalui.ui;

import java.io.File;
import java.util.ArrayList;

import es.ed0.terminalui.ui.style.BorderStyle;
import es.ed0.terminalui.ui.style.BorderStyle.BorderPiece;

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
		 * @param depth depth of item in tree (0 = root)
		 * @return text to print for this item
		 */
		public String getName(T item, int depth);
		/**
		 * Return a list of all the entries of the object type inside the entry <code>item</code>
		 * @param item parent entry
		 * @param depth depth of item in tree (0 = root)
		 * @return items inside the entry, to be printed in their respective node
		 */
		public T[] getChildren(T item, int depth);
	}
	
	
	private T root;
	private TreeViewPopulator<T> populator;
	private final ArrayList<TreeView<T>> sons;
	private final BorderPiece[] marks;
	private final int depth;
	
	private boolean opened = true;
	private boolean loaded = false;
	private BorderStyle style;
	
	public TreeView(T root, TreeViewPopulator<T> populator) {
		this(BorderStyle.UNICODE, root, populator, new BorderPiece[] { BorderPiece.ad });
	}
	
	public TreeView(BorderStyle style, T root, TreeViewPopulator<T> populator) {
		this(style, root, populator, new BorderPiece[] { BorderPiece.ad });
	}
	
	private TreeView(BorderStyle style, T root, TreeViewPopulator<T> populator, BorderPiece[] marks) {
		this.populator = populator;
		this.marks = marks;
		this.depth = marks.length - 1;
		this.style = style;
		this.sons = new ArrayList<TreeView<T>>();
		setRoot(root);
	}
	
	@Override
	protected void print(StringBuilder sb) {
		if (!this.loaded) {
			this.loadTree();
		}
		
		// create left and right paddings
		StringBuilder leftPadding = new StringBuilder(), rightPadding = new StringBuilder();
		StringBuilder leftPaddingMark = new StringBuilder(), rightPaddingMark = new StringBuilder();
		for (int p = 0; p < this.padding[3]; p++)  {
			leftPadding.append(this.getPaddingChar());
			leftPaddingMark.append(style.getPiece(BorderPiece.ad));
		}
		for (int p = 0; p < this.padding[1]; p++) {
			rightPadding.append(this.getPaddingChar());
			rightPaddingMark.append(style.getPiece(BorderPiece.ad));
		}
		
		// create top and bottom paddings
		StringBuilder bottomPadding = new StringBuilder(), topPadding = new StringBuilder();
		for (BorderPiece m : marks) {
			topPadding.append(leftPadding);
			bottomPadding.append(leftPadding);
			switch (m) {
			case wsd: case ws:
				topPadding.append(style.getPiece(BorderPiece.ws));
				bottomPadding.append(style.getPiece(BorderPiece.ws));
				break;
			case wd: 
				topPadding.append(style.getPiece(BorderPiece.ws));
				bottomPadding.append(style.getPiece(BorderPiece.none));
				break;
			case ad: case none: default:
				topPadding.append(style.getPiece(BorderPiece.none));
				bottomPadding.append(style.getPiece(BorderPiece.none));
				break;
			}
			topPadding.append(rightPadding);
			bottomPadding.append(rightPadding);
		}
		if (sons.size() > 0) {
			bottomPadding.append(leftPadding);
			bottomPadding.append(style.getPiece(BorderPiece.ws));
			bottomPadding.append(rightPadding);
		}

		// draw top padding
		if (this.padding[0] > 0) {
			for (int p = 0; p < this.padding[0]; p++) sb.append(topPadding).append("\n");
		}
		
		
		// draw symbols before text
		for (BorderPiece m : marks) {
			sb.append(leftPadding);
			sb.append(style.getPiece(m));
			if (m == BorderPiece.ad || m == BorderPiece.wsd || m == BorderPiece.wd)
				sb.append(rightPaddingMark);
			else
				sb.append(rightPadding);
		}

		// draw text
		sb.append(leftPaddingMark);
		sb.append(populator.getName(root, depth));
		sb.append(rightPadding);

		sb.append("\n");
		
		// draw bottom padding
		if (this.padding[2] > 0) {
			for (int p = 0; p < this.padding[2]; p++) sb.append(bottomPadding).append("\n");
		}
		
		// draw sons
		if (isOpened())
			for (TreeView<T> son : sons)
				son.print(sb);
	}
	
	public void loadTree() {
		sons.clear();
		final T[] newSons = populator.getChildren(root, depth);
		if (newSons != null) {
			for (int i = 0; i < newSons.length; i++) {
				BorderPiece[] newMarks = new BorderPiece[marks.length + 1];
				for (int m = 0; m < marks.length; m++) {
					switch (marks[m]) {
					case wsd: case ws: newMarks[m] = BorderPiece.ws; break;
					case ad: case wd: case none: default: newMarks[m] = BorderPiece.none; break;
					}
				}
				newMarks[newMarks.length - 1] = (i == newSons.length - 1 ? BorderPiece.wd : BorderPiece.wsd);
				final TreeView<T> sonTreeview = new TreeView<T>(style, newSons[i], populator, newMarks);
				sonTreeview.setAlignment(this.align);
				sonTreeview.setPadding(this.padding);
				sonTreeview.setPaddingChar(this.paddingChar);
				sons.add(sonTreeview);
			}
		}
		this.loaded = true;
	}

	public T getRoot() {
		return root;
	}

	public void setRoot(T root) {
		this.root = root;
		this.loaded = false;
	}

	public void setPopulator(TreeViewPopulator<T> populator) {
		this.populator = populator;
	}

	public ArrayList<TreeView<T>> getChildren() {
		return sons;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public boolean isLoaded() {
		return loaded;
	}
	
	

}
