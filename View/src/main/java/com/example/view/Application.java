package com.example.view;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("levelScene.fxml"));

        ResourceBundle bundle = ResourceBundle.getBundle("com.example.view.MyBundle");
        fxmlLoader.setResources(bundle);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(bundle.getString("title"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}