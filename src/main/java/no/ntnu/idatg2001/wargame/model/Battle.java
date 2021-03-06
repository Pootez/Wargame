package no.ntnu.idatg2001.wargame.model;

/**
 * Class that contains and simulates a battle between two armies.
 *
 * @author sondesp
 * @version 2022-02-24
 * @since 2022-02-24
 */

public class Battle {

    private Army armyOne;
    private Army armyTwo;

    /**
     * @param armyOne Army one
     * @param armyTwo Army two
     */
    public Battle(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
    }

    /**
     * @return Null if both armies are eliminated, else returns army with remaining units
     */
    public Army simulate(Terrain terrain) {
        boolean running = true;
        Army winner = null;

        armyOne.applyTerrain(terrain);
        armyTwo.applyTerrain(terrain);

        while (running) {
            Unit unitOne = armyOne.getRandom();
            Unit unitTwo = armyTwo.getRandom();
            unitOne.attack(unitTwo);
            unitTwo.attack(unitOne);
            if (unitOne.getHealth() <= 0) {armyOne.remove(unitOne);}
            if (unitTwo.getHealth() <= 0) {armyTwo.remove(unitTwo);}

            if (!armyOne.hasUnits() || !armyTwo.hasUnits()) {running = false;}
        }

        if (armyOne.hasUnits() || armyTwo.hasUnits()) {
            if (armyOne.hasUnits()) {winner = armyOne;}
            else if (armyTwo.hasUnits()) {winner = armyTwo;}
        }

        return winner;
    }

    /**
     * Returns the battle info as a string.
     * @return String of the two armies
     */
    @Override
    public String toString() {
        return "Battle{" +
                "armyOne=" + armyOne +
                ", armyTwo=" + armyTwo +
                '}';
    }

    public Army getArmyOne() {
        return armyOne;
    }

    public void setArmyOne(Army armyOne) {
        this.armyOne = armyOne;
    }

    public Army getArmyTwo() {
        return armyTwo;
    }

    public void setArmyTwo(Army armyTwo) {
        this.armyTwo = armyTwo;
    }
}
