package ca.mcmaster.se2aa4.island.team120;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


//CHANGE  this file to store ID's and seperate hash map for emergency 

//overall goal of this class; 1 track what moves made, 2 keep track of all POI found, 3 check distance from start to all creeks (so we know closest creek)
public class Tracker{
    static Map<String, Integer> x_coords = new HashMap<>(); //#love 2c03 
    static Map<String, Integer> y_coords = new HashMap<>();

    static int[] emergency = new int[2];

    private final Logger logger = LogManager.getLogger();
    
    private static int creek_counter = 0; 
    private Coordinates coords = new Coordinates(); 

    public void POI(String type, String id){
        
        int x = coords.x_coords(); 
        int y = coords.y_coords(); 

        if (type == "Creek"){
            creek_counter+=1; 

            x_coords.put(id, x);
            y_coords.put(id, y);
            logger.info("creek stored", creek_counter);
        }else{
            emergency[0] = x;
            emergency[1]= y; 
            
            logger.info("site stored");
        }
    }

    public static int getNumCreeks(){
        return creek_counter;
    }
}

