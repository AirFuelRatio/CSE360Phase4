package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AnonymizedDataEntry extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LoginScreen loginScreen = new LoginScreen();
        Scene scene = new Scene(loginScreen, 300, 200);
        primaryStage.setTitle("Anonymized Data Entry");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
