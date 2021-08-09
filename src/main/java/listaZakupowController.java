import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public class listaZakupowController {

    private LinkedList<String> listaZakupow = new LinkedList<>();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TextField dodajField;
    @FXML
    GridPane siatka;

    public void dodajButton(ActionEvent event) throws IOException {
        addElementToList();
        refreshList(event);
    }

    public void refreshList(ActionEvent event) throws IOException{
        LinkedList<String> username = listaZakupow;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("listaZakupow.fxml"));
        root = loader.load();
        listaZakupowController odswiez = loader.getController();
        odswiez.showList(username);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addElementToList(){
        if (!dodajField.getText().isEmpty()){
            listaZakupow.add(dodajField.getText());
            System.out.println("lista " + listaZakupow);
            dodajField.clear();
        }
        else {
            System.out.println("nic nie jest wpisane");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Coś poszło nie tak");
            alert.setHeaderText("Trzeba coś wpisać :)");
            alert.setContentText("");
            alert.show();
        }
    }

    public void showList(LinkedList<String> lista) {
        this.listaZakupow.addAll(lista);
        int rowIndex = 0;
        for (String l : listaZakupow) {
            Label listaLabela = new Label(rowIndex+1 + ". " + l);
            Button usunElement = new Button("Usuń");
            usunElement.setOnAction(e -> {
                listaZakupow.remove(l);
                //listaLabela.setOpacity(0.3);
                listaLabela.setVisible(false);
            });
            siatka.getChildren().add(listaLabela);
            siatka.getChildren().add(usunElement);
            siatka.setConstraints(listaLabela, 0, rowIndex);
            siatka.setConstraints(usunElement, 1, rowIndex);
            rowIndex++;
        }
    }

    public void usunWszystkoButton(ActionEvent event) throws IOException{
        listaZakupow.clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("listaZakupow.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
