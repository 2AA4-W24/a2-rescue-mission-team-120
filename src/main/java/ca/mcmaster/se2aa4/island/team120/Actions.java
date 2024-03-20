package ca.mcmaster.se2aa4.island.team120;

import org.json.JSONObject;
import org.json.JSONTokener;

import scala.annotation.meta.param;

import org.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Actions{
    //use this class instead of calling actions and placing parameters each time 

    private final Logger logger = LogManager.getLogger();
    JSONObject decision = new JSONObject();
    JSONObject parameters = new JSONObject();
    Data data= new Data();

    Coordinates coords = new Coordinates(); 
    Data data = new Data(); 


    public String fly(){
        decision.put("action", "fly");
       coords.location(data.getCurrDirection()); 
        return decision.toString();
    }
    
    public String changeDirection(String direction){
        decision.put("action", "heading");
        parameters.put("direction", direction);
        decision.put("parameters", parameters);
        data.setCurrDirection(direction);
        return decision.toString();
    }

    public String echo(String direction){
        decision.put("action", "echo");
        parameters.put("direction", direction);
        decision.put("parameters", parameters);
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }

    public String scan(){
        decision.put("action", "scan");
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }

    public String stop(){
        decision.put("action", "stop");
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }
}
