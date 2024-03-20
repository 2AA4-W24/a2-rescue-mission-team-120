package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class FindIsland {
     private final Logger logger = LogManager.getLogger();
    //algo to find island from start
    //once find island going to traverse island using an algo implementation to find POI's 

    private static Integer fly = 1;
    private static Integer signal = 0;
    private static Integer scanned = 1;
    
    private static String lastChecked;

    LastChecked lastDirection= new LastChecked();
    Coordinates update = new Coordinates();

    public String Finder(String currentDirection, String lastChecked, int fly, int signal, String newDirection, boolean onGround, boolean groundFound, int scanned, boolean lost){
        //check heading first; if a new direction to land has been found, change heading
        Actions task = new Actions();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();
        String rightDir = Direction.right(currentDirection);
        String leftDir = Direction.left(currentDirection);

        if (lastChecked == null){
            lastChecked = currentDirection;
        }

        if ((groundFound && newDirection != currentDirection) || (lost && newDirection != currentDirection)){
            currentDirection= newDirection;
            return task.changeDirection(currentDirection);
        }

        //radar signal echo or scan depending on if you're on ground or not
        //if not on ground, scan in directions
        else if (signal == 0 && fly == 1 && scanned == 1){
            if (onGround){
                signal = 1; // adding signal to stop echoing, start radaring
                fly = 0;
                scanned = 1;
                return task.scan();
            }

            else{
                //LAST CHEFCKED ISNTB EING UPDATED BEYON D CLASS
                if (lastChecked == currentDirection){
                    lastDirection.setLastDirection(rightDir);
                    logger.info("LAST CHECKED MANN {},", lastDirection.getLastDirection());
                    signal = 1;
                    fly = 1;
                    scanned = 0;
                    return task.echo(rightDir);
                }
                else if (lastChecked == rightDir){
                    lastDirection.setLastDirection(leftDir);
                    signal = 1;
                    fly = 1;
                    scanned = 0;
                    return task.echo(leftDir);
                }
                else if (lastChecked == leftDir){
                    lastDirection.setLastDirection(currentDirection);
                    signal = 1;
                    fly = 1;
                    scanned = 0;
                    return task.echo(currentDirection);
                }
            }
        }

        else if (scanned == 0 && signal == 1 && fly == 1){
            fly = 0;
            signal = 1;
            scanned = 1;
            return task.scan();
        }

        else if (scanned == 1 && signal == 1 && fly == 0){
            update.location(currentDirection); 
            fly = 1;
            signal = 0;
            scanned = 1;
            return task.fly();
        }
        return decision.toString();
    }
}