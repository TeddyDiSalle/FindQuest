package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class ChestObject extends SuperObject {
	GamePanel gp;
	UtilityTool uTool = new UtilityTool();

	public ChestObject(GamePanel gp) {
		this.gp = gp;
		name = "chest";
		try {
			image = uTool.scaleImage(
					ImageIO.read(getClass().getResourceAsStream("/objects/chest.png")),
					gp.getTileSize(), gp.getTileSize());

		} catch (IOException e) {
			e.printStackTrace();
		}

		collision = true;
	}
}
