package no.ntnu.idatg2001.wargame.model;

/**
 * Cavalry unit with an attack bonus of 6-2 and resistance bonus of 1
 * Attack of 20 and armor of 12
 *
 * @author sondesp
 * @version 2022-02-23
 * @since 2022-02-22
 */

public class CavalryUnit extends Unit {

    private int attackBonus = 6;

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
        return attackBonus;
    }

    /**
     * @return Resistance bonus of 1
     */
    public int getResistBonus() {
        return 1;
    }

    @Override
    public void attack(Unit opponent) {
        int hp = opponent.getHealth() - attack - getAttackBonus() + opponent.getArmor() + opponent.getResistBonus();
        if (attackBonus > 2) {attackBonus -= 4;}
        opponent.attacked();
        if (hp < opponent.getHealth()) {opponent.setHealth(hp);}
    }
}
