package no.ntnu.idatg2001.wargame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitFactoryTest {

    private Army army;

    @BeforeEach
    void setUp() {
        army = new Army("test");
    }

    @Test
    void createUnits() {
        army.addUnits(UnitFactory.createUnits(64,"Ranged", "Archer", 20));
        army.addUnits(UnitFactory.createUnits(2,"Commander", "Guy", 40));

        Army compare = new Army("compare",
                UnitFactory.createUnits(64,"Ranged", "Archer", 20));
        compare.addUnits(UnitFactory.createUnits(3,"Cavalry", "Dude", 50));

        assertEquals(army.getRangedUnits().size(), compare.getRangedUnits().size());
        assertNotEquals(army.getCommanderUnits().size(), compare.getCommanderUnits().size());
        assertNotEquals(army.getCommanderUnits().size(), compare.getCavalryUnits().size());
        assertEquals(army.getCommanderUnits().size() + 1, compare.getCavalryUnits().size());
    }

    @Test
    void createUnit() {
        Unit unit1 = UnitFactory.createUnit("Commander", "Greg", 50);
        Unit unit2 = UnitFactory.createUnit("Ranged", "Dave", 20);
        Unit unit3 = UnitFactory.createUnit("Cavalry", "John", 40);
        Unit unit4 = UnitFactory.createUnit("Infantry", "Paul", 60);
        List<Unit> units = new ArrayList<>();
        units.add(unit1);
        units.add(unit2);
        units.add(unit3);
        units.add(unit4);

        String types = "";
        String names = "";
        int health = 0;
        for (Unit unit : units) {
            types += unit.getClass().getSimpleName() + ",";
            names += unit.getName() + ",";
            health += unit.getHealth();
        }
        assert(types.equals("CommanderUnit,RangedUnit,CavalryUnit,InfantryUnit,"));
        assert(names.equals("Greg,Dave,John,Paul,"));
        assertEquals(50+20+40+60, health);
    }
}