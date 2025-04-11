public class SectorMap {
    final static int MAP_WIDTH = 5;
    final static int MAP_HEIGHT = 5;
    private static Zone[][] map = new Zone[MAP_WIDTH][MAP_HEIGHT];
    static void init() {
        // Initialize the map
        for (int i = 0; i < MAP_WIDTH; i++) {
            for (int j = 0; j < MAP_HEIGHT; j++) {
                map[i][j] = new Zone();
                map[i][j].init();
                // If there is already the player or an enemy, remove the obstruction
                for (int k = 0; k < Zone.MAP_HEIGHT; k++) {
                    for (int l = 0; l < Zone.MAP_WIDTH; l++) {
                        if ((Player.getSectorY() == i && Player.getSectorX() == j && Player.getSectorX() == l && Player.getSectorY() == k) || BaseEnemy.isEnemyAtCoords(j, i, l, k) != null) {
                            map[i][j].map[k][l] = ' ';
                        }
                    }
                }
            }
        }
    }
    static void printSelf() {
        System.out.println("        0     1     2     3     4");
        System.out.println("     -----------------------------");
        for (int i = 0; i < MAP_HEIGHT; i++) {
            System.out.print(i +" |  ");
            for (int j = 0; j < MAP_HEIGHT; j++) {
                if (Player.getSectorX() != j || Player.getSectorY() != i) {
                    String str = "%d %d";
                    System.out.print("[" + String.format(str, Base.numberOfBasesInSector(j, i), BaseEnemy.numberOfEnemiesAtCoords(j, i)) + "] ");
                } else {
                    System.out.print("[ P ] ");
                }
            }
            System.out.println();
        }
    }
    static Zone[][] getMap() {
        return map;
    }
}
