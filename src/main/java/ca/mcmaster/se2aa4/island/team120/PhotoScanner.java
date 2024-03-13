package ca.mcmaster.se2aa4.island.team120;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

public class PhotoScanner {

    private JSONObject response;
    private tracker track= new tracker(); 

    public PhotoScanner(JSONObject response){
        this.response= response;
    }

    //checks if scan action has been executed
    public boolean isScanned(){
        if(response.has("creeks")){
            return true;
        }
        return false;
    }


    public boolean isCreek(){
        if(isScanned()){
            if(response.getJSONArray("creeks").length()!=0){
                track.POI("Creek");
                return true;
            }
        }
        return false;
    }


    public boolean isSite(){
        if(isScanned()){
            if(response.getJSONArray("sites").length()!=0){
                track.POI("Emergency");
                return true;
            }
        }
        return false;
    }


    public String getCreek(){
        if(isCreek()){
            JSONArray id_arr = response.getJSONArray("creeks");
            String id= id_arr.getString(0);
            return id;
        }
        return "no creek";
    }

    public String getSite(){
        if(isSite()){
            JSONArray id_arr = response.getJSONArray("sites");
            String id= id_arr.getString(0);
            return id;
        }
        return "no sites";
    }


}

