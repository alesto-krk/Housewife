import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class CoNaObiadController {   //implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public Image soup1 = new Image("images/thinking-face.png");     //nauczyc sie w koncu mySQL zeby obrazki trzymac w bazie danych
    public Image soup2 = new Image("images/refresh-icon.png");
    public Image soup3 = new Image("images/kura-image.png");
    public Image soups[] = {soup1, soup2, soup3};
    public Image mainDish1 = new Image("images/mainDish1.jpg");
    public Image mainDish2 = new Image("images/mainDish2.jpg");
    public Image mainDish3 = new Image("images/mainDish3.jpg");
    public Image mainDish4 = new Image("images/mainDish4.jpg");
    public Image mainDishes[] = {mainDish1, mainDish2, mainDish3, mainDish4};
   //public Danie soup1 =
    @FXML
    ImageView myImage;
    @FXML
    ChoiceBox<String> soupChoiceBox;

    public void generateSoupButton(ActionEvent event) throws IOException {
        refreshScene(event, soups);
    }

    public void generateMainDishButton(ActionEvent event) throws IOException {
        refreshScene(event, mainDishes);
    }

    /*public void initialize(URL arg0, ResourceBundle arg1) {
    soupChoiceBox.getItems().addAll(soups.toString()); //pobiera kod obrazka a nie nazwe zupy, trzeba dodatkowo zrobic opisy zup
    }*/

    public int generate(Image generatedDish[]) {
        Random r = new Random();
        int dishNumber = r.nextInt(generatedDish.length);
        System.out.println(generatedDish[dishNumber]);
        return dishNumber;
    }

    public void refreshScene(ActionEvent event, Image generatedDish[]) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dish.fxml"));
        root = loader.load();
        CoNaObiadController dish = loader.getController();
        dish.showGeneratedImage(generatedDish[generate(generatedDish)]);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showGeneratedImage(Image image){
        myImage.setImage(image);
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



