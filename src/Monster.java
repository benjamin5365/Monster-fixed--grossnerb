public class Monster {

    // INSTANCE VARIABLES
    private int health;
    private int maxDamage;

    // CONSTRUCTOR
    public Monster() {
        health = 100;
        maxDamage = (int)(Math.random() * 15  + 1) + 10;
    }

    // ACCESORS
    public int getHealth() {
        return health;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    // MUTATORS
    public void takeDamage(int change) {
        health -= change;
        System.out.println("Monster takes " + change + " damage.");
        if (health <= 0) {
            System.out.println("Monster died");
        }
    }


}