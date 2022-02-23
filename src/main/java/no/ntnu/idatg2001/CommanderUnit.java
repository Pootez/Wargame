package no.ntnu.idatg2001;

/**
 * Cavalry unit with an attack bonus of 6-2 and resistance bonus of 1
 * Attack of 25 and armor of 15
 *
 * @author sondesp
 * @version 0.0.1
 * @since 0.0.1
 */

public class CommanderUnit extends CavalryUnit {

    /**
     * @param name Short descriptive name. Ex: "Archer", "Swordsman"
     * @param health Health of unit, greater than 0
     * @param attack Attack value represents the weapons damage
     * @param armor Armor value represents the units defence
     */

    public CommanderUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * @param name Short descriptive name. Ex: "Archer", "Swordsman"
     * @param health Health of unit, greater than 0
     */

    public CommanderUnit(String name, int health) {
        super(name, health);
        attack = 25;
        armor = 15;
    }
}
