import java.awt.Image;
import oop.ex2.*;

/**
 * The spaceship object that is used in the SpaceWars game. It's a abstract class.
 * All Ships have these functions and variables, and there are two abstracts methods:
 * doAction who represent the specific action for each Ship and getImage who return the image of the ship
 */
public abstract class SpaceShip{

    private static final int MAX_HEALTH = 22;
    protected static final int FIRE_TIME = 7;
    private static final int STARTING_MAX_ENERGY = 210;
    private static final int STARTING_ENERGY = 190;

    private static final int LOSE_ENERGY_FROM_COLLISION = 10;
    private static final int LOSE_ENERGY_FROM_FIRE = 10;
    private static final int COST_ENERGY_FROM_FIRE = 19;
    private static final int COST_ENERGY_FROM_TELEPORTING = 140;
    private static final int COST_ENERGY_FROM_SHIELDS = 3;
    private static final int INCREASE_ENERGY_FROM_BASHING = 18;


    protected static final int TURN_LEFT = 1;
    protected static final int TURN_RIGHT = -1;
    protected static final int STRAIGHT = 0;
    protected static final int FOLLOW = 1;
    protected static final int AVOID = -1;


    protected int max_energy = STARTING_MAX_ENERGY;
    protected int current_energy = STARTING_ENERGY;
    private int health = MAX_HEALTH;
    protected boolean activeShields = false;
    protected int last_fire = 0;

    private SpaceShipPhysics physicalState = new SpaceShipPhysics();

    /**
     * Does the actions of this ship for this round. Abstract method
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game);

    /**
     * This method is called to move the ship, if the direction is 1 the ship follow the closest ship and if the
     * direction is -1 the ship move to avoid the closest ship
     */
    protected void followShip(SpaceShip closest_ship, int direction){ // 1 to follow the ship -1 to avoid it
        double angle_closest_ship = getPhysics().angleTo(closest_ship.getPhysics());

        if (angle_closest_ship < 0 && -Math.PI < angle_closest_ship){
            getPhysics().move(true,TURN_RIGHT * direction);
        } else if(0 < angle_closest_ship && Math.PI > angle_closest_ship){
            getPhysics().move(true,TURN_LEFT * direction);
        } else {
            getPhysics().move(true,STRAIGHT);
        }
    }

    /**
     * This method is called every time a collision with this ship occurs 
     */
    public void collidedWithAnotherShip(){

        if (!activeShields){
            health--; // lose one life
            max_energy -= LOSE_ENERGY_FROM_COLLISION; // maximal energy bound decreases
            if (current_energy > max_energy) {
                current_energy = max_energy;
            }
        } else { // if collide with the shields
            max_energy += INCREASE_ENERGY_FROM_BASHING;
            current_energy += INCREASE_ENERGY_FROM_BASHING;
        }

    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset(){
        max_energy = STARTING_MAX_ENERGY;
        current_energy = STARTING_ENERGY;
        health = MAX_HEALTH;
        activeShields = false;
        last_fire = 0;
        physicalState = new SpaceShipPhysics();

    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        if (health == 0){
            return true;
        }
        return false;
    }

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return physicalState;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!activeShields){
            health--; // lose one life
            max_energy -= LOSE_ENERGY_FROM_FIRE; // maximal energy bound decreases
            if (current_energy > max_energy) {
                current_energy = max_energy;
            }
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * 
     * @return the image of this ship.
     */
    public abstract Image getImage();

    /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
       if (current_energy >= COST_ENERGY_FROM_FIRE){
           current_energy -= COST_ENERGY_FROM_FIRE;
           game.addShot(physicalState);
       }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if(current_energy >= COST_ENERGY_FROM_SHIELDS){
            activeShields = true;
            current_energy -= COST_ENERGY_FROM_SHIELDS;
        } else {
            activeShields = false;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
       if (current_energy >= COST_ENERGY_FROM_TELEPORTING){
           current_energy -= COST_ENERGY_FROM_TELEPORTING;
           physicalState = new SpaceShipPhysics();
       }
    }
    
}
