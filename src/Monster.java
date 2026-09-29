public class Monster {

    public static int specialMonsters = 1;

    // CONSTRUCTOR
    public Monster() {
        if (specialMonsters > 0) {
            System.out.println("I'm alive");
            Monster.specialMonsters--;
        } else {
            System.out.println("I'm just a typical monster");
        }
    }
}