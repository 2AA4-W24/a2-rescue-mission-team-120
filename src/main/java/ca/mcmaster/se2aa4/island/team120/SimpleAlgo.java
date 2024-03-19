package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

public class SimpleAlgo {
    //going to place our orginal algo for creeks - just going up and down the island no face implementations 
    //NEED TO CREATE INTERFACE FOR ALL ALGOS THAT GO IN NAVIGATION SYSTEM 
    private final Logger logger = LogManager.getLogger();
    private boolean onGround;
    JSONObject decision= new JSONObject();
    Actions action= new Actions();
    public SimpleAlgo(){
        this.onGround = onGround;
        this.decision= decision;
    }

    public String search(boolean onGround){
        while(onGround){
            String decision= action.fly();
            logger.info("** Decision: {}",decision.toString());
            return decision;
        }

        return decision.toString();
    }



}
