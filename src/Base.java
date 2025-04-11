import java.util.ArrayList;
import java.util.Random;

public class Base {
    Base(int sectorX, int sectorY, int zoneX, int zoneY) {
        this.sectorX=sectorX;
        this.sectorY=sectorY;
        this.zoneX=zoneX;
        this.zoneY=zoneY;
    }
    static int BASES_LIMIT = 10;
    int sectorX;
    int sectorY;
    int zoneX;
    int zoneY;
    static ArrayList<Base> bases = new ArrayList<>();
    static void init() {
        Random rand = new Random();
        for (int i = 0; i < BASES_LIMIT; i++) {
            // Put base at random location
            bases.add(new Base(rand.nextInt(0, Zone.MAP_WIDTH), rand.nextInt(0, Zone.MAP_HEIGHT), rand.nextInt(0, SectorMap.MAP_WIDTH), rand.nextInt(0, SectorMap.MAP_HEIGHT)));
        }
    }
    static Base isBaseAtCoords(int sX, int sY, int zX, int zY) {
        for (Base base : bases) {
            if (base.sectorX == sX && base.sectorY == sY && base.zoneX == zX && base.zoneY == zY) {
                return base;
            }
        }
        return null;
    }
    static int numberOfBasesInSector(int sX, int sY) {
        int count = 0;
        for (Base base : bases) {
            if (base.sectorX == sX && base.sectorY == sY) {
                count++;
            }
        }
        return count;
    }
    static boolean isPlayerNextToBase() {
        if (isBaseAtCoords(Player.getSectorX(), Player.getSectorY(), Player.getZoneX() +1, Player.getZoneY()) != null) return true;
        if (isBaseAtCoords(Player.getSectorX(), Player.getSectorY(), Player.getZoneX(), Player.getZoneY()+1) != null) return true;
        if (isBaseAtCoords(Player.getSectorX(), Player.getSectorY(), Player.getZoneX() -1, Player.getZoneY()) != null) return true;
        if (isBaseAtCoords(Player.getSectorX(), Player.getSectorY(), Player.getZoneX() , Player.getZoneY() -1) != null) return true;
        return false;
    }
}