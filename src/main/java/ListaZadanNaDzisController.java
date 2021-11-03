import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ListaZadanNaDzisController {

    @FXML
    Label date = new Label();

    LocalDate todaysDate = LocalDate.now();

    public void setTodaysDate(){
        //date.setText(todaysDate.toString());
       String df =  DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(todaysDate).toString();
       date.setText(df);

    }


}
