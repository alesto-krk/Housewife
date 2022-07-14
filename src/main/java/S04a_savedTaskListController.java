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
    List<String> fileList = new LinkedList<>();
    LinkedList<String> selected = new LinkedList<>();
    LinkedList<String> notselected = new LinkedList<>();
    int rowIndex = 0;
    int i;
    int j=0;
    String pathname;
    LocalDate todaysDate = LocalDate.now();
    @FXML
    GridPane siatka3;
    @FXML
    Button saveForNowButton;
    @FXML
    Label hiddenLabelForTxtFile;
    @FXML
    Label hiddenLabelForTitleDate;

    public LinkedList<File> doListOfCurrentSavedFiles(){
        LinkedList<File> listOfCurrentSavedLists = new LinkedList<>();
        LinkedList<File> listOfAllSavedTaskLists = CommonMethods.dolistOfSavedTaskLists();
        for (i = 0; i < listOfAllSavedTaskLists.size(); i++) {
            String fileItem = listOfAllSavedTaskLists.get(i).toString();
            LocalDate txtDate = LocalDate.parse(fileItem.substring(64, 74));
            if (!txtDate.isBefore(todaysDate)) {
                listOfCurrentSavedLists.add(listOfAllSavedTaskLists.get(i));
            }
        }
        return listOfCurrentSavedLists;
    }

    public void readTheFile(String fileItem, ChoiceBox<String> choicebox){  //sciezka!!!!!!!!
        System.out.println(pathname);
        String dfl = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(todaysDate).toString();
        if (choicebox.getValue().equals("dziś"))
            hiddenLabelForTxtFile.setText("C:\\Users\\Ola\\IdeaProjects\\KuraDomowa\\Listy-zadan\\lista-zadan-na-"+ todaysDate + "-" + dfl + ".txt");
        else{
        hiddenLabelForTxtFile.setText(pathname);
        hiddenLabelForTxtFile.setVisible(false);
        hiddenLabelForTitleDate.setText(pathname.substring(75,pathname.length()-4));
        hiddenLabelForTitleDate.setVisible(false);
        }
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileItem))) {
            fileList = br.lines().collect(Collectors.toList());
            System.out.println(fileList);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    j++;
                    selected.add(l);
                    notselected.remove(l);

                } else {
                    checkb.setOpacity(1);
                    // label.setText("--");
                    task.setVisible(false);
                    j--;
                    //selected.remove(l);
                    notselected.add(l);
                }
            });

            siatka3.getChildren().addAll(checkb, task);
            siatka3.setConstraints(checkb, 1, rowIndex);
            siatka3.setConstraints(task, 0, rowIndex);
            rowIndex++;
        }
    }


    public void addTocheckbox(ChoiceBox<String> choicebox) {
        LinkedList<File> listOfCurrentSavedFiles = doListOfCurrentSavedFiles();
        String choiceboxDate = choicebox.getValue();
        if (!choiceboxDate.equals("dziś")) {
            for (i = 0; i < listOfCurrentSavedFiles.size(); i++) {
             String fileItem = listOfCurrentSavedFiles.get(i).toString();
             pathname = fileItem;
              String fileItemShortened = fileItem.substring(75, fileItem.length() - 4); //to co w choiceboxie
            // System.out.println(fileItemShortened + "  **  " + fileItemShortenedToday + "*****" + choiceboxDate + "  -  " + todaysDate.toString());
             if (choiceboxDate.equals(fileItemShortened)) {
               readTheFile(fileItem, choicebox);
             }
            }
        } else {
       String fileItem = listOfCurrentSavedFiles.get(0).toString();
       readTheFile(fileItem, choicebox);
        }
    }

    public void saveForNow(ActionEvent event) throws IOException {
        System.out.println(j + "(ilosc usunietych elementow)");
        System.out.println("filelist " + fileList);
        System.out.println(selected + "___" + notselected);
        fileList.removeAll(selected);
        fileList.addAll(notselected);
        /*selected.addAll(notselected);
        for (int k = 0; k < selected.size(); k++) {
            for (int m = 0; m < fileList.size(); m++){
                if(fileList.get(m).equals(selected.get(k)))
                    fileList.remove(m);
            }
        }*/
        System.out.println("ta liste chcemy przeniesc  " + fileList); //ta liste chcemy przeniesc
        //System.out.println("---**----" + pathname);         //nie dziala pathname!!!!!!!!
        System.out.println("0o0o0o0o0" + hiddenLabelForTxtFile.getText());

        PrintWriter zapis = new PrintWriter(hiddenLabelForTxtFile.getText());
            for (String e : fileList) {
                zapis.println(e);
            }
            zapis.close();
            CommonMethods.showAlert(Alert.AlertType.CONFIRMATION, "Lista", "Odhaczone zadania będą trwale usunięte z listy. Czy na pewno je zrobiłeś? :)");

    }

}
