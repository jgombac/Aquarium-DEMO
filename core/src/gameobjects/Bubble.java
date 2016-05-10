package gameobjects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class Bubble extends GameObject{
    private Texture img;
    private float initX;
    private float initY;
    private float shift;
    private float scaleSize;
    private Random rnd = new Random();

    public Bubble(float x, float y){
        super(x, y);
        initX = x;
        initY = y;
        img = new Texture("decorations/bubble.png");
        scaleSize = rnd.nextFloat() * (2f - 1.5f) + 1.5f;
        shift = rnd.nextFloat() * (1f - 0.5f) + 0.5f;
    }

    public void update(){
        super.swim(shift, rnd.nextInt(7) + 5);
        if(rnd.nextInt(3) == 0)
            shift *= -1f;
    }

    public Texture getImg(){
        return img;
    }

    public void restart(){
        super.move(initX, initY);
    }

    public void dispose(){
        img.dispose();
    }

    public float getScale(){
        return scaleSize;
    }
}
