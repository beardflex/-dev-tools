package com.beardflex.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.URL;

/**
 * Created by David on 07/03/2017.
 */
public class DevToolsApplication extends Application {
    /**
     * JavaFX entry point. Sets up the stage and adds the UI items to the view.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Attempt to load the Main View, raise a notice if the URL is null
        URL mainViewUrl = getClass().getResource("fxml/MainView.fxml");
        if(mainViewUrl == null) {
            // Handle error
            throw new NotImplementedException();
        }
        // Load in the top level UI object and assign its Controller.
        FXMLLoader fxmlLoader = new FXMLLoader(mainViewUrl);
        fxmlLoader.setController(new MainViewController());
        Parent root = fxmlLoader.load();
        // Create a scene with the top level UI element created above.
        Scene scene = new Scene(root);
        // Attempt to load in the style sheet.
        URL styleSheetUrl = getClass().getResource("css/theme-light.css");
        if(styleSheetUrl == null) {
            // Handle Error
            throw new NotImplementedException();
        }
        // Add the loaded stylesheet to the scene
        scene.getStylesheets().add(styleSheetUrl.toExternalForm());
        // Set the primaryStage's scene
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        // Set up the close request logic.
        primaryStage.setOnCloseRequest( e -> onExit());
        // Show the stage.
        primaryStage.show();
    }

    public void onExit() {
        System.exit(0);
    }

    /** Entry point.
     *
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(DevToolsApplication.class, args);
    }
}
