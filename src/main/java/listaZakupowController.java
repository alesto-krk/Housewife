import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class listaZakupowController {

    private LinkedList<String> listaZakupow = new LinkedList<>();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TextField dodajField;
    @FXML
    GridPane siatka;
    @FXML
    Button showTheList, add, menu, saveTheList, deleteAll, refreshAll, exit;

    public void dodajButton(ActionEvent event) throws IOException {
        addElementToList();
        refreshList(event);
    }

    public void refreshList(ActionEvent event) throws IOException {
        LinkedList<String> shoppingList = listaZakupow;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("listaZakupow.fxml"));
        root = loader.load();
        listaZakupowController odswiez = loader.getController();
        odswiez.showList(shoppingList);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addElementToList() {
        if (!dodajField.getText().isEmpty() && listaZakupow.size() < 10) {
            listaZakupow.add(dodajField.getText());
            System.out.println("lista " + listaZakupow);
            dodajField.clear();
        } else {
            if(dodajField.getText().isEmpty())
            showAlert(Alert.AlertType.ERROR, "Coś poszło nie tak", "Trzeba coś wpisać :)");
            else
            showAlert(Alert.AlertType.ERROR, "Coś poszło nie tak", "Więcej się nie zmieści :)");
        }
    }

    public void showAlert(Alert.AlertType typAlertu, String tytulAlertu, String alertMsg) {
        Alert alert = new Alert(typAlertu);
        alert.setTitle(tytulAlertu);
        alert.setHeaderText(alertMsg);
        alert.setContentText(null);
        alert.show();
    }

    //for refreshList() method
    public void showList(LinkedList<String> lista) {
        this.listaZakupow.addAll(lista);
        int rowIndex = 0;
        for (String l : listaZakupow) {
            Label listaLabela = new Label(rowIndex + 1 + ". " + l);
            Button usunElement = new Button("Usuń");
            usunElement.setOnAction(e -> {
                listaZakupow.remove(l);
                //listaLabela.setOpacity(0.3);
                listaLabela.setVisible(false);
                usunElement.setVisible(false);
            });
            siatka.getChildren().add(listaLabela);
            siatka.getChildren().add(usunElement);
            siatka.setConstraints(listaLabela, 0, rowIndex);
            siatka.setConstraints(usunElement, 1, rowIndex);
            rowIndex++;
        }
    }

    public void usunWszystkoButton(ActionEvent event) throws IOException {
        listaZakupow.clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("listaZakupow.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveTheListButton(ActionEvent event) throws IOException {
        checkIfFileExists();
        try {
            if (!listaZakupow.isEmpty()) {
                PrintWriter zapis = new PrintWriter("moja-lista-zakupow.txt");
                for (String e : listaZakupow) {
                    zapis.println(e);
                }
                zapis.close();
               /* DataOutputStream out = new DataOutputStream(new FileOutputStream("moja-lista-zakupow.txt"));
                out.writeBytes(listaZakupow.toString());
                out.close();*/ //ale wtedy lista zapisuje sie w jednej linii
                showAlert(Alert.AlertType.INFORMATION, "Lista", "Zapisano listę zakupów");
            } else
                System.out.println("pusta lista");
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }

    public void checkIfFileExists() throws IOException {
        File f = new File("moja-lista-zakupow.txt");
        if (f.exists()) {
            System.out.println("hurra");
        } else if (f.createNewFile())
            System.out.println("Mamy nowy plik");
        else
            System.out.println("Nie ma takiego pliku");
    }

    public void showTheListButton(ActionEvent event) throws IOException {
        checkIfFileExists();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("savedList.fxml"));
            Parent root2 = (Parent) loader.load();
            Stage stage2 = new Stage();
            stage2.setTitle("Twoja lista zakupów");
            SavedListController savedListController = loader.getController();
            savedListController.checkboxes();
            Image icon = new Image(getClass().getResourceAsStream("images/rysunek-listy.jpg"));
            stage2.getIcons().add(icon);
            stage2.setScene(new Scene(root2, 300, 450));
            stage2.show();
            disableButtons(true);
            stage2.setOnCloseRequest(e -> {
                disableButtons(false);
            });
        }
        catch (Exception e){
            System.out.println("error!");
        }
    }

    public void disableButtons(boolean t){
        add.setDisable(t);
        menu.setDisable(t);
        saveTheList.setDisable(t);
        showTheList.setDisable(t);
        deleteAll.setDisable(t);
        refreshAll.setDisable(t);
    }

    public void goToMenu(ActionEvent event) throws IOException {
        tytulController t = new tytulController();
        t.goToMenu(event);
    }

    public void exitButton(){
        Platform.exit();
    }

}
