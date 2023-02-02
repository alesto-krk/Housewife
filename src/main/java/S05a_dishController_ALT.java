import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class S05a_dishController_ALT {
    private final String URL = "jdbc:mysql://localhost:3306/lunch";
    private final String USER = "root";
    private final String PASSWORD = "MYSQLmonica3#";
    @FXML
    ImageView myImage;

    public void showGeneratedImage(int dishNumber, String statement, String result){
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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

}
