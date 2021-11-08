import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ListaZadanNaDzisController {       //jak wyskoczy lista zadan do wykonania opcja done i zostaw na potem + zapisz

    @FXML
    Label date;
    @FXML
    Label chosenDate;
    @FXML
    DatePicker datePicker;

    LocalDate todaysDate = LocalDate.now();

    public void setTodaysDate(){
        //date.setText(todaysDate.toString());
       String df =  DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(todaysDate).toString();
       date.setText(df);
    }

    public void chosenDatePicker(ActionEvent event){
        if(!datePicker.getValue().isBefore(todaysDate)) {
            LocalDate dateForLabel = datePicker.getValue();
            String dfl = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(dateForLabel).toString();
            chosenDate.setText(dfl);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Nie da się ustawić zadań na minione dni :)");
            alert.setContentText(null);
            alert.show();
        }
       //String a = String.valueOf(dateForLabel);
    }


}
