package no.ntnu.idatg2001.wargame;

import no.ntnu.idatg2001.wargame.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArmyTest {

    private Army army;

    @BeforeEach
    void setup() {
        army = new Army("Larpers Unite");

        army.addUnit(new CommanderUnit("Bob Lee the Feared",240));

        army.addUnit(new RangedUnit("Archer",40));
        army.addUnit(new RangedUnit("Archer",40));
        army.addUnit(new RangedUnit("Archer",40));
        army.addUnit(new RangedUnit("Archer",40));
        army.addUnit(new RangedUnit("Archer",40));

        army.addUnit(new CavalryUnit("Swordsman",40));
        army.addUnit(new CavalryUnit("Swordsman",40));
        army.addUnit(new CavalryUnit("Swordsman",40));
        army.addUnit(new CavalryUnit("Swordsman",40));
        army.addUnit(new CavalryUnit("Swordsman",40));
        army.addUnit(new CavalryUnit("Swordsman",40));
        army.addUnit(new CavalryUnit("Swordsman",40));
        army.addUnit(new CavalryUnit("Swordsman",40));

        army.addUnit(new InfantryUnit("Jeremy from accounting",20));
        army.addUnit(new InfantryUnit("Greg, the General Store Manager",20));
    }

    @Test
    void getInfantryUnits() {
        assertEquals(2,army.getInfantryUnits().size());
    }

    @Test
    void getCavalryUnits() {
        assertEquals(8,army.getCavalryUnits().size());
    }

    @Test
    void getRangedUnits() {
        assertEquals(5,army.getRangedUnits().size());
    }

    @Test
    void getCommanderUnits() {
        assertEquals(1,army.getCommanderUnits().size());
    }
}