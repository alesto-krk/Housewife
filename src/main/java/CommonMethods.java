import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;

public class CommonMethods {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static void showAlert(Alert.AlertType alertType, String alertTitle, String alertMsg) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertMsg);
        alert.setContentText(null);
        alert.show();
    }

    public void goToMenu (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("s02_menu.fxml"));
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
            System.out.println("New file is created");
        else
            System.out.println("There is no file");
        //System.out.println(f.getName());
    }

    public static LinkedList<File> dolistOfSavedTaskLists(){       //sciezka u kogos na komputerze?
        LinkedList<File> listOfSavedTaskLists = new LinkedList<>();
        String directory = "C:\\Users\\Ola\\IdeaProjects\\KuraDomowa\\Listy-zadan";
        //String directory = "Listy-zadan";

        File file = new File(directory);
        File[] files = file.listFiles();
        for (File e : files) {
            listOfSavedTaskLists.add(e);
            //System.out.println(e);
        }
        return listOfSavedTaskLists;
    }

    public static void disableButtons(boolean t, Button...buttons){
        for(int i=0; i<buttons.length; i++)
            buttons[i].setDisable(t);
    }

}
