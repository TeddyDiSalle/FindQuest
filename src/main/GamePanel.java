package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

/**
 * Everything that will show up on the screen.
 */
public class GamePanel extends JPanel implements Runnable {

	/**
	 * Screen Settings.
	 */
	private final int ogTileSize = 16, scale = 3;
	// 16 pixels by 16 pixels tile
	// 16 * scale = resolution
	/**
	 * Tile size.
	 */
	private final int tileSize = ogTileSize * scale; // 48x48 pixel tile

	/**
	 * The screen.
	 */
	private final int maxScreenCol = 16, maxScreenRow = 12,
			screenWidth = tileSize * maxScreenCol, screenHeight = tileSize * maxScreenRow;
	// 16 tiles wide
	// 12 tiles tall
	// 768 pixels resolution wide
	// 576 pixels resolution high

	/**
	 * world settings.
	 */
	private final int maxWorldCol = 50, maxWorldRow = 50;

	/**
	 * FPS.
	 */
	private final int FPS = 60;

	/**
	 * The layers of the current map. One for the collision layer, one for behind
	 * the player non collision, one for in front of the player non collision
	 */
	private TileManager[] currentMap = new TileManager[1 + 1 + 1];
	/**
	 * The layers of the main map.
	 */
	private TileManager mainLayer1 = new TileManager(this, "/maps/BegMapCollision","res/tilesExterior", true),
			mainLayer2 = new TileManager(this, "/maps/BegMapLayer2", "res/tilesExterior"),
			mainLayer3 = new TileManager(this, "/maps/BegMapLayer3", "res/tilesExterior");

	/**
	 * The layers of the inside map.
	 */
	private TileManager interLayer1 = new TileManager(this, "/maps/interMapCollision", "res/tilesInterior", true),
			interLayer2 = new TileManager(this, "/maps/interMapLayer2", "res/tilesInterior", false);

	/**
	 * The name of the map that game panel is currently on.
	 */
	private String mapName = "main";

	/**
	 * Checks collision for all tiles and objects and entities and just everything.
	 */
	private final CollisionChecker cChecker = new CollisionChecker(this);

	/**
	 * The game's placer of objects.
	 */
	private final AssetPlacer objP = new AssetPlacer(this);

	/**
	 * Different sound channels for music and sound effects.
	 */
	private final Sound music = new Sound(), se = new Sound();

	/**
	 * Our little warrior who decides what to do when keys are pressed.
	 */
	private final KeyHandler keyH = new KeyHandler();

	/**
	 * The UI on the very top layer of the screen for information. Not in the gaming
	 * world.
	 */
	private final UI ui = new UI(this);

	/**
	 * Handle the events of the game.
	 */
	private final EventHandler eHandler = new EventHandler(this);

	/**
	 * The thread the game is running on.
	 */
	private Thread gameThread;

	/**
	 * The main character of this story.
	 */
	private final Player mainCharacter = new Player(this, keyH, objP);

	/**
	 * All the entities on screen.
	 */
	private Entity[] npc = new Entity[10];

	/**
	 * All the objects on screen.
	 */
	private SuperObject[] obj = new SuperObject[10];

	/**
	 * Object list, we can only render 10 objects not just have 10 in the whole game
	 * Number of objects effect performance
	 */

	/**
	 * Default constructor.
	 */
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		currentMap[0] = mainLayer1;
		currentMap[1] = mainLayer2;
		currentMap[2] = mainLayer3;

	}

	/**
	 * How the game starts.
	 */
	public void setUpGame() {
		objP.setObj(mapName);
		playMusic("IceVillage");
	}

	/**
	 * Starting the game loop.
	 */
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	/**
	 * How we run the game.
	 */
	public void run() {
		final int oneBillionNanoSeconds = 1000000000; // one second
		double drawInterval = oneBillionNanoSeconds / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentNanoTime;

		while (gameThread != null) {
			// Limit our beefy computers to only update 60 times per second
			currentNanoTime = System.nanoTime();

			delta += (currentNanoTime - lastTime) / drawInterval;
			lastTime = currentNanoTime;

			// updating the game and drawing the screen
			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}

	/**
	 * Updating the entities and tiles of the game.
	 */
	public void update() {
		mainCharacter.update();
	}

	/**
	 * paint all the tiles and entities.
	 *
	 * @param g The graphics twin
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		// Tiles
		switch (mapName) {
		case "main":
			currentMap[0] = mainLayer1;
			currentMap[1] = mainLayer2;
			currentMap[2] = mainLayer3;
			break;
		case "inter":
			currentMap[0] = interLayer1;
			currentMap[1] = interLayer2;
			currentMap[2] = null;
			break;
		default:
			break;
		}
		currentMap[0].draw(g2);
		currentMap[1].draw(g2);

		// Object
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		// Player
		mainCharacter.draw(g2);

		if (currentMap[2] != null) {
			currentMap[2].draw(g2);
		}

		ui.draw(g2);
		g2.dispose();
	}

	/**
	 * @return the main character
	 */
	public Player getMainCharacter() {
		return mainCharacter;
	}

	/**
	 * @param index The spot where the object is
	 * @return The object at that index
	 */
	public SuperObject getObj(int index) {
		return obj[index];
	}

	/**
	 * Resets the spot at index to a default value.
	 *
	 * @param index The spot where we will reset
	 * @return The object that used to occupy that index
	 */
	public SuperObject resetObj(int index) {
		return setObj(index, null);
	}

	/**
	 * Checks if an object at index is not a default value.
	 *
	 * @param index The part in the list to check.
	 * @return Whether object at the spot is there.
	 */
	public boolean isObjectInList(int index) {
		return obj[index] != null;
	}

	/**
	 * @param index  The spot where the new object is going
	 * @param newObj The object to replace the object
	 * @return oldObj The object that used to occupy that index
	 */
	public SuperObject setObj(int index, SuperObject newObj) {
		SuperObject oldObj = obj[index];
		this.obj[index] = newObj;

		return oldObj;
	}

	/**
	 * @return The length of our array of objects
	 */
	public int getObjectListLength() {
		return obj.length;
	}

	/**
	 * @param index The spot where the entity is
	 * @return The NPC at that index
	 */
	public Entity getNPC(int index) {
		return npc[index];
	}

	/**
	 * Resets the spot at index to a default value.
	 *
	 * @param index The spot where we will reset
	 * @return The entity that used to occupy that index
	 */
	public Entity resetNPC(int index) {
		return setNPC(index, null);
	}

	/**
	 * @param index  The spot where the new entity is going
	 * @param newEnt The entity to replace the object
	 * @return oldEnt The entity that used to occupy that index
	 */
	public Entity setNPC(int index, Entity newEnt) {
		Entity oldEnt = npc[index];
		npc[index] = newEnt;

		return oldEnt;
	}

	/**
	 * @return The length of our array of NPCs
	 */
	public int getNPCListLength() {
		return npc.length;
	}

	/**
	 * @return the screen's width
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * @return the screen's height
	 */
	public int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * @return worlds max columns
	 */
	public int getMaxWorldCol() {
		return maxWorldCol;
	}

	/**
	 * @return worlds max columns
	 */
	public int getMaxWorldRow() {
		return maxWorldRow;
	}

	/**
	 * @return the tile size
	 */
	public int getTileSize() {
		return tileSize;
	}

	/**
	 * @return name of current map
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * Sets the map name.
	 *
	 * @param mapName The new map name
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	/**
	 * Grabs the map of the map we are currently on and returns the first map since
	 * we only have one collision map per setup for the maps and we set it to the
	 * first one.
	 *
	 * @return The collision map of what is on screen
	 */
	public TileManager getCollisionMap() {
		return currentMap[0];
	}

	/**
	 * @return the collision checker.
	 */
	public CollisionChecker getCollisionChecker() {
		return cChecker;
	}

	/**
	 * @return the game's UI.
	 */
	public UI getGameUI() {
		return ui;
	}

	/**
	 * @return the eHandler
	 */
	public EventHandler getEventHandler() {
		return eHandler;
	}

	/**
	 * Plays our music.
	 *
	 * @param songName name of the desired song, name of the file.
	 */
	private void playMusic(String songName) {
		music.setFile(songName);
		music.play();
		music.loop();
	}

	/**
	 * Stops the music.
	 */
	public void stopMusic() {
		music.stop();
	}

	/**
	 * @param soundName The sound to be played
	 */
	public void playSoundEffect(String soundName) {
		se.setFile(soundName);
		se.play();
	}
}
