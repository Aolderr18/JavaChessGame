/**
 * The purpose of this class is to create a move in chess as an object. The methods in this class
 * are designed to evaluate the instance of an attempted move. Boolean values are returned to tell
 * whether or not the move is legal.
*/
package Chess;

import java.util.ArrayList;

public class ChessMove {
	private int i, j, v, w;
	private char selectionCode, destinationCode;
	private char[][] boardCode;
	private boolean isPlayerOneTurn;
	private ArrayList<Character> playerOnePieceCodes, playerTwoPieceCodes;
	boolean validCastlingMove;
	InvalidChessMoveException invalid;
	int dX, dY;
	int distance;
	int iteration;

	public ChessMove(int i, int j, int v, int w, char selectionCode, char destinationCode, char[][] boardCode,
			boolean isPlayerOneTurn, ArrayList<Character> playerOnePieceCodes, ArrayList<Character> playerTwoPieceCodes)
					throws InvalidChessMoveException {
		setI(i);
		setJ(j);
		setV(v);
		setW(w);
		setSelectionCode(selectionCode);
		setDestinationCode(destinationCode);
		setBoardCode(boardCode);
		setIsPlayerOneTurn(isPlayerOneTurn);
		setPlayerOnePieceCodes(playerOnePieceCodes);
		setPlayerTwoPieceCodes(playerTwoPieceCodes);
		invalid = new InvalidChessMoveException();
		// Ensure a player cannot attack their own pieces.
		if (!isPlayerOneTurn) {
			if (playerTwoPieceCodes.contains(destinationCode))
				throw invalid;
		} else {
			if (playerOnePieceCodes.contains(destinationCode))
				throw invalid;
		}
		switch (selectionCode) {
		case 'k':
			if (i == 7 && j == 4)
				validCastlingMove = v == 7 && w == 6 && boardCode[7][7] == 'r';
			break;
		case 'r':
			if (i == 7 && j == 7)
				validCastlingMove = v == 7 && w == 5 && boardCode[7][4] == 'k';
			break;
		case 'K':
			if (i == 0 && j == 4)
				validCastlingMove = v == 0 && w == 6 && boardCode[0][7] == 'R';
			break;
		case 'R':
			if (i == 0 && j == 7)
				validCastlingMove = v == 0 && w == 5 && boardCode[0][4] == 'K';
		}
		if (!isValid() || (i == v && j == w))
			throw invalid;
	}

	boolean isValid() {
		if (validCastlingMove) {
			try {
				searchPath();
				return true;
			} catch (InvalidChessMoveException invalid) {
				return false;
			}
		}
		// Ensure that a player must select from their own pieces.
		if (!isPlayerOneTurn) {
			if (playerOnePieceCodes.contains(selectionCode))
				return false;
		} else if (playerTwoPieceCodes.contains(selectionCode))
			return false;
		/*
		 * Different boolean finding algorithms are designed here for the
		 * different rules of moving different pieces.
		 */
		switch (selectionCode) {
		case 'R':
		case 'r':
			try {
				searchPath();
				return i == v || j == w;
			} catch (InvalidChessMoveException invalid) {
				return false;
			}
		case 'N':
		case 'n':
			return Math.abs((i - v) * (j - w)) == 2;
		case 'B':
		case 'b':
			try {
				searchPath();
				return Math.abs(i - v) == Math.abs(j - w);
			} catch (InvalidChessMoveException invalid) {
				return false;
			}
		case 'Q':
		case 'q':
			try {
				searchPath();
				return i == v || j == w || Math.abs(i - v) == Math.abs(j - w);
			} catch (InvalidChessMoveException invalid) {
				return false;
			}
		case 'K':
		case 'k':
			return Math.abs(i - v) < 2 && Math.abs(j - w) < 2;
		case 'P':
		case 'p':
			try {
				searchPath();
				if (!isPlayerOneTurn) {
					if (playerOnePieceCodes.contains(destinationCode))
						return v == i - 1 && Math.abs(j - w) == 1;
					if (i == 6) { 
						return j == w && v > 3 && tileEmpty()[v][w];
					} else
						return j == w && v == i - 1 && tileEmpty()[v][w];
				} else {
					if (playerTwoPieceCodes.contains(destinationCode))
						return v == i + 1 && Math.abs(j - w) == 1;
					if (i == 1) {
						return j == w && v < 4 && tileEmpty()[v][w];
					} else
						return j == w && v == i + 1 && tileEmpty()[v][w];
				}
			} catch (InvalidChessMoveException invalid) {
				return false;
			}
		default:
			return false;
		}
	}

	boolean[][] tileEmpty() { /*
								 * A boolean array that shows which tiles are
								 * unoccupied
								 */
		boolean[][] tileEmpty = new boolean[boardCode.length][boardCode[0].length];
		for (int i = 0; i < tileEmpty.length; ++i)
			for (int j = 0; j < tileEmpty[i].length; ++j)
				if (!(playerOnePieceCodes.contains(boardCode[i][j]) || playerTwoPieceCodes.contains(boardCode[i][j])))
					tileEmpty[i][j] = true;
		return tileEmpty;
	}

	/*
	 * Most move attempts require a path to be open to proceed.
	 */
	void searchPath() throws InvalidChessMoveException {
		distance = Math.abs(i - v);
		if (i > v)
			dX = -1;
		else if (i < v)
			dX = 1;
		else
			distance = Math.abs(j - w);
		if (j > w)
			dY = -1;
		else if (j < w)
			dY = 1;
		iteration = 1;
		while (iteration < distance) {
			if (!tileEmpty()[i + (iteration * dX)][j + (iteration * dY)])
				throw invalid;
			++iteration;
		}
	}
	
	// Create mutators.
	
	public void setI(int i) {
		this.i = i;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public void setV(int v) {
		this.v = v;
	}

	public void setW(int w) {
		this.w = w;
	}

	public void setSelectionCode(char selectionCode) {
		this.selectionCode = selectionCode;
	}

	public void setDestinationCode(char destinationCode) {
		this.destinationCode = destinationCode;
	}

	public void setBoardCode(char[][] boardCode) {
		this.boardCode = new char[boardCode.length][boardCode[0].length];
		this.boardCode = boardCode.clone();
	}

	public void setIsPlayerOneTurn(boolean isPlayerOneTurn) {
		this.isPlayerOneTurn = isPlayerOneTurn;
	}

	public void setPlayerOnePieceCodes(ArrayList<Character> playerOnePieceCodes) {
		this.playerOnePieceCodes = new ArrayList<Character>();
		this.playerOnePieceCodes.addAll(playerOnePieceCodes);
	}

	public void setPlayerTwoPieceCodes(ArrayList<Character> playerTwoPieceCodes) {
		this.playerTwoPieceCodes = new ArrayList<Character>();
		this.playerTwoPieceCodes.addAll(playerTwoPieceCodes);
	}

	// Create accessors.
	
	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public int getV() {
		return v;
	}

	public int getW() {
		return w;
	}

	public char getSelectionCode() {
		return selectionCode;
	}

	public char getDestinationCode() {
		return destinationCode;
	}

	public char[][] getBoardCode() {
		return boardCode;
	}

	public boolean getIsPlayerOneTurn() {
		return isPlayerOneTurn;
	}

	public ArrayList<Character> getPlayerOnePieceCodes() {
		return playerOnePieceCodes;
	}

	public ArrayList<Character> getPlayerTwoPieceCodes() {
		return playerTwoPieceCodes;
	}
}
