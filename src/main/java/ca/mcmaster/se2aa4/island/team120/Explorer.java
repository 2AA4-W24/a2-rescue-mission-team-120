package ca.mcmaster.se2aa4.island.team120;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Integer batteryLevel; //so we can track battery level 
    private String action = "stop"; //set to stop for now 
    private String currentDirection; //so we can know from parsing info what our starter direction is 
    private Integer range;
    private String creeks;
    private String biomes;
    private Integer fly = 1;
    private Integer signal = 0;
    private String lastChecked;
    private Boolean groundFound = false;
    private String newDirection;
    private Boolean onGround = false;
    private Integer scanned = 1;
    private Boolean lost = false;

    private int x;
    private int y; 
    
    Coordinates update= new Coordinates();
    Actions actions= new Actions();
    
    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));

        currentDirection = info.getString("heading");
        lastChecked = currentDirection;
        batteryLevel = info.getInt("budget");

        logger.info("The drone is facing {}", currentDirection);
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();
        String rightDir = Direction.right(currentDirection);
        String leftDir = Direction.left(currentDirection);
        logger.info(leftDir);
        logger.info(rightDir);

        //check heading first; if a new direction to land has been found, change heading
        //should change heading whenever 1. ground is found through echo
        //2. if plane gets off island
        if ((groundFound && newDirection != currentDirection) || (lost && newDirection != currentDirection)){
            currentDirection = newDirection;
            decision.put("action", "heading");
            parameters.put("direction", currentDirection);
            decision.put("parameters", parameters);
            logger.info("** Decision: {}",decision.toString());
            //groundFound = false;
        }

        //radar signal echo or scan depending on if you're on ground or not
        //if not on ground, scan in directions
        else if (signal == 0 && fly == 1 && scanned == 1){
            if (onGround){
                decision.put("action","scan");
                logger.info("** Decision: {}",decision.toString());
                signal = 1; // adding signal to stop echoing, start radaring
                fly = 0;
                scanned = 1;
            }
            else{
                if (lastChecked == currentDirection){
                    decision.put("action", "echo");
                    parameters.put("direction", rightDir);
                    decision.put("parameters", parameters);
                    logger.info("** Decision: {}",decision.toString());
                    lastChecked = rightDir;
                    signal = 1;
                    fly = 1;
                    scanned = 0;
                }
                else if (lastChecked == rightDir){
                    decision.put("action", "echo");
                    parameters.put("direction", leftDir);
                    decision.put("parameters", parameters);
                    logger.info("** Decision: {}",decision.toString());
                    lastChecked = leftDir;
                    signal = 1;
                    fly = 1;
                    scanned = 0;
                }
                else if (lastChecked == leftDir){
                    decision.put("action", "echo");
                    parameters.put("direction", currentDirection);
                    decision.put("parameters", parameters);
                    logger.info("** Decision: {}",decision.toString());
                    lastChecked = currentDirection;
                    signal = 1;
                    fly = 1;
                    scanned = 0;
                }
            }
        }

        else if (scanned == 0 && signal == 1 && fly == 1){
            decision.put("action","scan");
            logger.info("** Decision: {}",decision.toString());
            //lastChecked = currentDirection;
            fly = 0;
            signal = 1;
            scanned = 1;
        }

        else if (scanned == 1 && signal == 1 && fly == 0){
            decision.put("action", "fly");
            update.location(currentDirection); 
            logger.info("** Decision: {}",decision.toString());
            //lastChecked = currentDirection;
            fly = 1;
            signal = 0;
            scanned = 1;
        }

        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
       
        logger.info("The cost of the action was {}", cost);
        
        batteryLevel -= cost; 
        logger.info("Remaining battery{}", batteryLevel);

        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        
        if (batteryLevel==0){
            deliverFinalReport();
        }
       
        //check what direction is being echoed in
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
        logger.info("YUMMY");

        Radar radar = new Radar();
        PhotoScanner scan= new PhotoScanner(extraInfo);

        //if extras spots ground in direction, update dir
        /*
        if (!radar.verifyScan(extraInfo)){
            logger.info("ON OCEAN");
        }else{
            biomes = extraInfo.getString("biomes");
            if (radar.checkScan(extraInfo)){
                creeks = extraInfo.getString("creeks");

            }
            else{
                logger.info("CREEK NOT FOUND");
            }
        } */

        if (!radar.checkEcho(extraInfo)){
            logger.info("OUT OF RANGE");
            logger.info("CURR DIR {}", currentDirection);
            logger.info("LAST CHECKED {}",lastChecked);
        }else{
            range = extraInfo.getInt("range");
            if (range == 0){
                onGround = true;
            }
            logger.info("YOU'RE {} AWAY", range);
            logger.info("SWITCHING DIRECTION...");
            newDirection = lastChecked;
            //currentDirection = lastChecked;
            logger.info("NEW DIRECTION {}", newDirection);
            groundFound = true; 
        }

        if(scan.isScanned()){
            //CHECK IF DRONE IS IN AN OCEAN ON SCAN, SET A NEW DIRECTION FOR A LOST
            if(!scan.verifyBiome()){
                logger.info("IN THE OCEAN");
                if (onGround){
                    newDirection = Direction.left(currentDirection);
                    logger.info("NEW DIRECTION LOST {}", newDirection);
                    onGround = false;
                    lost = true;
                }
            }
            else{
                JSONArray biomes = extraInfo.getJSONArray("biomes");
                logger.info("YOU'RE ON {}", biomes);
            }

            if(!scan.isCreek() && !scan.isSite()){
                logger.info("NOT A CREEK OR EMERGENCY SITE, WE ARE ON WATAHHH!");
            }
            else if(!scan.isSite()){
                logger.info("NOT AN EMERGENCY SITE!");
                logger.info("MUST BE ON A CREEK");
            }
            else{
                logger.info("NOT A CREEK");
                logger.info("MUST BE ON AN EMERGENCY SITE!");
            }
        }
    }

    @Override
    public String deliverFinalReport() {//code does not run to this point? how does bot work exactly
        return "no creek found";
    }

}
