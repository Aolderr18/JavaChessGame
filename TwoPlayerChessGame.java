/**
 * This program is a simple two player electronic chess game. 
 */
package Chess;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.util.Scanner;

public class TwoPlayerChessGame {
	static JFrame gameWindow;
	static JButton tile;
	static Container pane;
	static Color tileColor;
	static GridLayout layout;
	static ChessBoard board;
	static ArrayList<Character> playerOnePieceCodes, playerTwoPieceCodes;
	static String playerOneName;
	static String playerTwoName;
	static int i, j, v, w;
	static int x, y;
	static int compilationStep;
	static char selectionCode, destinationCode;
	static boolean isPlayerOneTurn;
	static boolean playerOneCheckMode, playerTwoCheckMode;
	static boolean gameActive;

	private static void playRound() {
		compilationStep %= 2;
		if (playerOneCheckMode)
			if (board.playerOneInCheckMate()) {
				System.out.println("That's check mate, " + playerOneName + "!");
				System.out.println(playerTwoName + " wins the game!");
				endGame();
				gameActive = false;
			} else {
				playerOneCheckMode = false;
			}
		if (playerTwoCheckMode)
			if (board.playerTwoInCheckMate()) {
				System.out.println("That's check mate, " + playerTwoName + "!");
				System.out.println(playerOneName + " wins the game!");
				endGame();
				gameActive = false;
			} else {
				playerTwoCheckMode = false;
			}
		if (gameActive) {
			gameWindow = new JFrame();
			gameWindow.setSize(400, 400);
			pane = gameWindow.getContentPane();
			layout = new GridLayout(8, 8);
			pane.setLayout(layout);
			pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			for (x = 0; x < 8; ++x)
				for (y = 0; y < 8; ++y) {
					if ((x + y) % 2 == 0)
						tileColor = Color.green;
					else
						tileColor = Color.cyan;
					tile = new JButton("" + board.getBoardCode()[x][y]);
					tile.setSize(50, 50);
					tile.setBackground(tileColor);
					tile.addActionListener(new ActionListener() {
						final int X = x;
						final int Y = y;

						public void actionPerformed(ActionEvent E) {
							char[][] boardCode = board.getBoardCode();
							if (compilationStep == 0) {
								gameWindow.dispose();
								i = X;
								j = Y;
								selectionCode = boardCode[i][j];
								boardCode[i][j] = ' ';
								board.setBoardCode(boardCode);
								++compilationStep;
								playRound();
							} else {
								gameWindow.dispose();
								v = X;
								w = Y;
								destinationCode = boardCode[v][w];
								try {
									ChessMove move = new ChessMove(i, j, v, w, selectionCode, destinationCode,
											boardCode, isPlayerOneTurn, playerOnePieceCodes, playerTwoPieceCodes);
									boardCode[v][w] = selectionCode;
									isPlayerOneTurn = !isPlayerOneTurn;
									if (move.validCastlingMove)
										switch (selectionCode) {
										case 'r':
											boardCode[7][4] = ' ';
											boardCode[7][5] = selectionCode;
											boardCode[7][6] = 'k';
											break;
										case 'k':
											boardCode[7][7] = ' ';
											boardCode[7][6] = selectionCode;
											boardCode[7][5] = 'r';
											break;
										case 'R':
											boardCode[0][5] = selectionCode;
											boardCode[0][6] = 'K';
											boardCode[0][4] = ' ';
											break;
										case 'K':
											boardCode[0][7] = ' ';
											boardCode[0][6] = selectionCode;
											boardCode[0][5] = 'R';
											break;
										}
								} catch (InvalidChessMoveException invalid) {
									System.out.println("That move is invalid.");
									boardCode[i][j] = selectionCode;
								}
								board = new ChessBoard(boardCode, playerOnePieceCodes, playerTwoPieceCodes);
								if (board.playerOneInCheck()) {
									System.out.println("Player one is in check.");
									playerOneCheckMode = true;
									if (!isPlayerOneTurn) {
										System.out.println("That is not a valid move.");
										isPlayerOneTurn = true;
										boardCode[i][j] = selectionCode;
										boardCode[v][w] = destinationCode;
									}
								} else
									playerOneCheckMode = false;
								if (board.playerTwoInCheck()) {
									System.out.println("Player two is in check.");
									playerTwoCheckMode = true;
									if (isPlayerOneTurn) {
										System.out.println("That is not a valid move.");
										isPlayerOneTurn = false;
										boardCode[i][j] = selectionCode;
										boardCode[v][w] = destinationCode;
									}
								} else
									playerTwoCheckMode = false;
								++compilationStep;
								playRound();
							}
						}
					});
					pane.add(tile);
				}
			gameWindow.setVisible(true);
		}
	}

	public static void main(String[] args) {
		char[][] boardCode = new char[8][8];
		boardCode[0][0] = 'R';
		boardCode[0][7] = 'R';
		boardCode[0][1] = 'N';
		boardCode[0][6] = 'N';
		boardCode[0][2] = 'B';
		boardCode[0][5] = 'B';
		boardCode[0][3] = 'Q';
		boardCode[0][4] = 'K';
		for (j = 0; j < boardCode[0].length; ++j) {
			boardCode[1][j] = 'P';
			boardCode[6][j] = 'p';
		}
		boardCode[7][0] = 'r';
		boardCode[7][7] = 'r';
		boardCode[7][1] = 'n';
		boardCode[7][6] = 'n';
		boardCode[7][2] = 'b';
		boardCode[7][5] = 'b';
		boardCode[7][3] = 'q';
		boardCode[7][4] = 'k';
		playerOnePieceCodes = new ArrayList<Character>();
		playerOnePieceCodes.add('R');
		playerOnePieceCodes.add('N');
		playerOnePieceCodes.add('B');
		playerOnePieceCodes.add('Q');
		playerOnePieceCodes.add('K');
		playerOnePieceCodes.add('P');
		playerTwoPieceCodes = new ArrayList<Character>();
		playerTwoPieceCodes.add('r');
		playerTwoPieceCodes.add('n');
		playerTwoPieceCodes.add('b');
		playerTwoPieceCodes.add('q');
		playerTwoPieceCodes.add('k');
		playerTwoPieceCodes.add('p');
		board = new ChessBoard(boardCode, playerOnePieceCodes, playerTwoPieceCodes);
		isPlayerOneTurn = true;
		playerOneCheckMode = false;
		playerTwoCheckMode = false;
		Scanner userInput = new Scanner(System.in);
		System.out.println("What is the name of player 1?");
		playerOneName = userInput.next();
		System.out.println("What is the name of player 2?");
		playerTwoName = userInput.next();
		userInput.close();
		compilationStep = 0;
		gameActive = true;
		playRound();
	}

	private static void endGame() {
		gameWindow.dispose();
		System.out.println("Nicely done!");
	}
}
