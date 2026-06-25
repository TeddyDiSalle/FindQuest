package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.AssetPlacer;
import main.GamePanel;
import main.KeyHandler;

/**
 * Main charcter, das little boy.
 */
public class Player extends Entity {

    /**
     * The game panel we are playing on and the key handler to handle the keys.
     */
    private KeyHandler keyH;

    /**
     * Where the player is relative to the top right corner of the screen.
     */
    private final int screenX, screenY;

    /**
     * How many frames until the player switches arms.
     */
    private final int armSwingSpeed = 15;

    /**
     * Default player speed.
     */
    private final int defaultSpeed = 4;

    /**
     * The number of packages the player picked up.
     */
    private int numStuff = 0;

    /**
     * Places the assets.
     */
    private final AssetPlacer aPlacer;

    /**
     * Default tile size.
     */
    private final int tileSize;

    /**
     * Constructor. Takes in the GamePanel the player is on and the key inputs of
     * the player
     *
     * @param gamepnl        The game panel
     * @param iBarelyKnowHer The key handler
     * @param ap             The asset placer
     */
    public Player(GamePanel gamepnl, KeyHandler iBarelyKnowHer, AssetPlacer ap) {
        super(gamepnl);
        this.aPlacer = ap;
        this.keyH = iBarelyKnowHer;
        tileSize = getTileSize();

        // Player is starting at the center of the screen
        screenX = gamepnl.getScreenWidth() / 2 - (tileSize / 2);
        screenY = gamepnl.getScreenHeight() / 2 - (tileSize / 2);

        // The hitbox of the player
        setSolidArea(new Rectangle(8, 16, 28, 28));
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

        // Set up all the values
        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Sets the player's values upon start up.
     */
    public void setDefaultValues() {
        GamePanel gp = getGP();
        setWorldX(tileSize * 8);
        setWorldY(tileSize * 8);
        setSpeed(defaultSpeed);
        setDirection("up");
    }

    /**
     * Grabs all the files and stores them in their place.
     */
    public void getPlayerImage() {
        setUp("/player/boy_pureUp.png", 0);
        setUp("/player/boy_up1.png", 1);
        setUp("/player/boy_up2.png", 2);
        setDown("/player/boy_pureDown.png", 0);
        setDown("/player/boy_down1.png", 1);
        setDown("/player/boy_down2.png", 2);
        setLeft("/player/boy_pureLeft.png", 0);
        setLeft("/player/boy_left1.png", 1);
        setLeft("/player/boy_left2.png", 2);
        setRight("/player/boy_pureRight.png", 0);
        setRight("/player/boy_right1.png", 1);
        setRight("/player/boy_right2.png", 2);

    }

    /**
     * Set the action of the player.
     */
    public void setAction() {
        // TODO
    }

    /**
     * Update the player's "position" (we are really moving the world around the
     * player.
     *
     * @override
     */
    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
            GamePanel gp = getGP();
            /**
             * TODO Add functionality to have just the player move if we are at the end of
             * the map and also do not allow player to go past border If they are in a
             * room or something, the camera should stay stagnant
             */
            if (keyH.upPressed) {
                setDirection("up");
            } else if (keyH.downPressed) {
                setDirection("down");
            } else if (keyH.leftPressed) {
                setDirection("left");
            } else if (keyH.rightPressed) {
                setDirection("right");
            }

            // Check tile collision
            gp.getCollisionChecker().checkTile(this);

            // Check Object Collision
            int objIndex = gp.getCollisionChecker().checkObject(this, true);
            pickUpObject(objIndex);

            // if collision is false, player can move
            if (!isCollisionOn()) {
                switch (getDirection()) {
                case "up":
                    addWorldY(-1 * getSpeed());
                    break;
                default: // down direction, direction == "down"
                    addWorldY(getSpeed());
                    break;
                case "left":
                    addWorldX(-1 * getSpeed());
                    break;
                case "right":
                    addWorldX(getSpeed());
                    break;
                }
            }

            incSpriteCounter();
            if (getSpriteCounter() > armSwingSpeed) {
                if (getSpriteNum() == 1) {
                    setSpriteNum(2);
                } else {
                    setSpriteNum(1);
                }
                setSpriteCounter(0);
            }
        } else { // player is not moving
            setSpriteNum(0);
        }
    }

    /**
     * Deals with when the player picks something up.
     *
     * @param index Which object is being picked up
     */
    public void pickUpObject(int index) {
        GamePanel gp = getGP();

        if (index != -1) {
            String objName = gp.getObj(index).name;
            switch (objName) {
            case "door":
                switch (gp.getMapName()) {
                case ("main"):
                    if (gp.getObj(index).getWorldX() == 30 * tileSize
                            && gp.getObj(index).getWorldY() == 11 * tileSize) { // old
                        // man's
                        if (numStuff >= 5) {
                            gp.resetObj(index);
                            gp.setMapName("inter");
                            aPlacer.setObj("inter");
                            gp.playSoundEffect("doorOpen");
                            // go into old man's house
                            setWorldX(5 * tileSize);
                            setWorldY(3 * tileSize);
                        } else {
                            // dialogue: go away not until i am thanked
                        }
                    } else if (gp.getObj(index).getWorldX() == 39 * tileSize
                            && gp.getObj(index).getWorldY() == 36 * tileSize) {
                        gp.resetObj(index);
                        gp.setMapName("inter");
                        aPlacer.setObj("inter");
                        gp.playSoundEffect("doorOpen");
                        // teleport to tall house
                        setWorldX(45 * tileSize);
                        setWorldY(3 * tileSize);

                    } else if (gp.getObj(index).getWorldX() == 22 * tileSize
                            && gp.getObj(index).getWorldY() == 30 * tileSize) {
                        gp.resetObj(index);
                        gp.setMapName("inter");
                        aPlacer.setObj("inter");
                        gp.playSoundEffect("doorOpen");
                        // teleport to long house
                        setWorldX(3 * tileSize);
                        setWorldY(48 * tileSize);
                    }

                    break;
                case ("inter"):
                    if (gp.getObj(index).getWorldX() == 5 * tileSize
                            && gp.getObj(index).getWorldY() == 4 * tileSize) { // old
                        // man's
                        gp.resetObj(index);
                        gp.setMapName("main");
                        aPlacer.setObj("main");

                        // go into old man's house
                        setWorldX(30 * tileSize);
                        setWorldY(12 * tileSize);
                        gp.playSoundEffect("doorClose");
                    } else if (gp.getObj(index).getWorldX() == 45 * tileSize
                            && gp.getObj(index).getWorldY() == 4 * tileSize) {
                        gp.resetObj(index);
                        gp.setMapName("main");
                        aPlacer.setObj("main");

                        // teleport to tall house
                        setWorldX(39 * tileSize);
                        setWorldY(37 * tileSize);
                        gp.playSoundEffect("doorClose");
                    } else if (gp.getObj(index).getWorldX() == 3 * tileSize
                            && gp.getObj(index).getWorldY() == 49 * tileSize) {
                        gp.resetObj(index);
                        gp.setMapName("main");
                        aPlacer.setObj("main");

                        // teleport to long house
                        setWorldX(22 * tileSize);
                        setWorldY(31 * tileSize);
                        gp.playSoundEffect("doorClose");
                    }

                    break;
                default:
                    System.out.println("Dude, where's my map?");
                    break;
                }
                break;
            case "package":
                if (gp.getObj(index).getWorldX() == 44 * tileSize
                        && gp.getObj(index).getWorldY() == 1 * tileSize) {
                    aPlacer.setToPickedUp(1);
                } else if (gp.getObj(index).getWorldX() == 9 * tileSize
                        && gp.getObj(index).getWorldY() == 6 * tileSize) {

                    aPlacer.setToPickedUp(3);
                }
                gp.getGameUI().showNumStuff();
                gp.resetObj(index);
                numStuff++;
                break;
            case "barrel":
                aPlacer.setToPickedUp(4);
                gp.getGameUI().showNumStuff();
                gp.resetObj(index);
                numStuff++;
                break;
            case "chest":
                if (gp.getObj(index).getWorldX() == 35 * tileSize
                        && gp.getObj(index).getWorldY() == 41 * tileSize) { // outside
                    // chest

                    aPlacer.setToPickedUp(5);

                } else if (gp.getObj(index).getWorldX() == 3 * tileSize
                        && gp.getObj(index).getWorldY() == 45 * tileSize) { // inside
                    // chest

                    aPlacer.setToPickedUp(2);
                }
                gp.playSoundEffect("chestOpen");
                gp.getGameUI().showNumStuff();
                gp.resetObj(index);
                numStuff++;
                break;
            case "oldMan":
                aPlacer.setToPickedUp(0);
                gp.resetObj(index);
                // dialogue option
                break;
            default:
                break;
            }
        }

    }

    @Override
    public final void draw(Graphics2D g2) {

        BufferedImage image = null;
        final int spriteNum = getSpriteNum();
        switch (getDirection()) {
        case "up":
            image = getUp(spriteNum);
            break;
        default: // down direction, direction == "down"
            image = getDown(spriteNum);
            break;
        case "left":
            image = getLeft(spriteNum);
            break;
        case "right":
            image = getRight(spriteNum);
            break;
        }

        g2.drawImage(image, screenX, screenY, null);
    }

    /**
     * @return the numStuff
     */
    public int getNumStuff() {
        return numStuff;
    }

    /**
     * @param numStuff the numStuff to set
     */
    public void setNumStuff(int numStuff) {
        this.numStuff = numStuff;
    }

    /**
     * @return the screenX
     */
    public int getScreenX() {
        return screenX;
    }

    /**
     * @return the screenY
     */
    public int getScreenY() {
        return screenY;
    }
}
