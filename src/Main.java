import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BaseCommand command;
        BaseEnemy.init();
        Base.init();
        SectorMap.init();
        InputStream str = Main.class.getResourceAsStream("help");
        BufferedReader br = new BufferedReader(new InputStreamReader(str));
        for (int i = 1; i <= 10; i++) {
            try {
                System.out.println(br.readLine());
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
        //Main Game Loop
        while (true) {
            command = BaseCommand.parseCommand(sc.nextLine());
            if(command != null) command.execute(); // Execute valid command
            else {System.out.println("Invalid Command");} // Notify player if command is invalid
            Player.endGameDueToEnergy();
            Player.win();
            if (command != null) if (command.enemyTurn()) BaseEnemy.playEnemyTurn();
        }
    }
}
    