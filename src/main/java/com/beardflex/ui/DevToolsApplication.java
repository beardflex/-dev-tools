package com.beardflex.ui;

import com.beardflex.db.HibernateUtil;
import com.beardflex.event.EventManager;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by David on 07/03/2017.
 */
public class DevToolsApplication extends Application {

    private final Logger log = LogManager.getLogger();

    private static ResourceBundle bundle = ResourceBundle.getBundle("bundles.Strings");
    public static AssetManager assetManager;
    private BorderPane root;

    private Thread eventManagerThread;
    private Background background = Background.get();


    /**
     * JavaFX entry point. Sets up the stage and adds the UI items to the view.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        log.info("/dev/tools - Initialising...");

        // Start the Event Manager Thread.
        eventManagerThread = new Thread(EventManager.get());
        eventManagerThread.setName("/dev/tools Event Manager Thread");
        eventManagerThread.start();

        background.fireAndForget(new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                Session session = HibernateUtil.get().getSession();
                session.getTransaction().commit();
                session.close();
                return null;
            }
        });

        assetManager = new AssetManager();
        assetManager.loadImages();

        log.info("Loading Main View fxml.");
        // Attempt to load the Main View, raise a notice if the URL is null
        URL mainViewUrl = getClass().getResource("fxml/MainView.fxml");
        if(mainViewUrl == null) {
            // Handle error
            log.error("Failed to find Main View fxml on classpath.");
            throw new NotImplementedException();
        }
        // Load in the top level UI object and assign its Controller.
        FXMLLoader fxmlLoader = new FXMLLoader(mainViewUrl, bundle);
        fxmlLoader.setController(new MainViewController());
        root = fxmlLoader.load();
        // Create a scene with the top level UI element created above.
        Scene scene = new Scene(root);
        // Attempt to load in the style sheet.
        log.info("Loading application stylesheet.");
        URL styleSheetUrl = getClass().getResource("css/theme-light.css");
        if(styleSheetUrl == null) {
            // Handle Error
            log.error("Failed to find application stylesheet on classpath.");
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

    public void onExit() {
        log.info("Received exit request.");
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
