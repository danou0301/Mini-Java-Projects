import oop.ex2.GameGUI;
import java.awt.*;

/**
 * This class represents a basher ship, a ship that deliberately tries to collide with other ships.
 */
public class BasherShip extends SpaceShip {

    private static final double DISTANCE_MAX = 0.19;

    /**
     * This method manage the ship, this direction, shields, teleportation and fire
     */
    public void doAction(SpaceWars game){
        SpaceShip closest_ship = game.getClosestShipTo(this);

        double distance_closest_ship = getPhysics().distanceFrom(closest_ship.getPhysics());

        if (distance_closest_ship <= DISTANCE_MAX) {  // attempt to active the shields
            shieldOn();
        } else {
            activeShields = false;
        }

        followShip(closest_ship,FOLLOW);

        if (current_energy < max_energy){
            current_energy++;
        }

    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage(){
        if (activeShields){
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }
}

