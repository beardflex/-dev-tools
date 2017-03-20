package com.beardflex.ui.detail;

import com.beardflex.bean.*;
import com.beardflex.event.Intent;
import com.beardflex.ui.Mode;
import com.beardflex.ui.cell.EffortTypeListCell;
import com.beardflex.ui.commonmark.MarkdownView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by David on 09/03/2017.
 */
public class DetailViewController implements Initializable {

    @FXML private GridPane gridPane;
    @FXML private TabPane tabPane;

    @FXML private Label typeLabel;
    @FXML private Label nameLabel;
    @FXML private Label startDateLabel;
    @FXML private Label dueDateLabel;
    @FXML private Label completedDateLabel;

    @FXML private ComboBox<EffortType> typeComboBox;
    @FXML private TextField nameField;
    @FXML private DatePicker dueDatePicker;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker completedDatePicker;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private Effort bean;
    private Intent intent;

    private MarkdownView descriptionField;
    private ResourceBundle bundle;

    public DetailViewController() {

    }

    public DetailViewController(Effort bean, Intent intent) {
        this.bean = bean;
        this.intent = intent;
    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        bundle = resources;

        if(typeComboBox != null) {
            populateTypes();
        }

        if(bean != null && intent != Intent.Create) {
            loadFromBean();
        }

        switch(intent) {
            case Create:
                // Disable all fields until the type is chosen.
                nameField.setDisable(true);
                dueDatePicker.setDisable(true);
                startDatePicker.setDisable(true);
                completedDatePicker.setDisable(true);
                // Now add a listener onto the type combobox.
                typeComboBox.setOnAction( e -> {
                    EffortType type = typeComboBox.getSelectionModel().getSelectedItem();
                    if(type != null) {
                        switch(type) {
                            case Project:
                                // Check if the parent is null, if not, throw a message dialog up.
                                if(bean.getParent() != null) {
                                    // Dialog
                                    return;
                                }
                                Project project = new Project();
                                project.setParent(bean);
                                bean = project;



                                break;
                            case Feature:
                                // If no parent has been selected, it can't be assigned to a Project, so throw an error.
                                if(bean.getParent() == null) {
                                    // Dialog
                                    return;
                                }
                                if(bean.getParent().getType() != EffortType.Project) {
                                    // Dialog
                                    return;
                                }
                                Feature feature = new Feature();
                                feature.setParent(bean);
                                bean = feature;
                                break;
                            case Bug:
                                if(bean.getParent() == null) {
                                    // Dialog
                                    return;
                                }
                                if(bean.getParent().getType() == EffortType.ActionItem) {
                                    // Dialog
                                    return;
                                }
                                Bug bug = new Bug();
                                bug.setParent(bean);
                                bean = bug;
                                break;
                            case ActionItem:
                                if(bean.getParent() == null) {
                                    // Dialog
                                    return;
                                }
                                ActionItem actionItem = new ActionItem();
                                actionItem.setParent(bean);
                                bean = actionItem;
                        }
                    }
                });

                break;
            case View:
                typeComboBox.setEditable(false);
                typeComboBox.setDisable(true);
                nameField.setEditable(false);
                dueDatePicker.setEditable(false);
                startDatePicker.setEditable(false);
                completedDatePicker.setEditable(false);


                break;
        }
    }

    private void loadFromBean() {
        // Set the Name field
        nameField.setText(bean.getName());
        // Set the Type field.
        typeComboBox.getSelectionModel().select(bean.getType());
        // Set the Due Date field.
        dueDatePicker.setValue(bean.getDueDate());
        // Set the Start Date field.
        startDatePicker.setValue(bean.getStartDate());
        // Set the Completed Date field.
        completedDatePicker.setValue(bean.getCompletedDate());

        populateTabPane();
    }

    /** */
    private void populateTabPane() {

        descriptionField = new MarkdownView(bundle, bean.getDescription());
        Tab descriptionTab = new Tab();
        descriptionTab.setText("Description");
        descriptionTab.setContent(descriptionField);
        tabPane.getTabs().add(0, descriptionTab);

        switch(bean.getType()) {
            case Project:

                break;
            case Feature:

                break;
            case Bug:

                break;
            case ActionItem:

                break;
        }
    }

    private void populateTypes() {
        typeComboBox.setButtonCell(new EffortTypeListCell());
        typeComboBox.setCellFactory(
                param -> {
                    final ListCell<EffortType> cell = new EffortTypeListCell();
                    return cell;
                }
        );

        if(intent == Intent.View) {
            ObservableList<EffortType> types = FXCollections.observableArrayList(bean.getType());
            typeComboBox.setItems(types);
        } else if(intent == Intent.Create) {
            ObservableList<EffortType> types;
            switch(bean.getType()) {
                case Root:
                    types = FXCollections.observableArrayList(
                            EffortType.Project
                    );
                    typeComboBox.setItems(types);
                    break;
                case Project:
                    types = FXCollections.observableArrayList(
                            EffortType.Feature,
                            EffortType.Bug,
                            EffortType.ActionItem);
                    typeComboBox.setItems(types);
                    break;
                case Feature:
                    types = FXCollections.observableArrayList(
                            EffortType.Bug,
                            EffortType.ActionItem);
                    typeComboBox.setItems(types);
                    break;
                case Bug :
                case ActionItem:
                    types = FXCollections.observableArrayList(
                            EffortType.ActionItem);
                    typeComboBox.setItems(types);
                    break;
            }
        }
    }

    public Effort getBean() {
        return bean;
    }

    public void setBean(Effort bean) {
        this.bean = bean;
    }

    public boolean onExit() {
        return true;
    }
}
