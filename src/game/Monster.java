package game;

public class Monster {
    // INSTANCE VARIABLES (properties)
    private int health;
    private double damage;
    private int speed;
    private String special; 
    private Game game;

    // CONSTRUCTOR
    public Monster(Game game){
        this.game = game;
        // randomly generate health, damage, speed
        health = (int)(Math.random() * 80 + 1) + 20;
        // random 10 - 50
        damage = (Math.random() * 41) + 10;
        // speed: random 1-10
        speed = (int)(Math.random() * 10) + 1;
        // by default, the monster doesn't have a special move
        special = "";
    }
    // OVERLOADED CONSTRUCTOR
    public Monster(Game game, String special){
        this(game);
        this.special = special;

        if (this.special.equals("pfundstein")) {
            game.getGUI().displayMessage("Keep leading.. to your DOOM!");
        }

        if (this.special.equals("Lebron")) {
            game.getGUI().displayMessage("This one's for you Cleveland");
        }

    }
    
    // ACCESSOR METHODS
    public int health() { return this.health; }
    public double damage() { 
        if (this.special.equals("pfundstein")) {
            game.getGUI().displayMessage("Where's your YONDER!");
        }
        if (this.special.equals("Lebron")) {
            game.getGUI().displayMessage("Threeee");
        }
        return Math.round(damage * 100.0) / 100.0;
    }
    public int speed() { return speed; }
    public String special() { return this.special; }

    // MUTATOR METHODS
    public void takeDamage(int dmg){
        health -= dmg;
    }

    public void heal(int heal) {
        health += heal;
    }

    // UTILITY METHODS
    public void heal (Monster friend, int heal) {
        friend.heal(heal);
    }

    public void engrage() {
        game.getGUI().displayMessage("All beserk!");
        for (Monster m: game.getMonsters()) {
            if ( (game.getMonsters() != null) && (m != null) ) {
                m.twoFast2Furious();
            }
    
            game.getGUI().updateMonsters(game.getMonsters());
        }
    }

    public void twoFast2Furious() {
        // lowers health to 25%, not less than 5
        health = (int)(health * 0.25);
        if (health < 5) {
            health = 5;
        }

        // damage and speed increase by 4x
        damage *= 4;
        speed *= 4;

        // can we post a message?
        game.getGUI().displayMessage("twoFast2Furious");
    }
}