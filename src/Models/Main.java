package Models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args)
    {
        //MobilePhone validPhone = new MobilePhone("Apple","iPhone","iOS",10,64,12,12);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../Views/CreatePhoneView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Create Phone");
        stage.getIcons().add(new Image("Images/phoneIcon.jpg"));
        stage.show();
    }
}
