package connect4;

import connect4.characters.CharacterFactory;
import connect4.characters.Player;
import connect4.command.Command;
import connect4.command.PlacePieceCommand;
import connect4.observers.EventType;

import java.util.Scanner;

/**
 * Player vs Player game mode
 * Two human players take turns
 */
public class PlayerVsPlayerGame extends Game {
    private final Scanner scanner;
    private final Player player1;
    private final Player player2;
    private boolean isPlayer1Turn;

    public PlayerVsPlayerGame(Scanner scanner, String player1Name, String player2Name) {
        super();
        this.scanner = scanner;
        this.player1 = CharacterFactory.createPlayer(player1Name, rows, cols);
        this.player2 = CharacterFactory.createPlayer(player2Name, rows, cols);
        this.isPlayer1Turn = true;

        System.out.println(player1Name + " plays with: X");
        System.out.println(player2Name + " plays with: O");
        System.out.println();
    }

    @Override
    public void play() {
        displayInitialBoard();

        boolean gameRunning = true;

        while (gameRunning) {
            String currentPlayerName = isPlayer1Turn ? player1.getName() : player2.getName();
            char currentPiece = isPlayer1Turn ? 'X' : 'O';

            System.out.println("Current State: " + (isPlayer1Turn ? "PLAYER_1_TURN" : "PLAYER_2_TURN"));
            System.out.print(currentPlayerName + " (" + currentPiece + "), choose column (1-7) or 'u' to undo: ");

            String input = scanner.nextLine().trim();

            // Handle undo
            if (input.equalsIgnoreCase("u")) {
                if (handleUndo()) {
                    isPlayer1Turn = !isPlayer1Turn; // Switch back to previous player
                }
                continue;
            }

            // Handle move
            try {
                int col = Integer.parseInt(input) - 1;

                if (col < 0 || col > 6) {
                    System.out.println("Invalid column! Choose 1-7.");
                    continue;
                }

                // Execute move
                if (executeMove(col, currentPiece)) {
                    // Check for game end
                    if (checkWin(currentPlayerName, currentPiece)) {
                        gameRunning = false;
                    } else if (checkDraw()) {
                        gameRunning = false;
                    } else {
                        // Switch turns
                        isPlayer1Turn = !isPlayer1Turn;
                    }
                } else {
                    System.out.println("Column is full! Try another.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number 1-7 or 'u' to undo.");
            }
        }

        System.out.println("\n=== Game Over ===");
    }

    private boolean executeMove(int col, char piece) {
        Command command = new PlacePieceCommand(board, col, piece, rows);

        if (command.execute()) {
            lastCommand = command;
            eventBus.publish(EventType.MADE_A_MOVE, command);
            return true;
        }
        return false;
    }

    private boolean handleUndo() {
        if (lastCommand != null && lastCommand.undo()) {
            eventBus.publish(EventType.UNDO_MOVE, null);
            System.out.println("Move undone!");
            return true;
        } else {
            System.out.println("Cannot undo!");
            return false;
        }
    }

    private boolean checkWin(String playerName, char piece) {
        if (lastCommand != null && winChecker.checkWin(lastCommand.getRow(), lastCommand.getColumn(), piece)) {
            eventBus.publish(EventType.WIN, playerName);
            String loserName = isPlayer1Turn ? player2.getName() : player1.getName();
            eventBus.publish(EventType.LOSE, loserName);
            return true;
        }
        return false;
    }

    private boolean checkDraw() {
        if (isBoardFull()) {
            eventBus.publish(EventType.WIN, "Draw");
            return true;
        }
        return false;
    }
}