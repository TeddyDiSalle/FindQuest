package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

/**
 * A class to create entities. They'll walk around, maybe follow you, fight you,
 * dialogue you.
 */
public abstract class Entity {

    /**
     * The game panel being played on.
     */
    private GamePanel gp;

    /**
     * The position and speed of the entity.
     */
    private int worldX, worldY, speed;

    /**
     * what the entity looks like in different directions.
     */
    private BufferedImage[] up, down, left, right;

    /**
     * Size of BufferedImage arrays.
     */
    private static final int SPRITESPERDIRECTION = 3;

    /**
     * where bro is looking.
     */
    private String direction;

    /**
     * How many frames of current sprite has been on screen and which sprite we are
     * on in the walking animating.
     */
    private int spriteCounter = 0, spriteNum = 1;

    /**
     * Collision box.
     */
    private Rectangle solidArea;

    /**
     * Original size of box.
     */
    private int solidAreaDefaultX, solidAreaDefaultY;

    /**
     * Can this entity be collided with?
     */
    private boolean collisionOn = false;

    /**
     * The default tile size. Shouldnt change unless I have some tile refactoring
     * shiz in the future or something burh I ain't know. You know like everything
     * shrinks? I mean who even knows with this type of stuff.
     */
    private final int tileSize;

    /**
     * Really just an abstract class. Don't inistiatiate the entity
     *
     * @param gp the game panel the entity be playing on
     */
    protected Entity(GamePanel gp) {
        this.gp = gp;
        tileSize = gp.getTileSize();

        up = new BufferedImage[SPRITESPERDIRECTION];
        down = new BufferedImage[SPRITESPERDIRECTION];
        left = new BufferedImage[SPRITESPERDIRECTION];
        right = new BufferedImage[SPRITESPERDIRECTION];
    }

    /**
     * Set the action the entity is performing.
     */
    public abstract void setAction();

    /**
     * Update the entities.
     */
    public abstract void update();

    /**
     * .
     *
     * @param g2
     */
    public void draw(Graphics2D g2) {

        Player mC = gp.getMainCharacter();
        final int mCWorldX = mC.getWorldX();
        final int mCWorldY = mC.getWorldY();
        final int mCScreenX = mC.getScreenX();
        final int mCScreenY = mC.getScreenY();

        if (worldX + tileSize > mCWorldX - mCScreenX
                && worldX - tileSize < mCWorldX + mCScreenX
                && worldY + tileSize > mCWorldY - mCScreenY
                && worldY - tileSize < mCWorldY + mCScreenY) {

            BufferedImage image = null;

            switch (direction) {
            case "up":
                image = up[spriteNum];
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

            // final int screenX = worldX - mCWorldX + mCScreenX;
            // final int screenY = worldY - mCWorldY + mCScreenY;

            g2.drawImage(image, worldX - mCWorldX + mCScreenX,
                    worldY - mCWorldY + mCScreenY, null);
        }
    }

    /**
     * @return the entity's speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param newSpeed the speed to set
     */
    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    /**
     * @return the worldX
     */
    public int getWorldX() {
        return worldX;
    }

    /**
     * @param x the x to set
     */
    public void setWorldX(int x) {
        this.worldX = x;
    }

    /**
     * Add to worldX.
     *
     * @param x The additive
     */
    public void addWorldX(int x) {
        this.worldX += x;
    }

    /**
     * @return the worldY
     */
    public int getWorldY() {
        return worldY;
    }

    /**
     * @param y the y to set
     */
    public void setWorldY(int y) {
        this.worldY = y;
    }

    /**
     * Add to worldY.
     *
     * @param y The additive
     */
    public void addWorldY(int y) {
        this.worldY += y;
    }

    /**
     * Grabs the name of the image and returns the BufferedImage version of it,
     * prescaled.
     *
     * @param imageName The file name of the iamge
     * @return prescaled BufferedImage of image
     */
    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream(imageName));
            scaledImage = uTool.scaleImage(scaledImage, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaledImage;
    }

    /**
     * @param index which of 3 up do you want?
     * @return images in the up direction
     */
    public BufferedImage getUp(int index) {
        BufferedImage result;
        try {
            result = up[index];
        } catch (Throwable e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    /**
     * @param imageName The name of the image
     * @param index     The index of up you want
     */
    public void setUp(String imageName, int index) {
        up[index] = setup(imageName);
    }

    /**
     * @param index which of 3 downs do you want?
     * @return images in the down direction
     */
    public BufferedImage getDown(int index) {
        BufferedImage result;
        try {
            result = down[index];
        } catch (Throwable e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    /**
     * @param imageName The name of the image
     * @param index     The index of down you want
     */
    public void setDown(String imageName, int index) {
        down[index] = setup(imageName);
    }

    /**
     * @param index which of 3 left do you want?
     * @return images in the left direction
     */
    public BufferedImage getLeft(int index) {
        BufferedImage result;
        try {
            result = left[index];
        } catch (Throwable e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    /**
     * @param imageName The name of the image
     * @param index     The index of left you want
     */
    public void setLeft(String imageName, int index) {
        left[index] = setup(imageName);
    }

    /**
     * @param index which of 3 right do you want?
     * @return images in the right direction
     */
    public BufferedImage getRight(int index) {
        BufferedImage result;
        try {
            result = right[index];
        } catch (Throwable e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    /**
     * @param imageName The name of the image
     * @param index     The index of right you want
     */
    public void setRight(String imageName, int index) {
        right[index] = setup(imageName);
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * @return the spriteCounter
     */
    public int getSpriteCounter() {
        return spriteCounter;
    }

    /**
     * @param count The spriteCounter to set
     */
    public void setSpriteCounter(int count) {
        this.spriteCounter = count;
    }

    /**
     * increase the sprite counter.
     */
    public void incSpriteCounter() {
        this.spriteCounter++;
    }

    /**
     * @return the spriteNum
     */
    public int getSpriteNum() {
        return spriteNum;
    }

    /**
     * @param spriteNum the spriteNum to set
     */
    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    /**
     * @return the solidArea
     */
    public Rectangle getSolidArea() {
        return solidArea;
    }

    /**
     * @param solidArea the solidArea to set
     */
    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }

    /**
     * @return the solidAreaDefaultX
     */
    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    /**
     * @param solidAreaDefaultX the solidAreaDefaultX to set
     */
    public void setSolidAreaDefaultX(int solidAreaDefaultX) {
        this.solidAreaDefaultX = solidAreaDefaultX;
    }

    /**
     * @return the solidAreaDefaultY
     */
    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    /**
     * @param solidAreaDefaultY the solidAreaDefaultY to set
     */
    public void setSolidAreaDefaultY(int solidAreaDefaultY) {
        this.solidAreaDefaultY = solidAreaDefaultY;
    }

    /**
     * @return the collisionOn
     */
    public boolean isCollisionOn() {
        return collisionOn;
    }

    /**
     * @param collisionOn the collisionOn to set
     */
    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    /**
     * @return the game panel
     */
    public GamePanel getGP() {
        return gp;
    }

    /**
     * @return the tileSize
     */
    public int getTileSize() {
        return tileSize;
    }
}
