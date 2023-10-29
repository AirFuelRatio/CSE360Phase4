package application;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DataSubmissionScreen extends VBox {

    private TextArea dataArea;

    public DataSubmissionScreen() {
        Text introText = new Text("Enter the data you want to submit:");
        dataArea = new TextArea();
        dataArea.setPromptText("Your feedback...");

        Button submitButton = new Button("Submit Data");
        submitButton.setOnAction(e -> handleSubmit());

        getChildren().addAll(introText, dataArea, submitButton);
    }

    private void handleSubmit() {
        String data = dataArea.getText().trim();
        if (data.isEmpty()) {
            // Show some alert or notification
            return;
        }
        DataStorage.storeData(data);
        // Show success message or redirect to another screen
    }
}
