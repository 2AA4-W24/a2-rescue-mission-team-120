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
    private String action = "stop"; //set to stop for now 
    private String currentDirection; //so we can know from parsing info what our starter direction is 
    private Integer range;
    private Integer fly = 1;
    private Integer echo = 0;
    private String lastChecked;
    private Boolean groundFound = false;

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

        if (groundFound){
            decision.put("action", "heading");
            parameters.put("direction", currentDirection);
            decision.put("parameters", parameters);
            logger.info("** Decision: {}",decision.toString());
            groundFound = false;
        }
        else if (echo == 0 && fly == 1){
            if (lastChecked == currentDirection){
                decision.put("action", "echo");
                parameters.put("direction", rightDir);
                decision.put("parameters", parameters);
                logger.info("** Decision: {}",decision.toString());
                lastChecked = rightDir;
                echo = 1;
                fly = 0;
            }
            else if (lastChecked == rightDir){
                decision.put("action", "echo");
                parameters.put("direction", leftDir);
                decision.put("parameters", parameters);
                logger.info("** Decision: {}",decision.toString());
                lastChecked = leftDir;
                echo = 1;
                fly = 0;
            }
            else if (lastChecked == leftDir){
                decision.put("action", "echo");
                parameters.put("direction", currentDirection);
                decision.put("parameters", parameters);
                logger.info("** Decision: {}",decision.toString());
                lastChecked = currentDirection;
                echo = 1;
                fly = 0;
            }
        }

        else if (echo == 1 && fly == 0){
            decision.put("action", "fly");
            logger.info("** Decision: {}",decision.toString());
            lastChecked = currentDirection;
            fly = 1;
            echo = 0;
        }
        
        /* 
        if (i==1){
            decision.put("action", "echo");
            parameters.put("direction", leftDir);
            decision.put("parameters", parameters);
            logger.info("** Decision: {}",decision.toString());
            lastDir = leftDir;
        }else if(i==2){
            decision.put("action", "echo");
            parameters.put("direction", rightDir);
            decision.put("parameters", parameters);
            logger.info("** Decision: {}",decision.toString());
            lastDir = rightDir;
        }else if(i==3){ //NEED TO UPDATE DIRECTION AFTER TURNING 
            decision.put("action", "heading");
            parameters.put("direction", currentDirection);
            decision.put("parameters", parameters);
            logger.info("** Decision: {}",decision.toString());
        }
        i++;
        */

        //decision.put("action", action); // we stop the exploration immediately
        //logger.info("** Decision: {}",decision.toString());
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
            action = "stop";
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
            range = extraInfo.getInt("range");
            logger.info("YOU'RE {} AWAY", range);
            logger.info("SWITCHING DIRECTION...");
            currentDirection = lastChecked;
            logger.info("NEW DIRECTION {}", currentDirection);
            groundFound = true; 
        }
    }

    @Override
    public String deliverFinalReport() {//code does not run to this point? how does bot work exactly
        return "no creek found";
    }

}
