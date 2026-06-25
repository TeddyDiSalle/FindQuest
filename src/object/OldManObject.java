package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class OldManObject extends SuperObject {
	GamePanel gp;
	UtilityTool uTool = new UtilityTool();

	public OldManObject(GamePanel gp) {
		this.gp = gp;
		name = "oldMan";
		try {
			image = uTool.scaleImage(
					ImageIO.read(getClass().getResourceAsStream("/objects/oldMan.png")),
					gp.getTileSize(), gp.getTileSize());
		} catch (IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}

}
