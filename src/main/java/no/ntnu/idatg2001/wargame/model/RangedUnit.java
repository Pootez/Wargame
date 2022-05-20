package no.ntnu.idatg2001.wargame.model;

/**
 * Ranged unit with an attack bonus of 3 and resistance bonus of 6-2
 * Attack of 15 and armor of 8
 *
 * @author sondesp
 * @version 2022-02-23
 * @since 2022-02-22
 */

public class RangedUnit extends Unit {

    private int resist = 6;

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
        return resist;
    }

    @Override
    public void attacked() {
        if (resist > 2) {resist -= 2;}
    }
}
