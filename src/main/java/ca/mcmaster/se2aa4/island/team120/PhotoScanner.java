package ca.mcmaster.se2aa4.island.team120;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;
import org.json.JSONTokener;
public class PhotoScanner {

    //checks if scan action has been executed
    public boolean isScanned(JSONObject scan){
        if(scan.has("creeks")){
            return true;
        }
        return false;
    }

    public boolean isCreek(JSONObject scan){
        if(isScanned(scan)){
            if(scan.getJSONArray("creeks").length()!=0){
                return true;
            }
        }
        return false;
    }

    public boolean isSite(JSONObject scan){
        if(isScanned(scan)){
            if(scan.getJSONArray("sites").length()!=0){
                return true;
            }
        }
        return false;
    }


}