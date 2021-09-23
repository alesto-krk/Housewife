import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class CoNaObiadController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public Image image1 = new Image("images/thinking-face.png");     //nauczyc sie w koncu mySQL zeby obrazki trzymac w bazie danych
    public Image image2 = new Image("images/refresh-icon.png");
    public Image image3 = new Image("images/kura-image.png");
    public Image losowanieZupy[] = {image1, image2, image3};

    //mozna sprobowac zeby nie bylo dania controllera!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @FXML
    ImageView myImage;
    @FXML
    AnchorPane scenaDanie;

    public void losujZupeButton(ActionEvent event) throws IOException {
        losowanko();
        refreshscene(event);
    }

    public int losowanko() {
        Random r = new Random();
        int zupa = r.nextInt(losowanieZupy.length);
        System.out.println(losowanieZupy[zupa]);
        return zupa;
    }

    public void refreshscene(ActionEvent event) throws IOException {
        //Image username = losowanieZupy[zupa];
        FXMLLoader loader = new FXMLLoader(getClass().getResource("danie.fxml"));
        root = loader.load();
        DanieController wylosowanaZupa = loader.getController();
        wylosowanaZupa.showGeneratedImage(losowanieZupy[losowanko()]);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
