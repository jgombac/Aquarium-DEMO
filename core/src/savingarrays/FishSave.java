package savingarrays;


import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class FishSave implements Json.Serializable{
    private long timeOfCreation;
    private float x;
    private float y;
    private String name;
    private int age;
    private float scaleSize;

    public FishSave(float x, float y, String name, int age, long timeOfCreation, float scaleSize){
        this.x = x;
        this.y = y;
        this.name = name;
        this.age = age;
        this.timeOfCreation = timeOfCreation;
        this.scaleSize = scaleSize;
    }

    public FishSave(){}


    @Override
    public void write(Json json) {
        json.writeValue("x", x);
        json.writeValue("y", y);
        json.writeValue("name", name);
        json.writeValue("age", age);
        json.writeValue("timeOfCreation", timeOfCreation);
        json.writeValue("scaleSize", scaleSize);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        x = jsonData.getFloat("x");
        y = jsonData.getFloat("y");
        name = jsonData.getString("name");
        age = jsonData.getInt("age");
        timeOfCreation = jsonData.getLong("timeOfCreation");
        scaleSize = jsonData.getFloat("scaleSize");
    }


    public long getTimeOfCreation() {
        return timeOfCreation;
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

    public int getAge() {
        return age;
    }

    public float getScale(){
        return scaleSize;
    }
}
