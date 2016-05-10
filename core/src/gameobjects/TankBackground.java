package gameobjects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TankBackground {
    private Texture img;

    public TankBackground(){
        img = new Texture("backgrounds/tankbg.png");

    }

    public Texture getImg(){
        return img;
    }

    public void dispose(){
        img.dispose();
    }
}
