package no.ntnu.idatg2001;

/**
 * Superclass for units, given their name and statistics.
 *
 * @author sondesp
 * @version 0.0.1
 * @since 0.0.1
 */

public abstract class Unit {

    protected String name;
    protected int health;
    protected int attack;
    protected int armor;

    /**
     * Constructor for unit
     * @param name Short descriptive name. Ex: "Archer", "Swordsman"
     * @param health Health of unit, greater than 0
     * @param attack Attack value represents the weapons damage
     * @param armor Armor value represents the units defence
     */

    public Unit(String name, int health, int attack, int armor) {
        if (name.equals("") || health <= 0 || attack < 0 || armor < 0) {throw new IllegalArgumentException("Illegal argument for unit");}

        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
    }

    /**
     * Constructor for subclasses of unit
     * @param name Short descriptive name. Ex: "Archer", "Swordsman"
     * @param health Health of unit, greater than 0
     */

    public Unit(String name, int health) {
        if (name.equals("") || health <= 0 || attack < 0 || armor < 0) {throw new IllegalArgumentException("Illegal argument for unit");}

        this.name = name;
        this.health = health;
    }

    /**
     * @return Name of unit
     */
    public String getName() {
        return name;
    }

    /**
     * @return Health of unit
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return Attack of unit
     */
    public int getAttack() {
        return attack;
    }

    /**
     * @return Armor of unit
     */
    public int getArmor() {
        return armor;
    }

    /**
     * @param health Sets health of unit
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return String of unit's fields
     */
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", attack=" + attack +
                ", armor=" + armor +
                '}';
    }

    public abstract int getAttackBonus();
    public abstract int getResistBonus();

    /**
     * @param opponent Opponent to attack, whomst health is reduced
     */
    public void attack(Unit opponent) {
        int hp = opponent.getHealth() - attack - getAttackBonus() + opponent.getArmor() + opponent.getResistBonus();
        if (hp < opponent.getHealth()) {opponent.setHealth(hp);}
    }
}
