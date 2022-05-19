package no.ntnu.idatg2001.wargame.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with static methods for handling army related csv files.
 *
 * @author Sondre Espeland
 * @version 2022-03-26
 * @since 2022-03-26
 */

public class FileHandler {

    /**
     * Write a csv file of an army and its units.
     *
     * @param army Army to be serialized
     * @param file File path
     */

    public static void writeArmyCSV(Army army, String file) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(file))) {
            writer.write(army.getName() + "\n");
            if (!army.getUnits().isEmpty()) {
                for (Unit unit : army.getUnits()) {
                    writer.write(unit.getClass().getSimpleName() + "," + unit.getName() + "," + unit.getHealth() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read a csv file of an army and its units.
     *
     * @param file File path
     * @return Army with units
     */

    public static Army readArmyCSV(String file) {
        Army army = null;
        try (BufferedReader reader = Files.newBufferedReader(Path.of(file))) {
            String lineOfText;
            army = new Army(reader.readLine());
            while ((lineOfText = reader.readLine()) != null) {
                String[] f = lineOfText.split(",");
                String name = f[1].equals("null") ? null : f[1];
                int hp = f[2].equals("null") ? null : Integer.parseInt(f[2]);
                Unit temp = f[0].equals("CommanderUnit") ? new CommanderUnit(name,hp) :
                        f[0].equals("InfantryUnit") ? new InfantryUnit(name,hp) :
                        f[0].equals("CavalryUnit") ? new CavalryUnit(name,hp) :
                        f[0].equals("RangedUnit") ? new RangedUnit(name,hp) : null;

                army.addUnit(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return army;
    }

    /**
     * Returns all .csv files within a folder as a list of armies.
     *
     * @param path Path of folder
     * @return List of armies
     */

    public static List<Army> readArmies(String path) {
        ArrayList<Army> armies = new ArrayList<>();
        File f = new File(path);
        String[] pathnames = f.list();

        if (pathnames != null) {
            for (String pathname : pathnames) {
                if (pathname.contains(".csv")) {
                    armies.add(readArmyCSV(path + "/" + pathname));
                }
            }
        }
        return armies;
    }
}
