import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Kura extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("tytul.fxml"));
        primaryStage.setTitle("Kura Domowa");
        Image icon = new Image(getClass().getResourceAsStream("images/kura-image.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(new Scene(root, 355, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}