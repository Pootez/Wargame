package no.ntnu.idatg2001.wargame.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for creating a single or a number of units of the same type, name and hp
 *
 * @author Sondre Espeland
 * @version 2022-05-19
 * @since 2022-05-19
 */

public class UnitFactory {

    /**
     * Method for creating n number of units of the same type, name and hp.
     *
     * @param n number of units
     * @param type type of unit
     * @param name name of unit
     * @param health health point of unit
     * @return List of units
     */
    public static List<Unit> createUnits(int n, String type, String name, int health) {
        if (name.contains(",")) {throw new IllegalArgumentException("Unit name cannot contain ','");}
        if (n <= 0) {throw new IllegalArgumentException("number of units must be greater than 0");}

        ArrayList<Unit> units = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            units.add(createUnit(type, name, health));
        }
        return units;
    }

    /**
     * Method for creating a unit.
     *
     * @param type type of unit
     * @param name name of unit
     * @param health health point of unit
     * @return Single Unit
     */
    public static Unit createUnit(String type, String name, int health) {
        if (name.contains(",")) {throw new IllegalArgumentException("Unit name cannot contain ','");}
        return switch (type) {
            case "Commander" -> new CommanderUnit(name, health);
            case "Infantry" -> new InfantryUnit(name, health);
            case "Cavalry" -> new CavalryUnit(name, health);
            case "Ranged" -> new RangedUnit(name, health);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
