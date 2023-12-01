package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.HashMap;
import java.util.Map;

public class PokerController {

    @FXML
    private TextField pokerValueTextField;
    @FXML
    private Label resultLabel; // Add a label in FXML to display the result
    private int playerCount;

    private final Map<Integer, Integer> playerVotes = new HashMap<>();
    private int currentPlayer = 1;

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
        // You can add additional logic if needed
    }

    @FXML
    private void handlePassPlayerAction() {
        try {
            int value = Integer.parseInt(pokerValueTextField.getText());
            playerVotes.put(currentPlayer, value);

            if (playerVotes.size() == playerCount) { // Check if all players have entered their values
                if (allPlayersAgree()) {
                    resultLabel.setText("Agreed Story Points Value: " + value);
                } else {
                    resultLabel.setText("Players do not agree, continue to next round!");
                    resetForNextRound();
                }
            } else {
                currentPlayer++;
                pokerValueTextField.clear(); // Clear the field for the next player
                resultLabel.setText("Player " + currentPlayer + ", enter your value.");
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input. Please enter a number.");
        }
    }

    private boolean allPlayersAgree() {
        int firstValue = playerVotes.get(1);
        return playerVotes.values().stream().allMatch(v -> v == firstValue);
    }

    private void resetForNextRound() {
        playerVotes.clear();
        currentPlayer = 1;
        pokerValueTextField.clear();
    }
}
