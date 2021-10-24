import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DishController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    ImageView myImage;

    public void showGeneratedImage(int dishNumber, String statement, String result){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nazwy_dan", "root", "MYSQLmonica3#");
            Statement stat = conn.createStatement();
            ResultSet resultSet = stat.executeQuery(statement + dishNumber);
            while (resultSet.next()) {
                Image img = new Image(getClass().getResourceAsStream(resultSet.getString(result)));
                myImage.setImage(img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showChosenImage(ChoiceBox<String> choicebox, String statement, String result){
        String chosenDish = choicebox.getValue();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nazwy_dan", "root", "MYSQLmonica3#");
            Statement stat = conn.createStatement();
            ResultSet resultSet = stat.executeQuery(statement + chosenDish + "'");
            while (resultSet.next()) {
                Image img = new Image(getClass().getResourceAsStream(resultSet.getString(result)));
                myImage.setImage(img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backButton (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("coNaObiad.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
