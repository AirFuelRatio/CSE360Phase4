package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDate;
import java.time.LocalTime;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Controller {

    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Label clockLabel;
    @FXML
    private TableView<Entry> timesTable;
    @FXML
    private TableColumn<Entry, String> userColumn;
    @FXML
    private TableColumn<Entry, String> dateColumn;
    @FXML
    private TableColumn<Entry, String> timeColumn;
    @FXML
    private TableColumn<Entry, String> elapsedTimeColumn;

    private TextField userNameField; // user will input their name here
    private Timer timer;
    private int secondsElapsed = 0;
    private final ObservableList<Entry> timesList = FXCollections.observableArrayList();

    public void initialize() {
        userColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUserName()));
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCurrentDate()));
        timeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCurrentTime()));
        elapsedTimeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClockTimeElapsed()));

        timesTable.setItems(timesList);
        setClockLabelInactive(); // Set initial color to red
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
        setClockLabelActive(); // Set the background color to green
    }

    @FXML
    private void handleStopButtonAction(ActionEvent event) {
        if (timer != null) {
            timer.cancel();
            timer = null;

            // hash the user name to keep it anonoymus
            String hashedUserName = hashUserName(userNameField.getText());
            Entry entry = new Entry(hashedUserName,
                    LocalDate.now().toString(),
                    LocalTime.now().toString(),
                    getFormattedTime());
            timesList.add(entry);

            secondsElapsed = 0; // reset the timer
            updateClockLabel(); // update the clock label to show 00:00
            setClockLabelInactive(); // Set the background color to red
        }
    }

    private String hashUserName(String userName) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(userName.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Anonymous"; // Default to "Anonymous" if hashing fails.
        }
    }

    // need to add into FXML file to capture user name
    // <TextField fx:id="userNameField" />

    private String getFormattedTime() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void updateClockLabel() {
        javafx.application.Platform.runLater(() -> {
            clockLabel.setText(getFormattedTime());
        });
    }

    private void setClockLabelActive() {
        clockLabel.setStyle("-fx-background-color: #00FF00;"); // Hex code for green
    }

    private void setClockLabelInactive() {
        clockLabel.setStyle("-fx-background-color: #FF0000;"); // Hex code for red
    }

    public static class Entry {
        private final String userName;
        private final String currentDate;
        private final String currentTime;
        private final String clockTimeElapsed;

        public Entry(String userName, String currentDate, String currentTime, String clockTimeElapsed) {
            this.userName = userName;
            this.currentDate = currentDate;
            this.currentTime = currentTime;
            this.clockTimeElapsed = clockTimeElapsed;
        }

        public String getUserName() {
            return userName;
        }

        public String getCurrentDate() {
            return currentDate;
        }

        public String getCurrentTime() {
            return currentTime;
        }

        public String getClockTimeElapsed() {
            return clockTimeElapsed;
        }
    }
}