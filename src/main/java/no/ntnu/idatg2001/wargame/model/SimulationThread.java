package no.ntnu.idatg2001.wargame.model;

import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import no.ntnu.idatg2001.wargame.ui.controllers.Controller;

/**
 * BorderPane for displaying and simulating a battle.
 *
 * @author sondesp
 * @version 2022-05-24
 * @since 2022-05-24
 */

public class SimulationThread {

    private Army army1;
    private Army army2;
    private Terrain terrain;
    private int speed;
    private GraphicsContext gc;
    private Task simTask;
    private Task drawTask;


    public SimulationThread(Battle battle,Terrain terrain, int speed) {
        army1 = battle.getArmyOne();
        army2 = battle.getArmyTwo();
        this.terrain = terrain;
        this.speed = speed;
        gc = Controller.getGc();

        simTask = new Task<Integer>() {
            @Override protected Integer call() throws Exception {
                long waitTime = 1000 / speed;
                int iterations;
                for (iterations = 0; iterations < 100000; iterations++) {
                    if (isCancelled()) {
                        break;
                    }

                    simulate();

                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException interrupted) {
                        if (isCancelled()) {
                            break;
                        }
                    }
                }
                return iterations;
            }
        };

        if (speed < 1) {
            displayWinner(new Battle(army1, army2).simulate(terrain));
        }
        else {
            army1.applyTerrain(terrain);
            army2.applyTerrain(terrain);

            new Thread(simTask).start();
        }
    }

    private void simulate() {
        if (Controller.getSimRunning()) {
            if (army1.hasUnits() && army2.hasUnits()) {
                army1.attack(army2);
                army2.attack(army1);

                army1.removeDead();
                army2.removeDead();

                draw();
            }
            else {
                simTask.cancel();
                if (!army1.hasUnits() && !army2.hasUnits()) {
                    displayWinner(null);
                }
                else if (army1.hasUnits()) {
                    displayWinner(army1);
                }
                else {
                    displayWinner(army2);
                }
            }
        }
        else {
            simTask.cancel();
        }
    }

    private void draw() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,400,300);

        gc.setFill(Color.BLUE);
        for (Unit unit : army1.getUnits()){
            gc.fillArc(unit.getX(), unit.getY(), 5, 5, 0, 360, ArcType.ROUND);
        }
        gc.setFill(Color.ORANGE);
        for (Unit unit : army2.getUnits()){
            gc.fillArc(unit.getX(), unit.getY(), 5, 5, 0, 360, ArcType.ROUND);
        }
    }

    private void displayWinner(Army army) {
        Controller.simWinner(army);
    }
}
