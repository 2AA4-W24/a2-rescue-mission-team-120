package ca.mcmaster.se2aa4.island.team120;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Integer batteryLevel; //so we can track battery level 
    //private String action; 
    private String currentDirection; //so we can know from parsing info what our starter direction is 
    private Radar radar = new Radar(); 

    private PhotoScanner scanner = new PhotoScanner(); 

    //private JSONObject decision = new JSONObject();
    //private JSONObject parameters = new JSONObject();

    //private tracker tracking; 

    @Override
    public void initialize(String s) {
        
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));

        currentDirection = info.getString("heading");
        batteryLevel = info.getInt("budget");

        logger.info("The drone is facing {}", currentDirection);
        logger.info("Battery level is {}", batteryLevel);

        //gather all creek info + emergency site info (rank is closest to furthest)
        //based on creek 1 - find ur x,y (x,y of creek given)
        //once you have ur x,y change ur direction as needed and move to creek - is that allowed to do u have to move forward first?
        // review decsions/actions and rules for them 

        //tracker tracking = new tracker();
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();
    
        // Check for land in front
        decision.put("action", "echo");
        parameters.put("direction", currentDirection);
    
        logger.info(decision);
        logger.info(parameters);
    
        boolean groundFound = radar.checkEcho(parameters);
    
        if (groundFound) {
            logger.info("found land in front");
            decision.put("action", "stop");  // Stop if land is found
        } else {
            // Check for land on the left
            String leftDirection = Direction.turnLeft(currentDirection);
            parameters.put("direction", leftDirection);
    
            logger.info("Checking for land to the left");
            boolean leftGroundFound = radar.checkEcho(parameters);
    
            if (leftGroundFound) {
                logger.info("found land to the left");
                decision.put("action", "heading");
                decision.put("parameters", new JSONObject().put("direction", leftDirection));
            } else {
                // Check for land on the right
                String rightDirection = Direction.turnRight(currentDirection);
                parameters.put("direction", rightDirection);
    
                logger.info("Checking for land to the right");
                boolean rightGroundFound = radar.checkEcho(parameters);
    
                if (rightGroundFound) {
                    logger.info("found land to the right");
                    decision.put("action", "heading");
                    decision.put("parameters", new JSONObject().put("direction", rightDirection));
                } else {
                    // If no land found anywhere, step forward
                    logger.info("No land found in current direction, stepping forward");
                    decision.put("action", "fly");
                }
            }
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
        logger.info("Remaining battery {}", batteryLevel);

        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        
        if (batteryLevel==0){
            //action = "stop";
            //tracking.track(action);
            deliverFinalReport();
        }
       
        //check what direction is being echoed in
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
        logger.info("YUMMY");

        Radar radar = new Radar();

        //if extras spots ground in direction, update dir 
        if (!radar.checkEcho(extraInfo)){
            logger.info("OUT OF RANGE");
        }else{
            //range = extraInfo.getInt("range");
            //logger.info("YOU'RE {} AWAY", range);
            logger.info("SWITCHING DIRECTION...");
            //currentDirection = lastDir;
            logger.info("NEW DIRECTION {}", currentDirection);
        }
    }

    @Override
    public String deliverFinalReport() {//code does not run to this point? how does bot work exactly
        return "no creek found";
    }

}
