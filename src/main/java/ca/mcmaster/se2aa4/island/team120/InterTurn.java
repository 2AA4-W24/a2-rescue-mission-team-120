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
    private static boolean onGround;
    private static boolean reachGround;
    private static boolean goSouth;
    private static boolean goNorth;
    private static Integer range;
    private static boolean groundFound;

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

    /*
    new mechanism
    1. phase 1:echo left and right, if land to leftdir set lastchecked to rightdir
    3. turn lastchecked dir (rightdir) 3x
    2. phase 2: fly 3x and turn right again
    */

    Data data = new Data();
    Coordinates update = new Coordinates();

    public String Turn(){
        Actions task = new Actions();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        currentDirection = data.getCurrDirection();
        lastChecked = data.getLastDirection();

        fly = data.getFly();
        signal = data.getSignal();
        scanned = data.getScanned();
        count = data.getCountAlgo();
        reachGround = data.getReachGround();
        onGround = data.getOnGround();
        goNorth = data.getGoNorth();
        goSouth = data.getGoSouth();
        range = data.getRange();
        groundFound = data.getGroundFound();

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
                if (lastChecked == leftDir){
                    data.setGoNorth(true);
                }
                else if(lastChecked == rightDir){
                    data.setGoSouth(true);
                }
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
        //IF LAST DIRECTION CHECKED WAS LEFT, THEN SHOULD TURN IN THAT DIRECTION
        else if (signal == 1 && fly == 0 && scanned == 1){
            //THIS finally sees that you're back at the starting place, and initiates process to turn and
            //get into interlacing position
            logger.info(groundFound);
            if (range > 2){
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

             if (count == 0){
                logger.info("PHASE 1 CHANGE DIRECTION");
                data.setCountAlgo(1);
                data.setBeforeTurnDir(data.getCurrDirection());
                return task.changeDirection(lastChecked);
            }
            else if(count == 1){
                logger.info("PHASE 2 FLY");
                data.setLastDirection(currentDirection);
                data.setCountAlgo(2);
                return task.fly();
            }
            else if (count ==2){
                logger.info("north {}", goNorth);
                logger.info("south {}", goSouth);
                data.setCountAlgo(3);
                if (goNorth){
                    logger.info("GOING SOUTH");
                    data.setSouthAlgo(1);
                    data.setNorthAlgo(0);
                    data.setBeforeTurnDir(data.getCurrDirection());
                    return task.changeDirection("S");

                }else if(goSouth){
                    logger.info("GOING NORTH");
                    data.setSouthAlgo(0);
                    data.setNorthAlgo(1);
                    data.setBeforeTurnDir(data.getCurrDirection());
                    return task.changeDirection("N");

                }
                return task.changeDirection(lastChecked);
            }
            else if (count ==3){
                //now echo and check if theres land, if so get on the land and terminate/
                logger.info("PHASE 4");
                logger.info("CURR DIRECTION after second change {}", currentDirection);

                data.setCountAlgo(4);
                logger.info("ON GROUND? {}", onGround);
                return task.echo(currentDirection);
            }
            else if (count == 4){
                logger.info("reached? {}",reachGround);
                if (reachGround){
                    data.setCountAlgo(5);
                    return task.fly();
                }
                data.setCountAlgo(3);
                return task.fly();
            }
            else if(count==5){
                data.setCountAlgo(6);
                return task.scan();
            }
            else if(count==6){
                data.setInterTurn(false);
                data.setIsStartingLeft(!(data.getIsStartingLeft()));
                return task.scan();
            }
        }
        return decision.toString();
    }
}
