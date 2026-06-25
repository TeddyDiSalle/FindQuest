package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

/**
 * The basis for all objects in the game. Like items and shit
 */
public class SuperObject {
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	private int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	UtilityTool uTool = new UtilityTool();

	public void draw(Graphics2D g2, GamePanel gp) {
		int screenX = worldX - gp.getMainCharacter().getWorldX()
				+ gp.getMainCharacter().getScreenX();
		int screenY = worldY - gp.getMainCharacter().getWorldY()
				+ gp.getMainCharacter().getScreenY();

		if (worldX + gp.getTileSize() > gp.getMainCharacter().getWorldX()
				- gp.getMainCharacter().getScreenX()
				&& worldX - gp.getTileSize() < gp.getMainCharacter().getWorldX()
						+ gp.getMainCharacter().getScreenX()
				&& worldY + gp.getTileSize() > gp.getMainCharacter().getWorldY()
						- gp.getMainCharacter().getScreenY()
				&& worldY - gp.getTileSize() < gp.getMainCharacter().getWorldY()
						+ gp.getMainCharacter().getScreenY()) {
			g2.drawImage(image, screenX, screenY, null);
		}
	}

	/**
	 * @return the worldX
	 */
	public int getWorldX() {
		return worldX;
	}

	/**
	 * @param worldX the worldX to set
	 */
	public void setWorldX(int worldX) {
		this.worldX = worldX;
	}

	/**
	 * @return the worldY
	 */
	public int getWorldY() {
		return worldY;
	}

	/**
	 * @param worldY the worldY to set
	 */
	public void setWorldY(int worldY) {
		this.worldY = worldY;
	}
}
