/**
 * This class has a single static method (createSpaceships(String[])), which is currently empty.
 * It is used by the supplied driver to create all the spaceship objects according to the command
 * line arguments.
 */
public class SpaceShipFactory {

    /** String variable for the human ship */
    private static final String HUMAN = "h";

    /**String variable for the runner ship */
    private static final String RUNNER = "r";

    /** String variable for the basher ship */
    private static final String BASHER = "b";

    /** String variable for the aggressive ship */
    private static final String AGGRESSIVE = "a";

    /** String variable for the drunkard ship*/
    private static final String DRUNKARD = "d";

    /** String variable for the special ship */
    private static final String SPECIAL = "s";


    /**
     * This method return an array of all ships created
     */
    public static SpaceShip[] createSpaceShips(String[] args) {

        SpaceShip[] arrayShips = new SpaceShip[args.length];

        for (int i = 0; i < args.length; i++){

            switch (args[i]){
                case HUMAN:
                    arrayShips[i] = new HumanShip();
                    break;
                case RUNNER:
                    arrayShips[i] = new RunnerShip();
                    break;
                case BASHER:
                    arrayShips[i] = new BasherShip();
                    break;
                case AGGRESSIVE:
                    arrayShips[i] = new AggressiveShip();
                    break;
                case DRUNKARD:
                    arrayShips[i] = new DrunkardShip();
                    break;
                case SPECIAL:
                    arrayShips[i] = new SpecialShip();
                    break;
            }
        }
        return arrayShips;
    }
}
