package no.ntnu.idatg2001.wargame;

import no.ntnu.idatg2001.wargame.*;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileHandlerTest {

    private String armyString = "";
    private String duplicateString = "";

    @Test
    void readWriteArmyCSV() {
        Army army = new Army("Larpers Unite");

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
        army.addUnit(new InfantryUnit("Greg; the General Store Manager",20));

        FileHandler.writeArmyCSV(army, "src/main/resources/armies/armyTest.csv");

        Army duplicate = FileHandler.readArmyCSV("src/main/resources/armies/armyTest.csv");
        FileHandler.writeArmyCSV(duplicate,"src/main/resources/armies/armyDuplicate.csv");

        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/armies/armyTest.csv"))) {
            String lineOfText;
            while ((lineOfText = reader.readLine()) != null) {
                armyString += lineOfText;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/armies/armyDuplicate.csv"))) {
            String lineOfText;
            while ((lineOfText = reader.readLine()) != null) {
                duplicateString += lineOfText;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert(armyString.equals(duplicateString));
    }
}