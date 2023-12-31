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
    @FXML
    private TableColumn<Entry, String> projectColumn;
    @FXML
    private TableColumn<Entry, String> lifeCycleColumn;
    @FXML
    private TableColumn<Entry, String> categoryColumn;
    @FXML
    private TableColumn<Entry, String> planColumn;

    private String selectedProject = "";
    private String selectedLifeCycle = "";
    private String selectedCategory = "";
    private String selectedPlan = "";
    
    private Timer timer;
    private int secondsElapsed = 0;
    private final ObservableList<Entry> timesList = FXCollections.observableArrayList();
   

    //set up user data values
    public void initialize() {
        userColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUserName()));
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCurrentDate()));
        timeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCurrentTime()));
        elapsedTimeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClockTimeElapsed()));
        projectColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProject()));
        lifeCycleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLifeCycle()));
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));
        planColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPlan()));
        
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
                                    getFormattedTime(),
                                    selectedProject,
                                    selectedLifeCycle,
                                    selectedCategory,
                                    selectedPlan);
            						
			//String selectedPlan;
			//String selectedCategory;
			//String selectedLifeCycle;
			//String selectedProject;
            timesList.add(entry);
            
            // Reset selections
            selectedProject = "";
            selectedLifeCycle = "";
            selectedCategory = "";
            selectedPlan = "";
            
            secondsElapsed = 0;                 // reset the timer
            updateClockLabel();                 // update the clock label to show 00:00
            setClockLabelInactive();            // Set the background color to red
        }
    }
    
//Handle ALL Effort Console MenuItems--------------------
    
    
 //-----------------Choose Project-------------------------------   
    //CHoose a Project
    @FXML
    private void handleBusinessProjectMenuItem(ActionEvent event) {
    	selectedProject = "Business Project";
    }
    
  //CHoose a Project
        @FXML
        private void handleDevelopmentProjectMenuItem(ActionEvent event) {
        	selectedProject = "Development Project";
    }
        
 
 //----------------Choose Life Cycle Step------------------------------
        
        //Choose Life Cycle Step
        @FXML
        private void handleProblemUnderstandingMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Problem Understanding";
        	
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleConceptualDesignPlanMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Conceptual Design Plan";
        	
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleRequirementsMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Requirements";
        	
        }
    
      //Choose Life Cycle Step
        @FXML
        private void handleConceptualDesignMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Conceptual Design";
        	
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleDetailedDesignPrototypeMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Detailed Design/Prototype";
        }
    
      //Choose Life Cycle Step
        @FXML
        private void handleDetailedDesignPlanMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Detailed Design Plan";
        }
        
      //Choose Life Cycle Step
      //  @FXML
        //private void handleConceptualDesignPlanMenuItem2(ActionEvent event) {
        //	selectedLifeCycle = "Conceptual Design Plan";
       // }
    
      //Choose Life Cycle Step
        @FXML
        private void handleDetailedDesignReviewMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Detailed Design Review";
        }
    
      //Choose Life Cycle Step
        @FXML
        private void handleImplementationPlanMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Implementaition";
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleTestCaseGenerationMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Test Case Generation";
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleSolutionSpecificationMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Solution Specification";
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleSolutionReviewMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Solution Review";
        }
    
      //Choose Life Cycle Step
        @FXML
        private void handleSolutionImplementationMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Solution Implementation";
        }
    
      //Choose Life Cycle Step
        @FXML
        private void handleUnitSystemTestMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Unit System Test";
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleReflectionMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Reflection";
        }
    
      //Choose Life Cycle Step
        @FXML
        private void handleRepositoryUpdateMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Repository Update";
        }
    
      //Choose Life Cycle Step
        @FXML
        private void handlePlanningMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Planning";
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleInformationGatheringMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Information Gathering";
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleInformationUnderstandingMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Information Understanding";
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleVerifyingMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Verifying";
        }
    
      //Choose Life Cycle Step
        @FXML
        private void handleOutliningMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Outlining";
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleDraftingMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Drafting";
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleFinalizingMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Finalizing";
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleTeamMeetingMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Team Meeting";
        }
    
      //Choose Life Cycle Step
        @FXML
        private void handleCoachMeetingMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Coach Meeting";
        }
        
      //Choose Life Cycle Step
        @FXML
        private void handleStakeholderMeetingMenuItem(ActionEvent event) {
        	selectedLifeCycle = "Stakeholder Meeting";
        }
 //---------End Choose Life Cycle Step------------------------------------       
       
        
//-----------------Choose Category---------------------------------------------
        
      //Choose Category
        @FXML
        private void handleInterruptionsMenuItem(ActionEvent event) {
        	selectedCategory = "Interruption";
        }
        
      //Choose Category
        @FXML
        private void handleDefectsMenuItem(ActionEvent event) {
        	selectedCategory = "Defects";
        }
        
        //Choose Category
        @FXML
        private void handleOthersMenuItem(ActionEvent event) {
        	selectedCategory = "Others";
        }
 //--------------End Choose Category------------------------------
        
        
 //-----------Choose Plan-----------------------------------------
      //Choose Plan
        @FXML
        private void handleProjectPlanMenuItem(ActionEvent event) {
        	selectedPlan = "Project Plan";
        }
        
      //Choose Plan
        @FXML
        private void handleRiskManagementPlanMenuItem(ActionEvent event) {
        	selectedPlan = "Risk Management Plan";
        }
        
        @FXML
        private void handlePlansMenuItem(ActionEvent event) {
        	selectedPlan = "Plans";
        }
        
      //Choose Plan
      //  @FXML
       // private void handleInterruptionsMenuItem(ActionEvent event) {
        //	selectedPlan = "Conceptual Design Plan";
        //}
        
        //Choose Plan
        @FXML
        private void handleDeliverablesMenuItem(ActionEvent event) {
        	selectedPlan = "Deliverables";
        }
        
      //Choose Plan
        @FXML
        private void handleImplementationPlanMenuItem2(ActionEvent event) {
        	selectedPlan = "Implementation Plan";
        }
        
 //----------End Choose plan ---------------------------------------
        
 //End MenuItem Actions------------------------------------
        
        
        
    
    
    
    
    
    
    
    
//End of Handle all Effort Console Menu Items
    
    
    
//time for timer
    private String getFormattedTime() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    
//change from text to timer when started
    private void updateClockLabel() {
        javafx.application.Platform.runLater(() -> {
            clockLabel.setText("Clock is Running: " + getFormattedTime());
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
        private final String projectChosen;
        private final String lifeCycleStepChosen;
        private final String categoryChosen;
        private final String planChosen;

        public Entry(String userName, String currentDate, String currentTime, String clockTimeElapsed, String projectChosen, String lifeCycleStepChosen, String categoryChosen, String planChosen) {
            this.userName = userName;
            this.currentDate = currentDate;
            this.currentTime = currentTime;
            this.clockTimeElapsed = clockTimeElapsed;
            this.projectChosen = projectChosen;
            this.lifeCycleStepChosen = lifeCycleStepChosen;
            this.categoryChosen = categoryChosen;
            this.planChosen = planChosen;
            
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
        
        public String getProject() {
        	return projectChosen;
        }
        
        public String getLifeCycle() {
        	return lifeCycleStepChosen;
        }
        
        public String getCategory() {
        	return categoryChosen;
        }
        
        public String getPlan() {
        	return planChosen;
        }
    }
}
