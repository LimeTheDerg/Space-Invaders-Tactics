import java.util.Random;

public class Zone {
    static int MAP_WIDTH = 10;
    static int MAP_HEIGHT = 10;
    char[][] map = new char[MAP_WIDTH][MAP_HEIGHT];
    void init() {
        Random rand = new Random();
        // Initialize the zone
        for (int i = 0; i < MAP_WIDTH; i++) {
            for (int j = 0; j < MAP_HEIGHT; j++) {
                // Create an obstruction game element, one in 10 chance that a zone will
                // be obstructed
                int randGen = rand.nextInt(1, 11);
                if (randGen == 10) {
                    map[i][j] = '*';
                }
                else {
                    map[i][j] = ' ';
                }
            }
        }
    }
    void printSelf() {
        System.out.println("     0  1  2  3  4  5  6  7  8  9");
        System.out.println("     ----------------------------");
        for (int i = 0; i < MAP_WIDTH; i++) {
            System.out.print(i + " |  ");
            for (int j = 0; j < MAP_HEIGHT; j++) {
                BaseEnemy enemy = BaseEnemy.isEnemyAtCoords(Player.getSectorX(), Player.getSectorY(), j, i);
                if (i == Player.getZoneY() && j == Player.getZoneX())
                    System.out.print('P'+"  ");
                else if (enemy != null) {
                    if (enemy.cls == 1) {
                        System.out.print('1'+"  ");
                    }
                    if (enemy.cls == 2) {
                        System.out.print('2'+"  ");
                    }
                    if (enemy.cls == 3) {
                        System.out.print('3'+"  ");
                    }
                }
                else if (Base.isBaseAtCoords(Player.getSectorX(), Player.getSectorY(), j, i) != null) {
                    System.out.print('B'+"  ");
                }
                else System.out.print(map[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.print("You are in sector: ");
        System.out.println(Player.getSectorX() + " " + Player.getSectorY());
    }
    public char[][] getMap() {
        return map;
    }
}
