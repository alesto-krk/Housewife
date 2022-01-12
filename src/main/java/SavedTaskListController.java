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
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SavedTaskListController {
    List<String> fileList = new LinkedList<>();
    LinkedList<String> selected = new LinkedList<>();
    Set<String> notselected = new HashSet<>();
    int i;
    int j=0;
    String pathname;
    LocalDate todaysDate = LocalDate.now();

    @FXML
    GridPane siatka3;
    @FXML
    Button saveForNowButton;

    public void addTocheckbox(ChoiceBox<String> choicebox) {
       int rowIndex = 0;
        LinkedList<File> listOfSavedTaskLists = CommonMethods.dolistOfSavedTaskLists();
        String choiceboxDate = choicebox.getValue();

        for (i = 0; i < listOfSavedTaskLists.size(); i++) {
            String fileItem = listOfSavedTaskLists.get(i).toString();
            pathname = fileItem;
            //System.out.println(pathname);
            //System.out.println(todaysDate);
            String fileItemShortened = fileItem.substring(75, fileItem.length() - 4); //to co w choiceboxie
            String fileItemShortenedToday = fileItem.substring(64, 74);
            System.out.println(fileItemShortened + "  **  " + fileItemShortenedToday + "*****" + choiceboxDate + "  -  " + todaysDate.toString());

            if (choiceboxDate.equals(fileItemShortened) || fileItemShortenedToday.equals(todaysDate.toString())) { //źle! pokazuje zadania z obydwu dni, wybranego i todays daty!!!!!!
                try (BufferedReader br = Files.newBufferedReader(Paths.get(fileItem))) {
                    fileList = br.lines().collect(Collectors.toList());
                    System.out.println(fileList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (String l : fileList) {
                    CheckBox checkb = new CheckBox(l);
                    Label label = new Label("-");
                    label.setVisible(false);
                    checkb.setOnAction(e -> {
                                if (checkb.isSelected()) {
                                    checkb.setOpacity(0.3);
                                    label.setText("done");
                                    label.setVisible(true);
                                    j++;
                                    selected.add(l);

                                } else {
                                    checkb.setOpacity(1);
                                   // label.setText("--");
                                    label.setVisible(false);
                                    j--;
                                    selected.remove(l);
                                }
                            });

                    siatka3.getChildren().addAll(checkb, label);
                    siatka3.setConstraints(checkb, 1, rowIndex);
                    siatka3.setConstraints(label, 0, rowIndex);
                    rowIndex++;
                }
            }
        }
       /* saveForNowButton.setOnAction(e-> {

        });*/

    }

    public void saveForNow(ActionEvent event) throws IOException {
        System.out.println(j + "------");
        System.out.println(selected);
        System.out.println(fileList);
        for (int k = 0; k < selected.size(); k++) {
            for (int m = 0; m < fileList.size(); m++){
                if(fileList.get(m).equals(selected.get(k)))
                    fileList.remove(m);
            }
        }

        System.out.println(fileList); //ta liste chcemy przeniesc
        System.out.println("---**----" + pathname);         //nie dziala pathname!!!!!!!!
        PrintWriter zapis = new PrintWriter(pathname);

            for (String e : fileList) {
                zapis.println(e);
            }
            zapis.close();
            CommonMethods.showAlert(Alert.AlertType.CONFIRMATION, "Lista", "Odhaczone zadania będą trwale usunięte z listy. Czy na pewno je zrobiłeś? :)");


    }
}
    //jeszcze wroc do glownej sceny i zakoncz