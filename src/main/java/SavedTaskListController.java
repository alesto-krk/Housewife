import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SavedTaskListController {
    List<String> listaZPliku = new LinkedList<>();

    @FXML
    GridPane siatka3;

    public void checkboxes(ChoiceBox<String> choicebox){
        int rowIndex = 0;
        String xxx = choicebox.getValue();
        try (BufferedReader br = Files.newBufferedReader(Paths.get("Listy-zadan/" + xxx))) {
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
            siatka3.getChildren().add(checkb);
            siatka3.setConstraints(checkb, 0, rowIndex);
            rowIndex++;
        }
    }
}
