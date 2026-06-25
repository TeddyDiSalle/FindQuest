package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    File[] files;
    boolean collision = false;

    public TileManager(GamePanel gp, String mapLocation, String tileFile,
            boolean collision) {
        this.gp = gp;

        this.collision = collision;

        this.files = new File(tileFile).listFiles();

        tile = new Tile[files.length];
        mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];

        loadMap(mapLocation);

        getTileImages();
    }

    public TileManager(GamePanel gp, String mapLocation, String tileFile) {
        this.gp = gp;

        this.files = new File(tileFile).listFiles();

        tile = new Tile[files.length];
        mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];

        loadMap(mapLocation);

        getTileImages();
    }

    /**
     * .
     */
    public void getTileImages() {
        UtilityTool uTool = new UtilityTool();
        try {
            for (int i = 0; i < tile.length; i++) {
                tile[i] = new Tile();

                /*
                 * Pre scaling the image so graphics doesn't have to later, y' know?
                 * Performance 'n 'em
                 */
                tile[i].image = uTool.scaleImage(ImageIO.read(files[i]), gp.getTileSize(),
                        gp.getTileSize());
                tile[i].collision = this.collision;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String map) {

        int col = 0;
        int row = 0;
        final int maxWorldCol = gp.getMaxWorldCol();
        final int maxWorldRow = gp.getMaxWorldRow();
        BufferedReader br = null;

        try {
            InputStream is = getClass().getResourceAsStream(map);
            br = new BufferedReader(new InputStreamReader(is));

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        String line;
        while (col < maxWorldCol && row < maxWorldRow) {
            String[] numbers = null;

            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            numbers = line.split(" ");

            while (col < maxWorldCol) {
                int num;
                try {
                    num = Integer.parseInt(numbers[col]);
                } catch (NumberFormatException e) {
                    // cannot parse the int
                    num = -1;
                }
                mapTileNum[col][row] = num;
                col++;

            }
            if (col == maxWorldCol) {
                col = 0;
                row++;
            }
        }
        try {
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.getMaxWorldCol() && worldRow < gp.getMaxWorldRow()) {

            int tileNum = mapTileNum[worldCol][worldRow];

            if (tileNum != -1) {
                int worldX = worldCol * gp.getTileSize();
                int worldY = worldRow * gp.getTileSize();
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
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }
            }
            worldCol++;

            if (worldCol == gp.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

}
