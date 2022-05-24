package no.ntnu.idatg2001.wargame.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Superclass for units, given their name and statistics.
 *
 * @author sondesp
 * @version 2022-05-23
 * @since 2022-02-22
 */

public abstract class Unit {

    protected String name;
    protected int health;
    protected int attack;
    protected int armor;

    private double x;
    private double y;
    private int range;
    private double speed;
    private int cooldown;
    private int lastAttack;
    private Unit target;

    /**
     * Constructor for unit
     * @param name Short descriptive name. Ex: "Archer", "Swordsman"
     * @param health Health of unit, greater than 0
     * @param attack Attack value represents the weapons damage
     * @param armor Armor value represents the units defence
     */

    public Unit(String name, int health, int attack, int armor) {
        if (name.equals("") || health <= 0 || attack < 0 || armor < 0) {throw new IllegalArgumentException("Illegal argument for unit");}
        if (name.contains(",")) {throw new IllegalArgumentException("Unit name cannot contain ','");}

        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
        range = 20;
        speed = 5;
        cooldown = 20;
    }

    /**
     * Constructor for subclasses of unit
     * @param name Short descriptive name. Ex: "Archer", "Swordsman"
     * @param health Health of unit, greater than 0
     */
    public Unit(String name, int health) {
        if (name.equals("") || health <= 0) {throw new IllegalArgumentException("Illegal argument for unit");}
        if (name.contains(",")) {throw new IllegalArgumentException("Unit name cannot contain ','");}

        this.name = name;
        this.health = health;
        range = 20;
        speed = 5;
        cooldown = 20;
    }

    /**
     * @return Name of unit
     */
    public String getName() {
        return name;
    }

    /**
     * @return Health of unit
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return Attack of unit
     */
    public int getAttack() {
        return attack;
    }

    /**
     * @return Armor of unit
     */
    public int getArmor() {
        return armor;
    }

    /**
     * @param health Sets health of unit
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return String of unit's fields
     */
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", attack=" + attack +
                ", armor=" + armor +
                '}';
    }

    public abstract int getAttackBonus();
    public abstract int getResistBonus();
    public abstract void applyTerrain(Terrain terrain);
    public void attacked() {}

    /**
     * @param opponent Opponent to attack, whomst health is reduced
     */
    public void attack(Unit opponent) {
        int hp = opponent.getHealth() - attack - getAttackBonus() + opponent.getArmor() + opponent.getResistBonus();
        opponent.attacked();
        if (hp < opponent.getHealth()) {
            opponent.setHealth(hp);
        }
    }

    public void attack(Army army) {
        if (army.hasUnits()) {
            if (target == null || target.getHealth() <= 0) {
                List<Unit> enemyUnits = army.getUnits();
                target = findTarget(enemyUnits);
            }
            if (target != null) {
                if (distToUnit(target) <= range) {
                    if (lastAttack == -1 || lastAttack >= cooldown) {
                        attack(target);
                        lastAttack = 0;
                    } else {
                        lastAttack++;
                    }
                } else {
                    moveTowardsUnit(target);
                }
            }
        }
    }

    private double distToUnit(Unit unit) {
        return Math.sqrt(Math.pow(unit.getX() - x, 2) + Math.pow(unit.getY() - y, 2));
    }

    private Unit findTarget(List<Unit> units) {
        List<Unit> notDead = units.stream().filter(obj -> obj.getHealth() > 0).collect(Collectors.toList());
        if (notDead.size() == 0) {
            return null;
        }
        List<Unit> close = notDead.stream().sorted((a, b) -> Double.compare(distToUnit(a),distToUnit(b)))
                .collect(Collectors.toList());
        return close.get(0);
    }

    private void moveTowardsUnit(Unit unit) {
        double ang = Math.atan2(unit.getY() - y, unit.getX() - x);
        x += Math.cos(ang) * speed;
        y += Math.sin(ang) * speed;
    }

    public void randomPosition(int x1, int y1, int x2, int y2) {
        double width = Math.abs(x2 - x1);
        double height = Math.abs(y2 - y1);

        x = Math.random() * width + Math.min(x1, x2);
        y = Math.random() * height + Math.min(y1, y2);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
