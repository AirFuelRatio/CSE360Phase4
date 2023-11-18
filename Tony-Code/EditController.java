package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditController {
	@FXML
    private TextField textField;

    public void setTextFieldValue(String value) {
        textField.setText(value);
    }
}
