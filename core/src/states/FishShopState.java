package states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.gombi.aqua.AquaGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import buttons.MyButton;
import gameobjects.Fish;

public class FishShopState extends State{
    private Random rnd = new Random();
    private Texture bg;

    BitmapFont font;

    private List<Fish> fishList;
    private List<Fish> shopFish;
    private String selected = "";
    private boolean boughtSomething = false;

    private MyButton close;

    public FishShopState(GameStateManager gsm, List<Fish> fishList) {
        super(gsm);
        this.fishList = fishList;
        bg = new Texture("backgrounds/menubg.png");
        close = new MyButton(MyButton.ButtonName.CLOSE);

        loadFont();
        shopFish = new ArrayList<Fish>();
        shopFish.add(new Fish(AquaGame.WIDTH/10, AquaGame.HEIGHT/2, "greenfish"));
        shopFish.add(new Fish(shopFish.get(0).getPos().x + shopFish.get(0).getImg().getWidth(), shopFish.get(0).getPos().y, "discofish"));

        close.move(shopFish.get(0).getPos().x, close.getImg().getHeight());
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();
            if(close.isClicked(x, y)) {
                gsm.pop();
                dispose();
            }
            for(Fish fish : shopFish){
                if(fish.isClicked(x, y)){
                    if(fish.getName().equals(selected)){
                        int xx = rnd.nextInt(AquaGame.WIDTH);
                        int yy = rnd.nextInt(AquaGame.HEIGHT);
                        fishList.add(new Fish(xx, yy, fish.getName()));
                        System.out.println("you bought a " + fish.getName());
                        boughtSomething = true;
                    }
                    else{
                        selected = fish.getName();
                        boughtSomething = false;
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bg, 0, 0, AquaGame.WIDTH, AquaGame.HEIGHT);
        for(Fish fish : shopFish){
            sb.draw(fish.getImg(), fish.getPos().x, fish.getPos().y);
        }

        if(boughtSomething)
            font.draw(sb,"You bought a " + selected.toUpperCase() + "", close.getPos().x + close.getImg().getWidth()*1.5f, close.getPos().y + close.getImg().getHeight()/2);
        sb.draw(close.getImg(), close.getPos().x, close.getPos().y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        for(Fish fish : shopFish)
            fish.dispose();
        close.dispose();
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
