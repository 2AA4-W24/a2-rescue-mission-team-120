package ca.mcmaster.se2aa4.island.team120;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Radar {
    /* the drone has radar sensors on its nose (front) and left and right wings. 
    The sensor sends an echo signal. If the signal does not bounce back, it answers
    with an OUT_OF_RANGE response, meaning there is no land in this direction. 
    If it bounces back, it answers with a GROUND answer. 
    */

    private JSONObject response;

    private int north=0;
    private int east=0;
    private int south=0;
    private int west=0;
    
    public Radar(JSONObject response){
        this.response= response;
    }

    public boolean isEcho(){
        if (response.has("found")){
            return true;
        }
        return false;
    }


    public boolean groundFound(){
        if(response.getString("found").equals("GROUND")){
            return true;
        }
        return false;
    }


}