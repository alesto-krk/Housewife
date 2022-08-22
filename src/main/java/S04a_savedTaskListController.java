import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class S04a_savedTaskListController {
    private List<String> fileList = new LinkedList<>();
    private LinkedList<String> taskSelected = new LinkedList<>();
    private LinkedList<String> taskNOTselected = new LinkedList<>();
    private int rowIndex = 0;
    private int selectedIndex = 0;
    private String pathname;
    LocalDate todaysDate = LocalDate.now();
    @FXML
    private GridPane gridPaneForSAVEDTaskList;
    @FXML
    protected Label hiddenLabelForTxtFile;
    @FXML
    protected Label hiddenLabelForTitleDate;

    // for class S04_taskListController: showTaskList()
    public void addToCheckbox(ChoiceBox<String> choicebox) {
        LinkedList<File> listOfCurrentSavedFiles = doListOfCurrentSavedFiles();
        String choiceboxDate = choicebox.getValue();
        if (!choiceboxDate.equals("dziś")) {
            for (int i = 0; i < listOfCurrentSavedFiles.size(); i++) {
                String fileItem = listOfCurrentSavedFiles.get(i).toString();
                pathname = fileItem;
                String fileItemShortened = fileItem.substring(CommonMethods.getUserHomeLength() + 42 + 11, fileItem.length() - 4);
                if (choiceboxDate.equals(fileItemShortened))
                    readTheFile(fileItem, choicebox);
            }
        }
        else {
            String fileItem = listOfCurrentSavedFiles.get(0).toString();
            readTheFile(fileItem, choicebox);
        }
    }

    // for addToCheckbox()
    public LinkedList<File> doListOfCurrentSavedFiles(){
        LinkedList<File> listOfCURRENTSavedLists = new LinkedList<>();
        LinkedList<File> listOfAllSavedTaskLists = CommonMethods.dolistOfSavedTaskLists();
        for (int i = 0; i < listOfAllSavedTaskLists.size(); i++) {
            String fileItem = listOfAllSavedTaskLists.get(i).toString();
            LocalDate txtDate = LocalDate.parse(fileItem.substring(CommonMethods.getUserHomeLength() + 42, CommonMethods.getUserHomeLength() + 42 + 10));
            if (!txtDate.isBefore(todaysDate)) {
                listOfCURRENTSavedLists.add(listOfAllSavedTaskLists.get(i));
            }
        }
        return listOfCURRENTSavedLists;
    }

    // for addToCheckbox()
    public void readTheFile(String fileItem, ChoiceBox<String> choicebox){
        formatDate(choicebox);
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileItem))) {
            fileList = br.lines().collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        addSavedListToGridPane();
    }

    // for readTheFile()
    public void formatDate(ChoiceBox<String> choicebox){
        String dfl = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(todaysDate);
        if (choicebox.getValue().equals("dziś")) {
            hiddenLabelForTxtFile.setText("Listy-zadan/lista-zadan-na-" + todaysDate + "-" + dfl + ".txt");
            hiddenLabelForTxtFile.setVisible(false);
            hiddenLabelForTitleDate.setText("dziś");
            hiddenLabelForTitleDate.setVisible(false);
        }
        else{
            hiddenLabelForTxtFile.setText(pathname);
            hiddenLabelForTxtFile.setVisible(false);
            hiddenLabelForTitleDate.setText(pathname.substring(CommonMethods.getUserHomeLength() + 42 + 11, pathname.length()-4));
            hiddenLabelForTitleDate.setVisible(false);
        }
    }

    // for readTheFile()
    public void addSavedListToGridPane(){
        for (String l : fileList) {
            CheckBox checkb = new CheckBox(l);
            checkb.setStyle("-fx-text-fill: white;");
            Label task = new Label("(done)");
            task.setStyle("-fx-text-fill: white;");
            task.setVisible(false);
            checkb.setOnAction(e -> {
                if (checkb.isSelected()) {
                    checkb.setOpacity(0.3);
                    task.setText("done!");
                    task.setStyle("-fx-text-fill: white; -fx-effect: dropshadow(gaussian, lightpink, 10, 0.07, 2, 2);");
                    task.setVisible(true);
                    selectedIndex++;
                    taskSelected.add(l);
                    taskNOTselected.remove(l);
                } else {
                    checkb.setOpacity(1);
                    task.setVisible(false);
                    selectedIndex--;
                    taskNOTselected.add(l);
                }
            });
            gridPaneForSAVEDTaskList.getChildren().addAll(checkb, task);
            gridPaneForSAVEDTaskList.setConstraints(checkb, 1, rowIndex);
            gridPaneForSAVEDTaskList.setConstraints(task, 0, rowIndex);
            rowIndex++;
        }
    }

    // "Zapisz na później" button
    public void saveForNow(ActionEvent event) throws IOException {
        fileList.removeAll(taskSelected);
        fileList.addAll(taskNOTselected);
        PrintWriter zapis = new PrintWriter(hiddenLabelForTxtFile.getText());
            for (String e : fileList) {
                zapis.println(e);
            }
            zapis.close();
            CommonMethods.showAlert(Alert.AlertType.CONFIRMATION, "Lista", "Odhaczone zadania będą trwale usunięte z listy. Czy na pewno je zrobiłeś? :) \nJeśli tak, kliknij OK i wyjdz z listy");
    }

}