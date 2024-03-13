package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//overall goal of this class; 1 track what moves made, 2 keep track of all POI found, 3 check distance from start to all creeks (so we know closest creek)
public class tracker {
    private final Logger logger = LogManager.getLogger();
    private int counter =0; //simple counter to check initally track number of actions performed 

    public void track(String action){
        counter++; 
        logger.info("Move number {} action {}",counter,action); 
        //maybe create a list, 2d array or dictionary (decided data structure later) that will keep track of decsion number and action
        //maybe have it be canonical (like in maze) where you can multiply if you go straight 4 times
        //dicstionary best for this but not sure how it i will track of order of moves)
    }

    public void POI(String type,int x, int y){
        //add code to track all important points
        //need to be able to sort points - maybe through if statments - so we know all creek points, all emergency, etc
    }

}
