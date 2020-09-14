import oop.ex2.GameGUI;
import oop.ex2.SpaceShipPhysics;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;

/**
 * This class represents a special ship, who have the same features that the aggressive ship but
 * with a different image
 */
public class SpecialShip extends SpaceShip {

    private static final double FIRE_ANGLE = 0.21;
    private static final String IMAGE_NAME = "SpecialShip.png";
    /**
     * This method manage the ship, this direction and fire
     */
    public void doAction(SpaceWars game){
        SpaceShipPhysics shipPhysics = SpecialShip.this.getPhysics();
        SpaceShip closest_ship = game.getClosestShipTo(this);

        double angle_closest_ship = shipPhysics.angleTo(closest_ship.getPhysics());

        if (Math.abs(angle_closest_ship) < FIRE_ANGLE && last_fire > FIRE_TIME) {  // attempt to fire
            fire(game);
            last_fire = 0;
        }

        followShip(closest_ship, FOLLOW);

        if (current_energy < max_energy){
            current_energy++;
        }        last_fire++;

    }



    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage(){
            try {
                return ImageIO.read(new File(IMAGE_NAME));

            } catch (IOException e){
                return GameGUI.ENEMY_SPACESHIP_IMAGE;
            }

    }
}
