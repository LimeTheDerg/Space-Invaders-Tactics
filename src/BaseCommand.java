import java.io.*;

public class BaseCommand {
    //Base command class, any command class is a child of this class
    //Base command is not an actual command
    public static BaseCommand parseCommand(String command) {
        String[] parsedCommand = command.split(" ");
        BaseCommand result = null;
        // Move command was issued
        if (parsedCommand[0].equalsIgnoreCase("move") && parsedCommand.length == 3) {
            // Take command args
            int xInc;
            int yInc;
            // Try catch, in case the command was invalid
            try {
                xInc = Integer.parseInt(parsedCommand[1]);
                yInc = Integer.parseInt(parsedCommand[2]);
            } catch (Exception e) {
                return null;
            }
            result = new MoveCommand(xInc, yInc);
        }
        if (parsedCommand[0].equalsIgnoreCase("show") && parsedCommand.length == 2) {
            // Take command args
            if (parsedCommand[1].equalsIgnoreCase("zone")) {
                result = new ShowZoneCommand();
            }
            else if(parsedCommand[1].equalsIgnoreCase("sector")) {
                result = new ShowSectorCommand();
            }
        }
        if (parsedCommand[0].equalsIgnoreCase("fire") && (parsedCommand.length == 5 || parsedCommand.length == 4)) {
            // Take command args
            int x2;
            int y2;
            int damage = 0;
            try {
                x2 = Integer.parseInt(parsedCommand[2]);
                y2 = Integer.parseInt(parsedCommand[3]);
                if (parsedCommand.length == 5)
                    damage = Integer.parseInt(parsedCommand[4]);
            } catch (Exception e) {
                return null;
            }
            if (parsedCommand[1].equalsIgnoreCase("laser")) {
                result = new FireLaserCommand(x2, y2, damage);
            }
            else if (parsedCommand[1].equalsIgnoreCase("missle")) {
                result =  new FireMissleCommand(x2, y2);
            }
        }
        if (parsedCommand[0].equalsIgnoreCase("status") && parsedCommand.length == 1) {
            result =  new StatusCommand();
        }
        if (parsedCommand[0].equalsIgnoreCase("replenish") && parsedCommand[1].equalsIgnoreCase("shields") && parsedCommand.length == 3) {
            try {
                result = new ReplenishShieldCommand(Integer.parseInt(parsedCommand[2]));
            } catch (Exception e) {
                return null;
            }
        }
        if (parsedCommand[0].equalsIgnoreCase("help") && parsedCommand.length == 1) {
            result = new HelpCommand();
        }
        // Return command to main loop
        // If the command was invalid, it will return null, which will be handled by the main loop
        return result;
    }
    public void execute() {} //Will always be overridden
    boolean enemyTurn() {return false;}
}

class MoveCommand extends BaseCommand {
    int xI;
    int yI;
    MoveCommand(int xInc, int yInc) {
        xI = xInc;
        yI = yInc;
    }
    @Override
    public void execute() {
        int[] object = BaseProjectile.findObjectAlongPath(Player.getZoneX(), Player.getZoneY(), xI, yI);
        if (object == null) {
            // Check if the player leaves the current sector
            // One sector is 10 zones, so if more than 10 zones are moved, move sectors
            for (int i = 0; i < Math.abs(xI); i++) {
                Player.setZoneX(Player.getZoneX()+xI/Math.abs(xI)); // Increments by 1, keeping the sign
                if (Player.getZoneX() <= -1) {
                    Player.setSectorX(Player.getSectorX()-1);
                    Player.setZoneX(9);
                }
                if (Player.getZoneX() >= 10) {
                    Player.setSectorX(Player.getSectorX()+1);
                    Player.setZoneX(0);
                }
            }
            for (int i = 0; i < Math.abs(yI); i++) {
                Player.setZoneY(Player.getZoneY()+yI/Math.abs(yI)); // Increments by 1, keeping the sign
                if (Player.getZoneY() <= -1) {
                    Player.setSectorY(Player.getSectorY()-1);
                    Player.setZoneY(9);
                }
                if (Player.getZoneY() >= 10) {
                    Player.setSectorY(Player.getSectorY()+1);
                    Player.setZoneY(0);
                }
            }
            Zone[][] map = SectorMap.getMap();
            map[Player.getSectorY()][Player.getSectorX()].printSelf();
            int energyExpended = (int) Math.sqrt((xI*xI)+(yI*yI));
            Player.setEnergy(Player.getEnergy()-energyExpended);
            // Check if player is parked next to base
            if (Base.isPlayerNextToBase()) {
                Player.setEnergy(750);
                Player.setHull(100);
                Player.setShield(100);
                Player.setMissleCount(7);
                System.out.println("SHIP WEAPONS REPLENISHED AND DAMAGES REPAIRED");
            }
        } else {
            System.out.println("Obstruction at: " + object[0] + ", " + object[1]);
        }
    }
    boolean enemyTurn() {
         return true;
    }
}

class ShowZoneCommand extends BaseCommand {
    @Override
    public void execute() {
        // Print the zone in which the player is
        Zone[][] map = SectorMap.getMap();
        map[Player.getSectorY()][Player.getSectorX()].printSelf();
    }
    boolean enemyTurn() {
        return false;
    }
}

class ShowSectorCommand extends BaseCommand {
    @Override
    public void execute() {
        SectorMap.printSelf();
    }
    boolean enemyTurn() {
        return false;
    }
}

class FireLaserCommand extends BaseCommand {
    int x2;
    int y2;
    int damage;
    FireLaserCommand(int x2, int y2, int damage) {
        this.x2 = x2;
        this.y2 = y2;
        this.damage = damage;
    }
    @Override
    public void execute() {
        BaseProjectile laser = new Laser(Player.getSectorX(), Player.getSectorY(), x2, y2, damage, true);
        laser.fire();
        Player.setEnergy(Player.getEnergy()-damage);
    }
    boolean enemyTurn() {
        return true;
    }
}

class FireMissleCommand extends BaseCommand {
    int x2;
    int y2;
    int damage;
    FireMissleCommand(int x2, int y2) {
        this.x2 = x2;
        this.y2 = y2;
    }
    @Override
    public void execute() {
        if (Player.getMissleCount() <= 0) {
            System.out.println("NO MISSLES LEFT IN SILOS");
            return;
        }
        BaseProjectile missle = new Missle(Player.getSectorX(), Player.getSectorY(), x2, y2);
        missle.fire();
        Player.setMissleCount(Player.getMissleCount()-1);
    }
    boolean enemyTurn() {
        return true;
    }
}

class StatusCommand extends BaseCommand {
    @Override
    public void execute() {
        System.out.println("Hull integrity is at " + Player.getHull() + "%");
        System.out.println("Shields are at " + Player.getShield() + "%");
        System.out.println("Ship has " + Player.getEnergy() + " points of energy left");
        System.out.println(Player.getMissleCount() + " Missles remaining in silos");
        System.out.println("There are " + BaseEnemy.enemies.size() + " enemies left");
    }boolean enemyTurn() {
        return false;
    }
}

class ReplenishShieldCommand extends BaseCommand {
    int energy;
    ReplenishShieldCommand(int energy) {
        this.energy = energy;
    }
    @Override
    public void execute() {
        Player.setShield(Player.getShield()+energy);
        if (Player.getShield() > 100) {
            Player.setShield(100);
        }
        Player.setEnergy(Player.getEnergy()-energy);
    }
    boolean enemyTurn() {
        return true;
    }
}

class HelpCommand extends BaseCommand {
    @Override
    public void execute() {
        InputStream str = Main.class.getResourceAsStream("help");
        BufferedReader br = new BufferedReader(new InputStreamReader(str));
        for (int i = 1; i <= 10; i++) {
            try {
                br.readLine();
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
        for (int i = 0; i < 72; i++) {
            try {
                System.out.println(br.readLine());
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
    }
}