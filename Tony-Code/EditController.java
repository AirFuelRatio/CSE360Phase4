package application;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditController {
    @FXML
    private ComboBox<String> projectComboBox1;
    @FXML
    private ComboBox<String> lifecycleComboBox1;
    @FXML
    private ComboBox<String> effortComboBox1;
    @FXML
    private ComboBox<String> planComboBox1;

    public void populateFields(SearchData searchData) {
        projectComboBox1.setValue(searchData.getProject());
        lifecycleComboBox1.setValue(searchData.getLifeCycle());
        effortComboBox1.setValue(searchData.getEffort());
        planComboBox1.setValue(searchData.getPlan());
    }
}
