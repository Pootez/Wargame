package no.ntnu.idatg2001.wargame.model;

import no.ntnu.idatg2001.wargame.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    Army armyOne;
    Army armyTwo;
    Army armyThree;

    @BeforeEach
    void setUp() {
        armyOne = new Army("Conquesters of Midgard");
        armyTwo = new Army("Walmart Night-Shift");
        armyThree = new Army("Target Evening-Hours");
        
        armyOne.addUnit(new CommanderUnit("Bob Lee the Feared",240));
        armyOne.addUnit(new RangedUnit("Archer",40));
        armyOne.addUnit(new RangedUnit("Archer",40));
        armyOne.addUnit(new RangedUnit("Archer",40));
        armyOne.addUnit(new RangedUnit("Archer",40));
        armyOne.addUnit(new RangedUnit("Archer",40));
        
        armyTwo.addUnit(new InfantryUnit("Jeremy from accounting",20));

        armyThree.addUnit(new InfantryUnit("Greg, the General Store Manager",20));
    }

    @Test
    void simulate() {
        Battle battle = new Battle(armyOne,armyTwo);
        assertNotEquals(null,battle.simulate());
    }

    @Test
    void simulateTwo() {
        Battle battle = new Battle(armyTwo,armyThree);
        assertNull(battle.simulate());
    }
}