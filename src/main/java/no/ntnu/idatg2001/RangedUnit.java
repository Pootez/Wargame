package no.ntnu.idatg2001;

/**
 * Ranged unit with an attack bonus of 3 and resistance bonus of 6-2
 *
 * @author sondesp
 * @version 0.0.1
 * @since 0.0.1
 */

public class RangedUnit extends Unit {

    private int resist = 8;

    /**
     * @param name Short descriptive name. Ex: "Archer", "Swordsman"
     * @param health Health of unit, greater than 0
     * @param attack Attack value represents the weapons damage
     * @param armor Armor value represents the units defence
     */

    public RangedUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * @param name Short descriptive name. Ex: "Archer", "Swordsman"
     * @param health Health of unit, greater than 0
     */

    public RangedUnit(String name, int health) {
        super(name, health);
        attack = 15;
        armor = 8;
    }

    /**
     * @return Attack bonus of 3
     */
    public int getAttackBonus() {
        return 3;
    }

    /**
     * @return Resistance bonus of 6-2
     */
    public int getResistBonus() {
        if (resist > 2) {resist -= 2;}
        return resist;
    }
}
