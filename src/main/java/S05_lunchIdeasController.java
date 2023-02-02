import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class S05_lunchIdeasController {
    private static Map<Integer, List<String>> all_soups = DishDatabase.getAll_soups();
    private static Map<Integer, List<String>> all_mainDishes = DishDatabase.getAll_mainDishes();
    @FXML
    ChoiceBox<String> soupChoiceBox;
    @FXML
    ChoiceBox<String> mainDishChoiceBox;
    @FXML
    Button chooseSoupButton, chooseMainDishButton, generateSoupButton, generateMainDishButton;

    public void setDishChoiceBox(){
        for (int i=0; i<all_soups.size(); i++)
            soupChoiceBox.getItems().add(all_soups.get(i).get(0));
        soupChoiceBox.getSelectionModel().select(0);
        for (int i=0; i<all_mainDishes.size(); i++)
            mainDishChoiceBox.getItems().add(all_mainDishes.get(i).get(0));
        mainDishChoiceBox.getSelectionModel().select(0);
    }

    public void setStageForDish(Parent rootForDish) {
        Stage stageForDish = new Stage();
        stageForDish.setTitle("Przepis");
        Image icon = new Image(getClass().getResourceAsStream("images/food-emoji.png"));
        stageForDish.getIcons().add(icon);
        stageForDish.setScene(new Scene(rootForDish, 350, 400));
        stageForDish.setResizable(false);
        stageForDish.show();
        CommonMethods.disableButtons(true, chooseSoupButton, chooseMainDishButton, generateSoupButton, generateMainDishButton);
        stageForDish.setOnCloseRequest(e -> {
            CommonMethods.disableButtons(false, chooseSoupButton, chooseMainDishButton, generateSoupButton, generateMainDishButton);
        });
    }

    public void actionForChosen(ActionEvent event, ChoiceBox<String> choicebox, Map<Integer, List<String>> dishDatabase) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("s05a_dish.fxml"));
        Parent rootForDish = (Parent) loader.load();
        S05a_dishController dish = loader.getController();
        dish.showChosenImage(choicebox, dishDatabase);
        setStageForDish(rootForDish);
    }

    public void chooseMainDishButton(ActionEvent event) throws IOException {
        actionForChosen(event, mainDishChoiceBox, all_mainDishes);
    }

    public void chooseSoupButton(ActionEvent event) throws IOException {
        actionForChosen(event, soupChoiceBox, all_soups);
    }

    public int generate(Map<Integer,List<String>> dishMap) {
        Random r = new Random();
        int dishNumber = r.nextInt(dishMap.size());
        return dishNumber;
    }

    public void actionForGenerated(ActionEvent event, int dishNumber, Map<Integer, List<String>> dishDatabase) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("s05a_dish.fxml"));
        Parent rootForDish = (Parent) loader.load();
        S05a_dishController dish = loader.getController();
        dish.showGeneratedImage(dishNumber, dishDatabase);
        setStageForDish(rootForDish);
    }

    public void generateSoupButton(ActionEvent event) throws IOException {
        actionForGenerated(event, generate(all_soups), all_soups);
    }

    public void generateMainDishButton(ActionEvent event) throws IOException {
        actionForGenerated(event, generate(all_mainDishes), all_mainDishes);
    }

    public void goToMenuButton(ActionEvent event) throws IOException {
        CommonMethods menu = new CommonMethods();
        menu.goToMenu(event);
    }

    public void exitButton(){
        Platform.exit();
    }

}
