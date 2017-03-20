package com.beardflex.ui;

import com.beardflex.bean.*;
import com.beardflex.db.dao.ProjectDAO;
import com.beardflex.event.DevToolsEventListener;
import com.beardflex.event.EventManager;
import com.beardflex.ui.cell.EffortTypeTreeCell;
import com.beardflex.ui.detail.DetailViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by David on 07/03/2017.
 */
public class MainViewController implements Initializable {

    private final Logger log = LogManager.getLogger();

    @FXML private BorderPane borderPane;
    @FXML private TreeView<Effort> effortTree;
    @FXML private MenuBar menuBar;
    @FXML private TabPane tabPane;
    @FXML private VBox detailView;

    private TreeItem<Effort> root;

    private ResourceBundle bundle;

    private DevToolsEventListener eventListener;

    private DetailViewController currentDetailView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;
        String title = bundle.getString("title");

        if(effortTree != null) {
            populateTree();
        } else {
            throw new NotImplementedException();
        }

        eventListener = event -> {
            log.info("Received /dev/tools event.");
            // Check if the user wants to cancel.
            if(currentDetailView != null) {
                log.info("Calling 'onExit' on the current Detail View.");
                if(!currentDetailView.onExit()) {
                    log.info("The user requested a cancellation. Returning.");
                    return;
                }
            }
            URL detailViewFxmlUrl = getClass().getResource("detail/fxml/DetailView.fxml");
            switch(event.getIntent()) {
                case Create:
                    log.info("Received a 'Create Effort' under effort '{}' event.", event.getEffort().getName());
                    break;
                case View:
                    log.info("Received a 'View Effort' for effort '{}' event.", event.getEffort().getName());
                    break;
            }
            final DetailViewController controller = new DetailViewController(event.getEffort(), event.getIntent());
            Runnable createEffort = () -> {
                if(detailViewFxmlUrl != null) {
                    log.info("Setting new Detail View.");
                    FXMLLoader loader = new FXMLLoader(detailViewFxmlUrl, bundle);
                    loader.setController(controller);
                    try {
                        VBox detailView = loader.load();
                        borderPane.setCenter(detailView);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            Platform.runLater(createEffort);
            currentDetailView = controller;
        };

        EventManager.get().addListener(eventListener);

    }

    public void populateTree() {


        effortTree.setCellFactory( cellFactory -> {
            final EffortTypeTreeCell cell = new EffortTypeTreeCell();
            return cell;
        });
        Project rootNode = new Project();
        rootNode.setType(EffortType.Root);
        root = new TreeItem<Effort>(rootNode);
        effortTree.setRoot(root);

        List<Project> projects = new ProjectDAO().listProject();

        for (Project p: projects) {
            addToTree(root, p);
        }

        // Expand the top level tree item.
        root.setExpanded(true);

    }

    private void addToTree(TreeItem<Effort> parent, Effort effort) {
        TreeItem<Effort> __thisNode = new TreeItem<Effort>(effort);
        if(effort.getChildren().size() > 0) {
            for (Effort childEffort: effort.getChildren()) {
                addToTree(__thisNode, childEffort);
            }
        }
        parent.getChildren().add(__thisNode);
    }


    public void populateList() {

    }
}
