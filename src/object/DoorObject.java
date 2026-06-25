package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class DoorObject extends SuperObject {
	GamePanel gp;
	UtilityTool uTool = new UtilityTool();

	public DoorObject(GamePanel gp) {
		this.gp = gp;
		name = "door";
		try {
			image = uTool.scaleImage(
					ImageIO.read(
							getClass().getResourceAsStream("/objects/closedDoor.png")),
					gp.getTileSize(), gp.getTileSize());
		} catch (IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}

}
