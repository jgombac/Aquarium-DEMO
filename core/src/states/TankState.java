package states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.gombi.aqua.AquaGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import buttons.MyButton;
import gameobjects.Bubble;
import gameobjects.ButtonHUD;
import gameobjects.Decoration;
import gameobjects.Fish;
import gameobjects.Food;
import gameobjects.TankBackground;
import gameobjects.TankObjectStatus;
import savingarrays.DecorSave;
import savingarrays.FishSave;

public class TankState extends State{
    private Random rnd = new Random();
    private TankBackground tbg;
    private OrthographicCamera cam;

    BitmapFont font;

    private List<Fish> fishList;
    private List<Decoration> decorList;

    private Food food;

    private String statusString = "";
    private Fish statusfish;
    private Decoration statusDecor;
    private TankObjectStatus status;

    private ButtonHUD buttons;
    private MyButton close;
    private MyButton hudButton;
    private MyButton sell;
    private MyButton done;
    private MyButton edit;
    private MyButton up;
    private MyButton down;
    private MyButton upImg;
    private MyButton downImg;


    public TankState(GameStateManager gsm) {
        super(gsm);

        tbg = new TankBackground();
        status = new TankObjectStatus();
        loadFont();

        buttons = new ButtonHUD();
        close = new MyButton(MyButton.ButtonName.CLOSE);
        close.move((AquaGame.WIDTH / 10)*1.1f, (AquaGame.HEIGHT / 6)*1.1f);
        hudButton = new MyButton(MyButton.ButtonName.EMPTY);
        hudButton.move(AquaGame.WIDTH/2 - (close.getImg().getWidth()/2), - close.getImg().getHeight()/2);
        sell = new MyButton(MyButton.ButtonName.SELL);
        sell.move(AquaGame.WIDTH - close.getPos().x - close.getImg().getWidth(), close.getPos().y);
        done = new MyButton(MyButton.ButtonName.DONE);
        done.move(AquaGame.WIDTH - done.getImg().getWidth(), AquaGame.HEIGHT - done.getImg().getHeight());
        edit = new MyButton(MyButton.ButtonName.EDIT);
        edit.move(sell.getPos().x, sell.getPos().y + sell.getImg().getHeight());

        up = new MyButton(MyButton.ButtonName.UP);
        up.move(sell.getPos().x-up.getImg().getWidth()*1.5f, edit.getPos().y);
        down = new MyButton(MyButton.ButtonName.DOWN);
        down.move(up.getPos().x, sell.getPos().y);

        upImg = new MyButton(MyButton.ButtonName.UP);
        downImg = new MyButton(MyButton.ButtonName.DOWN);
        upImg.move(upImg.getImg().getWidth(), AquaGame.HEIGHT-upImg.getImg().getHeight());
        downImg.move(upImg.getImg().getWidth(), upImg.getPos().y - downImg.getImg().getHeight());


        cam = new OrthographicCamera(AquaGame.WIDTH, AquaGame.HEIGHT);
        cam.setToOrtho(false, AquaGame.WIDTH, AquaGame.HEIGHT);
        cam.update();

        fishList = new ArrayList<Fish>();
        decorList = new ArrayList<Decoration>();

        loadTheDecor();
        loadTheFish();


    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();

            for(Decoration dec : decorList){
                if(dec.isClicked(x, y) && !dec.isSet()) {
                    dec.move(x - (dec.getImg().getWidth()/dec.getScale())/2, AquaGame.HEIGHT - y - (dec.getImg().getHeight()/dec.getScale())/2);
                    return;
                }
            }
        }

        if(Gdx.input.justTouched()){
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();


            if (done.isClicked(x, y)) {
                for(Decoration dec : decorList){
                    if(!dec.isSet()) {
                        dec.setDown();
                        return;
                    }
                }
            }

            if(upImg.isClicked(x, y)){
                for(Decoration dec : decorList){
                    if(!dec.isSet()) {
                        dec.largeImg();
                        return;
                    }
                }
            }

            if(downImg.isClicked(x, y)){
                for(Decoration dec : decorList){
                    if(!dec.isSet()) {
                        dec.smallImg();
                        return;
                    }
                }
            }
            if(!statusString.equals("") && close.isClicked(x, y) && !status.isMoving()){
                status.setMoving();
                statusString = "";
                return;
            }

            if(statusString.equals("decoration") && edit.isClicked(x, y) && !status.isMoving()){
                statusDecor.bought();
                status.setMoving();
                statusString = "";
                return;
            }
            if(statusString.equals("decoration") && sell.isClicked(x, y) && !status.isMoving()){
                decorList.remove(statusDecor);
                status.setMoving();
                statusString = "";
                return;
            }

            if(statusString.equals("decoration") && up.isClicked(x, y) && statusDecor.getName().equals("bubbleboat") && !status.isMoving()){
                statusDecor.boostBubbles();
                return;
            }

            if(statusString.equals("decoration") && down.isClicked(x, y) && statusDecor.getName().equals("bubbleboat") && !status.isMoving()){
                statusDecor.decreaseBubbles();
                return;
            }

            if(statusString.equals("fish") && sell.isClicked(x, y) && !status.isMoving()){
                fishList.remove(statusfish);
                status.setMoving();
                statusString = "";
                return;
            }
            if(hudButton.isClicked(x, y) && !buttons.isMoving() && !buttons.isUp()){
                buttons.setMoving();
                return;
            }
            if(buttons.isUp() && buttons.isClicked(x, y)){
                if(buttons.getButtonClicked(x, y) != null){
                    switch(buttons.getButtonClicked(x, y)){
                        case FISH:
                            gsm.push(new FishShopState(gsm, fishList));
                            break;
                        case SHOP:
                            gsm.push(new DecorationShopState(gsm, decorList));
                            break;
                    }
                    buttons.setMoving();
                    return;
                }
            }
            if(buttons.isUp() && !buttons.isClicked(x, y)){
                buttons.setMoving();
                return;
            }

            if(statusString.equals("") && !status.isMoving() && !buttons.isUp()) {
                for (Decoration dec : decorList) {
                    if (dec.isClicked(x, y)) {
                        statusSetup(dec);
                        status.setMoving();
                        return;
                    }
                }
                for(int i = fishList.size() - 1; i >= 0; i--){
                    Fish fish = fishList.get(i);
                    if (fish.isClicked(x, y)) {
                        statusSetup(fish);
                        status.setMoving();
                        return;
                    }
                }
            }

            food = new Food(x , AquaGame.HEIGHT - y - 124);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        int deadIndex = -1;
        for(Fish fish : fishList){
            fish.update(dt);
            if(fish.getHunger() > 30 && food != null) {
                fish.moveTo(food.getPos().x + food.getImg().getWidth()/2, AquaGame.HEIGHT -  food.getPos().y + food.getImg().getHeight()/2);
                if(fish.getRect().overlaps(food.getRect())) {
                    fish.eat();
                    food.bit();
                    System.out.println("FISH HAS EATEN  " + food.getNutrition());
                }
            }
            if(fish.getAge() >= 50 || fish.getHunger() >= 100)
                deadIndex = fishList.indexOf(fish);
        }
        if(deadIndex != -1)
            fishList.remove(deadIndex);

        for(Decoration dec : decorList){
            if(dec.getName().equals("bubbleboat") && dec.isSet())
                dec.getBubbles().update(dt);
        }
        if(food != null && food.getNutrition() <= 0)
            food = null;
        status.update();
        buttons.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        float dt = Gdx.graphics.getDeltaTime();
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(tbg.getImg(), 0, 0, AquaGame.WIDTH, AquaGame.HEIGHT);

        if(food != null)
            sb.draw(food.getImg(), food.getPos().x, food.getPos().y);

        for(Fish fish : fishList){
            sb.draw(fish.getAni(dt), fish.getPos().x, fish.getPos().y, fish.getImg().getWidth() / fish.getScale(), fish.getImg().getHeight() / fish.getScale());
        }
        for(Decoration dec : decorList){
            sb.draw(dec.getImg(), dec.getPos().x, dec.getPos().y, dec.getImg().getWidth()/dec.getScale(), dec.getImg().getHeight()/dec.getScale());
            if(!dec.isSet()) {
                sb.draw(done.getImg(), done.getPos().x, done.getPos().y);
                sb.draw(downImg.getImg(), downImg.getPos().x, downImg.getPos().y);
                sb.draw(upImg.getImg(), upImg.getPos().x, upImg.getPos().y);
            }
            if(dec.getBubbles() != null){
                for(Bubble bub : dec.getBubbles().getBubbles()){
                    sb.draw(bub.getImg(), bub.getPos().x, bub.getPos().y, bub.getImg().getWidth()/bub.getScale(), bub.getImg().getHeight()/bub.getScale());
                }
            }
        }


        if(status.isMoving() || !statusString.equals("")){
            sb.draw(status.getImg(), status.getPos().x, status.getPos().y, (AquaGame.WIDTH / 10) * 8, (AquaGame.HEIGHT / 6) * 4);
            if(!status.isMoving() && statusString.equals("fish")) {
                sb.draw(statusfish.getAni(dt), status.getObjX(), status.getObjY(),
                        statusfish.getImg().getWidth() / statusfish.getScale(), statusfish.getImg().getHeight() / statusfish.getScale());
                font.draw(sb, status.fishInfo(), (AquaGame.WIDTH / 10) + (statusfish.getImg().getWidth()/2)*3, (AquaGame.HEIGHT / 6)*4);
                sb.draw(close.getImg(), close.getPos().x, close.getPos().y);
                sb.draw(sell.getImg(), sell.getPos().x, sell.getPos().y);
            }
            else if(!status.isMoving() && statusString.equals("decoration")){
                sb.draw(statusDecor.getImg(), status.getObjX(), status.getObjY());
                sb.draw(close.getImg(), close.getPos().x, close.getPos().y);
                sb.draw(sell.getImg(), sell.getPos().x, sell.getPos().y);
                sb.draw(edit.getImg(), edit.getPos().x, edit.getPos().y);
                if(statusDecor.getName().equals("bubbleboat")) {
                    sb.draw(down.getImg(), down.getPos().x, down.getPos().y);
                    sb.draw(up.getImg(), up.getPos().x, up.getPos().y);
                    font.draw(sb, statusDecor.getBubbleSize()+"", up.getPos().x + up.getImg().getWidth()/2, up.getPos().y + up.getImg().getHeight()*1.5f);
                }
            }
        }
        if (!buttons.isUp())
            sb.draw(hudButton.getImg(), hudButton.getPos().x, hudButton.getPos().y);
        sb.draw(buttons.menu.getImg(), buttons.menu.getPos().x, buttons.menu.getPos().y);
        sb.draw(buttons.shop.getImg(), buttons.shop.getPos().x, buttons.shop.getPos().y);
        sb.draw(buttons.fish.getImg(), buttons.fish.getPos().x, buttons.fish.getPos().y);
        sb.draw(buttons.stats.getImg(), buttons.stats.getPos().x, buttons.stats.getPos().y);

        sb.end();
    }

    @Override
    public void dispose() {
        saveTheFish();
        saveTheDecor();
        tbg.dispose();
        close.dispose();
        hudButton.dispose();
        status.dispose();
        buttons.dispose();
        font.dispose();
        for(Fish fish : fishList){
            fish.dispose();
        }
    }

    private void statusSetup(Fish fish){
        statusString = "fish";
        statusfish = fish;
        status.setFish(fish);
        status.down();
    }

    private void statusSetup(Decoration dec){
        statusString = "decoration";
        statusDecor = dec;
        status.setDecor(dec);
        status.down();
    }

    private void saveTheFish(){
        ArrayList<FishSave> fishSaves = new ArrayList<FishSave>();
        for(Fish fish : fishList){
            fishSaves.add(new FishSave(fish.getPos().x, fish.getPos().y, fish.getName(), fish.getAge(), fish.getTimeOfCreation(), fish.getScale()));
        }
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        FileHandle fishFileHandle = Gdx.files.local("/bin/fishSave.json");
        fishFileHandle.writeString(Base64Coder.encodeString(json.toJson(fishSaves)), false);
    }

    private void saveTheDecor(){
        ArrayList<DecorSave> decorSaves = new ArrayList<DecorSave>();
        for(Decoration dec : decorList){
            decorSaves.add(new DecorSave(dec.getPos().x, dec.getPos().y, dec.getName(), dec.getScale()));
        }
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        FileHandle fishFileHandle = Gdx.files.local("/bin/decorSave.json");
        fishFileHandle.writeString(Base64Coder.encodeString(json.toJson(decorSaves)), false);
    }

    private void loadTheDecor(){
        try{
            Json json = new Json();
            ArrayList<DecorSave> decorLoad = json.fromJson(ArrayList.class, FishSave.class, Base64Coder.decodeString(Gdx.files.local("/bin/decorSave.json").readString()));
            for(DecorSave save : decorLoad){
                decorList.add(new Decoration(save.getX(), save.getY(), save.getName(), save.getScale()));
            }
            System.err.println("Successfully loaded all the decoration:");
        }catch(Exception e){
            System.err.println("Error loading decoration.");
            decorList.add(new Decoration(100, 20, "bubbleboat"));
            decorList.add(new Decoration(300, 20, "fernplant"));
        }
    }

    private void loadTheFish(){
        try{
            Json json = new Json();
            ArrayList<FishSave> fishLoad = json.fromJson(ArrayList.class, FishSave.class, Base64Coder.decodeString(Gdx.files.local("/bin/fishSave.json").readString()));
            for(FishSave save : fishLoad){
                fishList.add(new Fish(save.getX(), save.getY(), save.getName(), save.getAge(), save.getTimeOfCreation(), save.getScale()));
            }
            System.err.println("Successfully loaded all the fish:");
        }catch(Exception e){
            System.err.println("Error loading fish.");
            for (int i = 0; i < 2; i++) {
                int x = rnd.nextInt(AquaGame.WIDTH);
                int y = rnd.nextInt(AquaGame.HEIGHT);
                Fish fish;
                if (i % 2 == 0)
                    fish = new Fish(x, y, "greenfish");
                else
                    fish = new Fish(x, y, "discofish");
                fishList.add(fish);
            }
        }
    }


    public void loadFont(){
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/grobold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 25;
        param.color = Color.WHITE;
        font = gen.generateFont(param);
        gen.dispose();
    }


}
