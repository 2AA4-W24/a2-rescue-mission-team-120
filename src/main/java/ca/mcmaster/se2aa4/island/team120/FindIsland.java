package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class FindIsland {
     private final Logger logger = LogManager.getLogger();
    private static Integer fly;
    private static Integer signal;
    private static Integer scanned;
    private static String lastChecked;
    private static String currentDirection;

    Data data = new Data();
    Coordinates update = new Coordinates();

    public String Finder(String newDirection, boolean onGround, boolean groundFound, boolean lost){
        Actions task = new Actions();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        currentDirection = data.getCurrDirection();
        lastChecked = data.getLastDirection();
        logger.info(lastChecked);

        fly = data.getFly();
        signal = data.getSignal();
        scanned = data.getScanned();

        String rightDir = Direction.right(currentDirection);
        String leftDir = Direction.left(currentDirection);

        /*
        logger.info("POOOOOSIITIONNNN: [" + update.x_coords() + ", " + update.y_coords() + "]");
        logger.info("GROUND IS FOUND? {}",groundFound);
        logger.info("NEW DIR {}", newDirection);
        logger.info("CURR DIR {}", currentDirection);
        */
        
        /*if (lastChecked == currDirection){
            fly = 1;
            signal = 0;
            scanned = 1;
        }*/

        if ((groundFound && newDirection != currentDirection) || (lost && newDirection != currentDirection)){
            data.setCurrDirection(newDirection);
            return task.changeDirection(data.getCurrDirection());
        }
        //radar signal echo or scan depending on if you're on ground or not
        //if not on ground, scan in directions
        else if (signal == 0 && fly == 1 && scanned == 1){
            if (onGround){
                data.setSignal(1); // adding signal to stop echoing, start radaring
                data.setFly(0);
                data.setScanned(1);
                return task.scan();
            }

            else{
                //update signal command
                data.setSignal(1); 
                data.setFly(1);
                data.setScanned(0);

                if (lastChecked == currentDirection){
                    data.setLastDirection(rightDir);
                    return task.echo(rightDir);
                }
                else if (lastChecked == rightDir){
                    data.setLastDirection(leftDir);
                    return task.echo(leftDir);
                }
                else if (lastChecked == leftDir){
                    data.setLastDirection(currentDirection);
                    return task.echo(currentDirection);
                }
            }
        }

        else if (scanned == 0 && signal == 1 && fly == 1){
            data.setSignal(1); 
            data.setFly(0);
            data.setScanned(1);

            return task.scan();
        }

        else if (scanned == 1 && signal == 1 && fly == 0){
            update.location(currentDirection);
            data.setSignal(0); 
            data.setFly(1);
            data.setScanned(1);

            return task.fly();
        }
        return decision.toString();
    }
}