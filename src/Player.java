import java.util.Random;

public class Player {
    // Great wall of variables
    static Random rand = new Random();
    private static int zoneX = rand.nextInt(0, 10);
    private static int zoneY = rand.nextInt(0, 10);
    private static int sectorX = rand.nextInt(0, 5);
    private static int sectorY = rand.nextInt(0, 5);
    private static int energy = 750;
    private static int hull = 100;
    private static int shield = 100;
    private static int missileCount = 7;
    // Getter and setter methods
    public static int getEnergy() {return energy;}
    public static void setEnergy(int energy) {Player.energy = energy;}
    public static int getShield() {return shield;}
    public static int getHull() {return hull;}
    public static int getmissileCount() {return missileCount;}
    public static void setmissileCount(int missileCount) {Player.missileCount = missileCount;}
    static int getZoneX() {return zoneX;}
    static int getZoneY() {return zoneY;}
    static int getSectorX() {return sectorX;}
    static int getSectorY() {return sectorY;}
    static void setZoneX(int newX) {zoneX = newX;}
    static void setZoneY(int newY) {zoneY = newY;}
    static void setSectorX(int newX) {sectorX = newX;}
    static void setSectorY(int newY) {sectorY = newY;}
    public static void setHull(int hull) {Player.hull = hull;}
    public static void setShield(int shield) {Player.shield = shield;}
    static void hit(int damage) {
        System.out.println("CAUTON! SHIP DAMAGED! ENEMY ATTACK CONNECTED!");
        shield -= damage;
        if(shield < 0) {
            hull += shield;
            shield = 0;
        }
        if (hull <= 0) {
            System.out.println("**********GAME OVER**********");
            System.out.println("Your ship was shot to oblivion by the invaders, \nthe human race shall soon fall to these attackers. \n**********Mission Failed**********");
            System.out.println("There were " + BaseEnemy.enemies.size() + " enemies left.");
            System.exit(0);
        }
    }
    static void endGameDueToEnergy() {
        if (energy <= 0) {
            System.out.println("**********GAME OVER**********");
            System.out.println("Your ship has run out of energy and drifts endlessly through space, \nthe human race shall soon fall to these attackers. \n**********Mission Failed**********");
            System.out.println("There were " + BaseEnemy.enemies.size() + " enemies left.");
            System.exit(0);
        }
    }
    static void win() {
        if (BaseEnemy.enemies.isEmpty()) {
            System.out.println("**********VICTORY**********");
            System.out.println("Humanity has emerged victorious \nEnemy presence has been completely wiped from the galaxy \nMankind has been saved, thanks to you \nGood job commander, humanity is safe... for now... \n**********MISSION ACCOMPLISHED**********");
            System.exit(0);
        }
    }
}
