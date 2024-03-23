package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class FindIsland {
    private final Logger logger = LogManager.getLogger();
    private static String lastChecked;
    private static String currentDirection;
    private static String newDirection;

    private static Integer count;
    private static Integer phase;

    private static Boolean notInPos;
    private static String beforeTurn;
    private static Boolean groundFound;
    private static Boolean onGround;

    Data data = new Data();
    //Coordinates update = new Coordinates();

    public String Finder(){
        Actions task = new Actions();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        currentDirection = data.getCurrDirection();
        lastChecked = data.getLastDirection();
        newDirection = data.getNewDirection();
        beforeTurn = data.getBeforeTurnDir();
        groundFound = data.getGroundFound();
        onGround = data.getOnGround();

        count = data.getCounter();
        notInPos = data.getNotInPos();
        phase = data.getPhase();

        String rightDir = Direction.right(currentDirection);
        String leftDir = Direction.left(currentDirection);

        // new algo: echo in all directions and if nothing found fly and scan, if something found, 
        //set foundground to true and start flying in that direction repeatedly until on ground

        if ((groundFound && newDirection != currentDirection)){
            data.setBeforeTurnDir(currentDirection);
            data.setCurrDirection(newDirection);
            return task.changeDirection(data.getCurrDirection());
        }

        switch (phase){
            case 0:
                if (groundFound){
                    if (notInPos){
                        return getInPos(task, rightDir, leftDir);
                    }
                    data.setPhase(1);
                    return task.fly();
                }
                else{
                    return checkGround(task, rightDir, leftDir);
                }
            case 1:
                data.setPhase(2);
                return task.scan();
            case 2:
                data.setPhase(3);
                return task.fly();
            case 3:
                data.setPhase(4);
                return task.echo(currentDirection);
            case 4:
                data.setPhase(0);
                return task.fly();
            default:
                throw new IllegalArgumentException("nope.");
        }
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
            data.setLastDirection(currentDirection);
            data.setPhase(4);
            return task.echo(currentDirection);
        }
        return "";
    }

    public String getInPos(Actions task, String rightDir, String leftDir){
        data.setBeforeTurnDir(currentDirection);
        switch (count){
            case 0: 
                data.setCounter(1);
                data.setNewDirection(rightDir);
                return task.scan();
            case 1:
                data.setCounter(2);
                return task.fly();
            case 2:
                data.setCounter(3);
                data.setNewDirection(leftDir);
                return task.scan();
            case 3:
                data.setCounter(4);
                data.setNewDirection(leftDir);
                return task.scan();
            case 4:
                data.setNotInPos(false);
                data.setNewDirection(rightDir);
                return task.scan();
            default:
                throw new IllegalArgumentException("nope.");
        }
    }
}