import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class S05_lunchIdeasController_ALT implements Initializable{
    private final String URL = "jdbc:mysql://localhost:3306/lunch";
    private final String USER = "root";
    private final String PASSWORD = "MYSQLmonica3#";
    private String soupStatementForChosen = "select soup_path from all_soups where soup_name='";
    private String mainDishStatementForChosen = "select main_dish_path from all_main_dishes where main_dish_name='";
    private String soupStatementForGenerated = "select soup_path from all_soups where id=";
    private String mainDishStatementForGenerated = "select main_dish_path from all_main_dishes where id=";
    private String soupResult = "soup_path";
    private String mainDishResult = "main_dish_path";
    private static Map<Integer, List<String>> all_soups = DishDatabase.getAll_soups();
    private static Map<Integer, List<String>> all_mainDishes = DishDatabase.getAll_mainDishes();
    @FXML
    ChoiceBox<String> soupChoiceBox;
    @FXML
    ChoiceBox<String> mainDishChoiceBox;
    @FXML
    Button chooseSoupButton, chooseMainDishButton, generateSoupButton, generateMainDishButton;
    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = conn.createStatement();
            ResultSet resultSetMD = statement.executeQuery("select main_dish_name from all_main_dishes");
            while (resultSetMD.next()) {
                mainDishChoiceBox.getItems().add(resultSetMD.getString("main_dish_name"));
            }
            ResultSet resultSetS = statement.executeQuery("select soup_name from all_soups");
            while (resultSetS.next()) {
                soupChoiceBox.getItems().add(resultSetS.getString("soup_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void actionForChosen(ActionEvent event, ChoiceBox<String> choicebox, String statement, String result) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("s05a_dish.fxml"));
        Parent rootForDish = (Parent) loader.load();
        S05a_dishController dish = loader.getController();
        dish.showChosenImage(choicebox, statement, result);
        setStageForDish(rootForDish);
    }

    public void chooseMainDishButton(ActionEvent event) throws IOException {
        actionForChosen(event, mainDishChoiceBox, mainDishStatementForChosen, mainDishResult);
    }

    public void chooseSoupButton(ActionEvent event) throws IOException {
        actionForChosen(event, soupChoiceBox, soupStatementForChosen, soupResult);
    }

    public int generate(Map<Integer,List<String>> dishMap) {
        Random r = new Random();
        int dishNumber = r.nextInt(dishMap.size());
        return dishNumber;
    }

    public void actionForGenerated(ActionEvent event, int dishNumber, Map<Integer, List<String>> dishDatabase) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("s05a_dish.fxml"));
        Parent rootForDish = (Parent) loader.load();
        S05a_dishController_ALT dish = loader.getController();
        dish.showGeneratedImage(dishNumber, dishDatabase);
        setStageForDish(rootForDish);
    }

    public void generateSoupButton(ActionEvent event) throws IOException {
        actionForGenerated(event, generate(all_soups), all_soups);
        System.out.println(all_soups.size());
    }

    public void generateMainDishButton(ActionEvent event) throws IOException {
        actionForGenerated(event, generate(all_mainDishes), all_mainDishes);
        System.out.println(all_mainDishes.size());
    }

    public void goToMenuButton(ActionEvent event) throws IOException {
        CommonMethods menu = new CommonMethods();
        menu.goToMenu(event);
    }

    public void exitButton(){
        Platform.exit();
    }

}
