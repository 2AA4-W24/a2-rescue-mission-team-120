package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class InterTurn{
    private final Logger logger = LogManager.getLogger();
    private static Integer fly;
    private static Integer signal;
    private static Integer scanned;
    private static String lastChecked;
    private static String currentDirection;
    private static Integer count;

    /* 
    turning mechanism for interlacing after done traversing once

    1. echo left and right, if groundfound in lastchecked direction then keep flying in that direction until ground is not found
    2. hardcode turn left of currentdirection then fly once then turn left again of currentdirection and return back to map traversal


    1. echo left, check if ground found, 
    2. if groundfound then keep echoing
    3. if no groundfound then switch to new signal case

    1. CHECK both left and right echo. if foundground is passed back, immediately get the lastchecked and set new signal
    2. only echo in the lastchecked direction and fly until foundground is false
    3. then final phase 
    */

    Data data = new Data();
    Coordinates update = new Coordinates();

    public String Turn(String newDirection, boolean onGround, boolean groundFound){
        Actions task = new Actions();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        currentDirection = data.getCurrDirection();
        lastChecked = data.getLastDirection();

        fly = data.getFly();
        signal = data.getSignal();
        scanned = data.getScanned();
        count = data.getCountAlgo();

        String rightDir = Direction.right(currentDirection);
        String leftDir = Direction.left(currentDirection);
        
        //phase 1 of turning... checks for ref point to start get back in pos
        //echo and flying
        if (signal == 0 && fly == 1 && scanned == 1){
            logger.info("CHICKEN {}",currentDirection);
            if(groundFound){
                //last direction should be initialized from last echo statement
                logger.info("1ST PART FLY");
                data.setSignal(1);
                data.setFly(0);
                data.setScanned(1);
                return task.fly();
            }
            else if(lastChecked == leftDir){
                logger.info("FRIES");
                data.setLastDirection(rightDir);
                return task.echo(rightDir);
            }
            else if (lastChecked == rightDir){
                logger.info("HAMBURG");
                data.setLastDirection(leftDir);
                return task.echo(leftDir);
            }
        }
        else if (signal == 1 && fly == 0 && scanned == 1){
            //THIS finally sees that you're back at the starting place, and initiates process to turn and
            //get into interlacing position
            logger.info(groundFound);
            if (!groundFound){
                logger.info("REACHED END TIME TO CHAGNE DIR");
                data.setCountAlgo(0);
                data.setSignal(1);
                data.setFly(1);
                data.setScanned(1);  
                return task.scan();
            }
            else{
                logger.info("2RD PART FLY");
                logger.info(currentDirection);
                data.setSignal(1);
                data.setFly(1);
                data.setScanned(0);  
                return task.fly();
            }
        }
        else if (signal == 1 && fly == 1 && scanned == 0){
            logger.info("3RD PART ECHO");
            data.setSignal(1);
            data.setFly(0);
            data.setScanned(1);  
            return task.echo(lastChecked);
        }
        //changedirection, fly, change direction --> process to get into interlacing position
        //taking last direction should let you go either east or west depending on where land was detected :pp
        else if (signal == 1 && fly == 1 && scanned == 1){
            logger.info("STARTING CHANGING");
            if (groundFound && !(onGround)){
                //to finish the traversing just add the boolean for onGround and switch phases in navsys
                task.fly();
            }
            else if (count == 0){
                logger.info("PHASE 1");
                data.setCountAlgo(1);
                return task.changeDirection(lastChecked);
            }
            else if(count == 1){
                logger.info("PHASE 2");
                data.setCountAlgo(2);
                return task.fly();
            }
            else if (count ==2){
                logger.info("PHASE 3");
                logger.info("CURR DIRECTION after first change {}", currentDirection);
                data.setCountAlgo(3);
                return task.changeDirection(leftDir);
            }
            else if (count ==3){
                //now echo and check if theres land, if so get on the land and terminate/
                logger.info("PHASE 4");
                logger.info("CURR DIRECTION after second change {}", currentDirection);

                data.setCountAlgo(4);
                return task.scan();
            }
            else if (onGround){
                data.setInterTurn(false);
                return task.stop();
            }
        }
        // if last direction is left of curr direction then it should turn left again...
        // if its right of it then it should turn right but idk how to make it different
        // when you turn to the last checked, maybe echo both ways and get a new last checked value to turn to
        return decision.toString();
    }
}
