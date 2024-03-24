package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class FindIsland {
    private final Logger logger = LogManager.getLogger();
    private static String lastChecked;
    static String currentDirection;
    private static String newDirection;

    private static Integer count;
    private static Integer phase;

    private static Boolean notInPos;
    private static String beforeTurn;
    private static Boolean groundFound;
    private static Boolean onGround;

    Data data = new Data();
    Actions task = new Actions();

    public String finder(){
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

        //after the echo phase is performed, if ground is found in a new direction, switch heading to that direction
        if ((groundFound && newDirection != currentDirection)){
            data.setBeforeTurnDir(currentDirection);
            data.setCurrDirection(newDirection);
            return task.changeDirection(data.getCurrDirection());
        }

        //if ground has been found, begin phase pattern to get to the first bit of land found
        switch (phase){
            case 0:
                if (groundFound){
                    //as algo skips one column of land, initiate action pattern to go back
                    if (notInPos){
                        return getInPos(rightDir, leftDir);
                    }
                    data.setPhase(1);
                    return task.fly();
                }
                //if ground still hasn't been found, continue ground checking pattern
                else{
                    return checkGround(rightDir, leftDir);
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
                throw new IllegalArgumentException("Phase Invalid");
        }
    }

    //method to check if theres ground located to the left, right, and in front of the drone
    //if land is detected, update heading to that direction through decisionBoard class
    private String checkGround(String rightDir, String leftDir){
        if (lastChecked.equals(currentDirection)){
            data.setLastDirection(rightDir);
            return task.echo(rightDir);
        } 

        else if (lastChecked.equals(rightDir)){
            data.setLastDirection(leftDir);
            return task.echo(leftDir);
        } 

        else if (lastChecked.equals(leftDir)){
            data.setLastDirection(currentDirection);
            data.setPhase(4);
            return task.echo(currentDirection);
        }
        return "";
    }

    // method for drone to get in the correct position/coordinates to begin land exploration
    // shifts one x coordinate over to account for the missed column
    private String getInPos(String rightDir, String leftDir){
        data.setBeforeTurnDir(currentDirection);
        switch (count){
            case 0: 
                data.setCounter(1);
                if (data.getInitialEastWest().equals("E")){
                    data.setNewDirection(rightDir);
                }
                else if (data.getInitialEastWest().equals("W")){
                    data.setNewDirection(leftDir);
                }
                return task.scan();

            case 1:
                data.setCounter(2);
                return task.fly();

            case 2:
                data.setCounter(3);
                if (data.getInitialEastWest().equals("E")){
                    data.setNewDirection(leftDir);
                }
                else if (data.getInitialEastWest().equals("W")){
                    data.setNewDirection(rightDir);
                }
                return task.scan();
            case 3:
                data.setCounter(4);
                if (data.getInitialEastWest().equals("E")){
                    data.setNewDirection(leftDir);
                }
                else if (data.getInitialEastWest().equals("W")){
                    data.setNewDirection(rightDir);
                }
                return task.scan();
            case 4:
                data.setNotInPos(false);
                if (data.getInitialEastWest().equals("E")){
                    data.setNewDirection(rightDir);
                }
                else if (data.getInitialEastWest().equals("W")){
                    data.setNewDirection(leftDir);
                }
                return task.scan();
            default:
                throw new IllegalArgumentException("Invalid Counter Step");
        }
    }
}