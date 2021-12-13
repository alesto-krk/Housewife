import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SavedTaskListController {
    List<String> fileList = new LinkedList<>();
    LinkedList<String> postponedTasks = new LinkedList<>();

    @FXML
    GridPane siatka3;

    public void addTocheckbox(ChoiceBox<String> choicebox){
        int rowIndex = 0;
        LinkedList<File> listOfSavedTaskLists = CommonMethods.dolistOfSavedTaskLists();
        String choiceboxDate = choicebox.getValue();

        for (int i = 0; i < listOfSavedTaskLists.size(); i++) {
            String fileItem = listOfSavedTaskLists.get(i).toString();
            String fileItemShortened = fileItem.substring(75, fileItem.length() - 4); //to co w choiceboxie

            if (choiceboxDate.equals(fileItemShortened)) {
                try (BufferedReader br = Files.newBufferedReader(Paths.get(fileItem))) {
                    fileList = br.lines().collect(Collectors.toList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (String l : fileList) {
                    CheckBox checkb = new CheckBox(l);
                    Button postpone = new Button("Kiedy indziej");
                    postpone.setDisable(false);
                    postpone.setOnAction(e -> {
                        postponedTasks.add(l);
                        checkb.setTextFill(Color.RED);
                    });
                    checkb.setOnAction(e -> {
                                if (checkb.isSelected()) {
                                    checkb.setOpacity(0.3);
                                    postpone.setDisable(true);
                                }
                                else {
                                    checkb.setOpacity(1);
                                    postpone.setDisable(false);
                                }
                            }
                    );
                    siatka3.getChildren().addAll(checkb, postpone);
                    siatka3.setConstraints(checkb, 0, rowIndex);
                    siatka3.setConstraints(postpone, 1, rowIndex);
                    rowIndex++;
                }
            }
        }
    }

    public void showPostponed(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("savedTaskList.fxml"));
            Parent root2 = (Parent) loader.load();
            Stage stage2 = new Stage();
            for (String e : postponedTasks){
                Label label = new Label(e);
                siatka3.getChildren().add(label); //zrobic nowy porzadny refresh sceny!!!!!!!!!!!!!!!!
            }

            //stage2.setTitle("Twoja lista zadań");
            //Image icon = new Image(getClass().getResourceAsStream("images/zadanie-na-dzis.jpg"));
            //stage2.getIcons().add(icon);
            stage2.setScene(new Scene(root2, 450, 450));
            stage2.show();
        }
        catch (Exception e){
            e.printStackTrace();
            //System.out.println("error!");
        }
    }
}
