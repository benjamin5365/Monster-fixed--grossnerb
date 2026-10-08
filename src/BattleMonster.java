import java.util.Scanner;


public class BattleMonster {
    // CLASS (NOT INSTANCE) VARIABLES
    private static Puppy puppy;
    private static Monster[] monsters = new Monster[5];
    private static int playerHealth = 100;
    private static int maxDamage = 100;

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
            else if(input.equals("fight")) {
                // CHECK FOR PUPPY
                if (puppy != null) {
                    // 1 in 100 chance of john wick
                    if (((int)(Math.random()*100)+1) == 1) {
                        johnWick();
                    }
                    
            
                }

                //IF NO DOG AND NO DOG ATTACK, ROLL FOR DAMAGE

                //APPLY DAMAGE TO FIRST MONSTER

            }
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
    
    public static void johnWick() {
        //LOOP THROUGH ALL MONSTERS AND THE PUPPY DESTROYS THEM
        for(Monster m: monsters) {
            if (m != null) {
                m.takeDamage(m.getHealth());
            }
        }
    }
}