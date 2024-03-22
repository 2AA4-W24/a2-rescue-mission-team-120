package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavigationSystem implements MissionType{
    //place where we keep steps
    //first call to search for ground
    //then call to search on island to find POI's 
    private final Logger logger = LogManager.getLogger();

    FindIsland island = new FindIsland();
    SimpleAlgo algoRun = new SimpleAlgo();
    InterTurn interlace = new InterTurn();
    
    StartPoint start = new StartPoint(); 
    Data data = new Data(); 
    Coordinates coords = new Coordinates();
    private static boolean interTurn;
    private static boolean turned;
    private static boolean onGround;
    private static String currentDirection;
    private static boolean groundFound;
    private static int range;
    private static int rangeCheck;
    private static boolean checkDone;

    public String run(int batteryLevel, int startingBatteryLevel){  
        interTurn = data.getInterTurn();
        onGround = data.getOnGround();
        turned= data.getTurned();
        currentDirection = data.getCurrDirection();
        groundFound = data.getGroundFound();
        range = data.getRange();
        rangeCheck = data.getRangeCheck();
        checkDone = data.getCheckDone();

        if(!(data.getTop())){
            return start.FourCorners(range, groundFound);
        }
        else if (!onGround && !interTurn){
            return island.Finder(); 
        }
        else if(interTurn){
            data.setTurned(true);
            
            return interlace.Turn();
        }
        else if(!(interTurn) && turned){
            return algoRun.search(currentDirection, batteryLevel, startingBatteryLevel, checkDone); 
        }
        else if (!(interTurn) && !(turned)){
            return algoRun.search(currentDirection, batteryLevel, startingBatteryLevel, checkDone); 
        }
        else{
            return algoRun.stop();
        }
    }
}
