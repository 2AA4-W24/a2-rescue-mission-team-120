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
    private static String newDirection;
    private static Integer count;
    private static Boolean notInPos;
    private static String beforeTurn;

    Data data = new Data();
    Coordinates update = new Coordinates();

    public String Finder(String newDirection, boolean onGround, boolean groundFound){
        Actions task = new Actions();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        currentDirection = data.getCurrDirection();
        lastChecked = data.getLastDirection();
        newDirection = data.getNewDirection();
        beforeTurn = data.getBeforeTurn();

        fly = data.getFly();
        signal = data.getSignal();
        scanned = data.getScanned();
        count = data.getCounter();
        notInPos = data.getNotInPos();

        String rightDir = Direction.right(currentDirection);
        String leftDir = Direction.left(currentDirection);

        // new algo: echo in all directions and if nothing found fly and scan, if something found, 
        //set foundground to true and start flying in that direction repeatedly until on ground

        if ((groundFound && newDirection != currentDirection)){
            logger.info("NEW SET");
            data.setBeforeTurn(currentDirection);
            data.setCurrDirection(newDirection);
            return task.changeDirection(data.getCurrDirection());
        }
        //radar signal echo or scan depending on if you're on ground or not
        //if not on ground, scan in directions
        else if (signal == 0 && fly == 1 && scanned == 1){
            if (groundFound){
                //should activate new pattern to check one pattern back
                if (notInPos){
                    logger.info("DOLPHIN");
                    return getInPos(task, rightDir, leftDir);
                }
                logger.info("WHALE");
                data.setSignal(1); // start flying
                data.setFly(0);
                data.setScanned(0);
                return task.fly();
            }
            else{
                //update signal command to chekc for ground
                return checkGround(task, rightDir, leftDir);
            }
        }
        else if (signal == 1 && fly == 0 && scanned == 0){
            data.setSignal(0); 
            data.setFly(0);
            data.setScanned(0);
            return task.scan();
        }
        else if (signal == 0 && fly == 0 && scanned == 0){
            data.setSignal(0); 
            data.setFly(1);
            data.setScanned(0);
            return task.fly();
        }
        else if (signal == 0 && fly == 1 && scanned == 0){
            data.setSignal(1); 
            data.setFly(1);
            data.setScanned(1);
            return task.echo(currentDirection);
        }

        else if (signal == 1 && fly == 1 && scanned == 1){
            //send back to decision maker
            data.setSignal(0); 
            data.setFly(1);
            data.setScanned(1);
            return task.fly();
        }

        return decision.toString();
    }

    public String checkGround(Actions task, String rightDir, String leftDir){
        if (lastChecked == currentDirection){
            data.setLastDirection(rightDir);
            return task.echo(rightDir);
        } 
        else if (lastChecked == rightDir){
            data.setLastDirection(leftDir);
            return task.echo(leftDir);
        } 
        else if (lastChecked == leftDir){
            //go fly
            data.setLastDirection(currentDirection);
            data.setSignal(1);
            data.setFly(1);
            data.setScanned(1);
            return task.echo(currentDirection);
        }
        return "";
    }

    public String getInPos(Actions task, String rightDir, String leftDir){
        if (count == 0){
            data.setCounter(1);
            data.setBeforeTurn(currentDirection);
            data.setNewDirection(rightDir);
            return task.scan();
        }
        else if(count == 1){
            data.setCounter(2);
            return task.fly();
        }
        else if (count == 2){
            data.setCounter(3);
            data.setBeforeTurn(currentDirection);
            data.setNewDirection(leftDir);
            return task.scan();
        }
        else if (count == 3){
            data.setCounter(4);
            data.setBeforeTurn(currentDirection);
            data.setNewDirection(leftDir);
            return task.scan();
        }
        else if (count == 4){
            data.setNotInPos(false);
            data.setBeforeTurn(currentDirection);
            data.setNewDirection(rightDir);
            return task.scan();
        }
        return "";
    }
}