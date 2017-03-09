package com.beardflex.ui.cell;

import com.beardflex.bean.EffortType;
import com.beardflex.ui.DevToolsApplication;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

/**
 * Created by David on 09/03/2017.
 */
public class EffortTypeListCell extends ListCell<EffortType> {
    @Override public void updateItem(EffortType value, boolean isEmpty) {
        super.updateItem(value, isEmpty);

        if(!isEmpty) {
            setText(value.name());
            switch(value) {
                case Project:
                    ImageView projectIcon = new ImageView(DevToolsApplication.assetManager.getImage("projectIcon"));
                    projectIcon.setFitHeight(16);
                    projectIcon.setFitWidth(16);
                    setGraphic(projectIcon);
                    break;
                case Bug:
                    ImageView bugIcon = new ImageView(DevToolsApplication.assetManager.getImage("bugIcon"));
                    bugIcon.setFitHeight(16);
                    bugIcon.setFitWidth(16);
                    setGraphic(bugIcon);
                    break;
                case Feature:
                    ImageView featureIcon = new ImageView(DevToolsApplication.assetManager.getImage("featureIcon"));
                    featureIcon.setFitHeight(16);
                    featureIcon.setFitWidth(16);
                    setGraphic(featureIcon);
                    break;
                case ActionItem:
                    ImageView actionItemIcon = new ImageView(DevToolsApplication.assetManager.getImage("actionItemIcon"));
                    actionItemIcon.setFitWidth(16);
                    actionItemIcon.setFitHeight(16);
                    setGraphic(actionItemIcon);
                    break;
            }
        }
    }
}
