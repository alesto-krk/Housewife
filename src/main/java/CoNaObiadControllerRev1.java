import javafx.application.Platform;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;

public class CoNaObiadControllerRev1 implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    ChoiceBox<String> soupChoiceBox;
    @FXML
    ChoiceBox<String> mainDishChoiceBox;

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nazwy_dan", "root", "MYSQLmonica3#");
            Statement statement = conn.createStatement();
            ResultSet resultSetMD = statement.executeQuery("select main_dish_name from drugie_dania");
            while (resultSetMD.next()) {
                mainDishChoiceBox.getItems().add(resultSetMD.getString("main_dish_name"));
                //Image img = new Image(getClass().getResourceAsStream(resultSet.getString("main_dish_path")));
                //image.setImage(img);
            }
            ResultSet resultSetS = statement.executeQuery("select soup_name from zupy");
            while (resultSetS.next()) {
                soupChoiceBox.getItems().add(resultSetS.getString("soup_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void goToMenu(ActionEvent event) throws IOException {
        tytulController t = new tytulController();
        t.goToMenu(event);
    }

    public void exitButton(){
        Platform.exit();
    }

}
