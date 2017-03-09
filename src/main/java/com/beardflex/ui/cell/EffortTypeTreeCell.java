package com.beardflex.ui.cell;

import com.beardflex.bean.*;
import com.beardflex.ui.DevToolsApplication;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;

/**
 * Created by David on 09/03/2017.
 */
public class EffortTypeTreeCell extends TreeCell<Effort> {

    public EffortTypeTreeCell() {

        ContextMenu contextMenu = new ContextMenu();

        MenuItem addItem = new MenuItem();
        addItem.setText("Add");
        contextMenu.getItems().add(addItem);

        MenuItem viewItem = new MenuItem();
        viewItem.setText("View");
        contextMenu.getItems().add(viewItem);
        viewItem.setOnAction(e -> {
            DevToolsApplication.instance.viewEffort(getItem());
        });

        MenuItem deleteItem = new MenuItem();
        deleteItem.setText("Delete");
        contextMenu.getItems().add(deleteItem);

        this.setContextMenu(contextMenu);
    }

    @Override public void updateItem(Effort value, boolean isEmpty) {
        super.updateItem(value, isEmpty);
        if(!isEmpty) {

            if (value.getType() == null) {
                setText("Projects");
            } else {
                switch (value.getType()) {
                    case Project:
                        Project project = (Project) value;
                        setText(String.format("%s | %s | %s", project.getName(), project.getVersion(), project.getCodeName()));
                        ImageView projectIcon = new ImageView(DevToolsApplication.assetManager.getImage("projectIcon"));
                        projectIcon.setFitWidth(16);
                        projectIcon.setFitHeight(16);
                        setGraphic(projectIcon);
                        break;
                    case Feature:
                        Feature feature = (Feature) value;
                        setText(feature.getName());
                        ImageView featureIcon = new ImageView(DevToolsApplication.assetManager.getImage("featureIcon"));
                        featureIcon.setFitWidth(16);
                        featureIcon.setFitHeight(16);
                        setGraphic(featureIcon);
                        break;
                    case Bug:
                        Bug bug = (Bug) value;
                        setText(bug.getName());
                        ImageView bugIcon = new ImageView(DevToolsApplication.assetManager.getImage("bugIcon"));
                        bugIcon.setFitWidth(16);
                        bugIcon.setFitHeight(16);
                        setGraphic(bugIcon);
                        break;
                    case ActionItem:
                        ActionItem actionItem = (ActionItem) value;
                        setText(actionItem.getName());
                        ImageView actionItemIcon = new ImageView(DevToolsApplication.assetManager.getImage("actionItemIcon"));
                        actionItemIcon.setFitWidth(16);
                        actionItemIcon.setFitHeight(16);
                        setGraphic(actionItemIcon);
                        break;
                }
            }
        }
    }
}