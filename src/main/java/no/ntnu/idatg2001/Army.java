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
        units = new ArrayList<Unit>();
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
}
