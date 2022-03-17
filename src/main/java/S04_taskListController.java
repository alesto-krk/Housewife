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

public class S04_taskListController {

    private LinkedList<String> taskList = new LinkedList<>();
    private String todaysDateForRefreshing;
    private String chosenDateForRefreshing;
    private String chosenDateForRefreshingForTxt;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private  CommonMethods commonMethods = new CommonMethods();
    LocalDate todaysDate = LocalDate.now();
    @FXML
    Label date;
    @FXML
    DatePicker datePicker;
    @FXML
    Label chosenDate;
    @FXML
    Label chosenDateFormatForTxtFile;
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

    //for S02_menuController menuTaskList()
    public void setTodaysDate(){
       String todaysDateFormat =  DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(todaysDate);
       date.setText(todaysDateFormat);
    }

    public void chooseDateFromDatePicker(ActionEvent event){
        LocalDate dateForLabel = datePicker.getValue();
        if(!dateForLabel.isBefore(todaysDate)) {
            String chosenDateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(dateForLabel);
            chosenDate.setText(chosenDateFormat);
            chosenDateFormatForTxtFile.setText(dateForLabel.toString());
            chosenDateFormatForTxtFile.setVisible(false);
        }
        else {
            CommonMethods.showAlert(Alert.AlertType.WARNING, "Warning", "Nie da się ustawić zadań na minione dni :)");
        }
    }

    public void addTask(ActionEvent event) throws IOException {
        addTaskToList();
        refreshTaskList(event);
    }

    public void addTaskToList() {
        if (!dodajField2.getText().isEmpty() && taskList.size() < 10) {
            taskList.add(dodajField2.getText());
            chosenDateForRefreshing = chosenDate.getText();
            chosenDateForRefreshingForTxt = chosenDateFormatForTxtFile.getText();
            todaysDateForRefreshing = date.getText();
            System.out.println("------lista zadan " + taskList);
            System.out.println("lista daty " + chosenDateForRefreshing);
            dodajField2.clear();
        } else {
            if(dodajField2.getText().isEmpty()) {
                CommonMethods.showAlert(Alert.AlertType.ERROR, "Coś poszło nie tak", "Trzeba coś wpisać :)");
                chosenDateForRefreshing = chosenDate.getText();
                chosenDateForRefreshingForTxt = chosenDateFormatForTxtFile.getText();
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
            listaLabela.setStyle("-fx-text-fill: white;");
            Button usunElement = new Button("Usuń");
            usunElement.setStyle("-fx-font-size: 9;");
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
        this.taskList.addAll(lista);
        this.todaysDateForRefreshing = y;
        this.chosenDateForRefreshing = z;
        this.chosenDateForRefreshingForTxt = x;
        addListToGridPane(taskList);
        chosenDate.setText(chosenDateForRefreshing);
        chosenDateFormatForTxtFile.setText(chosenDateForRefreshingForTxt);
        chosenDateFormatForTxtFile.setVisible(false);
        date.setText(todaysDateForRefreshing);
        datePicker.setDisable(true);
        if (taskList.isEmpty())
        saveTheListButton3.setDisable(true);
        else saveTheListButton3.setDisable(false);
        setDatesChoiceBox();

    }

    public void refreshTaskList(ActionEvent event) throws IOException {
        LinkedList<String> taskList = this.taskList;
        String z = chosenDateForRefreshing;
        String x = chosenDateForRefreshingForTxt;
        String y = todaysDateForRefreshing;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("s04_taskList.fxml"));
        root = loader.load();
        S04_taskListController odswiez = loader.getController();
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
        String pathname = "Listy-zadan/lista-zadan-na-" + chosenDateFormatForTxtFile.getText() + "-" + chosenDate.getText() + ".txt";
        System.out.println("^^^^^^^^^" + pathname);
        if (pathname.equals("Listy-zadan/lista-zadan-na--<nie wybrałeś/aś daty>.txt") || pathname.equals("Listy-zadan/lista-zadan-na-.txt") || pathname.equals("") || chosenDate.equals(null) || chosenDate.equals("<nie wybrałeś/aś daty>"))
            CommonMethods.showAlert(Alert.AlertType.WARNING, "Nieustawiona data", "Kliknij -Ustaw nową datę- ");
        else
            commonMethods.checkIfFileExists(pathname);
        try {
            if (!taskList.isEmpty()) {
                FileWriter fw = new FileWriter(pathname, true);
                BufferedWriter zapis = new BufferedWriter(fw);
                for (String e : taskList) {
                    zapis.write(e);
                    zapis.newLine();
                    System.out.println(pathname);
                   // zapis.println(e);
                }
                zapis.close();
                taskList.clear();
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
        if (!listOfSavedTaskLists.isEmpty()) {
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
        } else System.out.println("ppp888");
    }

    public void showTaskList(ActionEvent event) throws IOException {    
        if(!(datesChoiceBox.getValue()==null)) {
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
                System.out.println("error!");
            }
        } else System.out.println("nic nie wybrane");
    }

    public void goToMenuButton(ActionEvent event) throws IOException {
        commonMethods.goToMenu(event);
    }

    public void exitButton(){
        Platform.exit();
    }
}
