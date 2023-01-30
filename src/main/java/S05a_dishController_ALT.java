import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.Map;

public class S05a_dishController_ALT {
    @FXML
    ImageView myImage;

    public void showGeneratedImage(int dishNumber, Map<Integer, List<String>> dishDatabase){
        try {
            List<String> list = dishDatabase.get(dishNumber);
            String dishRecipe = list.get(1); //receive dish path
            Image img = new Image(getClass().getResourceAsStream(dishRecipe));
            myImage.setImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showChosenImage(ChoiceBox<String> choicebox, Map<Integer, List<String>> dishDatabase){
        String chosenDish = choicebox.getValue();
        try {
           for (int i=0; i<dishDatabase.size(); i++){
               if (chosenDish.equals(dishDatabase.get(i).get(0))){
                   String dishRecipe = dishDatabase.get(i).get(1); //receive dish path
                   Image img = new Image(getClass().getResourceAsStream(dishRecipe));
                   myImage.setImage(img);
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
