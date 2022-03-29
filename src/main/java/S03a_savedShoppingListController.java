import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class S03a_savedShoppingListController {
    private List<String> savedList = new LinkedList<>();
    private LinkedList<String> selected = new LinkedList<>();
    private LinkedList<String> notselected = new LinkedList<>();
    @FXML
    GridPane gridPaneForSavedList;

    public void checkboxes() throws IOException{
        readFromFile();
        addToCheckBox();
    }

    public void readFromFile() throws IOException {
        /*try (BufferedReader br = Files.newBufferedReader(Paths.get("Lista-zakupow/my-shopping-list.txt"))) {
            savedList = br.lines().collect(Collectors.toList());
        }*/
        try (BufferedReader br = new BufferedReader(new FileReader("Lista-zakupow/my-shopping-list.txt"))){
            String line;
            while ((line = br.readLine()) != null)
                savedList.add(line);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addToCheckBox(){
        int rowIndex = 0;
        for (String s : savedList) {
            CheckBox checkb = new CheckBox(s);
            checkb.setStyle("-fx-text-fill: white; -fx-font-family: \"Bookman Old Style\", Arial;");
            checkb.setOnAction(e -> {
                        if(checkb.isSelected()){
                            checkb.setOpacity(0.4);
                            selected.add(s);
                            notselected.remove(s);}
                        else {
                            checkb.setOpacity(1);
                            notselected.add(s);}
                    }
            );
            gridPaneForSavedList.getChildren().add(checkb);
            gridPaneForSavedList.setConstraints(checkb, 0, rowIndex);
            rowIndex++;
        }
    }

    public void saveForNowSL(ActionEvent event) throws IOException{
        savedList.removeAll(selected);
        savedList.addAll(notselected);
        try {
            PrintWriter zapis = new PrintWriter("Lista-zakupow/my-shopping-list.txt");
            for (String e : savedList) {
                zapis.println(e);
            }
            zapis.close();
            CommonMethods.showAlert(Alert.AlertType.CONFIRMATION, "Lista", "Odhaczone artykuły zostaną usunięte z listy. Czy na pewno je kupiłeś? :)");
        }
        catch (IOException ioe) {
            System.out.println("03a_saveForNowSLError!");
        }
    }

}
