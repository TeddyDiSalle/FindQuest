package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class BarrelObject extends SuperObject {
	GamePanel gp;
	UtilityTool uTool = new UtilityTool();

	public BarrelObject(GamePanel gp) {
		this.gp = gp;
		name = "barrel";
		try {
			image = uTool.scaleImage(
					ImageIO.read(getClass().getResourceAsStream("/objects/barrel.png")),
					gp.getTileSize(), gp.getTileSize());

		} catch (IOException e) {
			e.printStackTrace();
		}

		collision = true;
	}
}
