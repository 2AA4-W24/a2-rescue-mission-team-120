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

        boolean landfound = false; 
        int count = 1; 

        while (landfound == false){
            if (count==1){
                decision.put("action", "echo");
                parameters.put("direction", currentDirection);

                logger.info(decision);
                logger.info(parameters);

                boolean groundFound = radar.checkEcho(parameters);

                if (groundFound) {
                    logger.info("found land");
                    landfound=true; 
                    decision.put("action", "stop");
                } else {
                    logger.info("no land");
                    currentDirection = Direction.turnRight(currentDirection);
                    count = 2; 
                }

            }else if (count==2){
                decision.put("action", "fly");
                logger.info("fly");
                currentDirection = Direction.turnLeft(currentDirection);
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
/*    public String takeDecision() {
    JSONObject decision = new JSONObject();
    JSONObject parameters = new JSONObject();
    JSONObject echo = new JSONObject(); 

    int count = 0; 
    while (count != 5000) {
        decision.put("action", "echo");
        parameters.put("direction", currentDirection);

        if (echo.has("found")){
            String found = echo.getString("found");
            if ("GROUND".equals(found)){
                logger.info("found land");
                decision.put("action", "stop");
            }else{
                logger.info("no land");
                currentDirection = Direction.turnRight(currentDirection);
                decision.put("action", "fly");
                currentDirection = Direction.turnLeft(currentDirection);
            }
        }
    }   
    decision.put("action", "stop");
    return decision.toString();
}
 */

        /*JSONObject decision = new JSONObject();
        decision.put("action", action); // we stop the exploration immediately
        logger.info("** Decision: {}",decision.toString());

        return decision.toString();*/

        //echo and check the left direction, then right direction 
        //NEEDS TO UPDATE HEADING THROUGH ACTION BEFORE ECHOING
        //if curr dir is changed then update heading first
        //make echo conditional?? echo should be called when 
        //setting up decisions so that the drone scans first; if its ocean itll echo
        //echo from all sides and fly until ground is found; 
        //once ground is found start scanning for creeks
        //if scan is ocean again, start echoing for land again 
        
        /* if (i==1){
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

        //decision.put("action", action); // we stop the exploration immediately
        //logger.info("** Decision: {}",decision.toString());
        return decision.toString(); */

                /*if(count_dir==0){
            decision.put("action", "scan");
            tracking.track(action);
            logger.info("** Decision: {}",decision.toString());
        }
        else if(count_dir==1){
            decision.put("action", "heading");
            tracking.track(action);
            parameters.put("direction", "N");
            decision.put("parameters", parameters);
            logger.info("** Decision: {}",decision.toString());
            
        }
        else{
            decision.put("action","stop");
            tracking.track(action);
        }
        count_dir++;
        return decision.toString();*/

        /*            if (count_dir==1){
                decision.put("action", "echo");
                parameters.put("direction", leftDir);
                decision.put("parameters", parameters);
                logger.info("** Decision: {}",decision.toString());
                logger.info("** Decision: {}", decision.toString());
            }else if(count_dir==){
                decision.put("action", "echo");
                parameters.put("direction", rightDir);
                decision.put("parameters", parameters);
                logger.info("** Decision: {}",decision.toString());
                lastDir = rightDir;
                count_dir = 3; 
                logger.info("** Decision: {}", decision.toString());
            }else if(count_dir==3){ //NEED TO UPDATE DIRECTION AFTER TURNING 
                decision.put("action", "heading");
                parameters.put("direction", currentDirection);
                decision.put("parameters", parameters);
                logger.info("** Decision: {}",decision.toString());
                count_dir = 1; 
                logger.info("** Decision: {}", decision.toString());
            }     
        } */

        /*        int count =0; 
    
        while (count!=8000) {
            decision.put("action", "echo");
            parameters.put("direction", currentDirection);

            logger.info(decision);
            logger.info(parameters);

            boolean groundFound = radar.checkEcho(parameters);

            if (groundFound) {
                logger.info("found land");
                decision.put("action", "stop");
                break;
            } else {
                logger.info("no land");
                currentDirection = Direction.turnRight(currentDirection);
                decision.put("action", "fly");
                currentDirection = Direction.turnLeft(currentDirection);
            }
            count++;
        }
 */