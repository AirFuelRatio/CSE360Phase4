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

    private Timer timer;
    private int secondsElapsed = 0;
    private final ObservableList<Entry> timesList = FXCollections.observableArrayList();

    //set up user data values
    public void initialize() {
        userColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUserName()));
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCurrentDate()));
        timeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCurrentTime()));
        elapsedTimeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClockTimeElapsed()));
        
        timesTable.setItems(timesList);
        setClockLabelInactive();  // Set initial color to red
    }

    
    //when start button is pressed do this
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
        setClockLabelActive();  // Set the background color to green
    }

    //when stop button is pressed do this
    @FXML
    private void handleStopButtonAction(ActionEvent event) {
        if (timer != null) {
            timer.cancel();
            timer = null;
            
            Entry entry = new Entry("UserName", 
                                    LocalDate.now().toString(), 
                                    LocalTime.now().toString(), 
                                    getFormattedTime());
            timesList.add(entry);
            
            secondsElapsed = 0;                 // reset the timer
            updateClockLabel();                 // update the clock label to show 00:00
            setClockLabelInactive();            // Set the background color to red
        }
    }
//time for timer
    private String getFormattedTime() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
//change from text to timer when started
    private void updateClockLabel() {
        javafx.application.Platform.runLater(() -> {
            clockLabel.setText(getFormattedTime());
        });
    }

    //red when clock stopped
    private void setClockLabelActive() {
        clockLabel.setStyle("-fx-background-color: #00FF00;");  // Hex code for green
    }

    //green when clock starts
    private void setClockLabelInactive() {
        clockLabel.setStyle("-fx-background-color: #FF0000;");  // Hex code for red
    }

    
    //User data collection 
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
