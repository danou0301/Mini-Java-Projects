import oop.ex2.GameGUI;
import java.util.Random;
import java.awt.*;

/**
 * This class represents a drunkard ship, he fire randomly proximally once per 100 round, and revolves
 * around him.
 */
public class DrunkardShip extends SpaceShip {

    private static final double RANDOM_FIRE_BASE = 1;

    /**
     * This method manage the ship, this direction and fire
     */
    public void doAction(SpaceWars game){

        Random rand = new Random();
        int random_num = rand.nextInt(100); // do the random

        if (random_num <= RANDOM_FIRE_BASE ) {  // attempt to fire
            fire(game);
            last_fire = 0;
        }
        getPhysics().move(false,TURN_RIGHT); // revolves around him

        if (current_energy < max_energy){
            current_energy++;
        }
        last_fire++;

    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage(){

        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }
}
