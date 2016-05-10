package gameobjects;


import com.gombi.aqua.AquaGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bubbles extends GameObject{
    private List<Bubble> bubbles = new ArrayList<Bubble>();
    private int numBubbles = 6;
    private Random rnd = new Random();
    private float spawner = 0;
    private float spawnTime = rnd.nextFloat() * (1f - 0.5f) + 0.5f;

    public Bubbles(float x, float y){
        super(x, y);
    }

    public void update(float dt){
        spawner += dt;
        if(spawner > spawnTime && bubbles.size() < numBubbles){
            bubbles.add(new Bubble(getPos().x, getPos().y));
            spawnTime = rnd.nextFloat() * (1f - 0.5f) + 0.5f;
            spawner = 0;
        }
        for(Bubble bub : bubbles){
            bub.update();
            if(bub.getPos().y > AquaGame.HEIGHT){
                bub.restart();
            }
        }
    }

    public void boost(){
        if(numBubbles < 15)
            numBubbles++;
    }

    public void decrease(){
        if(numBubbles > 0) {
            numBubbles--;
            bubbles.remove(0);
        }
    }

    public int getNumBubbles(){
        return numBubbles;
    }

    public List<Bubble> getBubbles(){
        return bubbles;
    }

    public void dispose(){
        for(Bubble bub : bubbles){
            bub.dispose();
        }
    }
}
