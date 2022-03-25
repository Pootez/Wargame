package no.ntnu.idatg2001;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class representing an army of units.
 *
 * @author sondesp
 * @version 0.0.1
 * @since 0.0.1
 */

public class Army {

    private String name;
    private List<Unit> units;

    /**
     * @param name Name of army
     * @param units List of units
     */

    public Army(String name, List<Unit> units) {
        this.name = name;
        this.units = units;
    }

    /**
     * @param name Name of army
     */
    public Army(String name) {
        this.name = name;
        units = new ArrayList<>();
    }

    /**
     * @return Name of army
     */
    public String getName() {
        return name;
    }

    /**
     * @return List of units
     */
    public List<Unit> getUnits() {
        return units;
    }

    /**
     * @return Random unit from list of units
     */
    public Unit getRandom() {
        int index = (int)(Math.random() * units.size());
        return units.get(index);
    }

    /**
     * @param unit Unit to add
     * @return True if unit was successfully added
     */
    public boolean addUnit(Unit unit) {return units.add(unit);}

    /**
     * @param units List of units to add
     * @return True if units were successfully added
     */
    public boolean addUnits(List<Unit> units) {return this.units.addAll(units);}

    /**
     * @param unit Unit to remove from list
     * @return True if unit was successfully removed
     */
    public boolean remove(Unit unit) {return units.remove(unit);}

    /**
     * @return True if list is not empty
     */
    public boolean hasUnits() {return !units.isEmpty();}

    @Override
    public String toString() {
        return "Army{" +
                "name='" + name + '\'' +
                ", units=" + units +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Army army = (Army) o;
        return Objects.equals(name, army.name) && Objects.equals(units, army.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, units);
    }

    /**
     * Retrieves all infantry units from an army.
     *
     * @return List of infantry units in an army
     */

    public List<Unit> getInfantryUnits() {
        List<Unit> temp = new ArrayList<>();
        units.stream().filter(unit -> unit instanceof InfantryUnit).forEach(temp :: add);
        return temp;
    }

    /**
     * Retrieves all cavalry units from an army.
     *
     * @return List of cavalry units in an army
     */

    public List<Unit> getCavalryUnits() {
        List<Unit> temp = new ArrayList<>();
        units.stream().filter(unit -> unit instanceof CavalryUnit && !(unit instanceof CommanderUnit)).forEach(temp :: add);
        return temp;
    }

    /**
     * Retrieves all ranged units from an army.
     *
     * @return List of ranged units in an army
     */

    public List<Unit> getRangedUnits() {
        List<Unit> temp = new ArrayList<>();
        units.stream().filter(unit -> unit instanceof RangedUnit).forEach(temp :: add);
        return temp;
    }

    /**
     * Retrieves all commander units from an army.
     *
     * @return List of commander units in an army
     */

    public List<Unit> getCommanderUnits() {
        List<Unit> temp = new ArrayList<>();
        units.stream().filter(unit -> unit instanceof CommanderUnit).forEach(temp :: add);
        return temp;
    }
}
