package entity;

import java.util.Random;

import main.GamePanel;

/**
 * The old man in the first shack.
 */
public class OldManNPC extends Entity {

    /**
     * Constructor.
     *
     * @param gp The game panel.
     */
    public OldManNPC(GamePanel gp) {
        super(gp);

        setDirection("down");
        setSpeed(1);
        setupEntityImage("/res/npc/human2.png");
    }

    /**
     * Sets this guy up.
     *
     * @param name
     */
    public void setupEntityImage(String name) {
        setDown(name, 0);
    }

    /**
     * The old man will pace back and forth.
     */
    public void pace() {
        Random ran = new Random();
        if (ran.nextBoolean()) {
            setDirection("left");
        } else {
            setDirection("right");
        }
    }

    @Override
    public void setAction() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }
}
