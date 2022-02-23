package no.ntnu.idatg2001;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    private Unit infantry;
    private Unit cavalry;
    private Unit ranged;
    private Unit commander;

    @BeforeEach
    public void initEach() {
        infantry = new InfantryUnit("Gary",20);
        cavalry = new CavalryUnit("Charles",22);
        ranged = new RangedUnit("Steven",18);
        commander = new CommanderUnit("Bob",25);
    }

    @Test
    void getName() {

        assertEquals("Gary",infantry.getName());
        assertEquals("Charles",cavalry.getName());
        assertEquals("Steven",ranged.getName());
        assertEquals("Bob",commander.getName());
    }

    @Test
    void getHealth() {
        assertEquals(20,infantry.getHealth());
        assertEquals(22,cavalry.getHealth());
        assertEquals(18,ranged.getHealth());
        assertEquals(25,commander.getHealth());
    }

    @Test
    void getAttack() {
        assertEquals(15,infantry.getAttack());
        assertEquals(20,cavalry.getAttack());
        assertEquals(15,ranged.getAttack());
        assertEquals(25,commander.getAttack());
    }

    @Test
    void getArmor() {
        assertEquals(10,infantry.getArmor());
        assertEquals(12,cavalry.getArmor());
        assertEquals(8,ranged.getArmor());
        assertEquals(15,commander.getArmor());
    }

    @Test
    void setHealth() {
        infantry.setHealth(100);
        assertEquals(100,infantry.getHealth());
        commander.setHealth(0);
        assertEquals(0,commander.getHealth());
    }

    @Test
    void getAttackBonus() {

    }

    @Test
    void getResistBonus() {
    }

    @Test
    void attack() {
    }
}