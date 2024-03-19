package ca.mcmaster.se2aa4.island.team120;

import org.json.JSONObject;

public class SimpleAlgo {
    //going to place our orginal algo for creeks - just going up and down the island no face implementations 
    //NEED TO CREATE INTERFACE FOR ALL ALGOS THAT GO IN NAVIGATION SYSTEM 
    JSONObject decision = new JSONObject();
    
    public String search(){
        decision.put("action", "stop");
        return decision.toString(); 
    }
}
