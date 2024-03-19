package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class FindIsland {
     private final Logger logger = LogManager.getLogger();
    //algo to find island from start
    //once find island going to traverse island using an algo implementation to find POI's 
    Coordinates update= new Coordinates(); 

    public String Finder(String currentDirection, String lastChecked, int fly, int signal, String newDirection, boolean onGround, boolean groundFound, int scanned, boolean lost){
        //check heading first; if a new direction to land has been found, change heading
        Actions task = new Actions();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();
        String rightDir = Direction.right(currentDirection);
        String leftDir = Direction.left(currentDirection);
        logger.info(leftDir);
        logger.info(rightDir);

        //should change heading whenever 1. ground is found through echo
        //2. if plane gets off island
        if ((groundFound && newDirection != currentDirection) || (lost && newDirection != currentDirection)){
            currentDirection = newDirection;
            decision.put("action", "heading");
            parameters.put("direction", currentDirection);
            decision.put("parameters", parameters);
            logger.info("** Decision: {}",decision.toString());
            //groundFound = false;
        }

        //radar signal echo or scan depending on if you're on ground or not
        //if not on ground, scan in directions
        else if (signal == 0 && fly == 1 && scanned == 1){
            if (onGround){
                decision.put("action","scan");
                logger.info("** Decision: {}",decision.toString());
                signal = 1; // adding signal to stop echoing, start radaring
                fly = 0;
                scanned = 1;
            }
            else{
                if (lastChecked == currentDirection){
                    lastChecked = rightDir;
                    signal = 1;
                    fly = 1;
                    scanned = 0;
                    task.echo(rightDir);
                    /* decision.put("action", "echo");
                    parameters.put("direction", rightDir);
                    decision.put("parameters", parameters);
                    logger.info("** Decision: {}",decision.toString()); */
                }
                else if (lastChecked == rightDir){
                    decision.put("action", "echo");
                    parameters.put("direction", leftDir);
                    decision.put("parameters", parameters);
                    logger.info("** Decision: {}",decision.toString());
                    lastChecked = leftDir;
                    signal = 1;
                    fly = 1;
                    scanned = 0;
                }
                else if (lastChecked == leftDir){
                    decision.put("action", "echo");
                    parameters.put("direction", currentDirection);
                    decision.put("parameters", parameters);
                    logger.info("** Decision: {}",decision.toString());
                    lastChecked = currentDirection;
                    signal = 1;
                    fly = 1;
                    scanned = 0;
                }
            }
        }

        else if (scanned == 0 && signal == 1 && fly == 1){
            decision.put("action","scan");
            logger.info("** Decision: {}",decision.toString());
            //lastChecked = currentDirection;
            fly = 0;
            signal = 1;
            scanned = 1;
        }

        else if (scanned == 1 && signal == 1 && fly == 0){
            decision.put("action", "fly");
            update.location(currentDirection); 
            logger.info("** Decision: {}",decision.toString());
            //lastChecked = currentDirection;
            fly = 1;
            signal = 0;
            scanned = 1;
        }
        return decision.toString();
    }
}
