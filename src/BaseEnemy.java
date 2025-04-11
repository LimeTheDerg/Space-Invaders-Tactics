import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class BaseEnemy {
    int sectorX;
    int sectorY;
    int zoneX;
    int zoneY;
    int damage;
    int hull;
    int shield;
    int cls;
    // Higher the class, more health and damage, but fewer ships
    static final int NUMBER_OF_CLASS_I_ENEMIES = 15;
    static final int NUMBER_OF_CLASS_II_ENEMIES = 10;
    static final int NUMBER_OF_CLASS_III_ENEMIES = 5;
    static LinkedList<BaseEnemy> enemies = new LinkedList<>();
    static void init() {
        Random rand = new Random();
        // Spawn in the ships
        for (int i = 0; i < NUMBER_OF_CLASS_I_ENEMIES; i++) {
            int secX = rand.nextInt(0, SectorMap.MAP_WIDTH);
            int secY = rand.nextInt(0, SectorMap.MAP_HEIGHT);
            int zX = rand.nextInt(0, Zone.MAP_WIDTH);
            int zY = rand.nextInt(0, Zone.MAP_HEIGHT);
            enemies.addLast(new ClassIEnemy(secX, secY, zX, zY));
        }
        for (int i = 0; i < NUMBER_OF_CLASS_II_ENEMIES; i++) {
            int secX = rand.nextInt(0, SectorMap.MAP_WIDTH);
            int secY = rand.nextInt(0, SectorMap.MAP_HEIGHT);
            int zX = rand.nextInt(0, Zone.MAP_WIDTH);
            int zY = rand.nextInt(0, Zone.MAP_HEIGHT);
            enemies.addLast(new ClassIIEnemy(secX, secY, zX, zY));
        }
        for (int i = 0; i < NUMBER_OF_CLASS_III_ENEMIES; i++) {
            int secX = rand.nextInt(0, SectorMap.MAP_WIDTH);
            int secY = rand.nextInt(0, SectorMap.MAP_HEIGHT);
            int zX = rand.nextInt(0, Zone.MAP_WIDTH);
            int zY = rand.nextInt(0, Zone.MAP_HEIGHT);
            enemies.addLast(new ClassIIIEnemy(secX, secY, zX, zY));
        }
    }
    static void playEnemyTurn() {
        ArrayList<BaseEnemy> enemiesArr = new ArrayList<>();
        for (BaseEnemy enemy : enemies) {
            if (enemy.sectorX == Player.getSectorX() && enemy.sectorY == Player.getSectorY()) {
                Laser laser = new Laser(Player.getSectorX(), Player.getSectorY(), Player.getZoneX(), Player.getZoneY(), enemy.damage, false, enemy.zoneX, enemy.zoneY);
                laser.fire();
            }
        }
    }
    // Check if an enemy can be found at given coordinates
    static BaseEnemy isEnemyAtCoords(int sX, int sY, int zX, int zY) {
        for (BaseEnemy enemy : enemies) {
            if (enemy.sectorX == sX && enemy.sectorY == sY && enemy.zoneX == zX && enemy.zoneY == zY) {
                return enemy;
            }
        }
        return null;
    }
    // Check how many enemies can be found in given sector
    static int numberOfEnemiesAtCoords(int sX, int sY) {
        int count = 0;
        for (BaseEnemy enemy : enemies) {
            if (enemy.sectorX == sX && enemy.sectorY == sY) {
                count++;
            }
        }
        return count;
    }
    void hitByLaser(int damage) {}
    void hitByMissle() {}
}

class ClassIEnemy extends BaseEnemy {
    ClassIEnemy(int secX, int secY, int zX, int zY) {
        sectorX=secX;
        sectorY=secY;
        zoneX=zX;
        zoneY=zY;
        shield = 50;
        hull = 100;
        damage = 10;
        cls = 1;
    }
    @Override
    void hitByLaser(int damage) {
        System.out.println("LASER CONNECTED: ENEMY HIT");
        if (damage*2 >= shield) {
            damage -= shield/2;
            shield = 0;
            hull -= damage;
        }
        else {
            shield -= damage*2;
        }
        if (hull <= 0) {
            BaseEnemy.enemies.remove(this);
            System.out.println("ENEMY DESTROYED");
        }
    }
    @Override
    void hitByMissle() {
        int damage = 100;
        System.out.println("MISSLE CONNECTED: ENEMY HIT");
        if (damage >= shield) {
            damage -= shield;
            shield = 0;
            hull -= damage;
        }
        else {
            shield -= damage;
        }
        if (hull <= 0) {
            BaseEnemy.enemies.remove(this);
            System.out.println("ENEMY DESTROYED");
        }
    }
}
class ClassIIEnemy extends BaseEnemy {
    ClassIIEnemy(int secX, int secY, int zX, int zY) {
        sectorX=secX;
        sectorY=secY;
        zoneX=zX;
        zoneY=zY;
        shield = 80;
        hull = 150;
        damage = 15;
        cls = 2;
    }
    @Override
    void hitByLaser(int damage) {
        System.out.println("LASER CONNECTED: ENEMY HIT");
        if (damage*2 >= shield) {
            damage -= shield/2;
            shield = 0;
            hull -= damage;
        }
        else {
            shield -= damage*2;
        }
        if (hull <= 0) {
            BaseEnemy.enemies.remove(this);
            System.out.println("ENEMY DESTROYED");
        }
    }
    void hitByMissle() {
        int damage = 100;
        System.out.println("MISSLE CONNECTED: ENEMY HIT");
        if (damage >= shield) {
            damage -= shield;
            shield = 0;
            hull -= damage;
        }
        else {
            shield -= damage;
        }
        if (hull <= 0) {
            BaseEnemy.enemies.remove(this);
            System.out.println("ENEMY DESTROYED");
        }
    }
}
class ClassIIIEnemy extends BaseEnemy {
    ClassIIIEnemy(int secX, int secY, int zX, int zY) {
        sectorX=secX;
        sectorY=secY;
        zoneX=zX;
        zoneY=zY;
        shield = 100;
        hull = 200;
        damage = 20;
        cls = 3;
    }
    @Override
    void hitByLaser(int damage) {
        System.out.println("LASER CONNECTED: ENEMY HIT");
        if (damage*2 >= shield) {
            damage -= shield/2;
            shield = 0;
            hull -= damage;
        }
        else {
            shield -= damage*2;
        }
        if (hull <= 0) {
            BaseEnemy.enemies.remove(this);
            System.out.println("ENEMY DESTROYED");
        }
    }
    void hitByMissle() {
        int damage = 100;
        System.out.println("MISSLE CONNECTED: ENEMY HIT");
        if (damage >= shield) {
            damage -= shield;
            shield = 0;
            hull -= damage;
        }
        else {
            shield -= damage;
        }
        if (hull <= 0) {
            BaseEnemy.enemies.remove(this);
            System.out.println("ENEMY DESTROYED");
        }
    }
}
