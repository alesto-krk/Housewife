import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.LinkedList;

public class ListaZadanNaDzisController {

    @FXML
    Label date;
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
    @FXML
    ChoiceBox<String> datesChoiceBox;
    @FXML
    Button saveTheListButton3;
    @FXML
    Button showTaskListButton;

    private LinkedList<String> listaZadan = new LinkedList<>();
    private String todaysDateForRefreshing;
    private String chosenDateForRefreshing;
    private String chosenDateForRefreshingForTxt;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private  CommonMethods commonMethods = new CommonMethods();
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
            if(dodajField2.getText().isEmpty()) {
                CommonMethods.showAlert(Alert.AlertType.ERROR, "Coś poszło nie tak", "Trzeba coś wpisać :)");
                chosenDateForRefreshing = chosenDate.getText();
                chosenDateForRefreshingForTxt = chosenDateFormatForTxt.getText();
                todaysDateForRefreshing = date.getText();
            }
            else
            { CommonMethods.showAlert(Alert.AlertType.ERROR, "Niestety...", "Więcej się nie zmieści :)");
                }
        }
    }

    public void addListToGridPane(LinkedList<String> createdList){
        int rowIndex = 0;
        for (String l : createdList) {
            Label listaLabela = new Label(rowIndex + 1 + ". " + l);
            Button usunElement = new Button("Usuń");
            usunElement.setFont(Font.font(9));
            usunElement.setOnAction(e -> {
                createdList.remove(l);
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

    public void showList2(LinkedList<String> lista, String z, String y, String x) {
        this.listaZadan.addAll(lista);
        this.todaysDateForRefreshing = y;
        this.chosenDateForRefreshing = z;
        this.chosenDateForRefreshingForTxt = x;
        addListToGridPane(listaZadan);
        chosenDate.setText(chosenDateForRefreshing);
        chosenDateFormatForTxt.setText(chosenDateForRefreshingForTxt);
        chosenDateFormatForTxt.setVisible(false);
        date.setText(todaysDateForRefreshing);
        datePicker.setDisable(true);
        if (listaZadan.isEmpty())
        saveTheListButton3.setDisable(true);
        else saveTheListButton3.setDisable(false);
        setDatesChoiceBox();

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

    public void setAnotherDateOrTask(ActionEvent event) throws IOException{
        //listaZadan.clear();
        //siatka2.setVisible(false);
        datePicker.setDisable(false);
        /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
        });*/
    }

    public void saveTheListButton2(ActionEvent event) throws IOException {
        String pathname = "Listy-zadan/lista-zadan-na-" + chosenDateFormatForTxt.getText() + "-" + chosenDate.getText() + ".txt";
        System.out.println("^^^^^^^^^" + pathname);
        if (pathname.equals("Listy-zadan/lista-zadan-na--<nie wybrałeś/aś daty>.txt") || pathname.equals("Listy-zadan/lista-zadan-na-.txt") || pathname.equals("") || chosenDate.equals(null) || chosenDate.equals("<nie wybrałeś/aś daty>"))
            CommonMethods.showAlert(Alert.AlertType.WARNING, "Nieustawiona data", "Kliknij -Ustaw nową datę- ");
        else
            commonMethods.checkIfFileExists(pathname);
        try {
            if (!listaZadan.isEmpty()) {
                FileWriter fw = new FileWriter(pathname, true);
                BufferedWriter zapis = new BufferedWriter(fw);
                for (String e : listaZadan) {
                    zapis.write(e);
                    zapis.newLine();
                    System.out.println(pathname);
                   // zapis.println(e);
                }
                zapis.close();
                listaZadan.clear();
                siatka2.setVisible(false);
                CommonMethods.showAlert(Alert.AlertType.INFORMATION, "Lista", "Zapisano listę zadań");
            } else
                System.out.println("pusta lista");
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
        setDatesChoiceBox();
    }

    public void setDatesChoiceBox() {
        /*LinkedList<File> listOfSavedTaskLists = new LinkedList<>();

        String directory = "C:\\Users\\Ola\\IdeaProjects\\KuraDomowa\\Listy-zadan";     //jak u kogos na kompie to sprawdzic, u kazdego ta sciezka bedzie inna
        File file = new File(directory);
        File[] files = file.listFiles();
        for (File e : files) {
            listOfSavedTaskLists.add(e);
            System.out.println(e);
        }*/
        String fileItemForChoiceBox;
        LinkedList<File> listOfSavedTaskLists = CommonMethods.dolistOfSavedTaskLists();
        Collections.sort(listOfSavedTaskLists);
        for (int i = 0; i < listOfSavedTaskLists.size(); i++) {
            String fileItem = listOfSavedTaskLists.get(i).toString();
            LocalDate txtDate = LocalDate.parse(fileItem.substring(64, 74));
            if (!txtDate.isBefore(todaysDate)) {
                String fileItemShortened = fileItem.substring(75, fileItem.length() - 4); //to co w choiceboxie
                if (fileItemShortened.equals(date.getText())) {
                    fileItemForChoiceBox = "dziś";
                } else fileItemForChoiceBox = fileItemShortened;
                datesChoiceBox.getItems().add(fileItemForChoiceBox);
                datesChoiceBox.getSelectionModel().select(0);
            }
        }
    }

    public void showTaskList(ActionEvent event) throws IOException {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("savedTaskList.fxml"));
                Parent root3 = (Parent) loader.load();
                Stage stage3 = new Stage();
                stage3.setTitle("Twoja lista zadań");
                SavedTaskListController savedTaskListController = loader.getController();
                savedTaskListController.addTocheckbox(datesChoiceBox);
                Image icon = new Image(getClass().getResourceAsStream("images/zadanie-na-dzis.jpg"));
                stage3.getIcons().add(icon);
                stage3.setScene(new Scene(root3, 450, 450));
                stage3.show();
            /*disableButtons(true);
            stage2.setOnCloseRequest(e -> {
                disableButtons(false);
            });*/
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error!");
            }
    }

    public void goToMenuButton(ActionEvent event) throws IOException {
        commonMethods.goToMenu(event);
    }

    public void exitButton(){
        Platform.exit();
    }
}
