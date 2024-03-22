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

    public String Turn(){
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

        switch (phase){
            case 0:
                return firstPhase(task, leftDir, rightDir);
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
            case 3:
                return initTurning(task, lastChecked, currentDirection, goNorth, goSouth);

            default:
                throw new IllegalArgumentException("nope.");
            }

        }
        /*
        if (phase == 0){
            return firstPhase(task, leftDir, rightDir);
        }
        else if (phase == 1){
            if (data.getRange() > 2){
                data.setCountAlgo(0);
                data.setPhase(3);
                return task.scan();
            }
            else{
                data.setPhase(2);
                return task.fly();
            }
        }
        else if (phase == 2){
            data.setPhase(1);
            return task.echo(lastChecked);
        }

        else if (phase == 3){
             if (count == 0){
                logger.info("PHASE 1 CHANGE DIRECTION");
                data.setCountAlgo(1);
                data.setBeforeTurn(data.getCurrDirection());
                return task.changeDirection(lastChecked);
            }
            else if(count == 1){
                logger.info("PHASE 2 FLY");
                data.setLastDirection(currentDirection);
                data.setCountAlgo(2);
                return task.fly();
            }
            else if (count ==2){
                data.setCountAlgo(3);
                if (goNorth){
                    data.setSouthAlgo(1);
                    data.setNorthAlgo(0);
                    data.setBeforeTurn(data.getCurrDirection());
                    return task.changeDirection("S");

                }else if(goSouth){
                    data.setSouthAlgo(0);
                    data.setNorthAlgo(1);
                    data.setBeforeTurn(data.getCurrDirection());
                    return task.changeDirection("N");
                }
                return task.changeDirection(lastChecked);
            }
            else if (count == 3){
                data.setCountAlgo(4);
                return task.echo(currentDirection);
            }
            else if (count == 4){
                if (data.getReachGround()){
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
     */

    public String firstPhase(Actions task, String leftDir, String rightDir){
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

    public void goalDirection(String leftDir, String rightDir){
        if (lastChecked == leftDir){
            data.setGoNorth(true);
        }
        else if(lastChecked == rightDir){
            data.setGoSouth(true);
        }
    }

    public String initTurning(Actions task, String lastChecked, String currentDirection, Boolean goNorth, Boolean goSouth){
        switch(count){
            case 0:
                logger.info("PHASE 1 CHANGE DIRECTION");
                data.setCountAlgo(1);
                data.setBeforeTurn(data.getCurrDirection());
                return task.changeDirection(lastChecked);

            case 1:
                logger.info("PHASE 2 FLY");
                data.setLastDirection(currentDirection);
                data.setCountAlgo(2);
                return task.fly();

            case 2:
                data.setCountAlgo(3);
                if (goNorth){
                    data.setSouthAlgo(1);
                    data.setNorthAlgo(0);
                    data.setBeforeTurn(data.getCurrDirection());
                    return task.changeDirection("S");

                }else if(goSouth){
                    data.setSouthAlgo(0);
                    data.setNorthAlgo(1);
                    data.setBeforeTurn(data.getCurrDirection());
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
