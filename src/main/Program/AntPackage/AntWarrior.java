package AntPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AntWarrior extends Ant{
    public AntWarrior(double x, double y) {
        super(x, y);
        img = new Image("/imgs/antWarrior.png");
        imgView = new ImageView(img);
        imgView.setFitWidth(300);
        imgView.setFitHeight(200);
        imgView.setX(x);
        imgView.setY(y);
        imgView.setPreserveRatio(true);

        speedX = 2;
        speedY = 1;
    }

}
