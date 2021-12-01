import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
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

    public void checkIfFileExists(String pathname) throws IOException {
        File f = new File(pathname);
        if (f.exists()) {
            System.out.println("File exists. Go on.");
        } else if (f.createNewFile())
            System.out.println("Mamy nowy plik");
        else
            System.out.println("Nie ma takiego pliku");
    }
    //backTo(String np lista zadan na dzis)

}
