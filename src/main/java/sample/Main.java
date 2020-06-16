package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));

        primaryStage.setTitle("Tickets");
        
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/ticket.png")));
        primaryStage.setScene(new Scene(root, 570,514));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);

    }



}
