import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class S02_menuController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private FXMLLoader loader;

    public void menuShoppingList(ActionEvent event) throws IOException {
        loadFxml("s03_shoppingList.fxml");
        S03_shoppingListController textField = loader.getController();
        CommonMethods.textFieldLimit(25, textField.getAddField());
        loadStage(event);
    }

    public void menuTaskList(ActionEvent event) throws IOException {
        loadFxml("s04_taskList.fxml");
        S04_taskListController taskList = loader.getController();
        taskList.setTodaysDate();
        taskList.setDatesChoiceBox();
        CommonMethods.textFieldLimit(40, taskList.getAddTextField());
        taskList.getSaveTaskListButton().setDisable(true);
        loadStage(event);
    }

    public void menuLunchIdeas(ActionEvent event) throws IOException {
        CommonMethods.showAlert(Alert.AlertType.INFORMATION, "Otwarcie biblioteki przepisów może chwilkę zająć :)", "Loading");
        //generate dish database
        //System.out.println(DishDatabase.getAll_soups().size());
        //System.out.println(DishDatabase.getAll_mainDishes());
        //Map<Integer, List<String>> map = DishDatabase.getAll_soups();
        //System.out.println(map.size());
        loadFxml("s05_lunchIdeas.fxml");
        loadStage(event);
    }

    public void loadFxml(String fxmlFile) throws IOException {
        loader = new FXMLLoader(getClass().getResource(fxmlFile));
        root = loader.load();
    }

    public void loadStage(ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
