package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Label clockLabel;

    @FXML
    private void handleStartButtonAction(ActionEvent event) {
        // Start timer code here
    }

    @FXML
    private void handleStopButtonAction(ActionEvent event) {
        // Stop timer and display time code here
    }
}