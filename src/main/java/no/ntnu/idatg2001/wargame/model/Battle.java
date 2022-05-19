package no.ntnu.idatg2001.wargame.model;

/**
 * Class that contains and simulates a battle between two armies.
 *
 * @author sondesp
 * @version 0.0.1
 * @since 0.0.1
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
    public Army simulate() {
        boolean running = true;
        Army winner = null;

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

    @Override
    public String toString() {
        return "Battle{" +
                "armyOne=" + armyOne +
                ", armyTwo=" + armyTwo +
                '}';
    }
}
