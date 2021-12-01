import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.LinkedList;

public class ListaZadanNaDzisController {       //jak wyskoczy lista zadan do wykonania opcja done i zostaw na potem + zapisz

    @FXML                                       //ustaw przypominajke moze po kliknieciu w zapisz dopiero. ustaw na dzien przed godz.20, ten sam dzien godz.8, nie ustawiaj wogole
    Label date;                                 //pokaz zadania button w miejsce ustaw przypominajke
    @FXML
    Label chosenDate;
    @FXML
    Label chosenDateFormatForTxt;
    @FXML
    DatePicker datePicker;
    @FXML
    TextField dodajField2;
    @FXML
    GridPane siatka2;

    private LinkedList<String> listaZadan = new LinkedList<>();
    private String todaysDateForRefreshing;
    private String chosenDateForRefreshing;
    private String chosenDateForRefreshingForTxt;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private  CommonMethods file = new CommonMethods();
    LocalDate todaysDate = LocalDate.now();

    public void setTodaysDate(){
       String df =  DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(todaysDate).toString();
       date.setText(df);
    }

    public void chosenDatePicker(ActionEvent event){
        if(!datePicker.getValue().isBefore(todaysDate)) {
            LocalDate dateForLabel = datePicker.getValue();
            String dfl = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(dateForLabel).toString();
            chosenDate.setText(dfl);
            chosenDateFormatForTxt.setText(dateForLabel.toString());
            chosenDateFormatForTxt.setVisible(false);
        }
        else {
            CommonMethods.showAlert(Alert.AlertType.WARNING, "Warning", "Nie da się ustawić zadań na minione dni :)");
        }
       //String a = String.valueOf(dateForLabel);
    }

    public void dodajButton2(ActionEvent event) throws IOException { 
        addElementToList2();
        refreshList2(event);
    }

    public void addElementToList2() {
        if (!dodajField2.getText().isEmpty() && listaZadan.size() < 10) {
            listaZadan.add(dodajField2.getText());
            chosenDateForRefreshing = chosenDate.getText();
            chosenDateForRefreshingForTxt = chosenDateFormatForTxt.getText();
            todaysDateForRefreshing = date.getText();
            System.out.println("------lista zadan " + listaZadan);
            System.out.println("lista daty " + chosenDateForRefreshing);
            dodajField2.clear();
        } else {
            if(dodajField2.getText().isEmpty())
                CommonMethods.showAlert(Alert.AlertType.ERROR, "Coś poszło nie tak", "Trzeba coś wpisać :)");
            else
                CommonMethods.showAlert(Alert.AlertType.ERROR, "Niestety...", "Więcej się nie zmieści :)");
        }
    }

    public void showList2(LinkedList<String> lista, String z, String y, String x) {
        this.listaZadan.addAll(lista);
        this.todaysDateForRefreshing = y;
        this.chosenDateForRefreshing = z;
        this.chosenDateForRefreshingForTxt = x;
        int rowIndex = 0;
        for (String l : listaZadan) {
            Label listaLabela = new Label(rowIndex + 1 + ". " + l);
            Button usunElement = new Button("Usuń");
            usunElement.setFont(Font.font(9));
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
        chosenDate.setText(chosenDateForRefreshing);
        chosenDateFormatForTxt.setText(chosenDateForRefreshingForTxt);
        chosenDateFormatForTxt.setVisible(false);
        date.setText(todaysDateForRefreshing);
        datePicker.setDisable(true);
    }

    public void refreshList2(ActionEvent event) throws IOException {
        LinkedList<String> taskList = listaZadan;
        String z = chosenDateForRefreshing;
        String x = chosenDateForRefreshingForTxt;
        String y = todaysDateForRefreshing;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("listaZadanNaDzis.fxml"));
        root = loader.load();
        ListaZadanNaDzisController odswiez = loader.getController();
        odswiez.showList2(taskList, z, y,x);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setAnotherDate(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Czy skasować obecną listę?");
        alert.setContentText(null);
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == yesButton) {
                listaZadan.clear();
                siatka2.setVisible(false);
                datePicker.setDisable(false);
            }
            else if (type == noButton) {
                datePicker.setDisable(false);
            }
        });
    }

    public void saveTheListButton2(ActionEvent event) throws IOException {
        String pathname = "lista-zadan-na-" + chosenDateFormatForTxt.getText() + ".txt";
        if (pathname.equals("lista-zadan-na-<nie wybrałeś/aś daty>.txt") || pathname.equals("lista-zadan-na-.txt"))
            CommonMethods.showAlert(Alert.AlertType.WARNING, "Nieustawiona data", "Kliknij -Ustaw nową datę-");
        else
        file.checkIfFileExists(pathname);
        /*
        try {
            if (!listaZakupow.isEmpty()) {
                PrintWriter zapis = new PrintWriter("moja-lista-zakupow.txt");
                for (String e : listaZakupow) {
                    zapis.println(e);
                }
                zapis.close();*/
               /* DataOutputStream out = new DataOutputStream(new FileOutputStream("moja-lista-zakupow.txt"));
                out.writeBytes(listaZakupow.toString());
                out.close();*/ //ale wtedy lista zapisuje sie w jednej linii
                /*CommonMethods.showAlert(Alert.AlertType.INFORMATION, "Lista", "Zapisano listę zakupów");
            } else
                System.out.println("pusta lista");
        } catch (IOException ioe) {
            System.out.println("Error!");
        }*/
    }

    public void goToMenuButton(ActionEvent event) throws IOException {
        CommonMethods menu = new CommonMethods();
        menu.goToMenu(event);
    }
}
