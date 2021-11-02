import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.Calendar;

public class ListaZadanNaDzisController {

    @FXML
    Label date = new Label();

    Calendar todaysDate = Calendar.getInstance();

    public void setTodaysDate(){
        date.setText(todaysDate.getTime().toString()); //akcja po kliknieciu z menu

    }


}
