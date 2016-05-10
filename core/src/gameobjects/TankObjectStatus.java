package gameobjects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.gombi.aqua.AquaGame;

import java.text.SimpleDateFormat;

public class TankObjectStatus extends GameObject{
    public static final float startY = AquaGame.HEIGHT;
    public static final float endY = AquaGame.HEIGHT/6;

    private Texture bg;
    private Rectangle rect;

    private Fish fish;
    private Decoration decor;

    private String statusString = "";

    private boolean moving = false;
    private boolean moveDown = false;
    private boolean moveUp = false;

    public TankObjectStatus() {
        super(AquaGame.WIDTH/10, startY);
        bg = new Texture("backgrounds/menubg.png");
        rect = new Rectangle(getPos().x, AquaGame.HEIGHT - getPos().y - (AquaGame.HEIGHT / 6) * 4, (AquaGame.WIDTH / 10) * 8, (AquaGame.HEIGHT / 6) * 4);
    }

    public void down(){
        moving = true;
    }

    public Texture getImg(){
        return bg;
    }

    public void setFish(Fish fish){
        this.fish = fish;
        statusString = "fish";
    }

    public void setDecor(Decoration dec){
        this.decor = dec;
        statusString = "decoration";
    }

    public float getObjX(){
        if(statusString.equals("fish"))
            return getPos().x + (fish.getImg().getWidth()-(fish.getImg().getWidth()/fish.getScale()))/2;
        else
            return getPos().x;
    }
    public float getObjY(){
        if(statusString.equals("fish"))
            return (AquaGame.HEIGHT - (AquaGame.HEIGHT/6)) - fish.getImg().getHeight() + ((fish.getImg().getHeight() - fish.getImg().getHeight()/fish.getScale())/2);
        else
            return (AquaGame.HEIGHT - (AquaGame.HEIGHT/6)) - decor.getImg().getHeight();
    }

    @Override
    public void swim(float speedX, float speedY) {
        super.swim(speedX, speedY);
        if(getPos().y <= endY){
            moving = false;
        }
        else if(getPos().y >= startY){
            moving = false;
        }
    }

    public boolean isClicked(float x, float y){
        return rect.contains(x, y);
    }

    public boolean isMoving(){
        return moving;
    }

    public void setMoving(){
        moving = true;
    }

    public void update(){
        if(moving){
            if(getPos().y <= endY){
                moveUp = true;
            }
            else if(getPos().y >= startY){
                moveDown = true;
            }
            if(moveUp) {
                swim(0, 50);
                return;
            }
            swim(0, -50);
        }
        else{
            moveUp = false;
            moveDown = false;
        }
    }

    public String fishInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("Name : " + fish.getName().toUpperCase() + "\n");
        sb.append("Date of purchase : " + new SimpleDateFormat("dd:MM.yyyy").format(fish.getTimeOfCreation()) + "\n");
        sb.append("Age : " + fish.getAge() + " weeks\n");
        sb.append(fish.getHungerString() + "\n");
        return sb.toString();
    }

    public void dispose(){
        bg.dispose();
    }

}
