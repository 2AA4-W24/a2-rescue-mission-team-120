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
            if(groundFound){
                //last direction should be initialized from last echo statement
                data.setSignal(1);
                data.setFly(0);
                data.setScanned(1);                
            }
            else if(lastChecked == leftDir){
                data.setLastDirection(rightDir);
                return task.echo(rightDir);
            }
            else if (lastChecked == rightDir){
                data.setLastDirection(leftDir);
                return task.echo(leftDir);
            }
        }
        else if (signal == 1 && fly == 0 && scanned == 1){
            //THIS finally sees that you're back at the starting place, and initiates process to turn and
            //get into interlacing position
            if (!groundFound){
                data.setCountAlgo(0);
                data.setSignal(1);
                data.setFly(1);
                data.setScanned(1);  
            }
            else{
                data.setSignal(1);
                data.setFly(1);
                data.setScanned(0);  
                return task.fly();
            }
        }
        else if (signal == 1 && fly == 1 && scanned == 0){
            data.setSignal(1);
            data.setFly(0);
            data.setScanned(1);  
            return task.echo(lastChecked);
        }
        //changedirection, fly, change direction --> process to get into interlacing position
        //taking last direction should let you go either east or west depending on where land was detected :pp
        else if (signal == 1 && fly == 1 && scanned == 1){
            if (groundFound && !(onGround)){
                //to finish the traversing just add the boolean for onGround and switch phases in navsys
                task.fly();
            }
            else if (count == 0){
                return task.changeDirection(lastChecked);
            }
            else if(count == 1){
                return task.fly();
            }
            else if (count ==2){
                return task.changeDirection(lastChecked);
            }
            else if (count ==3){
                //now echo and check if theres land, if so get on the land and terminate/
                return task.echo(currentDirection);
            }
        }
        return decision.toString();
    }
}
