package ca.mcmaster.se2aa4.island.team120;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;



//overall goal of this class; 1 track what moves made, 2 keep track of all POI found, 3 check distance from start to all creeks (so we know closest creek)
public class tracker {
    /*private final Logger logger = LogManager.getLogger();
    private int counter = 0;
    private ArrayList<Coordinate> creeks = new ArrayList<>();
    private ArrayList<Coordinate> emergencySites = new ArrayList<>();
    JSONObject scan = new JSONObject();


    public void track(String action){
        counter++; 
        logger.info("Move number {} action {}",counter,action); 
        
        //maybe create a list, 2d array or dictionary (decided data structure later) that will keep track of decsion number and action
        //maybe have it be canonical (like in maze) where you can multiply if you go straight 4 times
        //dicstionary best for this but not sure how it i will track of order of moves)
        if (scan.has("creeks") && scan.getJSONArray("creeks").length() != 0) {
            // Extracting creek coordinates from the scan result
            JSONObject creeksInfo = scan.getJSONObject("creeks");
            for (String creekName : creeksInfo.keySet()) {
                JSONObject creek = creeksInfo.getJSONObject(creekName);
                int x = creek.getInt("x");
                int y = creek.getInt("y");
                creeks.add(new Coordinate(x, y));
            }
        }

        if (scan.has("sites") && scan.getJSONArray("sites").length() != 0) {
            // Extracting emergency site coordinates from the scan result
            JSONObject sitesInfo = scan.getJSONObject("sites");
            for (String siteName : sitesInfo.keySet()) {
                JSONObject site = sitesInfo.getJSONObject(siteName);
                int x = site.getInt("x");
                int y = site.getInt("y");
                emergencySites.add(new Coordinate(x, y));
            }
        }
    }


    public class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Getter methods for x and y coordinates
        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }*/


}
