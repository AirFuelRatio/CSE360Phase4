package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class LoginController {

    private HashMap<String, String> userDatabase = new HashMap<>();

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    public LoginController() {
        // Sample users. We'll store hashed passwords for security.
        userDatabase.put("user1", hashPassword("pass1"));
        userDatabase.put("user2", hashPassword("pass2"));
    }

    @FXML
    private void handleLoginButtonAction() {
        String username = nameField.getText();
        String enteredPasswordHash = hashPassword(passwordField.getText());

        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(enteredPasswordHash)) {
            loadMainScene();
        } else {
            showErrorDialog();
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : hashedBytes) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;  // This shouldn't happen if the hashing algorithm is supported
        }
    }

    private void loadMainScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Final.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showErrorDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText("Invalid username or password!");
        alert.showAndWait();
    }
}


