package buttons;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.gombi.aqua.AquaGame;

import java.io.FileNotFoundException;

import gameobjects.GameObject;

public class MyButton extends GameObject{
    private Texture img;
    private Rectangle rect;
    private ButtonName name;

    public enum ButtonName{
        CLOSE, DONE, SHOP, FISH, EDIT, STATS, MENU, EMPTY, SELL, UP, DOWN
    }

    public MyButton(ButtonName name){
        super();
        this.name = name;
        try {
            switch (name) {
                case CLOSE:
                    img = new Texture("buttons/closeButton.png");
                    break;
                case DONE:
                    img = new Texture("buttons/doneButton.png");
                    break;
                case SHOP:
                    img = new Texture("buttons/shopButton.png");
                    break;
                case FISH:
                    img = new Texture("buttons/fishButton.png");
                    break;
                case EDIT:
                    img = new Texture("buttons/editButton.png");
                    break;
                case STATS:
                    img = new Texture("buttons/statsButton.png");
                    break;
                case MENU:
                    img = new Texture("buttons/menuButton.png");
                    break;
                case EMPTY:
                    img = new Texture("buttons/emptyButton.png");
                    break;
                case SELL:
                    img = new Texture("buttons/sellButton.png");
                    break;
                case UP:
                    img = new Texture("buttons/upButton.png");
                    break;
                case DOWN:
                    img = new Texture("buttons/downButton.png");
                    break;
            }
        }catch(Exception e){
            System.err.println("Error loading button image.");
        }
        rect = new Rectangle();
    }

    @Override
    public void swim(float speedX, float speedY) {
        super.swim(speedX, speedY);
        rect.set(getPos().x, AquaGame.HEIGHT - img.getHeight() - getPos().y, img.getWidth(), img.getHeight());
    }

    @Override
    public void move(float x, float y) {
        super.move(x, y);
        rect.set(getPos().x, AquaGame.HEIGHT - img.getHeight() - getPos().y, img.getWidth(), img.getHeight());
    }

    public Texture getImg(){
        return img;
    }

    public boolean isClicked(float x, float y){
        return rect.contains(x , y);
    }

    public ButtonName getName(){
        return name;
    }

    public void dispose(){
        img.dispose();
    }
}
