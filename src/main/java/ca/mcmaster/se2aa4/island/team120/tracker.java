package ca.mcmaster.se2aa4.island.team120;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import java.util.Map;
import java.util.HashMap;



//overall goal of this class; 1 track what moves made, 2 keep track of all POI found, 3 check distance from start to all creeks (so we know closest creek)
public class tracker{
    static Map<String, Integer> x_coords = new HashMap<>(); //#love 2c03 
    static Map<String, Integer> y_coords = new HashMap<>();
    private final Logger logger = LogManager.getLogger();
    
    private static int creek_counter = 0; 
    private Coordinates coords = new Coordinates(); 

    public void POI(String type){
        
        int x = coords.x_coords(); 
        int y = coords.y_coords(); 

        if (type == "Creek"){
            creek_counter+=1; 

            x_coords.put("Creek"+creek_counter, x);
            y_coords.put("Creek"+creek_counter, y);
            logger.info("creek stored");
        }else{
            x_coords.put("Emergency", x);
            y_coords.put("Emergency", y);
            logger.info("site stored");
        }

    }
}