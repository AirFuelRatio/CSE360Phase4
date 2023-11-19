package application;

import application.Controller.Entry;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage; // TEST LINE

public class EditController {
    @FXML
    private ComboBox<String> projectComboBox1;
    @FXML
    private ComboBox<String> lifecycleComboBox1;
    @FXML
    private ComboBox<String> effortComboBox1;
    @FXML
    private ComboBox<String> planComboBox1;
    
    private Entry editableEntry;
    
    @FunctionalInterface
    public interface OnEditApplied {
        void handle();
    }
    private OnEditApplied onEditAppliedCallback;
    
    public void setOnEditAppliedCallback(OnEditApplied callback) {
        this.onEditAppliedCallback = callback;
    }
    
    public void populateFields(Entry entry) {
        this.editableEntry = entry; // Store the reference to the Entry
        projectComboBox1.setValue(entry.getProject());
        lifecycleComboBox1.setValue(entry.getLifeCycle());
        effortComboBox1.setValue(entry.getCategory());
        planComboBox1.setValue(entry.getPlan());
    }
    
    public void initialize() {
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
    
    @FXML
    private void handleApplyEdit() {
        if (editableEntry != null) {
            String selectedProject = projectComboBox1.getValue();
            String selectedLifeCycle = lifecycleComboBox1.getValue();
            String selectedEffort = effortComboBox1.getValue();
            String selectedPlan = planComboBox1.getValue();

            // Update the entry with new values from combo boxes
            editableEntry.setProject(selectedProject);
            editableEntry.setLifeCycle(selectedLifeCycle);
            editableEntry.setCategory(selectedEffort);
            editableEntry.setPlan(selectedPlan);
            
            if (onEditAppliedCallback != null) {
                onEditAppliedCallback.handle();
            }
            
            closeEditWindow();
        }
    }
    
    private void closeEditWindow() {
        // Get the current stage using one of the controls
        Stage stage = (Stage) projectComboBox1.getScene().getWindow();
        stage.close();
    }
}
