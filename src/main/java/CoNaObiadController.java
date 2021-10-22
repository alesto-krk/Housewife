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
import java.util.Random;
import java.util.ResourceBundle;

public class CoNaObiadController /*implements Initializable*/ {

    /*private Stage stage;
    private Scene scene;
    private Parent root;
    public Danie soup1 = new Danie("zupa pieczarkowa", new Image("images/thinking-face.png"));   //nauczyc sie w koncu mySQL zeby obrazki trzymac w bazie danych
    public Danie soup2 = new Danie ("zupa pomidorowa", new Image("images/refresh-icon.png"));
    public Danie soup3 = new Danie ("zupa koperkowa", new Image("images/kura-image.png"));
    public Danie soups[] = {soup1, soup2, soup3};
    public Danie mainDish1 = new Danie ("spaghetti", new Image("images/mainDish1.jpg"));
    public Danie mainDish2 = new Danie("ryba", new Image("images/mainDish2.jpg"));
    public Danie mainDish3 = new Danie("schabowy", new Image("images/mainDish3.jpg"));
    public Danie mainDish4 = new Danie("pierogi", new Image("images/mainDish4.jpg"));
    public Danie mainDishes[] = {mainDish1, mainDish2, mainDish3, mainDish4};
    @FXML
    ChoiceBox<String> soupChoiceBox;
    @FXML
    ChoiceBox<String> mainDishChoiceBox;

    public void generateSoupButton(ActionEvent event) throws IOException {
        actionForGenerated(event, soups);
    }

    public void generateMainDishButton(ActionEvent event) throws IOException {
        actionForGenerated(event, mainDishes);
    }

    public void chooseSoupButton(ActionEvent event) throws IOException {
        actionForChosen(event, soups, soupChoiceBox);
    }

    public void chooseMainDishButton(ActionEvent event) throws IOException {
        actionForChosen(event, mainDishes, mainDishChoiceBox);
    }

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1)  {
        for (int i=0; i<soups.length; i++) {
            soupChoiceBox.getItems().add(soups[i].getDishName());}          //O(n)
        for (int i=0; i<mainDishes.length; i++) {
            mainDishChoiceBox.getItems().add(mainDishes[i].getDishName());}         //O(n)
    }

    public int generate(Danie generatedDish[]) {
        Random r = new Random();
        int dishNumber = r.nextInt(generatedDish.length);
        System.out.println(generatedDish[dishNumber]);
        return dishNumber;
    }

    public void actionForGenerated(ActionEvent event, Danie generatedDish[]) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dish.fxml"));
        root = loader.load();
        DishController dish = loader.getController();
        dish.showGeneratedImage(generatedDish[generate(generatedDish)]);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void actionForChosen(ActionEvent event, Danie generatedDish[], ChoiceBox<String> choicebox) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dish.fxml"));
        root = loader.load();
        DishController dish = loader.getController();
        dish.showChosenImage(choicebox, generatedDish);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToMenu(ActionEvent event) throws IOException {
        tytulController t = new tytulController();
        t.goToMenu(event);
    }

    public void exitButton(){
        Platform.exit();
    }
*/
}



