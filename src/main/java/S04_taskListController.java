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
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.LinkedList;

public class S04_taskListController {

    private LinkedList<String> taskList = new LinkedList<>();
    private Stage stage;
    private Scene scene;
    private Parent root;
    private  CommonMethods commonMethods = new CommonMethods();
    LocalDate todaysDate = LocalDate.now();
    private String todaysDateForRefreshing;
    private String chosenDateForRefreshing;
    private String chosenDateForTxtFileForRefreshing;
    @FXML
    private Label date;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label chosenDate;
    @FXML
    private Label chosenDateFormatForTxtFile;
    @FXML
    private TextField addTextField;
    @FXML
    private GridPane gridPaneForTaskList;
    @FXML
    private ChoiceBox<String> datesChoiceBox;
    @FXML
    private Button addTaskButton, showTaskListButton, menuButton, saveTaskListButton, setAnotherDateOrTaskButton;

    public TextField getAddTextField() {
        return addTextField;
    }

    public Button getSaveTaskListButton() {
        return saveTaskListButton;
    }

    //for class S02_menuController: menuTaskList()
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

    // "Dodaj" button
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
        String todaysDateForRefreshing = this.todaysDateForRefreshing;
        String chosenDateForRefreshing = this.chosenDateForRefreshing;
        String chosenDateForTxtFileForRefreshing = this.chosenDateForTxtFileForRefreshing;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("s04_taskList.fxml"));
        root = loader.load();
        S04_taskListController refresh = loader.getController();
        CommonMethods.textFieldLimit(40, refresh.getAddTextField());
        refresh.showTaskListForRefresh(taskListForRefreshing, todaysDateForRefreshing, chosenDateForRefreshing,chosenDateForTxtFileForRefreshing);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //for refreshTaskList()
    public void showTaskListForRefresh(LinkedList<String> list, String todaysDateForRefreshing1, String chosenDateForRefreshing1, String chosenDateForTxtFileForRefreshing1) {
        this.taskList.addAll(list);
        this.todaysDateForRefreshing = todaysDateForRefreshing1;
        this.chosenDateForRefreshing = chosenDateForRefreshing1;
        this.chosenDateForTxtFileForRefreshing = chosenDateForTxtFileForRefreshing1;
        addListToGridPane();
        date.setText(todaysDateForRefreshing1);
        chosenDate.setText(chosenDateForRefreshing1);
        chosenDateFormatForTxtFile.setText(chosenDateForTxtFileForRefreshing1);
        chosenDateFormatForTxtFile.setVisible(false);
        datePicker.setDisable(true);
        if (taskList.isEmpty())
            saveTaskListButton.setDisable(true);
        else saveTaskListButton.setDisable(false);
        setDatesChoiceBox();
    }

    //for showTaskListForRefresh()
    public void addListToGridPane(){
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

    // "Ustaw nową datę/nowe zadania" button
    public void setAnotherDateOrTask(ActionEvent event) throws IOException{
        datePicker.setDisable(false);
    }

    // "Zapisz listę" button
    public void saveTaskList(ActionEvent event) throws IOException {
        datesChoiceBox.getItems().clear();
        String pathname = "Listy-zadan/lista-zadan-na-" + chosenDateFormatForTxtFile.getText() + "-" + chosenDate.getText() + ".txt";
        if (pathname.equals("Listy-zadan/lista-zadan-na--<nie wybrałeś/aś daty>.txt") || pathname.equals("Listy-zadan/lista-zadan-na--.txt") || pathname.equals("") || chosenDate.equals(null) || chosenDate.equals("<nie wybrałeś/aś daty>"))
            CommonMethods.showAlert(Alert.AlertType.WARNING, "Nieustawiona data", "Kliknij -Ustaw nową datę- ");
        else
            commonMethods.checkIfFileExists(pathname);
        try {
            if (!taskList.isEmpty()) {
                FileWriter fw = new FileWriter(pathname, true);
                BufferedWriter saveTaskListToFile = new BufferedWriter(fw);
                for (String s : taskList) {
                    saveTaskListToFile.write(s);
                    saveTaskListToFile.newLine();
                }
                saveTaskListToFile.close();
                taskList.clear();
                CommonMethods.showAlert(Alert.AlertType.INFORMATION, "Lista", "Zapisano listę zadań");
            } else
                CommonMethods.showAlert(Alert.AlertType.WARNING, "Lista", "Lista jest pusta");
        } catch (IOException ioe) {
            System.out.println("04_saveTaskListError!");
        }
        setDatesChoiceBox();
    }

    //for saveTaskList() & class S02_menuController: menuTaskList()
    public void setDatesChoiceBox() {
        String fileItemForChoiceBox;
        LinkedList<File> listOfSavedTaskLists = CommonMethods.dolistOfSavedTaskLists();
        if (!listOfSavedTaskLists.isEmpty()) {
            Collections.sort(listOfSavedTaskLists);
            for (int i = 0; i < listOfSavedTaskLists.size(); i++) {
                String fileItem = listOfSavedTaskLists.get(i).toString();
                LocalDate txtDate = LocalDate.parse(fileItem.substring(27, 37));
                if (!txtDate.isBefore(todaysDate)) {
                    String fileItemShortened = fileItem.substring(38, fileItem.length() - 4);
                    if (fileItemShortened.equals(date.getText())) {
                        fileItemForChoiceBox = "dziś";
                    } else fileItemForChoiceBox = fileItemShortened;
                    datesChoiceBox.getItems().add(fileItemForChoiceBox);
                    datesChoiceBox.getSelectionModel().select(0);
                }
            }
        } else System.out.println("04_setDatesChoiceBoxError!");
    }

    // "Pokaż" button
    public void showTaskList(ActionEvent event) throws IOException {
        if(!(datesChoiceBox.getValue()==null)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("s04a_savedTaskList.fxml"));
                Parent rootForTaskList = (Parent) loader.load();
                Stage stageForTaskList = new Stage();
                S04a_savedTaskListController savedTaskListController = loader.getController();
                savedTaskListController.addToCheckbox(datesChoiceBox);
                stageForTaskList.setTitle("Twoja lista zadań na: " + savedTaskListController.hiddenLabelForTitleDate.getText());
                Image icon = new Image(getClass().getResourceAsStream("images/task-for-today.jpg"));
                stageForTaskList.getIcons().add(icon);
                stageForTaskList.setScene(new Scene(rootForTaskList, 485, 455));
                stageForTaskList.setResizable(false);
                stageForTaskList.show();
                CommonMethods.disableButtons(true, addTaskButton,showTaskListButton,saveTaskListButton,menuButton,setAnotherDateOrTaskButton);
                stageForTaskList.setOnCloseRequest(e ->
                {
                    CommonMethods.disableButtons(false, addTaskButton,showTaskListButton,saveTaskListButton,menuButton,setAnotherDateOrTaskButton);
                });
            } catch (Exception e) {
                System.out.println("04_showTheListError!");
                e.printStackTrace();
            }
        } else CommonMethods.showAlert(Alert.AlertType.WARNING, "Lista", "Wybierz datę z listy lub stwórz nową listę");
    }

    // "<Menu" button
    public void goToMenuButton(ActionEvent event) throws IOException {
        commonMethods.goToMenu(event);
    }

    // "Zakończ" button
    public void exitButton(){
        Platform.exit();
    }
}