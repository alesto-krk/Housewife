import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HousewifeMAIN extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("s01_title.fxml"));
        primaryStage.setTitle("Housewife");
        Image icon = new Image(getClass().getResourceAsStream("images/icon.jpg"));
        primaryStage.getIcons().add(icon);
        Scene scene = new Scene(root, 400, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}