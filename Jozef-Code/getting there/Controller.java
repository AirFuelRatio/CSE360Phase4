package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Controller {

    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Label clockLabel;
    @FXML
    private TableView<String> timesTable;
    @FXML
    private TableColumn<String, String> timeColumn;

    private Timer timer;
    private int secondsElapsed = 0;
    private final ObservableList<String> timesList = FXCollections.observableArrayList();

    public void initialize() {
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
        timesTable.setItems(timesList);
    }

    @FXML
    private void handleStartButtonAction(ActionEvent event) {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                secondsElapsed++;
                updateClockLabel();
            }
        }, 0, 1000);
    }

    @FXML
    private void handleStopButtonAction(ActionEvent event) {
        if (timer != null) {
            timer.cancel();
            timer = null;
            timesList.add(getFormattedTime());  // add stopped time to the list
            resetTimer();  // Reset the timer to 0
        }
    }

    private String getFormattedTime() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void resetTimer() {
        secondsElapsed = 0;
        updateClockLabel();
    }

    private void updateClockLabel() {
        javafx.application.Platform.runLater(() -> {
            clockLabel.setText(getFormattedTime());
        });
    }
}