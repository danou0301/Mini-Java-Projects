import oop.ex2.GameGUI;
import oop.ex2.SpaceShipPhysics;
import java.awt.*;
/**
 * This class represents a ship controlled by a human. The player can control the ship by
 * using the keyboard.
 */
public class HumanShip extends SpaceShip {

    /**
     * This method manage the ship, this direction, shields, teleportation and fire
     */
    public void doAction(SpaceWars game){

        SpaceShipPhysics shipPhysics = getPhysics(); // get the physics of the ship

        if (game.getGUI().isTeleportPressed()) {  // attempt to teleport
            teleport();
        }

        if (game.getGUI().isRightPressed()){  // move and turn with or without acceleration
            shipPhysics.move(game.getGUI().isUpPressed(), TURN_RIGHT);
        } else if (game.getGUI().isLeftPressed()){
            shipPhysics.move(game.getGUI().isUpPressed(), TURN_LEFT);
        } else {
            shipPhysics.move(game.getGUI().isUpPressed(), STRAIGHT);
        }

        if (game.getGUI().isShieldsPressed()){ // check if a shields is activated
            shieldOn();
        } else {
            activeShields = false;
        }

        if (game.getGUI().isShotPressed() && last_fire > FIRE_TIME){  // attempt to fire
            fire(game);
            last_fire = 0;
        }
        if (current_energy < max_energy){ // add energy
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
        if (activeShields){

            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.SPACESHIP_IMAGE;
    }

}
