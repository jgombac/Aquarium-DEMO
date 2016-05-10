package gameobjects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.gombi.aqua.AquaGame;

import java.util.Random;

public class Decoration extends GameObject{
    private String name;
    private Texture img;
    private Rectangle rect;

    private float scale = 1f;

    private boolean set = true;

    private Random rnd = new Random();

    private Bubbles bubbles;

    public Decoration(float x, float y, String name, float scale){
        super(x, y);
        if(name.equals("fernplant")) {
            int fernNum = rnd.nextInt(3) + 1;
            img = new Texture("decorations/fern" + fernNum + ".png");
            this.name = "fernplant";
        }
        else{
            img = new Texture("decorations/blue.png");
            this.name = "bubbleboat";
            bubbles = new Bubbles(getPos().x + (img.getWidth()/5)/scale, getPos().y + img.getHeight()/scale - 20);

        }
        this.scale = scale;
        rect = new Rectangle(getPos().x, AquaGame.HEIGHT - getPos().y - img.getHeight()/scale, img.getWidth()/scale, img.getHeight()/scale);
    }

    public Decoration(float x, float y, String name){
        super(x, y);
        if(name.equals("fernplant")) {
            int fernNum = rnd.nextInt(3) + 1;
            img = new Texture("decorations/fern" + fernNum + ".png");
            this.name = "fernplant";
        }
        else{
            img = new Texture("decorations/blue.png");
            this.name = "bubbleboat";
            bubbles = new Bubbles(getPos().x + (img.getWidth()/5)/scale, getPos().y + img.getHeight()/scale - 20);

        }

        rect = new Rectangle(getPos().x, AquaGame.HEIGHT - getPos().y - img.getHeight()/scale, img.getWidth()/scale, img.getHeight()/scale);
    }

     @Override
     public void move(float x, float y){
         super.move(x, y);
         rect.setPosition(x, AquaGame.HEIGHT - getPos().y - img.getHeight()/scale);
     }


    public Texture getImg(){
        return img;
    }

    public String getName(){
        return name;
    }

    public Bubbles getBubbles(){
        if(bubbles != null)
            return bubbles;
        return null;
    }

    public boolean isSet(){
        return set;
    }

    public boolean isClicked(float x, float y){
        return rect.contains(x, y);
    }

    public void setDown(){
        move(getPos().x, rnd.nextFloat() * ((AquaGame.HEIGHT/10) - 5f) + 5f);
        if(name.equals("bubbleboat"))
            bubbles = new Bubbles(getPos().x + (img.getWidth()/5)/scale, getPos().y + img.getHeight()/scale - 20);
        set = true;
    }

    public void boostBubbles(){
        bubbles.boost();
    }

    public void decreaseBubbles(){
        bubbles.decrease();
    }

    public int getBubbleSize(){
        return bubbles.getNumBubbles();
    }

    public void bought(){
        move(AquaGame.WIDTH / 2, AquaGame.HEIGHT / 2);
        set = false;
    }

    public void largeImg(){
        if(scale >= 0.8f){
            scale -= 0.2f;
        }
        rect.set(getPos().x, AquaGame.HEIGHT - getPos().y - img.getHeight()/scale, img.getWidth()/scale, img.getHeight()/scale);
    }

    public void smallImg(){
        if(scale <= 2f){
            scale += 0.2f;
        }
        rect.set(getPos().x, AquaGame.HEIGHT - getPos().y - img.getHeight()/scale, img.getWidth()/scale, img.getHeight()/scale);
    }

    public float getScale(){
        return scale;
    }

    public void dispose(){
        img.dispose();
    }


}
