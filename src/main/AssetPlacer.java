package main;

import object.BarrelObject;
import object.ChestObject;
import object.DoorObject;
import object.OldManObject;
import object.PackageObject;

/**
 * Places the objects of the game on the maps.
 */
public class AssetPlacer {
    /**
     * The game panel.
     */
    private final GamePanel gp;

    /**
     * Holds if the objects are picked up between maps.
     */
    private boolean[] pickedUp;

    /**
     * The size of the array.
     */
    private final int pickedUpArrSize = 6;

    /**
     * Handles all the objects placing.
     *
     * @param gp The Game Panel
     */
    public AssetPlacer(GamePanel gp) {
        this.gp = gp;
        pickedUp = new boolean[pickedUpArrSize];
    }

    /**
     * Sets the object for every map.
     *
     * @param map Which map to set up
     */
    public void setObj(String map) {
        for (int i = 0; i < gp.getObjectListLength(); i++) {
            gp.resetObj(i);
        }

        switch (map) {
        case "main":
            setMainDoors();
            setObjectMain();
            break;
        case "inter":
            setInterDoors();
            setObjectInter();
            break;
        default:
            break;
        }
    }

    /**
     * Setup the doors on the main map.
     */
    public void setMainDoors() {
        gp.setObj(0, new DoorObject(gp));

        gp.getObj(0).setWorldX(30 * gp.getTileSize());
        gp.getObj(0).setWorldY(11 * gp.getTileSize());

        gp.setObj(1, new DoorObject(gp));

        gp.getObj(1).setWorldX(22 * gp.getTileSize());
        gp.getObj(1).setWorldY(30 * gp.getTileSize());

        gp.setObj(2, new DoorObject(gp));

        gp.getObj(2).setWorldX(39 * gp.getTileSize());
        gp.getObj(2).setWorldY(36 * gp.getTileSize());
    }

    /**
     * Set the doors on the inside map.
     */
    public void setInterDoors() {
        DoorObject topLeftDoor = new DoorObject(gp);
        final int topLeftDoorIndex = 0;
        final int tLDoorX = 5;
        final int tLDoorY = 4;

        DoorObject topRightDoor = new DoorObject(gp);
        final int topRightDoorIndex = 1;
        final int tRDoorX = 45;
        final int tRDoorY = 4;

        DoorObject bottomLeftDoor = new DoorObject(gp);
        final int bottomLeftDoorIndex = 2;
        final int bLDoorX = 3;
        final int bLDoorY = 49;

        gp.setObj(topLeftDoorIndex, topLeftDoor);
        topLeftDoor.setWorldX(tLDoorX * gp.getTileSize());
        topLeftDoor.setWorldY(tLDoorY * gp.getTileSize());
        topLeftDoor.image = null;

        gp.setObj(topRightDoorIndex, topRightDoor);
        topRightDoor.setWorldX(tRDoorX * gp.getTileSize());
        topRightDoor.setWorldY(tRDoorY * gp.getTileSize());
        topRightDoor.image = null;

        gp.setObj(bottomLeftDoorIndex, bottomLeftDoor);
        bottomLeftDoor.setWorldX(bLDoorX * gp.getTileSize());
        bottomLeftDoor.setWorldY(bLDoorY * gp.getTileSize());
        bottomLeftDoor.image = null;
    }

    /**
     * Specific objects and there places.
     */
    public void setObjectMain() {
        PackageObject topLeftPackage;
        final int topLeftPackagePicked = 3;
        final int tLPackage = 3;
        final int tLPackageX = 9;
        final int tLPackageY = 6;

        final int topMidBarrelPicked = 4;
        final int tMBarrel = 4;
        final int tMBarrelX = 32;
        final int tMBarrelY = 11;

        final int treeChestPicked = 5;
        final int tChest = 5;
        final int tChestX = 35;
        final int tChestY = 41;

        if (!pickedUp[topLeftPackagePicked]) {
            gp.setObj(tLPackage, new PackageObject(gp));

            gp.getObj(tLPackage).setWorldX(tLPackageX * gp.getTileSize());
            gp.getObj(tLPackage).setWorldY(tLPackageY * gp.getTileSize());
        }

        if (!pickedUp[topMidBarrelPicked]) {
            gp.setObj(tMBarrel, new BarrelObject(gp));

            gp.getObj(tMBarrel).setWorldX(tMBarrelX * gp.getTileSize());
            gp.getObj(tMBarrel).setWorldY(tMBarrelY * gp.getTileSize());
        }

        if (!pickedUp[treeChestPicked]) {
            gp.setObj(tChest, new ChestObject(gp));

            gp.getObj(tChest).setWorldX(tChestX * gp.getTileSize());
            gp.getObj(tChest).setWorldY(tChestY * gp.getTileSize());
        }
    }

    /**
     * Specific objects and there places.
     */
    public void setObjectInter() {
        final int topLeftOldManPicked = 0;
        final int tLOldMan = 3;
        final int tLOldManX = 2;
        final int tLOldManY = 1;

        final int topRightPackagePicked = 1;
        final int tRPackage = 4;
        final int tRPackageX = 44;
        final int tRPackageY = 1;

        final int bottomLeftChestPicked = 2;
        final int bLChest = 5;
        final int bLChestX = 3;
        final int bLChestY = 45;

        if (!pickedUp[topLeftOldManPicked]) {
            gp.setObj(tLOldMan, new OldManObject(gp));

            gp.getObj(tLOldMan).setWorldX(tLOldManX * gp.getTileSize());
            gp.getObj(tLOldMan).setWorldY(tLOldManY * gp.getTileSize());
        }

        if (!pickedUp[topRightPackagePicked]) {
            gp.setObj(tRPackage, new PackageObject(gp));

            gp.getObj(tRPackage).setWorldX(tRPackageX * gp.getTileSize());
            gp.getObj(tRPackage).setWorldY(tRPackageY * gp.getTileSize());
        }

        if (!pickedUp[bottomLeftChestPicked]) {
            gp.setObj(bLChest, new ChestObject(gp));

            gp.getObj(bLChest).setWorldX(bLChestX * gp.getTileSize());
            gp.getObj(bLChest).setWorldY(bLChestY * gp.getTileSize());
        }

    }

    /**
     * @param index The spot that represents the object
     * @return If the object at index is picked up
     */
    public boolean isPickedUp(int index) {
        return pickedUp[index];
    }

    /**
     * Set the index to true so we know the object is picked up.
     *
     * @param index The spot
     */
    public void setToPickedUp(int index) {
        pickedUp[index] = true;
    }

}
