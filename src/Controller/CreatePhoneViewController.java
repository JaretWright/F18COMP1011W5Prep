package Controller;

import Models.DBConnect;
import Models.MobilePhone;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreatePhoneViewController implements Initializable {
    @FXML private ChoiceBox<String> makeChoiceBox;
    @FXML private ChoiceBox<String> osChoiceBox;
    @FXML private Label memoryLabel;
    @FXML private Slider memorySlider;
    @FXML private TextField modelTextField;
    @FXML private TextField screenSizeTextField;
    @FXML private TextField frontCameraTextField;
    @FXML private TextField rearCameraTextField;
    @FXML private TextField priceTextField;
    @FXML private ImageView imageView;



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
        memoryLabel.setText("");
        memorySlider.valueProperty().addListener((observable, oldValue, newValue) ->
                                    memoryLabel.setText(String.format("%.0f",newValue)));
    }

    /**
     * This method will update the slider memory label.  Connect it with "onScroll" in SceneBuilder
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

    @FXML
    public void createPhoneButtonPushed()
    {
        MobilePhone newPhone = new MobilePhone(makeChoiceBox.getValue(),
                                               modelTextField.getText(),
                                            osChoiceBox.getValue(),
                                            Double.parseDouble(screenSizeTextField.getText()),
                                            Double.parseDouble(memoryLabel.getText()),
                                            Double.parseDouble(frontCameraTextField.getText()),
                                            Double.parseDouble(rearCameraTextField.getText()));
        System.out.printf("The new phone is %s%n", newPhone);
    }

    public void chooseImageButtonPushed(ActionEvent event)
    {
        //get the Stage to open a new window (or Stage in JavaFX)
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Instantiate a FileChooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        //filter for .jpg and .png
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("Image File (*.jpg, *.png)", "*.jpg", "*.png");
//        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("Image File (*.png)", "*.png");
//        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);
        fileChooser.getExtensionFilters().add(jpgFilter);

        //Set to the user's picture directory or user directory if not available
        String userDirectoryString = System.getProperty("user.home")+"\\Pictures";
        File userDirectory = new File(userDirectoryString);

        //if you cannot navigate to the pictures directory, go to the user home
        if (!userDirectory.canRead())
            userDirectory = new File(System.getProperty("user.home"));

        fileChooser.setInitialDirectory(userDirectory);

        //open the file dialog window
        File imageFile = fileChooser.showOpenDialog(stage);

        if (imageFile != null)
        {
            //update the ImageView with the new image
            if (imageFile.isFile())
            {
                try
                {
//                    BufferedImage bufferedImage = ImageIO.read(imageFile);
//                    Image img = SwingFXUtils.toFXImage(bufferedImage, null);
                    Image image = new Image(imageFile.toURI().toString());
                    imageView.setImage(image);
                }
                catch (Exception e)
                {
                    System.err.println(e.getMessage());
                }
            }
        }

    }

}
