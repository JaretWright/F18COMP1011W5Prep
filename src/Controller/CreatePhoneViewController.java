package Controller;

import Models.DBConnect;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreatePhoneViewController implements Initializable {
    @FXML
    private ChoiceBox<String> makeChoiceBox;

    @FXML
    private ChoiceBox<String> osChoiceBox;

    @FXML
    private Label memoryLabel;

    @FXML
    private Slider memorySlider;

    /**
     * This method will provide initial values for the view
     * and configure any listeners
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //configure the available manufacturers and OS ChoiceBox's
            makeChoiceBox.getItems().addAll(DBConnect.getPhoneManufacturers());
            osChoiceBox.getItems().addAll(DBConnect.getAllOperatingSystems());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //this will add a change listener to the manufacturer ChoiceBox.  When it changes, it
        //call the updateOSChoiceBox() method, which will query the DB and automatically choose
        //the operating system
        makeChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                        (observableValue, oldValue, newValue) -> updateOSChoiceBox(newValue));

        //this will add a change listener to the os ChoiceBox.  When the user selects a new
        //OS, the manufacturers list will be configured to show manufacturers that support
        //that operation system
//        osChoiceBox.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, newValue ) -> updateMakeChoiceBox(newValue));


        //Configure the slider
        memorySlider.setMin(8);
        memorySlider.setMax(512);
        memorySlider.valueProperty().addListener((observable, oldValue, newValue) ->
                                                memoryLabel.setText(String.format("%.0f",newValue)));
    }

    /**
     * This method will update the slider memory label
     * @param os
     */
    @FXML
    public void updateMemoryLabel()
    {
        memoryLabel.setText(String.format("%.0f",memorySlider.getValue()));
    }

    public void updateMakeChoiceBox(String os)
    {
        try {
            ArrayList<String> manufacturers = DBConnect.getManufacturersWithOS(os);
            makeChoiceBox.getItems().clear();
            makeChoiceBox.getItems().addAll(manufacturers);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void updateOSChoiceBox(String manufacturer)
    {
        try {
            String os = DBConnect.getOSWithManufacturer(makeChoiceBox.getValue());
            osChoiceBox.setValue(os);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
