import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class S01_titleController {

    @FXML
    AnchorPane titleContainer;

    public void goToMenuButton (ActionEvent event) throws IOException {
        CommonMethods menu = new CommonMethods();
        menu.goToMenu(event);
    }
}
