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
    private String rightDir;
    private String leftDir;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));

        currentDirection = info.getString("heading");
        batteryLevel = info.getInt("budget");

        logger.info("The drone is facing {}", currentDirection);
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        Direction direction = Direction.valueOf(currentDirection);
        Direction rightDir = direction.rightDirection(); 
        Direction leftDir = direction.leftDirection();


        decision.put("action", action); // we stop the exploration immediately
        logger.info("** Decision: {}",decision.toString());

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
       
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
        Radar radar = new Radar(); //radar used everytime you fly forward
        //for now check if ground is found to the right or left, if it is turn to that direction
        //return new dir
        //check echo in both dirs, return range if found, check which ones shorter, update direction
        if (!radar.echoRight(extraInfo) && !radar.echoRight(extraInfo)){
            logger.info("NO GROUND DETECTED, KEEP MOVING");
        }else if(!radar.echoRight(extraInfo)){
            logger.info("GROUND DETECTED TO YOUR LEFT");
            currentDirection = leftDir;
        }else{
            logger.info("GROUND DETECTED TO YOUR RIGHT");
            currentDirection = rightDir;
        }
    }

    @Override
    public String deliverFinalReport() {//code does not run to this point? how does bot work exactly
        return "no creek found";
    }

}
