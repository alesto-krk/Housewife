import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.LinkedList;

public class S03_shoppingListController {

    private LinkedList<String> shoppingList = new LinkedList<>();
    private Stage stage;
    private Scene scene;
    private Parent root;
    private CommonMethods file = new CommonMethods();
    @FXML
    TextField addField;
    @FXML
    GridPane gridPaneForList;
    @FXML
    Button showTheListButton, addButton, saveTheListButton, deleteAllButton, refreshAllButton;

    public void addArticle(ActionEvent event) throws IOException {
        addElementToList();
        refreshList(event);
    }

    public void addElementToList() {
        if (!addField.getText().isEmpty() && shoppingList.size() < 10) {
            shoppingList.add(addField.getText());
            addField.clear();
            System.out.println("current list: " + shoppingList);
        } else {
            if(addField.getText().isEmpty())
                CommonMethods.showAlert(Alert.AlertType.ERROR, "Puste pole", "Trzeba coś wpisać :)");
            else
                CommonMethods.showAlert(Alert.AlertType.ERROR, "Niestety...", "Więcej się nie zmieści :)");
        }
    }

    public void refreshList(ActionEvent event) throws IOException {
        LinkedList<String> shoppingListForRefreshing = this.shoppingList;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("s03_shoppingList.fxml"));
        root = loader.load();
        S03_shoppingListController refresh = loader.getController();
        refresh.showList(shoppingListForRefreshing);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //for refreshList() method
    public void showList(LinkedList<String> list) {
        this.shoppingList.addAll(list);
        int rowIndex = 0;
        for (String s : shoppingList) {
            Label article = new Label(rowIndex + 1 + ". " + s);
            article.setStyle("-fx-text-fill: white;");
            Button deleteArticleButton = new Button("Usuń");
            deleteArticleButton.setOnAction(e -> {
                shoppingList.remove(s);
                article.setVisible(false);
                deleteArticleButton.setVisible(false);
            });
            gridPaneForList.getChildren().addAll(article,deleteArticleButton);
            gridPaneForList.setConstraints(article, 0, rowIndex);
            gridPaneForList.setConstraints(deleteArticleButton, 1, rowIndex);
            rowIndex++;
        }
    }

    public void deleteAll(ActionEvent event) throws IOException {
        shoppingList.clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("s03_shoppingList.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveTheList(ActionEvent event) throws IOException {
        file.checkIfFileExists("Lista-zakupow/my-shopping-list.txt");
        try {
            if (!shoppingList.isEmpty()) {
                PrintWriter savedList = new PrintWriter("Lista-zakupow/my-shopping-list.txt");
                for (String e : shoppingList) {
                    savedList.println(e);
                }
                savedList.close();
                CommonMethods.showAlert(Alert.AlertType.INFORMATION, "Lista", "Zapisano listę zakupów");
            } else
                CommonMethods.showAlert(Alert.AlertType.WARNING, "Lista", "Lista jest pusta");
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }

    public void showTheList(ActionEvent event) throws IOException {
        file.checkIfFileExists("Lista-zakupow/my-shopping-list.txt");
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("s03a_savedShoppingList.fxml"));
            Parent rootForShList = (Parent) loader.load();
            Stage stageForShList = new Stage();
            stageForShList.setTitle("Twoje zakupy");
            S03a_savedShoppingListController savedShoppingListController = loader.getController();
            savedShoppingListController.checkboxes();
            Image icon = new Image(getClass().getResourceAsStream("images/sh-list2.jpg"));
            stageForShList.getIcons().add(icon);
            stageForShList.setScene(new Scene(rootForShList, 250, 400));
            stageForShList.show();
            disableButtons(true);
            stageForShList.setOnCloseRequest(e -> {
                disableButtons(false);
            });
        }
        catch (Exception e){
            System.out.println("error!");
            e.printStackTrace();
        }
    }

    public void disableButtons(boolean t){
        addButton.setDisable(t);
        saveTheListButton.setDisable(t);
        showTheListButton.setDisable(t);
        deleteAllButton.setDisable(t);
        refreshAllButton.setDisable(t);
    }

    public void goToMenuButton(ActionEvent event) throws IOException {
        CommonMethods menu = new CommonMethods();
        menu.goToMenu(event);
    }

    public void exitButton(){
        Platform.exit();
    }

}
