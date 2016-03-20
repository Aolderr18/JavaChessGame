package Chess;

import java.util.ArrayList;

public class ChessBoard {
	private char[][] boardCode;
	private ArrayList<Character> playerOnePieceCodes, playerTwoPieceCodes;
	int i, j, v, w;
	int playerOneKingCoordinates, playerTwoKingCoordinates;

	public ChessBoard(char[][] boardCode, ArrayList<Character> playerOnePieceCodes,
			ArrayList<Character> playerTwoPieceCodes) {
		setBoardCode(boardCode);
		setPlayerOnePieceCodes(playerOnePieceCodes);
		setPlayerTwoPieceCodes(playerTwoPieceCodes);
		for (int x = 0; x < boardCode.length; ++x)
			for (int y = 0; y < boardCode[x].length; ++y) {
				if (boardCode[x][y] == 'K')
					playerOneKingCoordinates = (10 * (x + 1)) + y + 1;
				if (boardCode[x][y] == 'k')
					playerTwoKingCoordinates = (10 * (x + 1)) + y + 1;
			}
	}

	boolean playerOneInCheck() {
		w = playerOneKingCoordinates % 10;
		v = playerOneKingCoordinates - w;
		v /= 10;
		v -= 1;
		w -= 1;
		for (i = 0; i < 8; ++i)
			for (j = 0; j < 8; ++j) {
				if (playerTwoPieceCodes.contains(boardCode[i][j])) {
					try {
						new ChessMove(i, j, v, w, boardCode[i][j], 'K', boardCode, false, playerOnePieceCodes,
								playerTwoPieceCodes);
						return true;
					} catch (InvalidChessMoveException invalid) {

					} catch (ArrayIndexOutOfBoundsException outOfBounds) {

					}
				}
			}
		return false;
	}

	boolean playerTwoInCheck() {
		w = playerTwoKingCoordinates % 10;
		v = playerTwoKingCoordinates - w;
		v /= 10;
		v -= 1;
		w -= 1;
		for (i = 0; i < 8; ++i)
			for (j = 0; j < 8; ++j) {
				if (playerOnePieceCodes.contains(boardCode[i][j])) {
					try {
						new ChessMove(i, j, v, w, boardCode[i][j], 'k', boardCode, true, playerOnePieceCodes,
								playerTwoPieceCodes);
						return true;
					} catch (InvalidChessMoveException invalid) {

					} catch (ArrayIndexOutOfBoundsException outOfBounds) {

					}
				}
			}
		return false;
	}

	boolean playerOneInCheckMate() {
		for (i = 0; i < 8; ++i)
			for (j = 0; j < 8; ++j) {
				if (playerOnePieceCodes.contains(boardCode[i][j])) {
					VirtualMoves vm = new VirtualMoves(i, j, boardCode, boardCode[i][j], true, playerOnePieceCodes,
							playerTwoPieceCodes);
					if (!vm.stillInCheck)
						return false;
				}
			}
		return true;
	}

	boolean playerTwoInCheckMate() {
		for (i = 0; i < 8; ++i)
			for (j = 0; j < 8; ++j) {
				if (playerTwoPieceCodes.contains(boardCode[i][j])) {
					VirtualMoves vm = new VirtualMoves(i, j, boardCode, boardCode[i][j], false, playerOnePieceCodes,
							playerTwoPieceCodes);
					if (!vm.stillInCheck) {
						return false;
					}
				}
			}
		return true;
	}

	public void setBoardCode(char[][] boardCode) {
		this.boardCode = new char[boardCode.length][boardCode[0].length];
		this.boardCode = boardCode.clone();
	}

	public void setPlayerOnePieceCodes(ArrayList<Character> playerOnePieceCodes) {
		this.playerOnePieceCodes = new ArrayList<Character>();
		this.playerOnePieceCodes.addAll(playerOnePieceCodes);
	}

	public void setPlayerTwoPieceCodes(ArrayList<Character> playerTwoPieceCodes) {
		this.playerTwoPieceCodes = new ArrayList<Character>();
		this.playerTwoPieceCodes.addAll(playerTwoPieceCodes);
	}

	public char[][] getBoardCode() {
		return boardCode;
	}

	public ArrayList<Character> getPlayerOnePieceCodes() {
		return playerOnePieceCodes;
	}

	public ArrayList<Character> getPlayerTwoPieceCodes() {
		return playerTwoPieceCodes;
	}
}
