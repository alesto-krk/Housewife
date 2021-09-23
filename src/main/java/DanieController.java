import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DanieController {

    @FXML
    ImageView myImage;

    public void showGeneratedImage(Image image){
        myImage.setImage(image);
    }
}
