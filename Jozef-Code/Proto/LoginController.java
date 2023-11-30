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
    private static final String COMPANY_ID = "1221497147";

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField companyIDField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button signupBtn;

    //Predetermined logins, for company to fill out with usernames and password for employees
    public LoginController() {
        // Sample users.
        userDatabase.put("Jozef", hashPassword("123"));
        userDatabase.put("Tony", hashPassword("123"));
        userDatabase.put("Deborah", hashPassword("123"));
        userDatabase.put("Dylan", hashPassword("123"));
        userDatabase.put("Tyler", hashPassword("123"));
    }

    //Decides what the login button does, input texts get stored, and determine whether oits in the database
    @FXML
    private void handleLoginButtonAction() {
        String username = nameField.getText();
        String enteredPasswordHash = hashPassword(passwordField.getText());

        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(enteredPasswordHash)) {
            loadMainScene();
        } else {
            showErrorDialog("Invalid username or password!");
        }
    }

    //stores new employee users registered username and password when signup is clicked
    @FXML
    private void handleSignupButtonAction() {
        String enteredCompanyID = companyIDField.getText();

        if (enteredCompanyID.equals(COMPANY_ID)) {
            String newUsername = nameField.getText();
            String newPassword = passwordField.getText();
            
            if (userDatabase.containsKey(newUsername)) {
                showErrorDialog("Username already exists!");
            } else {
                userDatabase.put(newUsername, hashPassword(newPassword));
                showSuccessDialog("User successfully registered!");
            }
        } else {
            showErrorDialog("Invalid Company ID!");
        }
    }

    //visibility of the buttons, when signup/login/register are prressed
    @FXML
    private void toggleView() {
        if (signupBtn.isVisible()) {
            signupBtn.setVisible(false);
            companyIDField.setVisible(false);
            loginBtn.setVisible(true);
        } else {
            signupBtn.setVisible(true);
            companyIDField.setVisible(true);
            loginBtn.setVisible(false);
        }
    }

    //load main console
    private void loadMainScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Final.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //hashing the passwords and storing them 
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    //error for incorrect login
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
//successful creation dialogue
    private void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
