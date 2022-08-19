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
    private String directory = System.getProperty("user.home") + "\\Desktop\\Listy\\Lista-zakupow\\";
    private String pathname = directory + "lista-zakupow.txt";
    private Stage stage;
    private Scene scene;
    private Parent root;
    private CommonMethods file = new CommonMethods();
    @FXML
    private TextField addField;
    @FXML
    private GridPane gridPaneForList;
    @FXML
    private Button showTheListButton, addButton, saveTheListButton, deleteAllButton, refreshAllButton;

    public String getPathname() {
        return pathname;
    }

    public TextField getAddField() {
        return addField;
    }

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
        CommonMethods.textFieldLimit(25, refresh.getAddField());
        refresh.showShoppingList(shoppingListForRefreshing);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //for refreshList()
    public void showShoppingList(LinkedList<String> list) {
        this.shoppingList.addAll(list);
        int rowIndex = 0;
        for (String s : shoppingList) {
            Label article = new Label(rowIndex + 1 + ". " + s);
            article.setStyle("-fx-text-fill: white; -fx-font-size: 12;");
            Button deleteArticleButton = new Button("Usuń");
            deleteArticleButton.setStyle("-fx-font-size: 11;");
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
        file.createDirectoryForList(directory);
        file.checkIfFileExists(pathname);
        System.out.println(pathname);
        try {
            if (!shoppingList.isEmpty()) {
                PrintWriter savedList = new PrintWriter(pathname);
                for (String e : shoppingList) {
                    savedList.println(e);
                }
                savedList.close();
                CommonMethods.showAlert(Alert.AlertType.INFORMATION, "Lista", "Zapisano listę zakupów");
            } else
                CommonMethods.showAlert(Alert.AlertType.WARNING, "Lista", "Lista jest pusta lub lista już istnieje");
        } catch (IOException ioe) {
            System.out.println("03_saveTheListError!");
        }
    }

    public void showSavedList(ActionEvent event) throws IOException {
        file.createDirectoryForList(directory);
        file.checkIfFileExists(pathname);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("s03a_savedShoppingList.fxml"));
            Parent rootForShList = (Parent) loader.load();
            S03a_savedShoppingListController savedShoppingListController = loader.getController();
            savedShoppingListController.checkboxes();
            Stage stageForShList = new Stage();
            stageForShList.setTitle("Twoje zakupy");
            Image icon = new Image(getClass().getResourceAsStream("images/shopping-list.jpg"));
            stageForShList.getIcons().add(icon);
            stageForShList.setScene(new Scene(rootForShList, 350, 400));
            stageForShList.setResizable(false);
            stageForShList.show();
            CommonMethods.disableButtons(true, addButton,saveTheListButton,showTheListButton,deleteAllButton,refreshAllButton);
            stageForShList.setOnCloseRequest(e -> {
                CommonMethods.disableButtons(false, addButton,saveTheListButton,showTheListButton,deleteAllButton,refreshAllButton);
            });
        }
        catch (Exception e){
            System.out.println("03_showTheListError!");
            e.printStackTrace();
        }
    }

    public void goToMenuButton(ActionEvent event) throws IOException {
        CommonMethods menu = new CommonMethods();
        menu.goToMenu(event);
    }

    public void exitButton(){
        Platform.exit();
    }

}
