package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Controller {
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Label clockLabel;
    
    // Columns located in the Effort Log -----------------
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
    @FXML
    private TableColumn<Entry, Integer> storypointsColumn;
    
    
    // MenuButtons located in the Effort Console ----------
    @FXML
    private MenuButton projectMenuButton;
    @FXML
    private MenuButton lifecycleMenuButton;
    @FXML
    private MenuButton effortMenuButton;
    @FXML
    private MenuButton planMenuButton;
    
    // ComboBoxes located in the Planning Poker page -------
    @FXML
    private ComboBox<String> projectComboBox;
    @FXML
    private ComboBox<String> lifecycleComboBox;
    @FXML
    private ComboBox<String> effortComboBox;
    @FXML
    private ComboBox<String> planComboBox;
    
    // ComboBoxes for Search page -----
    @FXML
    private ComboBox<String> projectComboBox1;
    @FXML
    private ComboBox<String> lifecycleComboBox1;
    @FXML
    private ComboBox<String> effortComboBox1;
    @FXML
    private ComboBox<String> planComboBox1;
    
    @FXML
    private TextField storyPoints1;
    @FXML
    private TextField storyPoints2;
    
    @FXML
    private TextField playerCountTextField;
    

    private String selectedProject = "";
    private String selectedLifeCycle = "";
    private String selectedCategory = "";
    private String selectedPlan = "";
    private Integer selectedStoryPoints = 0;
    
    private Timer timer;
    private int secondsElapsed = 0;
    private final ObservableList<Entry> timesList = FXCollections.observableArrayList();

    // Definitions Page initialization --------------
    @FXML
    private TableView<Definition> definitionsTable2;
    //@FXML
    //private TableColumn<Definition, int> stepNumber;
    //@FXML
    //private TableColumn<Definition, String> lifeCycleColumn2;
    @FXML
    private TableColumn<Definition, String> categoryColumn2;
    @FXML
    private TableColumn<Definition, String> planColumn2;
    @FXML
    private TableColumn<Definition, String> deliverableColumn2;
    @FXML
    private TableColumn<Definition, String> interruptionColumn2;
    @FXML
    private TableColumn<Definition, String> defectColumn2;
    
    ObservableList<Definition> definitionsList = FXCollections.observableArrayList(
    		new Definition(1, "Problem Understanding", "Plans", "Project Plan", "Conceptual Design",
    				"Break", "Not Specified"),
    		new Definition(2, "Conceptual Design Plan", "Deliverables", "Risk Management Plan", "Detailed Design",
    				"Phone", "10 Docummentation"),
    		new Definition(3, "Requirements", "Interruptions", "Conceptual Design Plan", "Test Cases",
    				"Teammate", "20 Syntax"),
    		new Definition(4, "Conceptual Design", "Plans", "Project Management", "Conceptual Design",
    				"Break", "Not Specified")
    		);
    
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
        storypointsColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStoryPoints()).asObject());
        timesTable.setItems(timesList);
        setClockLabelInactive();  // Set initial color to red
        
        // ComboBoxes in the Planning Poker page -----------------------------------------------------------------
        projectComboBox.setItems(FXCollections.observableArrayList("Business Project", "Development Project"));
        lifecycleComboBox.setItems(FXCollections.observableArrayList("Problem Understanding","Conceptual Design Plan",
        "Requirements","Conceptual Design","Conceptual Design Review","Detailed Design Plan","Detailed Design/Prototype",
        "Detailed Design Review","Implementation Plan","Test Case Generation","Solution Specification","Solution Review",
        "Solution Implementation","Unit/System Test","Reflection","Repository Update","Planning","Information Gathering",
        "Information Understanding","Verifying","Outlining","Drafting","Finalizing"));
        effortComboBox.setItems(FXCollections.observableArrayList("Plans", "Deliverables","Interruptions","Defects","Other"));
        planComboBox.setItems(FXCollections.observableArrayList("Project Plan", "Risk Management Plan", 
        "Conceptual Design Plan","Detailed Design Plan", "Implementation Plan"));
        
        // ComboBoxes in the Search page -----------------------------------------------------------------
        projectComboBox1.setItems(FXCollections.observableArrayList("Business Project", "Development Project"));
        lifecycleComboBox1.setItems(FXCollections.observableArrayList("Problem Understanding","Conceptual Design Plan",
        "Requirements","Conceptual Design","Conceptual Design Review","Detailed Design Plan","Detailed Design/Prototype",
        "Detailed Design Review","Implementation Plan","Test Case Generation","Solution Specification","Solution Review",
        "Solution Implementation","Unit/System Test","Reflection","Repository Update","Planning","Information Gathering",
        "Information Understanding","Verifying","Outlining","Drafting","Finalizing"));
        effortComboBox1.setItems(FXCollections.observableArrayList("Plans", "Deliverables","Interruptions","Defects","Other"));
        planComboBox1.setItems(FXCollections.observableArrayList("Project Plan", "Risk Management Plan", 
        "Conceptual Design Plan","Detailed Design Plan", "Implementation Plan"));
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
            
            try {
                selectedStoryPoints = storyPoints1.getText().isEmpty() ? 0 : Integer.parseInt(storyPoints1.getText());
            } catch (NumberFormatException e) {
                selectedStoryPoints = 0; // Default value or error handling
                System.out.println("Invalid number format for story points.");
            }

            Entry entry = new Entry("UserName",
                                    LocalDate.now().toString(),
                                    LocalTime.now().toString(),
                                    getFormattedTime(),
                                    selectedProject,
                                    selectedLifeCycle,
                                    selectedCategory,
                                    selectedPlan,
                                    selectedStoryPoints);

            timesList.add(entry);
            insertEntryToDatabase(entry);
            
            // Reset selections
            selectedProject = "";
            selectedLifeCycle = "";
            selectedCategory = "";
            selectedPlan = "";
            selectedStoryPoints = 0;
            
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
        projectMenuButton.setText(selectedProject);
    }
    
  //CHoose a Project
    @FXML
    private void handleDevelopmentProjectMenuItem(ActionEvent event) {
        selectedProject = "Development Project";
        projectMenuButton.setText(selectedProject);
    }
        
 
 //----------------Choose Life Cycle Step------------------------------
        
    //Choose Life Cycle Step
    @FXML
    private void handleProblemUnderstandingMenuItem(ActionEvent event) {
        selectedLifeCycle = "Problem Understanding";
        lifecycleMenuButton.setText(selectedLifeCycle);
        }
        
    //Choose Life Cycle Step
    @FXML
    private void handleConceptualDesignPlanMenuItem2(ActionEvent event) {
        selectedLifeCycle = "Conceptual Design Plan";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
        
    //Choose Life Cycle Step
    @FXML
    private void handleRequirementsMenuItem(ActionEvent event) {
        selectedLifeCycle = "Requirements";
        lifecycleMenuButton.setText(selectedLifeCycle);        	
    }
    
    //Choose Life Cycle Step
    @FXML
    private void handleConceptualDesignMenuItem(ActionEvent event) {
        selectedLifeCycle = "Conceptual Design";
        lifecycleMenuButton.setText(selectedLifeCycle);        	
    }
    
  //Choose Life Cycle Step
    @FXML
    private void handleConceptualDesignReviewMenuItem(ActionEvent event) {
        selectedLifeCycle = "Conceptual Design Review";
        lifecycleMenuButton.setText(selectedLifeCycle);        	
    }
    
    //Choose Life Cycle Step
    @FXML
    private void handleDetailedDesignPrototypeMenuItem(ActionEvent event) {
        selectedLifeCycle = "Detailed Design/Prototype";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
    
    //Choose Life Cycle Step
    @FXML
    private void handleDetailedDesignReviewMenuItem(ActionEvent event) {
        selectedLifeCycle = "Detailed Design Review";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
    
   //Choose Life Cycle Step
    @FXML
    private void handleDetailedDesignPlanMenuItem2(ActionEvent event) {
        selectedLifeCycle = "Detailed Design Plan";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
        
  //Choose Life Cycle Step
    @FXML
    private void handleImplementationPlanMenuItem2(ActionEvent event) {
    	selectedLifeCycle = "Implementation Plan";
    	lifecycleMenuButton.setText(selectedLifeCycle);
    	System.out.println("life cycle");
    }
    
    //Choose Life Cycle Step
    @FXML
    private void handleTestCaseGenerationMenuItem(ActionEvent event) {
        selectedLifeCycle = "Test Case Generation";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
        
    //Choose Life Cycle Step
    @FXML
    private void handleSolutionSpecificationMenuItem(ActionEvent event) {
        selectedLifeCycle = "Solution Specification";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
        
    //Choose Life Cycle Step
    @FXML
    private void handleSolutionReviewMenuItem(ActionEvent event) {
        selectedLifeCycle = "Solution Review";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
    
    //Choose Life Cycle Step
    @FXML
    private void handleSolutionImplementationMenuItem(ActionEvent event) {
        selectedLifeCycle = "Solution Implementation";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
    
    //Choose Life Cycle Step
    @FXML
    private void handleUnitSystemTestMenuItem(ActionEvent event) {
        selectedLifeCycle = "Unit System Test";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
        
    //Choose Life Cycle Step
    @FXML
    private void handleReflectionMenuItem(ActionEvent event) {
        selectedLifeCycle = "Reflection";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
    
    //Choose Life Cycle Step
    @FXML
    private void handleRepositoryUpdateMenuItem(ActionEvent event) {
    	selectedLifeCycle = "Repository Update";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
    
    //Choose Life Cycle Step
    @FXML
    private void handlePlanningMenuItem(ActionEvent event) {
        selectedLifeCycle = "Planning";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
        
    //Choose Life Cycle Step
    @FXML
    private void handleInformationGatheringMenuItem(ActionEvent event) {
    	selectedLifeCycle = "Information Gathering";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
        
    //Choose Life Cycle Step
    @FXML
    private void handleInformationUnderstandingMenuItem(ActionEvent event) {
    	selectedLifeCycle = "Information Understanding";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
        
    //Choose Life Cycle Step
    @FXML
    private void handleVerifyingMenuItem(ActionEvent event) {
    	selectedLifeCycle = "Verifying";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
    
    //Choose Life Cycle Step
    @FXML
    private void handleOutliningMenuItem(ActionEvent event) {
    	selectedLifeCycle = "Outlining";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
        
    //Choose Life Cycle Step
    @FXML
    private void handleDraftingMenuItem(ActionEvent event) {
        selectedLifeCycle = "Drafting";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
        
    //Choose Life Cycle Step
    @FXML
    private void handleFinalizingMenuItem(ActionEvent event) {
        selectedLifeCycle = "Finalizing";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
        
    //Choose Life Cycle Step
    @FXML
    private void handleTeamMeetingMenuItem(ActionEvent event) {
        selectedLifeCycle = "Team Meeting";
        lifecycleMenuButton.setText(selectedLifeCycle);
    }
    
    //Choose Life Cycle Step
    @FXML
    private void handleCoachMeetingMenuItem(ActionEvent event) {
    	selectedLifeCycle = "Coach Meeting";            
    	lifecycleMenuButton.setText(selectedLifeCycle);
    }
        
    //Choose Life Cycle Step
    @FXML
    private void handleStakeholderMeetingMenuItem(ActionEvent event) {
    	selectedLifeCycle = "Stakeholder Meeting";
    	lifecycleMenuButton.setText(selectedLifeCycle);
    }
    
 //---------End Choose Life Cycle Step------------------------------------       
       
        
        
//-----------------Choose Category---------------------------------------------
  //Choose Category
    @FXML
    private void handlePlansMenuItem(ActionEvent event) {
    	selectedCategory = "Plans";
        effortMenuButton.setText(selectedCategory);
    }
  //Choose Category
    @FXML
    private void handleDeliverablesMenuItem(ActionEvent event) {
    	selectedCategory = "Deliverables";
        effortMenuButton.setText(selectedCategory);
    }
    //Choose Category
    @FXML
    private void handleInterruptionsMenuItem(ActionEvent event) {
    	selectedCategory = "Interruptions";
        effortMenuButton.setText(selectedCategory);
    }
        
    //Choose Category
    @FXML
    private void handleDefectsMenuItem(ActionEvent event) {
        selectedCategory = "Defects";
        effortMenuButton.setText(selectedCategory);
    }
        
    //Choose Category
    @FXML
    private void handleOthersMenuItem(ActionEvent event) {
    	selectedCategory = "Others";
        effortMenuButton.setText(selectedCategory);
    }
 //--------------End Choose Category------------------------------
   
    
        
 //-----------Choose Plan-----------------------------------------
      //Choose Plan
        @FXML
        private void handleProjectPlanMenuItem(ActionEvent event) {
        	selectedPlan = "Project Plan";
        	planMenuButton.setText(selectedPlan);

        }
      //Choose Plan
        @FXML
        private void handleRiskManagementPlanMenuItem(ActionEvent event) {
        	selectedPlan = "Risk Management Plan";
        	planMenuButton.setText(selectedPlan);
        }
        //Choose Plan 
        
        @FXML
        private void handleConceptualDesignPlanMenuItem(ActionEvent event) {
        	selectedPlan = "Conceptual Design Plan";
        	planMenuButton.setText(selectedPlan);
        }  
      //Choose Plan
        @FXML
        private void handleDetailedDesignPlanMenuItem(ActionEvent event) {
        	selectedPlan = "Detailed Design Plan";
        	planMenuButton.setText(selectedPlan);
        }
       //Choose Plan
        @FXML
        private void handleImplementationPlanMenuItem(ActionEvent event) {
        	selectedPlan = "Implementation Plan";
        	planMenuButton.setText(selectedPlan);
        	System.out.println("plan");

        }
 //----------End Choose plan ---------------------------------------
        
 //End MenuItem Actions------------------------------------
        
    
    
//End of Handle all Effort Console Menu Items -------------------------------
    
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
// ----------------------------------------------------------------------------------------

    public class Definition {
    	int number; 
    	String lifeCycleStep, effortCategory, plan, deliverable, interruption, defectCategory;
    	
    	public Definition(int number, String lifeCycleStep, String effortCategory, String plan, String deliverable,
    	String interruption, String defectCategory) {
    		this.number = number;
    		this.lifeCycleStep = lifeCycleStep;
    		this.effortCategory = effortCategory;
    		this.plan = plan;
    		this.deliverable = deliverable;
    		this.interruption = interruption;
    		this.defectCategory = defectCategory;
    	}
    }
 
// EFFORT LOG BACKEND ----------------------------------------------------------------------
// -----------------------------------------------------------------------------------------
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
        private final Integer storypoints;

        public Entry(String userName, String currentDate, String currentTime, String clockTimeElapsed, String projectChosen, 
        String lifeCycleStepChosen, String categoryChosen, String planChosen, Integer storypoints) {
            this.userName = userName;
            this.currentDate = currentDate;
            this.currentTime = currentTime;
            this.clockTimeElapsed = clockTimeElapsed;
            this.projectChosen = projectChosen;
            this.lifeCycleStepChosen = lifeCycleStepChosen;
            this.categoryChosen = categoryChosen;
            this.planChosen = planChosen;
            this.storypoints = storypoints;
            
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
        
        public int getStoryPoints() {
        	return storypoints;
        }

		public String getDuration() {
			// TODO Auto-generated method stub
			return null;
		}
    }
    
    // PLANNING POKER CODE ----------------------------------------------------------------------------------------------------
    
    @FXML
    private void handlePlayerCount(ActionEvent event) {
        int playerCount = Integer.parseInt(playerCountTextField.getText()); // Convert to int (add error handling as necessary)
    }

    
    @FXML
    private void handleStoryPoints(ActionEvent event) {
        String selectedProject = projectComboBox.getValue();
        String selectedLifeCycle = lifecycleComboBox.getValue();
        String selectedEffort = effortComboBox.getValue();
        String selectedPlan = planComboBox.getValue();
        
    	//int playerCount = Integer.parseInt(playerCountTextField.getText());
        if(timesTable.getItems().isEmpty()) {
        	loadPokerScene(3);
        }
    	
        
        for (Entry entry : timesList) {
            // Check if the values in the current row match the selected values
            if (entry.getProject().equals(selectedProject) &&
                entry.getLifeCycle().equals(selectedLifeCycle) &&
                entry.getCategory().equals(selectedEffort) &&
                entry.getPlan().equals(selectedPlan)) {
                // Matching values found, you can perform further actions here
                System.out.println("Matching row found: " + entry.getUserName());
                
                storyPoints1.setText(String.valueOf(entry.getStoryPoints()));
                storyPoints2.setText(String.valueOf(entry.getStoryPoints()));
            }
            else {
            	System.out.println("Not Found!!!");
            	System.out.println("Entry 1: " + entry.getProject() + ", " + entry.getLifeCycle()
            	+ ", " + entry.getCategory() + ", " + entry.getPlan());
            	System.out.println("Entry 2: " + selectedProject + ", " + selectedLifeCycle
            	+ ", " + selectedEffort + ", " + selectedPlan);
            	loadPokerScene(3);

            }
        }
    }
    
    private void loadPokerScene(int playerCount) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PokerPage.fxml"));
            Parent root = loader.load();
            PokerController pokerController = loader.getController();
            pokerController.setPlayerCount(playerCount); // Assuming there is a method setPlayerCount in PokerController
            Stage stage = new Stage();
            stage.setTitle("Planning Poker");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
    // EFFORT LOG SEARCH / EDIT BACK END ----------------------------------------------------------------------------------------------------
    @FXML
    private void handleSearchEffort(ActionEvent event) {
        String selectedProject = projectComboBox1.getValue();
        String selectedLifeCycle = lifecycleComboBox1.getValue();
        String selectedEffort = effortComboBox1.getValue();
        String selectedPlan = planComboBox1.getValue();

        // Create an instance of SearchData with the selected values
        SearchData searchData = new SearchData(selectedProject, selectedLifeCycle, selectedEffort, selectedPlan);
        
        for (Entry entry : timesList) {
          // Check if the values in the current row match the selected values
          if (entry.getProject().equals(selectedProject) &&
              entry.getLifeCycle().equals(selectedLifeCycle) &&
              entry.getCategory().equals(selectedEffort) &&
              entry.getPlan().equals(selectedPlan)) {
              // Matching values found, you can perform further actions here
              System.out.println("Matching row found: " + entry.getUserName());
              loadEditScene(searchData); // Load editor window
          }
          else {
          	System.out.println("Not Found!!!");
          }
      }

    }

    private void loadEditScene(SearchData searchData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditPage.fxml"));
            Parent root = loader.load();
            EditController editController = loader.getController();

            if (searchData != null) {
                editController.populateFields(searchData);
            }

            Stage stage = new Stage();
            stage.setTitle("Edit Effort Logs");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    //sql store data
    
    public void insertEntryToDatabase(Entry entry) {
    	String sql = "INSERT INTO effort_and_defect_data (username, date, time, duration, project, lifecycle, category, plan, storypoints) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

         try (Connection conn = DatabaseConnector.connect();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {

             pstmt.setString(1, entry.getUserName());
             pstmt.setString(2, entry.getCurrentDate());
             pstmt.setString(3, entry.getCurrentTime());
             pstmt.setString(4, entry.getDuration());
             pstmt.setString(5, entry.getProject());
             pstmt.setString(6, entry.getLifeCycle());
             pstmt.setString(7, entry.getCategory());
             pstmt.setString(8, entry.getPlan());
             pstmt.setInt(9, entry.getStoryPoints());

             pstmt.executeUpdate();
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
    }
}
// --------------------------------------------------------------------------------------------------------
