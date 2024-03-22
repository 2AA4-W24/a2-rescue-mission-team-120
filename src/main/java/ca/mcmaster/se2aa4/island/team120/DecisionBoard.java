package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;

public class DecisionBoard {
    private final Logger logger = LogManager.getLogger();

    Data data = new Data(); 


    private static int rangeCheck;
    private static boolean onGround;
    private static boolean reachGround;
    private static String newDirection;
    private static String lastChecked;
    private static int range;
    private static String currentDirection;

    /*
     * should acknowledge decisions and return results
     * call by hav ing multiple methods
     * 
     * groundfound -
     * rangecheck -
     * range - 
     * onground +
     * reachground +
     * newdirewction +
     * lastdirection +
     * 
     *  */
     
    

    public void makeDecision(JSONObject response){
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
        Radar radar = new Radar(extraInfo);
        PhotoScanner scan = new PhotoScanner(extraInfo);

        //groundFound = data.getGround
        rangeCheck = data.getRangeCheck();
        range = data.getRange();
        reachGround = data.getReachGround();
        onGround = data.getOnGround();
        newDirection = data.getNewDirection();
        lastChecked = data.getNewDirection();
        currentDirection = data.getCurrDirection();

        if(!radar.isEchoed()){
            //data.setGroundFound(data.setGroundFound());
        }
        else{
            if(radar.isGround()){
                data.setRange(extraInfo.getInt("range"));
                data.setRangeCheck(extraInfo.getInt("range"));
                //rangeCheck = 1;
                data.setNewDirection(data.getLastDirection());
                data.setGroundFound(true); 
                data.setReachGround(false);
                if (data.getRange() == 0){
                    data.setOnGround(true);
                    logger.info("RYAN {}",onGround);
                    data.setReachGround(true);
                }
            }else{
                // out of range range
                logger.info("WTF");
                data.setRange(extraInfo.getInt("range"));
                data.setRangeCheck(-1);
                data.setGroundFound(false);
                //groundFound = false;
            }
        }

        if(scan.isScanned()){
            //CHECK IF DRONE IS IN AN OCEAN ON SCAN, SET A NEW DIRECTION FOR A LOST
            scan.isCreek();
            scan.isSite();
            if(!scan.verifyBiome()){
                logger.info("IN THE OCEAN");
                if (onGround){
                    //newDirection = Direction.left(currentDirection);
                    data.setNewDirection(Direction.left(currentDirection));
                }
            }
            else{
                JSONArray biomes = extraInfo.getJSONArray("biomes");
                logger.info("YOU'RE ON {}", biomes);
            }
        }
    }
}

/*
if (!radar.isEchoed()){
    groundFound = groundFound;
}else{
    if(radar.isGround()){
        range = extraInfo.getInt("range");
        rangeCheck = extraInfo.getInt("range");
        data.setNewDirection(data.getLastDirection());
        groundFound = true; 
        data.setReachGround(false);
        if (range == 0){
            data.setOnGround(true);
            data.setReachGround(true);
            logger.info("REACHED ZERO");
        }
    }else{
        range = extraInfo.getInt("range");
        rangeCheck = -1;
        groundFound = false;
    }
}
 */