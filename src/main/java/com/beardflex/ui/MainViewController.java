package com.beardflex.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by David on 07/03/2017.
 */
public class MainViewController implements Initializable {

    @FXML private TreeView<String> projectTree;
    @FXML private MenuBar menuBar;
    @FXML private ListView effortList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(projectTree != null) {
            populateTree();
        } else {
            throw new NotImplementedException();
        }
    }

    public void populateTree() {
        List<String> projects = Arrays.<String>asList(
                "PANCloudDirector :: 3.1 :: Meteor",
                "PANCloudDirector :: 3.2 :: Nano",
                "PANCloudDirector :: 3.3 :: Ovary",
                "PCDTools :: 3.1 :: Meteor"
        );

        List<String> efforts = Arrays.<String>asList(
                "[BUG    ] 41686 :: DNS should not be updated.",
                "[FEATURE] 51678 :: Add cancer curing."
        );

        TreeItem root = new TreeItem<String>("Projects");
        projectTree.setRoot(root);

        for (String project : projects ) {
            TreeItem<String> rootNode = new TreeItem<String>(project);
            for (String child: efforts ) {
                TreeItem childNode = new TreeItem<String>(child);
                rootNode.getChildren().add(childNode);
            }
            root.getChildren().add(rootNode);
        }

    }

    public void populateList() {

    }
}
