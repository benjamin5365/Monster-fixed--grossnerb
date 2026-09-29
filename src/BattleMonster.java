import java.util.Scanner;


public class BattleMonster {
    private static Puppy puppy;
    private static Monster[] monsters = new Monster[5];

    public static void main(String args[]) {
        Monster m = new Monster();
        Monster m2 = new Monster();


        Scanner s = new Scanner(System.in);
        String input = "";
        System.out.println("Your first choice: Fight or puppy?");
        do { 
            System.out.print("INPUT: ");
            input = s.nextLine().toLowerCase().trim();

            // OUR TURN
            if (input.equals("puppy") && puppy == null) {
                puppy = new Puppy();
            }
            


            // THEIR TURN


        } while (!input.equals("quit"));
    }

    public static boolean noMonsters() {
        // LOOP AND CHECK FOR MONSTERS
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] != null) {
                return false;
            }
        }
    }



}