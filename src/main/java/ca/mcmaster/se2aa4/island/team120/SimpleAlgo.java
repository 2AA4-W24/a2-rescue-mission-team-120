package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

public class SimpleAlgo implements SearchIsland{
    //going to place our orginal algo for creeks - just going up and down the island no face implementations 
    //NEED TO CREATE INTERFACE FOR ALL ALGOS THAT GO IN NAVIGATION SYSTEM 

    private final Logger logger = LogManager.getLogger();
 
    Actions action= new Actions();

    private int changeDir;
    private int count;
    Direction direction= new Direction();
    Data data= new Data();
    private int south;
    private int north;

    private boolean left;
    private boolean turned;
    private int rangeCheck;

    public String search(String currentDirection, int batteryLevel, int startingBatteryLevel, boolean checkDone){

        String decision="";
        this.changeDir= data.getChangeDirAlgo();
        this.count= data.getCountAlgo();
        this.south= data.getSouthAlgo();
        this.north= data.getNorthAlgo();
        this.left= data.getIsStartingLeft();
        this.turned= data.getTurned();
        this.rangeCheck = data.getRangeCheck();


        //once we reach left most island and want to start search
        while(batteryLevel> 0.15*startingBatteryLevel){
            if(count==0 && rangeCheck>=0 && checkDone){
                data.setCountAlgo(1);
                return action.scan();
            }
            else if(count==1 && rangeCheck>=0 && changeDir!=6 && checkDone){
                data.setCountAlgo(2);
                return action.echo(currentDirection);
            }
            else if(count==2 && rangeCheck>=0 && checkDone){
                logger.info("MAP FLY");
                data.setCountAlgo(0);
                return action.fly();
            }

        
            else if(rangeCheck<0 && changeDir== 0 ){
                logger.info("PREPARING FOR TURN. MOVING AHEAD");
                data.setChangeDirAlgo(1);
                data.setCheckDone(false);
                data.setCountAlgo(4);
                return action.fly();
            }
            else if(changeDir== 1 && !checkDone){
                logger.info("SCAN CAN NEW TILE");
                data.setChangeDirAlgo(2);
                return action.scan();
            }

            else if(changeDir== 2  && !checkDone){
                return secondDirStep(currentDirection);
            }
            else if(changeDir== 3 && !checkDone){
                return thirdDirStep();
            }
            else if(changeDir== 4){       
            logger.info("HELLO SECOND DIR STEP");
                return secondTurn(currentDirection);
            }
            else if(changeDir== 5){
                logger.info("THIRD DIR STEP, IN CORRECT POS");
                decision= action.echo(currentDirection);
                data.setChangeDirAlgo(6);
                return decision;
            }
            else if(rangeCheck>=0 && changeDir==6){
                logger.info("TURN SUCCESS");
                return turnSuccess();
            }  
            else if(rangeCheck<0 && changeDir== 6){
                logger.info("BEYOND ISLAND BOUNDS");
                return beyondMapBounds(currentDirection, turned);
            }
        }
        logger.info("BATTERY LEVEL BELOW THRESHOLD");
        return action.stop();
    }
    


    public String secondDirStep(String currentDirection){
        data.setChangeDirAlgo(3);
        if ((north==1 && turned) || (south==1 && !(turned))){
            logger.info("PREPARING FOR ECHOING LEFT.");
            return action.echo(Direction.left(currentDirection));
        }
        else{
            logger.info("PREPARING FOR ECHOING RIGHT.");
            return action.echo(Direction.right(currentDirection));
        }

    }
    public String thirdDirStep(){
        if(rangeCheck>=0 && rangeCheck<=2){
            logger.info("CONTINUE MOVING FORWARD");
            data.setChangeDirAlgo(1);                
            return action.fly();
        }
        else{
            logger.info("CHECK ECHO");
            logger.info("TURN STARTING");
            return firstTurn(left);

        }

    }
    public String firstTurn(boolean left){
        if (left){
            data.setChangeDirAlgo(4);
            return action.changeDirection("E");
        }
        else{
            data.setChangeDirAlgo(4);
            return action.changeDirection("W");
        }

    }
    public String secondTurn(String currentDirection){
        data.setChangeDirAlgo(5);

        if ((currentDirection.equals("E")|| currentDirection.equals("W"))  && south==1){
            data.setNorthAlgo(1);
            data.setSouthAlgo(0);
            return action.changeDirection("N");
        }
        else{
            data.setNorthAlgo(0);
            data.setSouthAlgo(1);
            return action.changeDirection("S");
        }
    }

    public String turnSuccess(){
        data.setCheckDone(true);
        data.setCountAlgo(0);
        data.setChangeDirAlgo(0);
        return action.scan();
    }

    public String beyondMapBounds(String currentDirection, boolean turned){
        data.setInterTurn(true);
        data.setOnGround(false);
        data.setFly(1);
        data.setSignal(0);
        data.setScanned(1);
        data.setLastDirection(Direction.left(currentDirection));
        if(!turned){
            return action.scan();
        }
        else{
            return action.stop();
        }
    }
}


