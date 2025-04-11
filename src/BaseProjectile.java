public class BaseProjectile {
    static int[] findObjectAlongPath(int x1, int y1, int xI, int yI) {
        int x2 = x1 + xI;
        int y2 = y1 + yI;
        double rise;
        double run;
        double x = x1;
        double y = y1;
        int xRnd = (int) Math.round(x);
        int yRnd = (int) Math.round(y);
        int len; // Determine the distance between two points
        int playerSectorX = Player.getSectorX();
        int playerSectorY = Player.getSectorY();
        // Determine whether to simply rise or run
        // ensure it is negative
        if (Math.abs(xI) > Math.abs(yI)) {
            rise = (double) yI /xI;
            run = 1;
            len = Math.abs(xI);
        } else {
            rise = 1;
            run = (double) xI /yI;
            len = Math.abs(yI);
        }
        // Ensure that the numbers are in their proper signs
        if (xI < 0) {
            run = Math.abs(run) * -1;
        } else run = Math.abs(run);
        if (yI < 0) {
            rise = Math.abs(rise) * -1;
        } else rise = Math.abs(rise);
        // This algorithm move the object along a line where all the points are integers, the line will not be very straight. It will move the object incrementally len times, and if an obstruction is found along the way, return
        // the coordinates of the obstruction
        for (int i = 0; i < len; i++) {
            x += run;
            y += rise;
            xRnd = (int) Math.round(x);
            yRnd = (int) Math.round(y);
            // Check if the player leaves the current Sector
            if (xRnd >= 10) {
                playerSectorX++;
                x = 0;
                xRnd = 0;
            }
            if (xRnd <= -1) {
                playerSectorX--;
                x = 9;
                xRnd = 9;
            }
            if (yRnd >= 10) {
                playerSectorY++;
                y = 0;
                yRnd = 0;
            }
            if (yRnd <= -1) {
                playerSectorY--;
                y = 9;
                yRnd = 9;
            }
            if (playerSectorX >= 5 || playerSectorY >= 5 || playerSectorX < 0 || playerSectorY < 0) {
                return new int[]{-1, -1};
            }
            Zone playerSector = SectorMap.getMap()[playerSectorY][playerSectorX]; // Finds the sector the player is currently in
            char contentsOfZone = playerSector.getMap()[yRnd][xRnd]; // Finds the contents of the zone the projectile would be in, rounding x and y will generate a line where all the points are integers
            BaseEnemy enemy = BaseEnemy.isEnemyAtCoords(playerSectorX, playerSectorY, xRnd, yRnd);
            if (enemy != null) {
                return new int[]{xRnd, yRnd};
            }
            if (contentsOfZone != ' ') {
                return new int[]{xRnd, yRnd};
            }
            if (Base.isBaseAtCoords(playerSectorX, playerSectorY, xRnd, yRnd) != null) {
                return new int[]{xRnd, yRnd};
            }
        }
        return null;
    }
    static int[] findObjectAlongPath(int x1, int y1, int x2, int y2, int len) {
        double rise;
        double run;
        int xI = x2 - x1;
        int yI = y2 - y1;
        double x = x1;
        double y = y1;
        int xRnd = (int) Math.round(x);
        int yRnd = (int) Math.round(y);
        int playerSectorX = Player.getSectorX();
        int playerSectorY = Player.getSectorY();

        // Determine whether to simply rise or run
        // ensure it is negative
        if (Math.abs(xI) > Math.abs(yI)) {
            rise = (double) yI /xI;
            run = 1;
        } else {
            rise = 1;
            run = (double) xI /yI;
        }
        // Ensure that the numbers are in their proper signs
        if (xI < 0) {
            run = Math.abs(run) * -1;
        } else run = Math.abs(run);
        if (yI < 0) {
            rise = Math.abs(rise) * -1;
        } else rise = Math.abs(rise);
        // This algorithm move the object along a line where all the points are integers, the line will not be very straight. It will move the object incrementally len times, and if an obstruction is found along the way, return
        // the coordinates of the obstruction
        for (int i = 0; i < len; i++) {
            x += run;
            y += rise;
            xRnd = (int) Math.round(x);
            yRnd = (int) Math.round(y);
            // Check if the player leaves the current Sector
            if (xRnd >= 10) {
                playerSectorX++;
                x = 0;
                xRnd = 0;
            }
            if (xRnd <= -1) {
                playerSectorX--;
                x = 9;
                xRnd = 9;
            }
            if (yRnd >= 10) {
                playerSectorY++;
                y = 0;
                yRnd = 0;
            }
            if (yRnd <= -1) {
                playerSectorY--;
                y = 9;
                yRnd = 9;
            }
            if (playerSectorX >= 5 || playerSectorY >= 5 || playerSectorX < 0 || playerSectorY < 0) {
                return new int[]{-1, -1};
            }
            Zone playerSector = SectorMap.getMap()[playerSectorY][playerSectorX]; // Finds the sector the player is currently in
            char contentsOfZone = playerSector.getMap()[yRnd][xRnd]; // Finds the contents of the zone the projectile would be in, rounding x and y will generate a line where all the points are integers
            BaseEnemy enemy = BaseEnemy.isEnemyAtCoords(playerSectorX, playerSectorY, xRnd, yRnd);
            if (enemy != null) {
                return new int[]{xRnd, yRnd};
            }
            if (contentsOfZone != ' ') {
                return new int[]{xRnd, yRnd};
            }
            if (Base.isBaseAtCoords(playerSectorX, playerSectorY, xRnd, yRnd) != null) {
                return new int[]{xRnd, yRnd};
            }
            if (Player.getSectorX() == playerSectorX && Player.getSectorY() == playerSectorY && Player.getZoneX() == xRnd && Player.getZoneY() == yRnd) {
                return new int[]{xRnd, yRnd};
            }
        }
        return null;
    }
    void fire() {};
}

class Laser extends BaseProjectile {
    int fromX, fromY;
    int sectorX, sectorY, zoneX, zoneY, damage;
    boolean fromPlayer;
    Laser(int sX, int sY, int zX, int zY, int dmg, boolean player) {
        fromPlayer = player;
        sectorX=sX;
        sectorY=sY;
        zoneX=zX;
        zoneY=zY;
        damage=dmg;
    }
    Laser(int sX, int sY, int zX, int zY, int dmg, boolean player, int fromX, int fromY) {
        fromPlayer = player;
        sectorX=sX;
        sectorY=sY;
        zoneX=zX;
        zoneY=zY;
        damage=dmg;
        this.fromX = fromX;
        this.fromY = fromY;
    }
    @Override
    void fire() {
        // Fire projectile
        if (fromPlayer) { // Check if the projectile came from the player
            // First check if an obstruction is found, projectiles do not persist through sectors
            int coords[] = BaseProjectile.findObjectAlongPath(Player.getZoneX(), Player.getZoneY(), zoneX, zoneY, 5);
            if (coords != null) {
                BaseEnemy enemy = BaseEnemy.isEnemyAtCoords(Player.getSectorX(), Player.getSectorY(), coords[0], coords[1]);
                if (enemy != null) enemy.hitByLaser(damage);
                return;
            }
            // Contact with the enemy
            BaseEnemy enemy = BaseEnemy.isEnemyAtCoords(sectorX, sectorY, zoneX, zoneY);
            if (enemy != null) {
                enemy.hitByLaser(damage); // Deal damage
            }
        }
        else {
            int coords[] = BaseProjectile.findObjectAlongPath(fromX, fromY, zoneX, zoneY, 10);
            if (coords != null) {
                BaseEnemy enemy = BaseEnemy.isEnemyAtCoords(Player.getSectorX(), Player.getSectorY(), fromX, fromY);
                if (enemy != null && coords[0] == Player.getZoneX() && coords[1] == Player.getZoneY() && sectorX == Player.getSectorX() && sectorY == Player.getSectorY())
                    Player.hit(enemy.damage);
            }
        }
    }
}

class Missle extends BaseProjectile {
    int sectorX, sectorY, zoneX, zoneY, damage;
    Missle(int sX, int sY, int zX, int zY) {
        sectorX=sX;
        sectorY=sY;
        zoneX=zX;
        zoneY=zY;
        damage=100;
    }
    @Override
    void fire() {
        // Fire projectile
        // First check if an obstruction is found, projectiles do not persist through sectors
        int[] coords = BaseProjectile.findObjectAlongPath(Player.getZoneX(), Player.getZoneY(), zoneX, zoneY, 10);
        if (coords != null) {
            BaseEnemy enemy = BaseEnemy.isEnemyAtCoords(Player.getSectorX(), Player.getSectorY(), coords[0], coords[1]);
            if (enemy != null) enemy.hitByMissle();
            return;
        }
        // Contact with the enemy
        BaseEnemy enemy = BaseEnemy.isEnemyAtCoords(sectorX, sectorY, zoneX, zoneY);
        if (enemy != null) {
            enemy.hitByMissle(); // Deal damage
        }
    }
}
