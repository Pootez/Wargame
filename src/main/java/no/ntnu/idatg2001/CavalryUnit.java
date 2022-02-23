package no.ntnu.idatg2001;

/**
 * Cavalry unit with an attack bonus of 6-2 and resistance bonus of 1
 *
 * @author sondesp
 * @version 0.0.1
 * @since 0.0.1
 */

public class CavalryUnit extends Unit {

    private int attackBonus = 10;

    /**
     * @param name Short descriptive name. Ex: "Archer", "Swordsman"
     * @param health Health of unit, greater than 0
     * @param attack Attack value represents the weapons damage
     * @param armor Armor value represents the units defence
     */

    public CavalryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * @param name Short descriptive name. Ex: "Archer", "Swordsman"
     * @param health Health of unit, greater than 0
     */

    public CavalryUnit(String name, int health) {
        super(name, health);
        attack = 20;
        armor = 12;
    }

    /**
     * @return Attack bonus of 6-2
     */
    public int getAttackBonus() {
        if (attackBonus > 2) {attackBonus -=4;}
        return attackBonus;
    }

    /**
     * @return Resistance bonus of 1
     */
    public int getResistBonus() {
        return 1;
    }
}