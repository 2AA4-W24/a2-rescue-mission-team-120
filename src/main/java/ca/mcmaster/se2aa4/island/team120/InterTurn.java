package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class InterTurn{
    private final Logger logger = LogManager.getLogger();
    private static String lastChecked;
    private static String currentDirection;
    private static Integer count;
    private static Integer phase;
    private static boolean goSouth;
    private static boolean goNorth;

    Data data = new Data();
    Coordinates update = new Coordinates();
    Actions task = new Actions();


    public String turn(){
        Actions task = new Actions();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        currentDirection = data.getCurrDirection();
        lastChecked = data.getLastDirection();
        count = data.getCountAlgo();
        phase = data.getPhase();
        goNorth = data.getGoNorth();
        goSouth = data.getGoSouth();

        String rightDir = Direction.right(currentDirection);
        String leftDir = Direction.left(currentDirection);

        // breaks down the phases to get the drone back on the island after 
        // completing the first interlace phase in GridSearch
        switch (phase){
            // drone checks if land is to its right or left direction
            case 0:
                return firstPhase(leftDir, rightDir);
            // drone will continuously check in the same direction that land was last found
            // and fly in that direction until it is no longer found
            case 1:
                if (data.getRange() > 2){
                    data.setCountAlgo(0);
                    data.setPhase(3);
                    return task.scan();
                }
                else{
                    data.setPhase(2);
                    return task.fly();
                }
            case 2:
                data.setPhase(1);
                return task.echo(lastChecked);
            // drone will then initial turning process to finally get back on land, shifted one- 
            // coordinate over in order to allow for the second interlacing phase
            case 3:
                return initTurning(lastChecked, currentDirection, goNorth, goSouth);

            default:
                throw new IllegalArgumentException("nope.");
        }
    }
    
    // method for drone to check if land is land is to the right or left and if land is found,
    // then through goal direction, sets the end direction based on the direction of the land found
    public String firstPhase(String leftDir, String rightDir){
        if(data.getGroundFound()){
            data.setPhase(1);
            goalDirection(leftDir, rightDir);
            return task.fly();
        }
        else if(lastChecked == leftDir){
            data.setLastDirection(rightDir);
            return task.echo(rightDir);
        }
        else if (lastChecked == rightDir){
            data.setLastDirection(leftDir);
            return task.echo(leftDir);
        }
        return "";
    }

    // method to set the final end direction of the drone
    public void goalDirection(String leftDir, String rightDir){
        if (lastChecked == leftDir){
            data.setGoNorth(true);
        }
        else if(lastChecked == rightDir){
            data.setGoSouth(true);
        }
    }

    // method to initiate turning phase of the drone
    // once it's echoes no more land, it turns twice in that direction to perform a u-turn
    // then fly until land is reached again
    public String initTurning(String lastChecked, String currentDirection, Boolean goNorth, Boolean goSouth){
        switch(count){
            case 0:
                data.setCountAlgo(1);
                data.setBeforeTurnDir(data.getCurrDirection());
                return task.changeDirection(lastChecked);

            case 1:
                data.setLastDirection(currentDirection);
                data.setCountAlgo(2);
                return task.fly();
            
            // taking the end goal direction from the previous method, then turn in that direction
            case 2:
                data.setCountAlgo(3);
                if (goNorth){
                    data.setSouthAlgo(1);
                    data.setNorthAlgo(0);
                    data.setBeforeTurnDir(data.getCurrDirection());
                    return task.changeDirection("S");
                }else if(goSouth){
                    data.setSouthAlgo(0);
                    data.setNorthAlgo(1);
                    data.setBeforeTurnDir(data.getCurrDirection());
                    return task.changeDirection("N");
                }
                return task.changeDirection(lastChecked);

            case 3:
                data.setCountAlgo(4);
                return task.echo(currentDirection);

            case 4:
                if (data.getReachGround()){
                    data.setCountAlgo(5);
                    return task.fly();
                }
                data.setCountAlgo(3);
                return task.fly();

            case 5:
                data.setCountAlgo(6);
                return task.scan();

            case 6:
                data.setInterTurn(false);
                data.setIsStartingLeft(!(data.getIsStartingLeft()));
                return task.scan();

            default:
                throw new IllegalArgumentException("nope.");
        }
    }
}
