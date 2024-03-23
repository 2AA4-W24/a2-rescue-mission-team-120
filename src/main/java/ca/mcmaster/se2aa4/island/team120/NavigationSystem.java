package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavigationSystem implements MissionType{
    private final Logger logger = LogManager.getLogger();

    FindIsland island = new FindIsland();
    GridSearch algoRun = new GridSearch();
    InterTurn interlace = new InterTurn();
    
    StartPoint start = new StartPoint(); 
    Data data = new Data(); 
    Coordinates coords = new Coordinates();
    Actions action= new Actions();

    private static boolean interTurn;
    private static boolean hasChangedDir;
    private static boolean onGround;
    private static String currentDirection;
    private static boolean groundFound;
    private static int range;
    private static boolean checkDone;

    public String run(int batteryLevel, int startingBatteryLevel){ 
        
        //initializes variables
        interTurn = data.getInterTurn();
        onGround = data.getOnGround();
        hasChangedDir= data.getHasChangedDir();
        currentDirection = data.getCurrDirection();
        groundFound = data.getGroundFound();
        range = data.getRange();
        checkDone = data.getCheckDone();

        logger.info("navigation");
        logger.info(data.getTop());
        logger.info(onGround);
        logger.info(interTurn);

        if(!(data.getTop())){
            return start.FourCorners(groundFound);
        }
        else if (!onGround && !interTurn){
            logger.info("running finder");
            return island.Finder(); 
        }
        else if(interTurn){
            logger.info("interlace");
            data.setHasChangedDir(true);
            
            return interlace.Turn();
        }
        else if(!(interTurn) && hasChangedDir){
            logger.info("algo search");
            return algoRun.search(currentDirection, batteryLevel, startingBatteryLevel, checkDone); 
        }
        else if (!(interTurn) && !(hasChangedDir)){
            logger.info("algo search2");
            return algoRun.search(currentDirection, batteryLevel, startingBatteryLevel, checkDone); 
        }
        else{
            logger.info("stop");
            return action.stop();
        }
    }
}
