import javafx.event.ActionEvent;
import java.io.IOException;

public class tytulController {

    public void goToMenuButton (ActionEvent event) throws IOException {
        CommonMethods menu = new CommonMethods();
        menu.goToMenu(event);
    }
}
