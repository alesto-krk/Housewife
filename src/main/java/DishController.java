import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class DishController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    ImageView myImage;

    public void showGeneratedImage(Danie image){
        myImage.setImage(image.getDishPicture());
    }

    public void showChosenImage(ChoiceBox<String> choicebox, Danie[] danie){
        String chosenDish = choicebox.getValue();
        for(int i=0; i<danie.length; i++) {                     //O(n)
            if (chosenDish.equals(danie[i].getDishName()))
                myImage.setImage(danie[i].getDishPicture());
        }
    }

    public void backButton (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("coNaObiad.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
