package gameobjects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.gombi.aqua.AquaGame;

public class Food extends GameObject {

    private int nutrition = 20;

    private Texture img;
    private Rectangle rect;

    public Food(float x, float y){
        super(x, y);
        img = new Texture("fish/foods/brownFood.png");
        rect = new Rectangle(x, AquaGame.HEIGHT - getPos().y - img.getHeight(), img.getWidth(), img.getHeight());
    }


    public void bit(){
        nutrition -= 2;
    }

    public Texture getImg(){
        return img;
    }

    public int getNutrition(){
        return nutrition;
    }

    public Rectangle getRect(){
        return rect;
    }
}
