package gameobjects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.gombi.aqua.AquaGame;

import java.util.Date;
import java.util.Random;

public class Fish extends GameObject{
    private Random rnd = new Random();

    private float speedX;
    private float speedY;
    private float moveToX;
    private float moveToY;
    private boolean moving = false;


    private Rectangle rect;
    private Texture img;
    private Sprite sprite;

    private Texture moveRight;
    private Texture moveLeft;
    TextureRegion[] framesRight;
    TextureRegion[] framesLeft;
    Animation aniRight;
    Animation aniLeft;
    Animation currentAni;
    float timer = 0;

    private long timeOfCreation;
    private String name;
    private int age;
    private float scaleSize;

    private int hunger;

    public Fish(float x, float y, String name, int age, long timeOfCreation, float scaleSize){
        super(x, y);
        this.timeOfCreation = timeOfCreation;
        this.name = name;
        this.age = age;
        this.scaleSize = scaleSize;
        img = new Texture("fish/" + name + "/" + name + ".png");
        sprite = new Sprite(img);
        rect = new Rectangle(x, AquaGame.HEIGHT-img.getHeight()-y, img.getWidth(), img.getHeight());


        moveRight = new Texture("fish/" + name + "/" + name + "_right.png");
        moveLeft = new Texture("fish/" + name + "/" + name + "_left.png");

        TextureRegion[][] tempFramesR = TextureRegion.split(moveRight, 256, 256);
        framesRight = new TextureRegion[6];

        TextureRegion[][] tempFramesL = TextureRegion.split(moveLeft, 256, 256);
        framesLeft = new TextureRegion[6];

        for(int i = 0; i < 6; i++){
            framesRight[i] = tempFramesR[0][i];
            framesLeft[i] = tempFramesL[0][i];
        }

        aniRight = new Animation(1f/7f, framesRight);
        aniLeft = new Animation(1f/7f, framesLeft);
        currentAni = aniRight;
    }

    public Fish(float x, float y, String name){
        super(x, y);
        timeOfCreation = new Date().getTime();
        this.name = name;
        age = 0;
        hunger = 0;
        img = new Texture("fish/" + name + "/" + name + ".png");
        scaleSize = 2f;
        sprite = new Sprite(img);
        rect = new Rectangle(x, AquaGame.HEIGHT-img.getHeight()-y, img.getWidth(), img.getHeight());


        moveRight = new Texture("fish/" + name + "/" + name + "_right.png");
        moveLeft = new Texture("fish/" + name + "/" + name + "_left.png");

        TextureRegion[][] tempFramesR = TextureRegion.split(moveRight, 256, 256);
        framesRight = new TextureRegion[6];

        TextureRegion[][] tempFramesL = TextureRegion.split(moveLeft, 256, 256);
        framesLeft = new TextureRegion[6];

        for(int i = 0; i < 6; i++){
            framesRight[i] = tempFramesR[0][i];
            framesLeft[i] = tempFramesL[0][i];
        }

        aniRight = new Animation(1f/7f, framesRight);
        aniLeft = new Animation(1f/7f, framesLeft);
        currentAni = aniRight;
    }


    public void update(float dt){
        if(!moving){
            newDirection();
            speedX = (moveToX - getPos().x) * (rnd.nextFloat() *(0.008f - 0.003f) + 0.005f);
            speedY = (moveToY - getPos().y) * (rnd.nextFloat() *(0.008f - 0.003f) + 0.005f);
            moving = true;
        }
        if(moving){
            speedX = (Math.abs(speedX) < 0.2f) ? speedX : (float) (speedX * 0.995);
            speedY = (Math.abs(speedY) < 0.2f) ? speedY : (float) (speedY * 0.995);
            swim(speedX, speedY);
            rect.setPosition(getPos().x, AquaGame.HEIGHT - getPos().y - getImg().getHeight() / getScale());
        }

        if(Math.abs(getPos().x)-Math.abs(moveToX) > 0 && Math.abs(getPos().x)-Math.abs(moveToX) < Math.abs(speedX) ||
                Math.abs(getPos().y)-Math.abs(moveToY) > 0 && Math.abs(getPos().y)-Math.abs(moveToY) < Math.abs(speedY)){
            moving = false;
        }
        currentAni = (speedX < 0) ? aniLeft : aniRight;
        if((int)timer > 10){
            setScaleSize((float) (getScale()*0.98));
            timer = 0;
            age++;
            hunger += 20;
        }
        rect.set(getPos().x, AquaGame.HEIGHT - (img.getHeight() / getScale()) - getPos().y, img.getWidth()/getScale(), img.getHeight()/getScale());

    }

    public void moveTo(float x, float y){
        moveToX = x - img.getWidth() / 2;
        moveToY = AquaGame.HEIGHT - y - (img.getHeight() / getScale()) / 2;
        speedX = (moveToX - getPos().x) * (rnd.nextFloat() * (0.008f - 0.003f) + 0.005f);
        speedY = (moveToY - getPos().y) * (rnd.nextFloat() * (0.008f - 0.003f) + 0.005f);
        moving = true;

    }

    private void newDirection(){
        int nY = rnd.nextInt(2);
        int nX = rnd.nextInt(2);
        float minY = 0;
        float maxY = 0;
        float minX = 0;
        float maxX = 0;
        switch(nY) {
            case 0:
                minY = 0;
                maxY = AquaGame.HEIGHT/3;
                break;
            case 1:
                minY = AquaGame.HEIGHT - (img.getHeight() / getScale()) - (AquaGame.HEIGHT/3);
                maxY = AquaGame.HEIGHT - (img.getHeight() / getScale());
                break;
        }
        switch(nX){
            case 0:
                minX = 0;
                maxX = AquaGame.WIDTH/3;
                break;
            case 1:
                minX = AquaGame.WIDTH - (img.getWidth() / getScale()) - (AquaGame.WIDTH/3);
                maxX = AquaGame.WIDTH - (img.getWidth() / getScale());
                break;
        }
        moveToX = rnd.nextFloat() * (maxX - minX) + minX;
        moveToY = rnd.nextFloat() * (maxY - minY) + minY;
    }

    public void eat(){
        hunger -= 10;
    }


    public Texture getImg(){
        return img;
    }

    public TextureRegion getAni(float dt){
        timer += dt;
        return currentAni.getKeyFrame(timer, true);
    }

    public void setScaleSize(float amount){
        scaleSize = amount;
        rect.set(getPos().x, AquaGame.HEIGHT - (img.getHeight()/getScale()) - getPos().y, img.getWidth()/getScale(), img.getHeight()/getScale());
    }

    public float getScale(){
        if(scaleSize > 1f)
            return scaleSize;
        return 1f;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public Vector3 getPos(){
        return super.getPos();
    }

    public Rectangle getRect(){
        return rect;
    }

    public boolean isClicked(float x, float y){
        return rect.contains(x, y);
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

    public String getHungerString(){
        if(hunger > 70)
            return "This fish is very hungry.";
        if(hunger > 50)
            return "This fish is kinda hungry.";
        if(hunger > 30)
            return "This fish is not very hungry";
        return "This fish is not hungry";
    }

    public int getHunger(){
        return hunger;
    }

    public long getTimeOfCreation(){
        return timeOfCreation;
    }

    public void dispose(){
        img.dispose();
        moveRight.dispose();
        moveLeft.dispose();
    }



}
