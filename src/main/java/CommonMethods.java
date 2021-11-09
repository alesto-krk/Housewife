import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class CommonMethods {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static void showAlert(Alert.AlertType typAlertu, String tytulAlertu, String alertMsg) {
        Alert alert = new Alert(typAlertu);
        alert.setTitle(tytulAlertu);
        alert.setHeaderText(alertMsg);
        alert.setContentText(null);
        alert.show();
    }

    public void goToMenu (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //backTo(String np lista zadan na dzis)
    //checkIfFileExists ?
}
