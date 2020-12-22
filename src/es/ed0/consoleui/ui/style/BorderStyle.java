/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.consoleui.ui.style;

/**
 * 
 */
public abstract class BorderStyle {

	/**
	 * +----------------------+<br>
	 * |                      |<br>
	 * +----------------------+
	 */
	public static BorderStyle sql = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "+", "|", "+", "+", "+", "+", "+", "+", "-", "+", "+", " " }[piece.toIndex()];
		}
	};

	/**
	 * ┏━━━━━━━━━━━━━━━━━━━━━━┓<br>
	 * ┃                      ┃<br>
	 * ┗━━━━━━━━━━━━━━━━━━━━━━┛
	 */
	public static BorderStyle unicode = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "└", "│", "┘", "├", "┤", "┴", "┼", "┌", "─", "┬", "┐", " " }[piece.toIndex()];
		}
	};

	/**
	 * ╔══════════════════════╗<br>
	 * ║                      ║<br>
	 * ╚══════════════════════╝
	 */
	public static BorderStyle hollow = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "╚", "║", "╝", "╠", "╣", "╩", "╬", "╔", "═", "╦", "╗", " " }[piece.toIndex()];
		}
	};
	

	public static BorderStyle none = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "", "", "", "", "", "", "", "", "", "", "", " " }[piece.toIndex()];
		}
	};
	
	public abstract String getPiece(BorderPiece piece);

	public enum BorderPiece {
		wd, ws, wa, wds, wsa, wda, wdsa,
		ds, da, dsa,
		sa, none;
		public int toIndex() {
			switch (this) {
			case wd: return 0; case ws: return 1; case wa: return 2;
			case wds: return 3; case wsa: return 4; case wda: return 5;
			case wdsa: return 6; case ds: return 7; case da: return 8;
			case dsa: return 9; case sa: return 10; case none: return 11;
			default: return 0;
			}
		}
	}
	
	
}
