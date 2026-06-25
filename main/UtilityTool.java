package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {

	/**
	 * meant to pre-scale the images to save performance on rendering.
	 *
	 * @param original the image to be scaled
	 * @param width    width to be scaled by
	 * @param height   height to be scaled by
	 * @return the original image scaled to the parameters
	 */
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, height, width, null);

		return scaledImage;
	}
}
