//example code, nowhere near complete

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.HashMap;

public class LoginController {
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label statusLabel;

    private final HashMap<String, String> users = new HashMap<>();

    public LoginController() {
        // You can populate the hashmap here with some sample users
        users.put("user1", "password1");
        users.put("user2", "password2");
        // Add more users as needed
    }

    @FXML
    public void onLoginClicked() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            statusLabel.setText("Logged in successfully!");
            // Navigate to another scene or do other tasks after successful login
        } else {
            statusLabel.setText("Wrong username/password. Try again.");
        }
    }
}
