package no.ntnu.idatg2001.wargame.model;

import java.util.ArrayList;
import java.util.List;

public class UnitFactory {

    public List<Unit> createUnits(int n, String type, String name, int health) {
        if (name.contains(",")) {throw new IllegalArgumentException("Unit name cannot contain ','");}
        if (n <= 0) {
            return new ArrayList<>();
        }

        ArrayList<Unit> units = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            units.add(createUnit(type, name, health));
        }
        return units;
    }

    public Unit createUnit(String type, String name, int health) {
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
