import javafx.scene.image.Image;

public class Danie {
    private String dishName = "-";
    private Image dishPicture = new Image("images/torba-z-zakupami.jpg");

    Danie(String dishName, Image dishPicture){
        this.dishName = dishName;
        this.dishPicture = dishPicture;
    }

    public String getDishName() {
        return dishName;
    }

    public Image getDishPicture() {
        return dishPicture;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setDishPicture(Image dishPicture) {
        this.dishPicture = dishPicture;
    }
}
