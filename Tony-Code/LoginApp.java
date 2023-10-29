package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

public class LoginApp extends Application {

    private Map<String, User> users = new HashMap<String, User>();

    @Override
    public void start(final Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        final TextField nameField = new TextField();
        nameField.setPromptText("Enter your username");
        GridPane.setConstraints(nameField, 0, 0);
        grid.getChildren().add(nameField);

        final PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        GridPane.setConstraints(passwordField, 0, 1);
        grid.getChildren().add(passwordField);

        Button loginBtn = new Button("Login");
        GridPane.setConstraints(loginBtn, 1, 1);
        grid.getChildren().add(loginBtn);

        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = nameField.getText();
                String password = passwordField.getText();
                if (!users.containsKey(username)) {
                    createUser(username, password);
                } else {
                    User user = users.get(username);
                    if (user.password.equals(password)) {
                        showSuccessLoginWindow();
                    } else {
                        // Handle incorrect password logic here
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Incorrect Password");
                        alert.setContentText("Please check your password and try again.");
                        alert.showAndWait();
                    }
                }
            }
        });

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setTitle("Login App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showSuccessLoginWindow() {
        Stage successStage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        Label successLabel = new Label("Successfully logged in!");
        vbox.getChildren().add(successLabel);

        Scene scene = new Scene(vbox, 200, 100);
        successStage.setTitle("Success");
        successStage.setScene(scene);
        successStage.show();
    }

    private void createUser(final String username, final String password) {
        final Stage stage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        Label label = new Label("Is " + username + " an admin?");
        vbox.getChildren().add(label);

        ToggleGroup group = new ToggleGroup();

        final RadioButton adminRadio = new RadioButton("Admin");
        adminRadio.setToggleGroup(group);
        vbox.getChildren().add(adminRadio);

        RadioButton userRadio = new RadioButton("User");
        userRadio.setToggleGroup(group);
        vbox.getChildren().add(userRadio);

        Button confirmButton = new Button("Confirm");
        vbox.getChildren().add(confirmButton);

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean isAdmin = adminRadio.isSelected();
                users.put(username, new User(username, password, isAdmin));
                stage.close();
            }
        });

        Scene scene = new Scene(vbox, 200, 150);
        stage.setScene(scene);
        stage.show();
    }

    private static class User {
        String username;
        String password;
        boolean isAdmin;

        User(String username, String password, boolean isAdmin) {
            this.username = username;
            this.password = password;
            this.isAdmin = isAdmin;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
