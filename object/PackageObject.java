package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class PackageObject extends SuperObject {
	GamePanel gp;
	UtilityTool uTool = new UtilityTool();

	public PackageObject(GamePanel gp) {
		this.gp = gp;
		name = "package";
		try {

			image = uTool.scaleImage(
					ImageIO.read(getClass().getResourceAsStream("/objects/package.png")),
					gp.getTileSize(), gp.getTileSize());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
