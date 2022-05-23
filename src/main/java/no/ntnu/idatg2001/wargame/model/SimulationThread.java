package no.ntnu.idatg2001.wargame.model;

import no.ntnu.idatg2001.wargame.ui.controllers.Controller;

/**
 * BorderPane for displaying and simulating a battle.
 *
 * @author sondesp
 * @version 2022-05-24
 * @since 2022-05-24
 */

public class SimulationThread extends Thread {

    private Army army1;
    private Army army2;
    private Terrain terrain;
    private int speed;


    public SimulationThread(Battle battle,Terrain terrain, int speed) {
        army1 = battle.getArmyOne();
        army2 = battle.getArmyTwo();
        this.terrain = terrain;
        this.speed = speed;
    }

    public void run() {
        if (speed < 1) {
            displayWinner(new Battle(army1, army2).simulate(terrain));
        }
        else {

        }

    }

    private void displayWinner(Army army) {
        Controller.simWinner(army);
    }
}
