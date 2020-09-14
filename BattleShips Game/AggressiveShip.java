import oop.ex2.GameGUI;
import oop.ex2.SpaceShipPhysics;
import java.awt.*;

/**
 * This class represents an aggressive ship, a ship that tries to pursue other ships and fire at them
 */
public class AggressiveShip extends SpaceShip {

    private static final double FIRE_ANGLE = 0.21;

    /**
     * This method manage the ship, this direction, shields, teleportation and fire
     */
    public void doAction(SpaceWars game){
        SpaceShipPhysics shipPhysics = getPhysics();
        SpaceShip closest_ship = game.getClosestShipTo(this);  // find the closest ship

        double angle_closest_ship = shipPhysics.angleTo(closest_ship.getPhysics());

        if (Math.abs(angle_closest_ship) < FIRE_ANGLE && last_fire > FIRE_TIME) {  // attempt to fire
            fire(game);
            last_fire = 0;
        }

        followShip(closest_ship, FOLLOW);

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
