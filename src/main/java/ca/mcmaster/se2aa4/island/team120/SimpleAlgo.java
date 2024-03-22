package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SimpleAlgo implements SearchIsland{
    //going to place our orginal algo for creeks - just going up and down the island no face implementations 
    //NEED TO CREATE INTERFACE FOR ALL ALGOS THAT GO IN NAVIGATION SYSTEM 

    private final Logger logger = LogManager.getLogger();
 
    Actions action= new Actions();
    Direction direction= new Direction();
    Data data= new Data();

    private int changeDir;
    private int south;
    private int north;
    private int count;
    private boolean left;
    private boolean turned;
    private int rangeCheck;

    public String search(String currentDirection, int batteryLevel, int startingBatteryLevel, boolean checkDone){
        this.changeDir= data.getChangeDirAlgo();
        this.count= data.getCountAlgo();
        this.south= data.getSouthAlgo();
        this.north= data.getNorthAlgo();
        this.left= data.getIsStartingLeft();
        this.turned= data.getTurned();
        this.rangeCheck = data.getRangeCheck();


        //once we reach left most island and want to start search
        while(batteryLevel> 0.15*startingBatteryLevel){
            if(rangeCheck>=0 && checkDone){
                switch(count) {
                    case 0:
                        data.setCountAlgo(1);
                        return action.scan();
                    case 1:
                        data.setCountAlgo(2);
                        return action.echo(currentDirection);
                    case 2:
                        logger.info("MAP FLY");
                        data.setCountAlgo(0);
                        return action.fly();
                    default:
                        throw new IllegalArgumentException("Invalid count value: " + count);
                }    
            }
            switch(changeDir) {
                case 0:
                    if (rangeCheck<0) {
                        return firstDirStep();
                    }
                    break;
                case 1:
                    return secondDirStep();
                case 2:
                    return thirdDirStep(currentDirection);
                case 3:
                    return fourthDirStep();
                case 4:
                    return secondTurn(currentDirection);
                case 5:
                    return fifthDirStep(currentDirection);
                case 6:
                    return sixthDirStep(currentDirection);
                    default:
                        throw new IllegalArgumentException("Invalid changeDir value: " + changeDir);
                    
            }
        }
        logger.info("BATTERY LEVEL BELOW THRESHOLD");
        return action.stop();
    }
    

    public String firstDirStep(){
        data.setChangeDirAlgo(1);
        data.setCheckDone(false);
        data.setCountAlgo(4);
        return action.fly();
    }

    public String secondDirStep(){
        data.setChangeDirAlgo(2);
        return action.scan();
    }

    public String thirdDirStep(String currentDirection){
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

    public String fourthDirStep(){
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
            data.setBeforeTurn(data.getCurrDirection());
            data.setChangeDirAlgo(4);
            return action.changeDirection("E");
        }
        else{
            data.setBeforeTurn(data.getCurrDirection());
            data.setChangeDirAlgo(4);
            return action.changeDirection("W");
        }

    }

    public String secondTurn(String currentDirection){
        data.setChangeDirAlgo(5);

        if ((currentDirection.equals("E")|| currentDirection.equals("W"))  && south==1){
            data.setBeforeTurn(data.getCurrDirection());
            data.setNorthAlgo(1);
            data.setSouthAlgo(0);
            return action.changeDirection("N");
        }
        else{
            data.setBeforeTurn(data.getCurrDirection());
            data.setNorthAlgo(0);
            data.setSouthAlgo(1);
            return action.changeDirection("S");
        }
    }

    public String fifthDirStep(String currentDirection){
        logger.info("THIRD DIR STEP, IN CORRECT POS");
        data.setChangeDirAlgo(6);
        return action.echo(currentDirection);

    }

    public String sixthDirStep(String currentDirection){
        if(rangeCheck>=0){
            logger.info("TURN SUCCESS");
            return turnSuccess();
        }
        else{
            logger.info("BEYOND ISLAND BOUNDS");
            return beyondMapBounds(currentDirection, turned);
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


