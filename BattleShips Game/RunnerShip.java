import oop.ex2.GameGUI;
import oop.ex2.SpaceShipPhysics;
import java.awt.*;

/**
 * This class represents a runner ship, a ship that tries to avoid all other ships.
 */
public class RunnerShip extends SpaceShip {

    private static final double DISTANCE_MAX = 0.25;
    private static final double ANGLE_MAX = 0.23;

    public void doAction(SpaceWars game){
        SpaceShipPhysics spaceShipPhysics = getPhysics();
        SpaceShip closest_ship = game.getClosestShipTo(this);

        double angle_closest_ship = spaceShipPhysics.angleTo(closest_ship.getPhysics());
        double distance_closest_ship = getPhysics().distanceFrom(closest_ship.getPhysics());

        if (distance_closest_ship < DISTANCE_MAX && Math.abs(angle_closest_ship) < ANGLE_MAX) {  // attempt to teleport
            teleport();
        }

       followShip(closest_ship, AVOID);

        // no need energy

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
