package ca.mcmaster.se2aa4.island.team120;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;
public class PhotoScanner {

    private JSONObject response;
    tracker track= new tracker();

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
                JSONArray id_arr = response.getJSONArray("creeks");
                String id= id_arr.getString(0);
                track.POI("Creek", id);
                return true;
            }
        }
        return false;
    }

    public boolean isSite(){
        if(isScanned()){
            if(response.getJSONArray("sites").length()!=0){
                JSONArray id_arr = response.getJSONArray("sites");
                String id= id_arr.getString(0);
                track.POI("Emergency", id);
                return true;
            }
        }
        return false;
    }

    public boolean verifyBiome(){
        // check for biome, if ocean then false
        // IF BIOME IS OCEAN, MANGROVE BUG GETS STUCK IN A CIRCLE. CHECK IF OCEAN IS ONLY ONE AND IF SO FALSE
        JSONArray biomes = response.getJSONArray("biomes");
        boolean partOcean = false;
        if(isScanned()){
            for (int i = 0; i < biomes.length(); i++){
                String biome = biomes.getString(i);
                if ("OCEAN".equals(biome)){
                    partOcean = true;
                }
                else{
                    return true;
                }
            }
            return (!partOcean);
        }
        return false;
    }

}

