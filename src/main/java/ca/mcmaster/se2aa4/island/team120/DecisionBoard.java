package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

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

    public void makeDecision(JSONObject response){
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
        Radar radar = new Radar(extraInfo);
        PhotoScanner scan = new PhotoScanner(extraInfo);

        rangeCheck = data.getRangeCheck();
        range = data.getRange();
        reachGround = data.getReachGround();
        onGround = data.getOnGround();
        newDirection = data.getNewDirection();
        lastChecked = data.getNewDirection();
        currentDirection = data.getCurrDirection();

        // if decision made was was echo, get and update the information to pass back to the 
        // navigation system; if it finds land, update drone to go in the direction where land was found
        // if drone has reached land, update variables
        if(!radar.isEchoed()){
        }
        else{
            if(radar.isGround()){
                data.setRange(extraInfo.getInt("range"));
                data.setRangeCheck(extraInfo.getInt("range"));
                data.setNewDirection(data.getLastDirection());
                data.setGroundFound(true); 
                data.setReachGround(false);

                if (data.getRange() == 0){
                    data.setOnGround(true);
                    data.setReachGround(true);
                }
            }else{
                data.setRange(extraInfo.getInt("range"));
                data.setRangeCheck(-1);
                data.setGroundFound(false);
            }
        }

        // if last decision was scan, check if a creek or emergency site was scanned
        if(scan.isScanned()){
            scan.isCreek();
            scan.isSite();
        }
    }
}