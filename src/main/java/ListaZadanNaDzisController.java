import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.LinkedList;

public class ListaZadanNaDzisController {       //jak wyskoczy lista zadan do wykonania opcja done i zostaw na potem + zapisz

    @FXML
    Label date;
    @FXML
    Label chosenDate;
    @FXML
    DatePicker datePicker;
    @FXML
    TextField dodajField2;
    @FXML
    GridPane siatka2;

    private LinkedList<String> listaZadan = new LinkedList<>();
    private Stage stage;
    private Scene scene;
    private Parent root;
    LocalDate todaysDate = LocalDate.now();

    public void setTodaysDate(){
        //date.setText(todaysDate.toString());
       String df =  DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(todaysDate).toString();
       date.setText(df);
    }

    public String chosenDatePicker(ActionEvent event){
        if(!datePicker.getValue().isBefore(todaysDate)) {
            LocalDate dateForLabel = datePicker.getValue();
            String dfl = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(dateForLabel).toString();
            chosenDate.setText(dfl);
        }
        else {
            CommonMethods.showAlert(Alert.AlertType.WARNING, "Warning", "Nie da się ustawić zadań na minione dni :)");
        } return chosenDate.getText(); //nie dziala,moze nowa metoda gdzie bedzie tylko gettext
       //String a = String.valueOf(dateForLabel);
    }

    public void dodajButton2(ActionEvent event) throws IOException { //data sie nie zapisuje!!!!!!!!
        addElementToList2();
        refreshList2(event);
    }

    public void addElementToList2() {
        if (!dodajField2.getText().isEmpty() && listaZadan.size() < 10) {
            listaZadan.add(dodajField2.getText());
            System.out.println("lista " + listaZadan);
            dodajField2.clear();
        } else {
            if(dodajField2.getText().isEmpty())
                CommonMethods.showAlert(Alert.AlertType.ERROR, "Coś poszło nie tak", "Trzeba coś wpisać :)");
            else
                CommonMethods.showAlert(Alert.AlertType.ERROR, "Niestety...", "Więcej się nie zmieści :)");
        }
    }

    public void refreshList2(ActionEvent event) throws IOException {
        LinkedList<String> taskList = listaZadan;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("listaZadanNaDzis.fxml"));
        root = loader.load();
        ListaZadanNaDzisController odswiez = loader.getController();
        odswiez.showList2(taskList);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showList2(LinkedList<String> lista) {

        this.listaZadan.addAll(lista);
        int rowIndex = 0;
        for (String l : listaZadan) {
            Label listaLabela = new Label(rowIndex + 1 + ". " + l);
            Button usunElement = new Button("Usuń");
            usunElement.setOnAction(e -> {
                listaZadan.remove(l);
                //listaLabela.setOpacity(0.3);
                listaLabela.setVisible(false);
                usunElement.setVisible(false);
            });
            siatka2.getChildren().add(listaLabela);
            siatka2.getChildren().add(usunElement);
            siatka2.setConstraints(listaLabela, 0, rowIndex);
            siatka2.setConstraints(usunElement, 1, rowIndex);
            rowIndex++;
        }
    }

    public void goToMenuButton(ActionEvent event) throws IOException {
        CommonMethods menu = new CommonMethods();
        menu.goToMenu(event);
    }
}
