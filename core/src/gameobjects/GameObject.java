package gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameObject {
    private Vector3 pos;

    public GameObject(){
        pos = new Vector3();
    }
    public GameObject(float x, float y){
        pos = new Vector3(x, y, 0);
    }

    public void move(float x, float y){
        pos.set(x, y, 0);
    }

    public Vector3 getPos(){
        return pos;
    }

    public void swim(float speedX, float speedY){
        pos.add(speedX, speedY, 0);
    }


}
