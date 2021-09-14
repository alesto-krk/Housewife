import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.util.LinkedList;

public class SavedListController {

    public void checkboxes(LinkedList<String> lista){
        int rowIndex = 0;
        for (String l : lista) {
            CheckBox checkb = new CheckBox(l);


            /*Label listaLabela = new Label(rowIndex + 1 + ". " + l);
            Button usunElement = new Button("Usuń");
            usunElement.setOnAction(e -> {
                listaZakupow.remove(l);
                //listaLabela.setOpacity(0.3);
                listaLabela.setVisible(false);
            });
            siatka.getChildren().add(listaLabela);
            siatka.getChildren().add(usunElement);
            siatka.setConstraints(listaLabela, 0, rowIndex);
            siatka.setConstraints(usunElement, 1, rowIndex);*/
            rowIndex++;
        }
    }
}
