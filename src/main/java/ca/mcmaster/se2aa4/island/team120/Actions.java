package ca.mcmaster.se2aa4.island.team120;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

 //use this class instead of calling actions and placing parameters each time 
public class Actions{
    private final Logger logger = LogManager.getLogger();
    JSONObject decision = new JSONObject();
    JSONObject parameters = new JSONObject();

    Tracker track = new Tracker();

    //allows coordinate updates when fly action is called
    Coordinates coords = new Coordinates(); 

    //allows current direction to be updated when changing heading
    Data data = new Data(); 


    //drone flying action
    public String fly(){
        decision.put("action", "fly");
        //updates coordinates based on current facing direction
        coords.flyLocation(data.getCurrDirection()); 

        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }
    
    //changes drone direction
    public String changeDirection(String direction){
        decision.put("action", "heading");
        parameters.put("direction", direction);
        decision.put("parameters", parameters);
        data.setCurrDirection(direction);
        //update coordinate based on changed direction
        coords.turnLocation(data.getBeforeTurnDir(), data.getCurrDirection());
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }

    //drone echoes in inputted direction
    public String echo(String direction){
        decision.put("action", "echo");
        parameters.put("direction", direction);
        decision.put("parameters", parameters);
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }

    //drone scans current tile
    public String scan(){
        decision.put("action", "scan");
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }

    //drone stops
    public String stop(){
        decision.put("action", "stop");
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }


}
