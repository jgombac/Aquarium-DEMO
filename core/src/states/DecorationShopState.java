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

import buttons.MyButton;
import gameobjects.Decoration;
import gameobjects.Fish;


public class DecorationShopState extends State{

    private Texture bg;
    BitmapFont font;

    private List<Decoration> decorList;
    private List<Decoration> shopDecor;

    private String selected = "";
    private boolean boughtSomething = false;

    private MyButton close;

    protected DecorationShopState(GameStateManager gsm, List<Decoration> decorList) {
        super(gsm);
        bg = new Texture("backgrounds/menubg.png");
        loadFont();

        this.decorList = decorList;
        close = new MyButton(MyButton.ButtonName.CLOSE);

        shopDecor = new ArrayList<Decoration>();
        shopDecor.add(new Decoration(AquaGame.WIDTH/10, AquaGame.HEIGHT/2, "fernplant"));
        shopDecor.add(new Decoration(shopDecor.get(0).getPos().x + shopDecor.get(0).getImg().getWidth(), shopDecor.get(0).getPos().y, "bubbleboat"));

        close.move(shopDecor.get(0).getPos().x, close.getImg().getHeight());
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
            for(Decoration dec : shopDecor){
                if(dec.isClicked(x, y)){
                    if(dec.getName().equals(selected)){
                        decorList.add(dec);
                        dec.bought();
                        boughtSomething = true;
                        gsm.pop();
                        dispose();
                    }
                    else{
                        selected = dec.getName();
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
        for(Decoration dec : shopDecor){
            sb.draw(dec.getImg(), dec.getPos().x, dec.getPos().y);
        }
        if(boughtSomething)
            font.draw(sb,"You bought a " + selected.toUpperCase() + "", close.getPos().x + close.getImg().getWidth()*1.5f, close.getPos().y + close.getImg().getHeight()/2);
        sb.draw(close.getImg(), close.getPos().x, close.getPos().y);
        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
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
