import java.util.Scanner;


public class BattleMonster {
    // STATIC CLASS VARIABLES
    private static Puppy puppy;
    private static Monster[] monsters = new Monster[5];

    public static void main(String args[]) {

        // SETUP
        Scanner s = new Scanner(System.in);
        String input = "";

        // INTRO
        System.out.println("Your first choice: Fight or puppy?");

        // GAME LOOP
        do { 
            // CHECK FOR MONSTERS
            if (noMonsters()) {
                makeMonster();
            }
            

            System.out.print("INPUT: ");
            input = s.nextLine().toLowerCase().trim();

            // OUR TURN
            if (input.equals("puppy") && puppy == null) {
                puppy = new Puppy();
            }
            // ATTACK

            // HEAL


            // MONSTER'S TURN


        } while (!input.equals("quit"));
    }

    public static boolean noMonsters() {
        // LOOP AND CHECK FOR MONSTERS
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] != null) {
                return false;
            }
        }
        return true;
    }

    public static void makeMonster() {   
        // LOOP AND FIND FIRST FREE SPOT
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] == null) {
                monsters[i] = new Monster();
                return;
            }
        }
    }

}