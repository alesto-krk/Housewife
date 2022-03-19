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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SavedTaskListController {
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
    Label labelDate;

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

    public void readTheFile(String fileItem, ChoiceBox<String> choicebox){
        System.out.println(pathname);
        String dfl = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(todaysDate).toString();
        if (choicebox.getValue().equals("dziś"))
            labelDate.setText("C:\\Users\\Ola\\IdeaProjects\\KuraDomowa\\Listy-zadan\\lista-zadan-na-"+ todaysDate + "-" + dfl + ".txt");
        else
        labelDate.setText(pathname);
        labelDate.setVisible(false);
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileItem))) {
            fileList = br.lines().collect(Collectors.toList());
            System.out.println(fileList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String l : fileList) {
            CheckBox checkb = new CheckBox(l);
            //checkb.setPrefHeight(60.5);
            Label label = new Label("(done)");
            label.setVisible(false);
            checkb.setOnAction(e -> {
                if (checkb.isSelected()) {
                    checkb.setOpacity(0.3);
                    label.setText("done");
                    label.setVisible(true);
                    j++;
                    selected.add(l);
                    notselected.remove(l);

                } else {
                    checkb.setOpacity(1);
                    // label.setText("--");
                    label.setVisible(false);
                    j--;
                    //selected.remove(l);
                    notselected.add(l);
                }
            });

            siatka3.getChildren().addAll(checkb, label);
            siatka3.setConstraints(checkb, 1, rowIndex);
            siatka3.setConstraints(label, 0, rowIndex);
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
        System.out.println("0o0o0o0o0" + labelDate.getText());

        PrintWriter zapis = new PrintWriter(labelDate.getText());
            for (String e : fileList) {
                zapis.println(e);
            }
            zapis.close();
            CommonMethods.showAlert(Alert.AlertType.CONFIRMATION, "Lista", "Odhaczone zadania będą trwale usunięte z listy. Czy na pewno je zrobiłeś? :)");

    }

}
