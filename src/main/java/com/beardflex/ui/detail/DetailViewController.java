package com.beardflex.ui.detail;

import com.beardflex.bean.Effort;
import com.beardflex.bean.EffortType;
import com.beardflex.ui.Mode;
import com.beardflex.ui.cell.EffortTypeListCell;
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

    private Effort bean;
    private Mode mode;


    public DetailViewController() {

    }

    public DetailViewController(Effort bean, Mode mode) {
        this.bean = bean;
        this.mode = mode;
    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        if(bean == null) {
            System.out.println("Bean is null");
        }

        if(typeComboBox != null) {
            populateTypes();
        }

        if(bean != null) {
            nameField.setText(bean.getName());

            typeComboBox.getSelectionModel().select(bean.getType());
        }

        if(mode == Mode.View) {
            typeComboBox.setEditable(false);
            typeComboBox.setDisable(true);
            nameField.setEditable(false);
            dueDatePicker.setEditable(false);
            startDatePicker.setEditable(false);
            completedDatePicker.setEditable(false);
        }
    }

    /** */
    private void populateTabPane() {

    }

    private void populateTypes() {
        typeComboBox.setButtonCell(new EffortTypeListCell());
        typeComboBox.setCellFactory(
                param -> {
                    final ListCell<EffortType> cell = new EffortTypeListCell();
                    return cell;
                }
        );

        ObservableList<EffortType> types = FXCollections.observableArrayList(EffortType.values());
        typeComboBox.setItems(types);
    }

    public Effort getBean() {
        return bean;
    }

    public void setBean(Effort bean) {
        this.bean = bean;
    }
}
