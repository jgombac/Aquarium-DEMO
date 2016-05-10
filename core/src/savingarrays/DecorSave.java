package savingarrays;


import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import gameobjects.Decoration;

public class DecorSave implements Json.Serializable{

    private float x;
    private float y;
    private String name;
    private float scale;

    public DecorSave(){

    }

    public DecorSave(float x, float y, String name, float scale){
        this.x = x;
        this.y = y;
        this.name = name;
        this.scale = scale;
    }

    @Override
    public void write(Json json) {
        json.writeValue("x", x);
        json.writeValue("y", y);
        json.writeValue("name", name);
        json.writeValue("scale", scale);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        x = jsonData.getFloat("x");
        y = jsonData.getFloat("y");
        name = jsonData.getString("name");
        scale = jsonData.getFloat("scale");
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public float getScale(){
        return scale;
    }
}
