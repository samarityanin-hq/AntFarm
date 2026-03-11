package AntPackage;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AntWorker extends Ant{
    public AntWorker(double x, double y) {
        super(x, y);
        img = new Image("/imgs/antWorker.png");
        imgView = new ImageView(img);
        imgView.setFitWidth(150);
        imgView.setFitHeight(100);
        imgView.setX(x);
        imgView.setY(y);
        imgView.setPreserveRatio(true);

        speedX = 4;
        speedY = 2.5;
    }

}
