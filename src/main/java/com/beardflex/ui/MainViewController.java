package com.beardflex.ui;

import com.beardflex.bean.*;
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

    @FXML private BorderPane borderPane;
    @FXML private TreeView<Effort> effortTree;
    @FXML private MenuBar menuBar;
    @FXML private TabPane tabPane;
    @FXML private VBox detailView;

    private TreeItem<Effort> root;

    private ResourceBundle bundle;

    private DevToolsEventListener eventListener;

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
            URL detailViewFxmlUrl = getClass().getResource("detail/fxml/DetailView.fxml");
            switch(event.getIntent()) {
                case Create:
                    Runnable createEffort = () -> {
                        if(detailViewFxmlUrl != null) {
                            DetailViewController controller = new DetailViewController(event.getEffort(), event.getIntent());
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
                    break;
                case View:
                    Runnable viewEffort = () -> {
                        if(detailViewFxmlUrl != null) {
                            DetailViewController controller = new DetailViewController(event.getEffort(), event.getIntent());
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
                    Platform.runLater(viewEffort);
                    break;
            }
        };

        EventManager.get().addListener(eventListener);

    }

    public void populateTree() {


        effortTree.setCellFactory( cellFactory -> {
            final EffortTypeTreeCell cell = new EffortTypeTreeCell();

            ContextMenu contextMenu = new ContextMenu();

            return cell;
        });
        Project rootNode = new Project();
        root = new TreeItem<Effort>(rootNode);
        effortTree.setRoot(root);

        List<Project> projects = getProjects();

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

    public List<Project> getProjects() {
        Version version = new Version(3,1,0);
        Project pcd31 = new Project("PANCloudDirector", "Meteor", version);

        Bug bug41686 = new Bug();
        bug41686.setName("[POV 1719754] PCDTools expects that all servers have a primary and secondary DNS server configured and fails in the case of only the Primary DNS being set");
        bug41686.setIssueNumber("41686");

        pcd31.getChildren().add(bug41686);

        Feature feature = new Feature();
        feature.setName("Partner Uplift for Customer Servers");
        feature.setIssueNumber("41273");

        pcd31.getChildren().add(feature);

        return Arrays.asList(pcd31);
    }

    public void populateList() {

    }
}
