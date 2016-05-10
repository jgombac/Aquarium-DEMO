package gameobjects;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.gombi.aqua.AquaGame;

import buttons.MyButton;

public class ButtonHUD {
    private float startY;
    public static final float endY = 0;

    private Vector3 pos;


    private Rectangle rect;

    private boolean moving = false;
    private boolean movingUp = false;
    private boolean movingDown = false;

    public MyButton menu;
    public MyButton shop;
    public MyButton fish;
    public MyButton stats;



    public ButtonHUD(){
        menu = new MyButton(MyButton.ButtonName.MENU);
        shop = new MyButton(MyButton.ButtonName.SHOP);
        fish = new MyButton(MyButton.ButtonName.FISH);
        stats = new MyButton(MyButton.ButtonName.STATS);
        pos = new Vector3((AquaGame.WIDTH - menu.getImg().getWidth()*4)/2, -menu.getImg().getHeight(), 0);
        startY = -menu.getImg().getHeight();
        menu.move(pos.x, pos.y);
        shop.move(menu.getPos().x + menu.getImg().getWidth(), pos.y);
        fish.move(shop.getPos().x + shop.getImg().getWidth(), pos.y);
        stats.move(fish.getPos().x + fish.getImg().getWidth(), pos.y);


        rect = new Rectangle(pos.x, AquaGame.HEIGHT - pos.y - menu.getImg().getHeight(), menu.getImg().getWidth()*4, menu.getImg().getHeight());
        System.err.println(rect.getWidth() + "  " + rect.getHeight());
    }

    public boolean isMoving(){
        return moving;
    }

    public void setMoving(){
        moving = true;
    }


    public boolean isUp(){
        return pos.y >= endY;
    }


    public void swim(float speedX, float speedY) {
        pos.add(speedX, speedY, 0);
        menu.swim(speedX, speedY);
        shop.swim(speedX, speedY);
        fish.swim(speedX, speedY);
        stats.swim(speedX, speedY);
        rect.setPosition(pos.x, AquaGame.HEIGHT - pos.y - menu.getImg().getHeight());
        if(pos.y >= endY){
            moving = false;
        }
        else if(pos.y <= startY){
            moving = false;
        }
    }


    public void update(){
        if(moving){
            if(pos.y <= startY){
                movingUp = true;
            }
            else if(pos.y >= endY){
                movingDown = true;
            }
            if(movingUp) {
                swim(0, 25);
                return;
            }
            swim(0, -25);
        }
        else{
            movingUp = false;
            movingDown = false;
        }
    }

    public MyButton.ButtonName getButtonClicked(float x, float y){
        if (shop.isClicked(x, y))
            return shop.getName();
        else if(menu.isClicked(x, y))
            return menu.getName();
        else if(fish.isClicked(x, y))
            return fish.getName();
        else if(stats.isClicked(x, y))
            return stats.getName();
        else return null;
    }

    public void dispose(){
        shop.dispose();
        menu.dispose();
        fish.dispose();
        stats.dispose();
    }

    public boolean isClicked(float x, float y){
        return rect.contains(x, y);
    }
}
