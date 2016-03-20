package Chess;

import java.util.ArrayList;

/**
 * This class was made to determine if a player is in check mate. What is does
 * is find each possible move a given player can make and then determines if
 * that possible move will bring that player out of check.
 */
public class VirtualMoves {
	private int i, j;
	private char[][] boardCode;
	private char selectionCode;
	private boolean isPlayerOneTurn;
	private char[][] virtualBoardCode;
	private ArrayList<Character> playerOnePieceCodes, playerTwoPieceCodes;
	int v, w;
	char destinationCode;
	boolean stillInCheck;
	ChessBoard virtualChessBoard;

	public VirtualMoves(int i, int j, char[][] boardCode, char selectionCode, boolean isPlayerOneTurn,
			ArrayList<Character> playerOnePieceCodes, ArrayList<Character> playerTwoPieceCodes) {
		setI(i);
		setJ(j);
		setBoardCode(boardCode);
		setSelectionCode(selectionCode);
		setIsPlayerOneTurn(isPlayerOneTurn);
		setPlayerOnePieceCodes(playerOnePieceCodes);
		setPlayerTwoPieceCodes(playerTwoPieceCodes);
		virtualBoardCode = new char[boardCode.length][boardCode[0].length];
		virtualBoardCode = boardCode.clone();
		stillInCheck = true;
		virtualChessBoard = new ChessBoard(boardCode, playerOnePieceCodes, playerTwoPieceCodes);
		switch (selectionCode) {
		case 'R':
		case 'r':
			runAllVirtualRookMoves();
			break;
		case 'N':
		case 'n':
			runAllVirtualKnightMoves();
			break;
		case 'B':
		case 'b':
			runAllVirtualBishopMoves();
			break;
		case 'Q':
		case 'q':
			runAllVirtualQueenMoves();
			break;
		case 'K':
		case 'k':
			runAllVirtualKingMoves();
			break;
		case 'P':
		case 'p':
			runAllVirtualPawnMoves();
		}
	}

	void runAllVirtualRookMoves() {
		w = j;
		for (v = i + 1; v <= i + 8; ++v) {// Moves in the vertical direction
			try {
				new ChessMove(i, j, v % 8, w, selectionCode, boardCode[v % 8][w], boardCode, isPlayerOneTurn,
						playerOnePieceCodes, playerTwoPieceCodes);
				virtualBoardCode[i][j] = ' ';
				destinationCode = virtualBoardCode[v % 8][w];
				virtualBoardCode[v % 8][w] = selectionCode;
				virtualChessBoard.setBoardCode(virtualBoardCode);
				if (!isPlayerOneTurn) {
					if (!virtualChessBoard.playerTwoInCheck())
						stillInCheck = false;
				} else {
					if (!virtualChessBoard.playerOneInCheck())
						stillInCheck = false;
				}
				virtualBoardCode[v % 8][w] = destinationCode;
				virtualBoardCode[i][j] = selectionCode;
			} catch (InvalidChessMoveException invalid) {

			} catch (ArrayIndexOutOfBoundsException outOfBounds) {

			}
			virtualBoardCode = boardCode.clone();
		}
		v = i;
		for (w = j + 1; w <= j + 8; ++w) {// Moves in the horizontal direction
			try {
				new ChessMove(i, j, v, w % 8, selectionCode, boardCode[v][w % 8], boardCode, isPlayerOneTurn,
						playerOnePieceCodes, playerTwoPieceCodes);
				virtualBoardCode[i][j] = ' ';
				destinationCode = virtualBoardCode[v % 8][w];
				virtualBoardCode[v % 8][w] = selectionCode;
				virtualChessBoard.setBoardCode(virtualBoardCode);
				if (!isPlayerOneTurn) {
					if (!virtualChessBoard.playerTwoInCheck())
						stillInCheck = false;
				} else {
					if (!virtualChessBoard.playerOneInCheck())
						stillInCheck = false;
				}
				virtualBoardCode[v % 8][w] = destinationCode;
				virtualBoardCode[i][j] = selectionCode;
			} catch (InvalidChessMoveException invalid) {

			} catch (ArrayIndexOutOfBoundsException outOfBounds) {

			}
			virtualBoardCode = boardCode.clone();
		}
	}

	void runAllVirtualKnightMoves() { /*
										 * There are 8 different possible moves
										 * of the knight.
										 */
		int possibleMoveIndex = 0;
		while (possibleMoveIndex < 8) {
			switch (possibleMoveIndex) {
			case 0:
				v = i + 1;
				w = j + 2;
				break;
			case 1:
				v = i + 1;
				w = j - 2;
				break;
			case 2:
				v = i - 1;
				w = j + 2;
				break;
			case 3:
				v = i - 1;
				w = j - 2;
				break;
			case 4:
				v = i + 2;
				w = j + 1;
				break;
			case 5:
				v = i + 2;
				w = j - 1;
				break;
			case 6:
				v = i - 2;
				w = j + 1;
				break;
			case 7:
				v = i - 2;
				w = j - 1;
			}
			++possibleMoveIndex;
			try {
				new ChessMove(i, j, v, w, selectionCode, boardCode[v][w], boardCode, isPlayerOneTurn,
						playerOnePieceCodes, playerTwoPieceCodes);
				virtualBoardCode[i][j] = ' ';
				destinationCode = virtualBoardCode[v % 8][w];
				virtualBoardCode[v % 8][w] = selectionCode;
				virtualChessBoard.setBoardCode(virtualBoardCode);
				if (!isPlayerOneTurn) {
					if (!virtualChessBoard.playerTwoInCheck())
						stillInCheck = false;
				} else {
					if (!virtualChessBoard.playerOneInCheck())
						stillInCheck = false;
				}
				virtualBoardCode[v % 8][w] = destinationCode;
				virtualBoardCode[i][j] = selectionCode;
			} catch (InvalidChessMoveException invalid) {

			} catch (ArrayIndexOutOfBoundsException outOfBounds) {

			}
			virtualBoardCode = boardCode.clone();
		}
	}

	void runAllVirtualBishopMoves() {
		int trial;
		trial = 1;
		while (trial <= 8) {
			v = i + trial;
			w = j + trial;
			++trial;
			try {
				new ChessMove(i, j, v % 8, w % 8, selectionCode, boardCode[v % 8][w % 8], boardCode, isPlayerOneTurn,
						playerOnePieceCodes, playerTwoPieceCodes);
				virtualBoardCode[i][j] = ' ';
				destinationCode = virtualBoardCode[v % 8][w];
				virtualBoardCode[v % 8][w] = selectionCode;
				virtualChessBoard.setBoardCode(virtualBoardCode);
				if (!isPlayerOneTurn) {
					if (!virtualChessBoard.playerTwoInCheck())
						stillInCheck = false;
				} else {
					if (!virtualChessBoard.playerOneInCheck())
						stillInCheck = false;
				}
				virtualBoardCode[v % 8][w] = destinationCode;
				virtualBoardCode[i][j] = selectionCode;
			} catch (InvalidChessMoveException invalid) {

			} catch (ArrayIndexOutOfBoundsException outOfBounds) {

			}
			virtualBoardCode = boardCode.clone();
		}
		trial = 1;
		while (trial <= 8) {
			v = i + trial;
			w = j - trial;
			++trial;
			try {
				new ChessMove(i, j, v % 8, w % 8, selectionCode, boardCode[v % 8][w % 8], boardCode, isPlayerOneTurn,
						playerOnePieceCodes, playerTwoPieceCodes);
				virtualBoardCode[i][j] = ' ';
				destinationCode = virtualBoardCode[v % 8][w];
				virtualBoardCode[v % 8][w] = selectionCode;
				virtualChessBoard.setBoardCode(virtualBoardCode);
				if (!isPlayerOneTurn) {
					if (!virtualChessBoard.playerTwoInCheck())
						stillInCheck = false;
				} else {
					if (!virtualChessBoard.playerOneInCheck())
						stillInCheck = false;
				}
				virtualBoardCode[v % 8][w] = destinationCode;
				virtualBoardCode[i][j] = selectionCode;
			} catch (InvalidChessMoveException invalid) {

			} catch (ArrayIndexOutOfBoundsException outOfBounds) {

			}
			virtualBoardCode = boardCode.clone();
		}
	}

	void runAllVirtualQueenMoves() { /*
										 * The virtual queen moves must follow
										 * the previous algorithms used in the
										 * virtual rook moves and virtual bishop
										 * moves.
										 */
		int trial;
		trial = 1;
		while (trial <= 8) {
			v = i + trial;
			w = j + trial;
			++trial;
			try {
				new ChessMove(i, j, v % 8, w % 8, selectionCode, boardCode[v % 8][w % 8], boardCode, isPlayerOneTurn,
						playerOnePieceCodes, playerTwoPieceCodes);
				virtualBoardCode[i][j] = ' ';
				destinationCode = virtualBoardCode[v % 8][w];
				virtualBoardCode[v % 8][w] = selectionCode;
				virtualChessBoard.setBoardCode(virtualBoardCode);
				if (!isPlayerOneTurn) {
					if (!virtualChessBoard.playerTwoInCheck())
						stillInCheck = false;
				} else {
					if (!virtualChessBoard.playerOneInCheck())
						stillInCheck = false;
				}
				virtualBoardCode[v % 8][w] = destinationCode;
				virtualBoardCode[i][j] = selectionCode;
			} catch (InvalidChessMoveException invalid) {

			} catch (ArrayIndexOutOfBoundsException outOfBounds) {

			}
			virtualBoardCode = boardCode.clone();
		}
		trial = 1;
		while (trial <= 8) {
			v = i + trial;
			w = j - trial;
			++trial;
			try {
				new ChessMove(i, j, v % 8, w % 8, selectionCode, boardCode[v % 8][w % 8], boardCode, isPlayerOneTurn,
						playerOnePieceCodes, playerTwoPieceCodes);
				virtualBoardCode[i][j] = ' ';
				destinationCode = virtualBoardCode[v % 8][w];
				virtualBoardCode[v % 8][w] = selectionCode;
				virtualChessBoard.setBoardCode(virtualBoardCode);
				if (!isPlayerOneTurn) {
					if (!virtualChessBoard.playerTwoInCheck())
						stillInCheck = false;
				} else {
					if (!virtualChessBoard.playerOneInCheck())
						stillInCheck = false;
				}
				virtualBoardCode[v % 8][w] = destinationCode;
				virtualBoardCode[i][j] = selectionCode;
			} catch (InvalidChessMoveException invalid) {

			} catch (ArrayIndexOutOfBoundsException outOfBounds) {

			}
			virtualBoardCode = boardCode.clone();
		}
		w = j;
		for (v = i + 1; v <= i + 8; ++v) {// Moves in the vertical direction
			try {
				new ChessMove(i, j, v % 8, w, selectionCode, boardCode[v % 8][w], boardCode, isPlayerOneTurn,
						playerOnePieceCodes, playerTwoPieceCodes);
				virtualBoardCode[i][j] = ' ';
				destinationCode = virtualBoardCode[v % 8][w];
				virtualBoardCode[v % 8][w] = selectionCode;
				virtualChessBoard.setBoardCode(virtualBoardCode);
				if (!isPlayerOneTurn) {
					if (!virtualChessBoard.playerTwoInCheck())
						stillInCheck = false;
				} else {
					if (!virtualChessBoard.playerOneInCheck())
						stillInCheck = false;
				}
				virtualBoardCode[v % 8][w] = destinationCode;
				virtualBoardCode[i][j] = selectionCode;
			} catch (InvalidChessMoveException invalid) {

			} catch (ArrayIndexOutOfBoundsException outOfBounds) {

			}
			virtualBoardCode = boardCode.clone();
		}
		v = i;
		for (w = j + 1; w <= j + 8; ++w) {// Moves in the horizontal direction
			try {
				new ChessMove(i, j, v, w % 8, selectionCode, boardCode[v][w % 8], boardCode, isPlayerOneTurn,
						playerOnePieceCodes, playerTwoPieceCodes);
				virtualBoardCode[i][j] = ' ';
				destinationCode = virtualBoardCode[v % 8][w];
				virtualBoardCode[v % 8][w] = selectionCode;
				virtualChessBoard.setBoardCode(virtualBoardCode);
				if (!isPlayerOneTurn) {
					if (!virtualChessBoard.playerTwoInCheck())
						stillInCheck = false;
				} else {
					if (!virtualChessBoard.playerOneInCheck())
						stillInCheck = false;
				}
				virtualBoardCode[v % 8][w] = destinationCode;
				virtualBoardCode[i][j] = selectionCode;
			} catch (InvalidChessMoveException invalid) {

			} catch (ArrayIndexOutOfBoundsException outOfBounds) {

			}
			virtualBoardCode = boardCode.clone();
		}
	}

	void runAllVirtualKingMoves() {
		int possibleMoveIndex = 0;
		while (possibleMoveIndex < 8) {
			switch (possibleMoveIndex) {
			case 0:
				v = i + 1;
				w = j + 1;
				break;
			case 1:
				v = i + 1;
				w = j;
				break;
			case 2:
				v = i + 1;
				w = j - 1;
				break;
			case 3:
				v = i;
				w = j - 1;
				break;
			case 4:
				v = i - 1;
				w = j - 1;
				break;
			case 5:
				v = i - 1;
				w = j;
				break;
			case 6:
				v = i - 1;
				w = j - 1;
				break;
			case 7:
				v = i;
				w = j + 1;
			}
			++possibleMoveIndex;
			try {
				new ChessMove(i, j, v, w, selectionCode, boardCode[v][w], boardCode, isPlayerOneTurn,
						playerOnePieceCodes, playerTwoPieceCodes);
				virtualBoardCode[i][j] = ' ';
				destinationCode = virtualBoardCode[v % 8][w];
				virtualBoardCode[v % 8][w] = selectionCode;
				virtualChessBoard.setBoardCode(virtualBoardCode);
				if (!isPlayerOneTurn) {
					if (!virtualChessBoard.playerTwoInCheck()) {
						stillInCheck = false;
					}
				} else {
					if (!virtualChessBoard.playerOneInCheck()) {
						stillInCheck = false;
					}
				}
				virtualBoardCode[v % 8][w] = destinationCode;
				virtualBoardCode[i][j] = selectionCode;
			} catch (InvalidChessMoveException invalid) {

			} catch (ArrayIndexOutOfBoundsException outOfBounds) {

			}
			virtualBoardCode = boardCode.clone();
		}
	}

	void runAllVirtualPawnMoves() {
		int possibleMoveIndex = 0;
		if (!isPlayerOneTurn) {
			if (i == 6) {
				while (possibleMoveIndex < 4) {
					switch (possibleMoveIndex) {
					case 0:
						v = 4;
						w = j;
						break;
					case 1:
						v = 5;
						w = j;
						break;
					case 2:
						v = 5;
						w = j - 1;
						break;
					case 3:
						v = 5;
						w = j + 1;
					}
					++possibleMoveIndex;
					try {
						new ChessMove(i, j, v, w, selectionCode, boardCode[v][w], boardCode, isPlayerOneTurn,
								playerOnePieceCodes, playerTwoPieceCodes);
						virtualBoardCode[i][j] = ' ';
						destinationCode = virtualBoardCode[v % 8][w];
						virtualBoardCode[v % 8][w] = selectionCode;
						virtualChessBoard.setBoardCode(virtualBoardCode);
						if (!virtualChessBoard.playerTwoInCheck())
							stillInCheck = false;
						virtualBoardCode[v % 8][w] = destinationCode;
						virtualBoardCode[i][j] = selectionCode;
					} catch (InvalidChessMoveException invalid) {

					} catch (ArrayIndexOutOfBoundsException outOfBounds) {

					}
					virtualBoardCode = boardCode.clone();
				}
			} else {
				v = i - 1;
				while (possibleMoveIndex < 3) {
					switch (possibleMoveIndex) {
					case 0:
						w = j - 1;
						break;
					case 1:
						w = j;
						break;
					case 2:
						w = j + 1;
					}
					++possibleMoveIndex;
					try {
						new ChessMove(i, j, v, w, selectionCode, boardCode[v][w], boardCode, isPlayerOneTurn,
								playerOnePieceCodes, playerTwoPieceCodes);
						virtualBoardCode[i][j] = ' ';
						destinationCode = virtualBoardCode[v % 8][w];
						virtualBoardCode[v % 8][w] = selectionCode;
						virtualChessBoard.setBoardCode(virtualBoardCode);
						if (!virtualChessBoard.playerTwoInCheck())
							stillInCheck = false;
						virtualBoardCode[v % 8][w] = destinationCode;
						virtualBoardCode[i][j] = selectionCode;
					} catch (InvalidChessMoveException invalid) {

					} catch (ArrayIndexOutOfBoundsException outOfBounds) {

					}
					virtualBoardCode = boardCode.clone();
				}
			}
		} else {
			if (i == 1) {
				while (possibleMoveIndex < 4) {
					switch (possibleMoveIndex) {
					case 0:
						v = 3;
						w = j;
						break;
					case 1:
						v = 2;
						w = j;
						break;
					case 2:
						v = 2;
						w = j - 1;
						break;
					case 3:
						v = 2;
						w = j + 1;
					}
					++possibleMoveIndex;
					try {
						new ChessMove(i, j, v, w, selectionCode, boardCode[v][w], boardCode, isPlayerOneTurn,
								playerOnePieceCodes, playerTwoPieceCodes);
						virtualBoardCode[i][j] = ' ';
						destinationCode = virtualBoardCode[v % 8][w];
						virtualBoardCode[v % 8][w] = selectionCode;
						virtualChessBoard.setBoardCode(virtualBoardCode);
						if (!virtualChessBoard.playerTwoInCheck())
							stillInCheck = false;
						virtualBoardCode[v % 8][w] = destinationCode;
						virtualBoardCode[i][j] = selectionCode;
					} catch (InvalidChessMoveException invalid) {

					} catch (ArrayIndexOutOfBoundsException outOfBounds) {

					}
					virtualBoardCode = boardCode.clone();
				}
			} else {
				v = i + 1;
				while (possibleMoveIndex < 3) {
					switch (possibleMoveIndex) {
					case 0:
						w = j - 1;
						break;
					case 1:
						w = j;
						break;
					case 2:
						w = j + 1;
					}
					++possibleMoveIndex;
					try {
						new ChessMove(i, j, v, w, selectionCode, boardCode[v][w], boardCode, isPlayerOneTurn,
								playerOnePieceCodes, playerTwoPieceCodes);
						virtualBoardCode[i][j] = ' ';
						destinationCode = virtualBoardCode[v % 8][w];
						virtualBoardCode[v % 8][w] = selectionCode;
						virtualChessBoard.setBoardCode(virtualBoardCode);
						if (!virtualChessBoard.playerTwoInCheck())
							stillInCheck = false;
						virtualBoardCode[v % 8][w] = destinationCode;
						virtualBoardCode[i][j] = selectionCode;
					} catch (InvalidChessMoveException invalid) {

					} catch (ArrayIndexOutOfBoundsException outOfBounds) {

					}
					virtualBoardCode = boardCode.clone();
				}
			}
		}
	}

	public void setI(int i) {
		this.i = i;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public void setBoardCode(char[][] boardCode) {
		this.boardCode = new char[boardCode.length][boardCode[0].length];
		this.boardCode = boardCode.clone();
	}

	public void setSelectionCode(char selectionCode) {
		this.selectionCode = selectionCode;
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

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public char[][] getBoardCode() {
		return boardCode;
	}

	public char getSelectionCode() {
		return selectionCode;
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
