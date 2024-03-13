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
    private String action = "fly"; //set to stop for now 
    private String currentDirection; //so we can know from parsing info what our starter direction is 
    private JSONObject info;
    private int count=0;
    private int count_dir=0;

    private JSONObject decision = new JSONObject();
    private JSONObject parameters = new JSONObject();

    @Override
    public void initialize(String s) {
        
        logger.info("** Initializing the Exploration Command Center");
        info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Info first method: {}",info.toString());

        logger.info("** Initialization info:\n {}",info.toString(2));
        currentDirection = info.getString("heading");
        batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", currentDirection);
        logger.info("Battery level is {}", batteryLevel);

        

        //gather all creek info + emergency site info (rank is closest to furthest)
        //based on creek 1 - find ur x,y (x,y of creek given)
        //once you have ur x,y change ur direction as needed and move to creek - is that allowed to do u have to move forward first?
        // review decsions/actions and rules for them 
    }

    @Override
    public String takeDecision() {
        
               
        String rightDir = Direction.right(currentDirection);
        String leftDir = Direction.left(currentDirection);
        logger.info(leftDir);
        logger.info(rightDir);

        if(count_dir==0){
            decision.put("action", "scan");
            logger.info("** Decision: {}",decision.toString());       
        }
        else if(count_dir==1){
            logger.info("------------");
            decision.put("action", "echo");
            parameters.put("direction", currentDirection);
            decision.put("parameters", parameters);

        }
        else if(count_dir==2){
            logger.info("------------");
            logger.info("Now Checking Left");
            decision.put("action", "echo");
            parameters.put("direction", leftDir);
            decision.put("parameters", parameters);
        }   
        else if(count_dir==3){
            logger.info("------------");
            logger.info("Now Checking Right");
            decision.put("action", "echo");
            parameters.put("direction", rightDir);
            decision.put("parameters", parameters);
        }   
            
        else{
            decision.put("action","stop");
        }
        
        logger.info("** Decision: {}",decision.toString());
        count_dir++;
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
            decision.put("action", "stop");
            deliverFinalReport();
        }
       
   
        //extra info contains response for echo or scan
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
        
        logger.info("COUNTTTTT: " + count_dir);
        
        PhotoScanner scan= new PhotoScanner(extraInfo);
        Radar echo= new Radar(extraInfo);



        if(scan.isScanned()){
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
        //code does run to this point
        logger.info("hello world");
        return "no creek found";
    }

}