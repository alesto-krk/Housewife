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
import java.util.LinkedList;

public class CommonMethods {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static final String directoryForShoppingList = System.getProperty("user.home") + "\\Desktop\\Listy\\Lista-zakupow\\";
    private static final String directoryForTaskList = System.getProperty("user.home") + "\\Desktop\\Listy\\Listy-zadan\\";
    private static final int userHomeLength = System.getProperty("user.home").length();

    public static String getDirectoryForShoppingList() {
        return directoryForShoppingList;
    }

    public static String getDirectoryForTaskList() {
        return directoryForTaskList;
    }

    public static int getUserHomeLength() {
        return userHomeLength;
    }

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

   public void createDirectoryForList(String directory){
       File f = new File(directory);
       if(f.mkdirs())
           System.out.println("Folder created");
       else System.out.println("Creating folder got wrong or the folder exists");
   }

    public void checkIfFileExists(String pathname) throws IOException {
        File f = new File(pathname);
        if (f.exists()) {
            System.out.println("File exists. Go on.");
        } else if (f.createNewFile())
            System.out.println("New file is created");
        else
            System.out.println("There is no file");
    }

    public static LinkedList<File> dolistOfSavedTaskLists(){
        LinkedList<File> listOfSavedTaskLists = new LinkedList<>();
        String directory = getDirectoryForTaskList();
        File file = new File(directory);
        File[] files = file.listFiles();
        for (File e : files) {
            listOfSavedTaskLists.add(e);
        }
        return listOfSavedTaskLists;
    }

    public static void disableButtons(boolean t, Button...buttons){
        for(int i=0; i<buttons.length; i++)
            buttons[i].setDisable(t);
    }

    public static void textFieldLimit(int limit, TextField textField){
        textField.setOnKeyTyped(e-> {
            if(textField.getText().length()>limit){
                textField.deleteText(limit, limit+1);
            }
        });
    }

}
