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
    
    public void track(JSONObject scan){
        //get response string from scanner 
        

    }

    public void POI(String type,int x, int y){
        //add code to track all important points
        //need to be able to sort points - maybe through if statments - so we know all creek points, all emergency, etc
        
        //maybe create a list, 2d array or dictionary (decided data structure later) that will keep track of decsion number and action
        //maybe have it be canonical (like in maze) where you can multiply if you go straight 4 times
        //dicstionary best for this but not sure how it i will track of order of moves)
    }
}