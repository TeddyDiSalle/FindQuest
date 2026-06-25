package main;

import entity.Entity;

public class CollisionChecker {

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	/**
	 * Can the entity move through this tile?.
	 *
	 * @param entity
	 */
	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
		int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x
				+ entity.getSolidArea().width;
		int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
		int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y
				+ entity.getSolidArea().height;

		int entityLeftCol = entityLeftWorldX / gp.getTileSize();
		int entityRightCol = entityRightWorldX / gp.getTileSize();
		int entityTopRow = entityTopWorldY / gp.getTileSize();
		int entityBottomRow = entityBottomWorldY / gp.getTileSize();

		int tileNum1, tileNum2;

		switch (entity.getDirection()) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.getTileSize();
			tileNum1 = gp.getCollisionMap().mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.getCollisionMap().mapTileNum[entityRightCol][entityTopRow];
			if ((tileNum1 != -1 && gp.getCollisionMap().tile[tileNum1].collision)
					|| (tileNum2 != -1
							&& gp.getCollisionMap().tile[tileNum2].collision)) {
				entity.setCollisionOn(true);
			} else {
				entity.setCollisionOn(false);
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.getTileSize();
			tileNum1 = gp.getCollisionMap().mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.getCollisionMap().mapTileNum[entityRightCol][entityBottomRow];
			if ((tileNum1 != -1 && gp.getCollisionMap().tile[tileNum1].collision)
					|| (tileNum2 != -1
							&& gp.getCollisionMap().tile[tileNum2].collision)) {
				entity.setCollisionOn(true);
			} else {
				entity.setCollisionOn(false);
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.getTileSize();
			tileNum1 = gp.getCollisionMap().mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.getCollisionMap().mapTileNum[entityLeftCol][entityBottomRow];
			if ((tileNum1 != -1 && gp.getCollisionMap().tile[tileNum1].collision)
					|| (tileNum2 != -1
							&& gp.getCollisionMap().tile[tileNum2].collision)) {
				entity.setCollisionOn(true);
			} else {
				entity.setCollisionOn(false);
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.getTileSize();
			tileNum1 = gp.getCollisionMap().mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.getCollisionMap().mapTileNum[entityRightCol][entityBottomRow];
			if ((tileNum1 != -1 && gp.getCollisionMap().tile[tileNum1].collision)
					|| (tileNum2 != -1
							&& gp.getCollisionMap().tile[tileNum2].collision)) {
				entity.setCollisionOn(true);
			} else {
				entity.setCollisionOn(false);
			}
			break;
		default:
			entity.setCollisionOn(false);
			break;

		}
	}

	public int checkObject(Entity entity, boolean player) {
		int index = -1;

		for (int i = 0; i < gp.getObjectListLength(); i++) {
			if (gp.isObjectInList(i)) {

				// Get entity's solid area position
				entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
				entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

				// Get the object's solid area position
				gp.getObj(i).solidArea.x = gp.getObj(i).getWorldX()
						+ gp.getObj(i).solidArea.x;
				gp.getObj(i).solidArea.y = gp.getObj(i).getWorldY()
						+ gp.getObj(i).solidArea.y;

				switch (entity.getDirection()) {
				case "up":
					entity.getSolidArea().y -= entity.getSpeed();
					if (entity.getSolidArea().intersects(gp.getObj(i).solidArea)) {
						if (gp.getObj(i).collision) {
							entity.setCollisionOn(true);
						}
						if (player) {
							index = i;
						}
					}
					break;
				case "down":
					entity.getSolidArea().y += 2 * entity.getSpeed();
					if (entity.getSolidArea().intersects(gp.getObj(i).solidArea)) {
						if (gp.getObj(i).collision) {
							entity.setCollisionOn(true);
						}
						if (player) {
							index = i;
						}

					}
					break;
				case "left":
					entity.getSolidArea().x -= entity.getSpeed();
					if (entity.getSolidArea().intersects(gp.getObj(i).solidArea)) {
						if (gp.getObj(i).collision) {
							entity.setCollisionOn(true);
						}
						if (player) {
							index = i;
						}

					}
					break;
				case "right":
					entity.getSolidArea().x += 2 * entity.getSpeed();
					if (entity.getSolidArea().intersects(gp.getObj(i).solidArea)) {
						if (gp.getObj(i).collision) {
							entity.setCollisionOn(true);
						}
						if (player) {
							index = i;

						}
					}
					break;
				default:
					System.out.println("what");
					break;

				}

				entity.getSolidArea().x = entity.getSolidAreaDefaultX();
				entity.getSolidArea().y = entity.getSolidAreaDefaultY();
				gp.getObj(i).solidArea.x = gp.getObj(i).solidAreaDefaultX;
				gp.getObj(i).solidArea.y = gp.getObj(i).solidAreaDefaultY;
			}
		}

		return index;
	}
}
