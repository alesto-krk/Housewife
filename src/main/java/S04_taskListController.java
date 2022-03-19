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
    private String chosenDateForTxtFileForRefreshing;
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
    TextField addTextField;
    @FXML
    GridPane gridPaneForTaskList;
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

    public void forRefreshing(){
        todaysDateForRefreshing = date.getText();
        chosenDateForRefreshing = chosenDate.getText();
        chosenDateForTxtFileForRefreshing = chosenDateFormatForTxtFile.getText();
    }

    public void addTaskToList() {
        if (!addTextField.getText().isEmpty() && taskList.size() < 10) {
            taskList.add(addTextField.getText());
            forRefreshing();
            addTextField.clear();
        } else {
            if(addTextField.getText().isEmpty()) {
                CommonMethods.showAlert(Alert.AlertType.ERROR, "Coś poszło nie tak", "Trzeba coś wpisać :)");
                forRefreshing();
            }
            else {
             CommonMethods.showAlert(Alert.AlertType.ERROR, "Niestety...", "Więcej się nie zmieści :)");
            }
        }
    }

    public void refreshTaskList(ActionEvent event) throws IOException {
        LinkedList<String> taskListForRefreshing = this.taskList;
        String y = chosenDateForRefreshing;
        String z = chosenDateForTxtFileForRefreshing;
        String x = todaysDateForRefreshing;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("s04_taskList.fxml"));
        root = loader.load();
        S04_taskListController refresh = loader.getController();
        refresh.showList2(taskListForRefreshing, x, y,z);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addListToGridPane(){ //wrapping text????
        int rowIndex = 0;
        for (String s : taskList) {
            Label task = new Label(rowIndex + 1 + ". " + s);
            task.setStyle("-fx-text-fill: white;");
            Button deleteTaskButton = new Button("Usuń");
            deleteTaskButton.setStyle("-fx-font-size: 9;");
            deleteTaskButton.setOnAction(e -> {
                taskList.remove(s);
                task.setVisible(false);
                deleteTaskButton.setVisible(false);
            });
            gridPaneForTaskList.getChildren().addAll(task, deleteTaskButton);
            gridPaneForTaskList.setConstraints(task, 0, rowIndex);
            gridPaneForTaskList.setConstraints(deleteTaskButton, 1, rowIndex);
            rowIndex++;
        }
    }

    //for refreshTaskList() method
    public void showList2(LinkedList<String> list, String todaysDateForRefreshing, String chosenDateForRefreshing,  String chosenDateForTxtFileForRefreshing) {
        this.taskList.addAll(list);
        this.todaysDateForRefreshing = todaysDateForRefreshing;
        this.chosenDateForRefreshing = chosenDateForRefreshing;
        this.chosenDateForTxtFileForRefreshing = chosenDateForTxtFileForRefreshing;
        addListToGridPane();
        date.setText(todaysDateForRefreshing);
        chosenDate.setText(chosenDateForRefreshing);
        chosenDateFormatForTxtFile.setText(this.chosenDateForTxtFileForRefreshing);
        chosenDateFormatForTxtFile.setVisible(false);
        datePicker.setDisable(true);
        if (taskList.isEmpty())
        saveTheListButton3.setDisable(true);
        else saveTheListButton3.setDisable(false);
        setDatesChoiceBox();

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
                gridPaneForTaskList.setVisible(false);
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
