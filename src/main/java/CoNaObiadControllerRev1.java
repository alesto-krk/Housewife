import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
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
    private String soupStatementForChosen = "select soup_path from zupy where soup_name='";
    private String mainDishStatementForChosen = "select main_dish_path from drugie_dania where main_dish_name='";
    private String soupStatementForGenerated = "select soup_path from zupy where id=";
    private String mainDishStatementForGenerated = "select main_dish_path from drugie_dania where id=";
    private String soupResult = "soup_path";
    private String mainDishResult = "main_dish_path";
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
            }
            ResultSet resultSetS = statement.executeQuery("select soup_name from zupy");
            while (resultSetS.next()) {
                soupChoiceBox.getItems().add(resultSetS.getString("soup_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionForChosen(ActionEvent event, ChoiceBox<String> choicebox, String statement, String result) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dish.fxml"));
        root = loader.load();
        DishController dish = loader.getController();
        dish.showChosenImage(choicebox, statement, result);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void chooseMainDishButton(ActionEvent event) throws IOException {
        actionForChosen(event, mainDishChoiceBox, mainDishStatementForChosen, mainDishResult);
    }

    public void chooseSoupButton(ActionEvent event) throws IOException {
        actionForChosen(event, soupChoiceBox, soupStatementForChosen, soupResult);
    }

    public int generate(String nazwaTabeliMySql) {      //losowanie bez powtorzen?
        Random r = new Random();
        int countId=0;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nazwy_dan", "root", "MYSQLmonica3#");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select id from " + nazwaTabeliMySql);
            while (resultSet.next()) {
                countId++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int dishNumber = r.nextInt(countId)+1;
        return dishNumber;
    }

    public void actionForGenerated(ActionEvent event, int dishNumber, String statement, String result) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dish.fxml"));
        root = loader.load();
        DishController dish = loader.getController();
        dish.showGeneratedImage(dishNumber, statement, result);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void generateSoupButton(ActionEvent event) throws IOException {
        actionForGenerated(event, generate("zupy"), soupStatementForGenerated, soupResult);
    }

    public void generateMainDishButton(ActionEvent event) throws IOException {
        actionForGenerated(event, generate("drugie_dania"), mainDishStatementForGenerated, mainDishResult);
    }

    public void goToMenuButton(ActionEvent event) throws IOException {
        CommonMethods menu = new CommonMethods();
        menu.goToMenu(event);
    }

    public void exitButton(){
        Platform.exit();
    }

}
