package ca.mcmaster.se2aa4.island.team120;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Actions{
    //use this class instead of calling actions and placing parameters each time 

    private final Logger logger = LogManager.getLogger();
    JSONObject decision= new JSONObject();
    JSONObject parameters = new JSONObject();


    public String fly(){
        decision.put("action", "fly");
        return decision.toString();
    }
    
    public String changeDirection(String direction){
        decision.put("action", "heading");
        parameters.put("direction", direction);
        decision.put("parameters", parameters);
        return decision.toString();
    }

    public String echo(String direction){
        decision.put("action", "echo");
        parameters.put("direction", direction);
        decision.put("parameters", parameters);
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }

    /*public String scan(String direction){
        decision.put("action", "scan");
        parameters.put("direction", direction);
        decision.put("parameters", parameters);
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }*/


}
