import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class S02_menuController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private FXMLLoader loader;

    public void menuShoppingList(ActionEvent event) throws IOException {
        loadFxml("s03_shoppingList.fxml");
        //loadFxml("xxx.fxml");
        loadStage(event);
    }

    public void menuLunchIdeas(ActionEvent event) throws IOException {
        loadFxml("coNaObiad.fxml");
        loadStage(event);
    }

    public void menuTaskList(ActionEvent event) throws IOException {
        loadFxml("s04_taskList.fxml");
        S04_taskListController date = loader.getController();
        date.setTodaysDate();
        date.setDatesChoiceBox();
        date.saveTheListButton3.setDisable(true);
        loadStage(event);
    }
    
    public void loadFxml(String fxmlFile) throws IOException {
        loader = new FXMLLoader(getClass().getResource(fxmlFile));
        root = loader.load();
    }

    public void loadStage(ActionEvent event) throws IOException{
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
