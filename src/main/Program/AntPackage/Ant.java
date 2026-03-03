package AntPackage;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public abstract class Ant implements IBehaviour{
    protected Image img;
    protected ImageView imgView;
    protected double x, y;
    protected double speedX, speedY;
    protected long timeOfBirth;
    protected int ID, lifeTime;;

    public Ant(double x, double y){
        this.x = x;
        this.y = y;

    }
    @Override
    public void move(double width, double height) {
        imgView.setX(imgView.getX() + speedX);
        imgView.setY(imgView.getY() + speedY);

        if (imgView.getX() <= 0){
            speedX = -speedX;
            imgView.setScaleX(1);
        }
        else if (imgView.getX() + imgView.getFitWidth() >= width){
            speedX = -speedX;
            imgView.setScaleX(-1);
        }

        if (imgView.getY() <= 0){
            speedY = -speedY;
        }
        else if (imgView.getY() + imgView.getFitHeight() >= height){
            speedY = -speedY;

        }
    }

    public void setLifeTime(int time){
        lifeTime = time;
    }
    public void setTimeOfBirth(long time){
        timeOfBirth = time;
    }
    public void setID(int ID){
        this.ID = ID;
    }

    public long getTimeOfBirth(){
        return timeOfBirth;
    }
    public int getID() {return ID;}
    public ImageView getImgView() {
        return imgView;
    }
    public long getLifeTime(){
        return lifeTime;
    }
}
