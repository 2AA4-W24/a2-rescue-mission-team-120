package ca.mcmaster.se2aa4.island.team120;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;
import java.util.HashMap;


//CHANGE  this file to store ID's and seperate hash map for emergency 

//overall goal of this class; 1 track what moves made, 2 keep track of all POI found, 3 check distance from start to all creeks (so we know closest creek)
public class Tracker{
    static Map<String, Integer> x_coords = new HashMap<>(); //#love 2c03 
    static Map<String, Integer> y_coords = new HashMap<>();

    static int[] emergency = new int[2];

    private static Logger logger = LogManager.getLogger();
    
    private static int creek_counter = 0; 
    private Coordinates coords = new Coordinates(); 

    private static double minDistance = Double.MAX_VALUE;
    private static String closestCreek;
    private static String ESite;

    public void POI(String type, String id){
        
        int x = coords.x_coords(); 
        int y = coords.y_coords(); 

        if (type == "Creek" && !(x_coords.containsKey(id))){
            creek_counter+=1; 
            x_coords.put(id, x);
            y_coords.put(id, y);
        }else if (type == "Emergency"){
            emergency[0] = x;
            emergency[1]= y; 
            ESite = id; 
        }
    }

    private static int getNumCreeks(){
        return creek_counter;
    }

    public static String getEmergencySite(){
        return "Emergency X coordinate: " + emergency[0] + "        Emergency y coordinate: " + emergency[1];
    }
    
    public static String CurrentClosest(){
        //print out first creek found until emergency site found and then do calculation to find closest creek 
        if (emergency[0] != 0 || emergency[1] != 0) {
            for (Map.Entry<String, Integer> entry : x_coords.entrySet()) {
                int CreekX = entry.getValue();
                int CreekY = y_coords.get(entry.getKey());

                double tempDistance = Math.sqrt(Math.pow(Math.abs(emergency[0]) -  Math.abs(CreekX), 2) + Math.pow(Math.abs(emergency[1]) - Math.abs(CreekY), 2)); 

                if (tempDistance<minDistance){
                    minDistance = tempDistance;
                    closestCreek = entry.getKey();
                }
            }
            return closestCreek; 
            
        }else{
            int middle = creek_counter / 2;
            int currentIndex = 0;
            
            for (String creekId : x_coords.keySet()){
                if (currentIndex == middle) {
                    return creekId;
                }
                currentIndex++;
            }
        }
        return "no creek found"; 
    }

    public static String getClosetCreekCoords(){
        String creekID = CurrentClosest();
        return "Closest Creek X coordinate: " + x_coords.get(creekID)+ "        Closest Creek y coordinate: " + y_coords.get(creekID);
    }
}

