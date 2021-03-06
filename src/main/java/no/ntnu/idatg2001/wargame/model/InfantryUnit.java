package no.ntnu.idatg2001.wargame.model;

/**
 * Infantry unit with an attack bonus of 2 and resistance bonus of 1
 * Attack of 15 and armor of 10
 *
 * @author sondesp
 * @version 2022-02-23
 * @since 2022-02-22
 */

public class InfantryUnit extends Unit {

    private int attackBonus = 2;
    private int resistBonus = 1;

    /**
     * @param name Short descriptive name. Ex: "Archer", "Swordsman"
     * @param health Health of unit, greater than 0
     * @param attack Attack value represents the weapons damage
     * @param armor Armor value represents the units defence
     */
    public InfantryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * @param name Short descriptive name. Ex: "Archer", "Swordsman"
     * @param health Health of unit, greater than 0
     */
    public InfantryUnit(String name, int health) {
        super(name, health);
        attack = 15;
        armor = 10;
    }
    /**
     * @return Attack bonus of 2
     */
    public int getAttackBonus() {
        return attackBonus;
    }

    /**
     * @return Resistance bonus of 1
     */
    public int getResistBonus() {
        return resistBonus;
    }

    @Override
    public void applyTerrain(Terrain terrain) {
        switch (terrain) {
            case FOREST:
                attackBonus = 4;
                resistBonus = 3;
                break;
        }
    }
}
