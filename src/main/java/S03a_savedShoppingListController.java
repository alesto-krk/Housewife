import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class S03a_savedShoppingListController {
    private List<String> savedList = new LinkedList<>();
    @FXML
    private GridPane gridPaneForSavedList;

    public void checkboxes() throws IOException{
        readFromFile();
        addToCheckBox();
    }

    public void readFromFile() throws IOException {
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
                            }
                        else {
                            checkb.setOpacity(1);
                            }}
            );
            gridPaneForSavedList.getChildren().add(checkb);
            gridPaneForSavedList.setConstraints(checkb, 0, rowIndex);
            rowIndex++;
        }
    }

}
