/*package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;

public class DecisionBoard {
    Radar radar = new Radar(extraInfo);
    PhotoScanner scan = new PhotoScanner(extraInfo);

    /* 
     * should acknowledge decisions and return results
     * call by hav ing multiple methods
     * 
     * 
    

    public void MakeDecision(){
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);

        if(!radar.isEchoed){
            groundFound = groundFound;
        }
        else{
            if(radar.isGround()){
                range = extraInfo.getInt("range");
                rangeCheck = 1;
                data.setNewDirection(data.getLastDirection());
                groundFound = true; 
                data.setReachGround(false);
                if (range == 0){
                    data.setOnGround(true);
                    data.setReachGround(true);
                    logger.info("REACHED ZERO");
                }
            }else{
                // out of range range
                range = extraInfo.getInt("range");
                rangeCheck = -1;
                groundFound = false;
            }
        }

        if(scan.isScanned()){
            //CHECK IF DRONE IS IN AN OCEAN ON SCAN, SET A NEW DIRECTION FOR A LOST
            if(!scan.verifyBiome()){
                logger.info("IN THE OCEAN");
            }
            else{
                JSONArray biomes = extraInfo.getJSONArray("biomes");
                logger.info("YOU'RE ON {}", biomes);
            }
        }
    }
}*/