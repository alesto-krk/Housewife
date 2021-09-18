import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SavedListController {
    List<String> listaZPliku = new LinkedList<>();

    @FXML
    GridPane siatka2;

    public void checkboxes(){
        int rowIndex = 0;
        try (BufferedReader br = Files.newBufferedReader(Paths.get("moja-lista-zakupow.txt"))) {
            listaZPliku = br.lines().collect(Collectors.toList());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        for (String l : listaZPliku) {
            CheckBox checkb = new CheckBox(l);
            checkb.setOnAction(e -> {
                if(checkb.isSelected())
                    checkb.setTextFill(Color.GREEN);
                else
                    checkb.setTextFill(Color.BLACK);
                    }
            );
            siatka2.getChildren().add(checkb);
            siatka2.setConstraints(checkb, 0, rowIndex);
            rowIndex++;
        }
    }
}
