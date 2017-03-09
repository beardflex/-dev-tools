package com.beardflex.ui;

import com.beardflex.bean.Effort;
import com.beardflex.ui.detail.DetailViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by David on 07/03/2017.
 */
public class DevToolsApplication extends Application {

    private static ResourceBundle bundle = ResourceBundle.getBundle("bundles.Strings");
    public static AssetManager assetManager;
    private static MainViewController mainViewController;
    private BorderPane root;

    public static DevToolsApplication instance;

    public static MainViewController getMainViewController() {
        return mainViewController;
    }

    /**
     * JavaFX entry point. Sets up the stage and adds the UI items to the view.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        instance = this;

        assetManager = new AssetManager();
        assetManager.loadImages();

        // Attempt to load the Main View, raise a notice if the URL is null
        URL mainViewUrl = getClass().getResource("fxml/MainView.fxml");
        if(mainViewUrl == null) {
            // Handle error
            throw new NotImplementedException();
        }
        // Load in the top level UI object and assign its Controller.
        FXMLLoader fxmlLoader = new FXMLLoader(mainViewUrl, bundle);
        fxmlLoader.setController(new MainViewController());
        root = fxmlLoader.load();
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

        primaryStage.setTitle(bundle.getString("title").replaceAll("\"", ""));


    }

    public void viewEffort(Effort effort) {

        URL url = getClass().getResource("detail/fxml/DetailView.fxml");
        FXMLLoader loader = new FXMLLoader(url, bundle);
        DetailViewController controller = new DetailViewController(effort, Mode.View);
        loader.setController(controller);
        try {
            VBox detailsView = loader.load();
            root.setCenter(detailsView);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
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

    public static ResourceBundle getBundle() {
        return bundle;
    }
}
