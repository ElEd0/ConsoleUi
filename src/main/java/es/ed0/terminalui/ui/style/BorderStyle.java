/**
 * Created by Ed0 in 22 feb. 2019
 */
package es.ed0.terminalui.ui.style;

/**
 * 
 */
public abstract class BorderStyle {

	/*
	public static BorderStyle sql = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "+", "|", "+", "+", "+", "+", "+", "+", "-", "+", "+", " " }[piece.toIndex()];
		}
	};

	public static BorderStyle unicode = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "└", "│", "┘", "├", "┤", "┴", "┼", "┌", "─", "┬", "┐", " " }[piece.toIndex()];
		}
	};

	public static BorderStyle hollow = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "╚", "║", "╝", "╠", "╣", "╩", "╬", "╔", "═", "╦", "╗", " " }[piece.toIndex()];
		}
	};*/
	

	//*
	public static BorderStyle NONE = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "", "", "", "", "", "", "", "", "", "", "", " " }[piece.toIndex()];
		}
	};
	//*/

	/**
	 * 	+-+-+\<br>
	 * 	| | |\<br>
	 * 	+-+-+\<br>
	 * 	+-+-+
	 */
	public static BorderStyle SQL = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "+", "-", "-", "+", "+", "|", "|", "+", "+", "+", "+", "+", "+", " " }[piece.toIndex()];
		}
	};

	/**
	 * 	┌─┬─┐\<br>
	 * 	│ │ │\<br>
	 * 	├─┼─┤\<br>
	 * 	└─┴─┘
	 */
	public static BorderStyle UNICODE = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "┌", "─", "─", "┬", "┐", "│", "│", "├", "┼", "┤", "└", "┴", "┘", " " }[piece.toIndex()];
		}
	};

	/**
	 * ┏━┳━┓\<br>
	 * ┃ ┃ ┃\<br>
	 * ┣━╋━┫\<br>
	 * ┗━┻━┛
	 */
	public static BorderStyle UNICODE_BOLD = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "┏", "━", "━", "┳", "┓", "┃", "┃", "┣", "╋", "┫", "┗", "┻", "┛", " " }[piece.toIndex()];
		}
	};

	/**
	 * ╭─┬─╮\<br>
	 * │ │ │\<br>
	 * ├─┼─┤\<br>
	 * ╰─┴─╯\
	 */
	public static BorderStyle UNICODE_ROUND = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "╭", "─", "─", "┬", "╮", "│", "│", "├", "┼", "┤", "╰", "┴", "╯", " " }[piece.toIndex()];
		}
	};

	/**
	 * ╔═╦═╗\<br>
	 * ║ ║ ║\<br>
	 * ╠═╬═╣\<br>
	 * ╚═╩═╝
	 */
	public static BorderStyle UNICODE_DOUBLE = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "╔", "═", "═", "╦", "╗", "║", "║", "╠", "╬", "╣", "╚", "╩", "╝", " " }[piece.toIndex()];
		}
	};

	/**
	 * ╔═╤═╗\<br>
	 * ║ │ ║\<br>
	 * ╟─┼─╢\<br>
	 * ╚═╧═╝
	 */
	public static BorderStyle UNICODE_DOUBLE_EXTERIOR = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "╔", "═", "─", "╤", "╗", "║", "│", "╟", "┼", "╢", "╚", "╧", "╝", " " }[piece.toIndex()];
		}
	};

	/**
	 * ┌─╥─┐\<br>
	 * │ ║ │\<br>
	 * ╞═╬═╡\<br>
	 * └─╨─┘
	 */
	public static BorderStyle UNICODE_DOUBLE_INTERIOR = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "┌", "─", "═", "╥", "┐", "│", "║", "╞", "╬", "╡", "└", "╨", "┘", " " }[piece.toIndex()];
		}
	};

	/**
	 * ╒═╤═╕\<br>
	 * │ │ │\<br>
	 * ╞═╪═╡\<br>
	 * ╘═╧═╛
	 */
	public static BorderStyle UNICODE_DOUBLE_HORIZONTAL = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "╒", "═", "═", "╤", "╕", "│", "│", "╞", "╪", "╡", "╘", "╧", "╛", " " }[piece.toIndex()];
		}
	};

	/**
	 * ╓─╥─╖\<br>
	 * ║ ║ ║\<br>
	 * ╟─╫─╢\<br>
	 * ╙─╨─╜
	 */
	public static BorderStyle UNICODE_DOUBLE_VERTICAL = new BorderStyle() {
		@Override
		public String getPiece(BorderPiece piece) {
			return new String[] { "╓", "─", "─", "╥", "╖", "║", "║", "╟", "╫", "╢", "╙", "╨", "╜", " " }[piece.toIndex()];
		}
	};



	public abstract String getPiece(BorderPiece piece);

	public enum BorderPiece {
		sd, ad, ad_in, asd, as,
		ws, ws_in, wsd, wasd, was,
		wd, wad, wa,
		none;
		public int toIndex() {
			switch (this) {
				case sd: return 0; case ad: return 1; case ad_in: return 2; case asd: return 3; case as: return 4;
				case ws: return 5; case ws_in: return 6; case wsd: return 7; case wasd: return 8; case was: return 9;
				case wd: return 10; case wad: return 11; case wa: return 12;
				case none: default: return 11;
			}
		}
	}
	
	
}
