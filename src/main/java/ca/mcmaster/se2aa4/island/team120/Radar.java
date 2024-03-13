package ca.mcmaster.se2aa4.island.team120;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

//take curr dir as echo heading 
//{ "action": "echo", "parameters": { "direction": "E" } }
//{ "cost": 1, "extras": { "range": 2, "found": "GROUND" }, "status": "OK" }
//{ "cost": 1, "extras": { "range": 0, "found": "OUT_OF_RANGE" }, "status": "OK"

public class Radar{
    private final Logger logger = LogManager.getLogger();

    public boolean checkEcho(JSONObject echo){
        if (echo.has("found")){
            String found = echo.getString("found");
            if ("GROUND".equals(found)){
                logger.info("YUP");
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    /*public boolean verifyScan(JSONObject scan){
        // check for biome, if ocean then false
        if (scan.has("biomes")){
            String biomes = scan.getString("biomes");
            if ("OCEAN".equals(biomes)){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }
    }

    public boolean checkScan(JSONObject scan){
        if (scan.has("creeks")){
            String creeks = scan.getString("creeks");
            if ("".equals(creeks)){
                return false;
            }
            else{
                return true;
            }
        }
        return false;
    }*/
}