package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GridSearch implements SearchIsland{
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
    private boolean hasChangedDir;
    private int rangeCheck;
    private boolean checkDone;
    private String currentDirection;

    public String search(int batteryLevel, int startingBatteryLevel){
        this.changeDir= data.getChangeDirAlgo();
        this.count= data.getCountAlgo();
        this.south= data.getSouthAlgo();
        this.north= data.getNorthAlgo();
        this.left= data.getIsStartingLeft();
        this.hasChangedDir= data.getHasChangedDir();
        this.rangeCheck = data.getRangeCheck();
        this.checkDone= data.getCheckDone();
        this.currentDirection= data.getCurrDirection();


        //if the battery goes below 17.5% of its original battery, island search stops.
        while(batteryLevel> 0.175*startingBatteryLevel){

            //if drone senses the presence of ground ahead, it goes through
            //the island search cycle of scanning, echoing, and flying.
            if(rangeCheck>=0 && checkDone){
                switch(count) {
                    case 0:
                        data.setCountAlgo(1);
                        return action.scan();
                    case 1:
                        data.setCountAlgo(2);
                        return action.echo(currentDirection);
                    case 2:
                        data.setCountAlgo(0);
                        return action.fly();
                    default:
                        throw new IllegalArgumentException("Invalid count value: " + count);
                }    
            }

            
            switch(changeDir) {
                case 0:
                //once drone no longer senses ground ahead, it begins the turning cycle, and stops the island searching cycle
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
        //stop drone if battery goes below set threshold
        return action.stop();
    }
    
    //drone flies one step ahead to account for the square 3x3 tile which gets skipped while changing heading
    public String firstDirStep(){
        //moves onto next turning cycle step
        data.setChangeDirAlgo(1);
        //since the drone has yet to check if there is ground in the neighbouring tiles, checoDone is set to false
        data.setCheckDone(false);
        //prevents regular island search from executing
        data.setCountAlgo(4);
        return action.fly();
    }

    //scans the new tile that the drone flies ahead on
    public String secondDirStep(){
        //moves onto next turning cycle step
        data.setChangeDirAlgo(2);
        return action.scan();
    }

    //checks if the tile, in the direction that the drone is searching the island, is ground
    //this prevents ground from not being explored once the drones changes directions
    public String thirdDirStep(String currentDirection){
        //moves onto next turning cycle step for following search run
        data.setChangeDirAlgo(3);

        //checks if drone is facing north or drone is facing south, and echoes to the left depending on whether it is turned or not
        //because left is the direction the drone is moving towards/searching the island
        if ((north==1 && hasChangedDir) || (south==1 && !(hasChangedDir))){
            return action.echo(Direction.left(currentDirection));
        }
        else{
            //drone echoes to the right since it is completing the island search towards that direction
            return action.echo(Direction.right(currentDirection));
        }
    }

    //if the tiles do containg ground, the drone moves ahead to avoid not exploring it once it turns (goes back to case 2)
    //if there is no more ground in the neighbouring tiles, the drone moves onto the first step of the turn, which is to do a 
    //90 degree turn in that direction
    public String fourthDirStep(){
        //means there is land that might be missed when turning, must keep moving forward to avoid this
        if(rangeCheck>=0 && rangeCheck<=2){
            //loops back to previous turning cycle step
            data.setChangeDirAlgo(1);                
            return action.fly();
        }
        else{
            //no more land in the turning direction, proceeds to next step fo turning cycle
            return firstTurn(left);
        }
    }

    //first part of turning step to avoid the drone from doing a U-turn
    public String firstTurn(boolean left){
        //if drone is searching from left to right, drone turns east
        if (left){
            data.setBeforeTurnDir(data.getCurrDirection());
            //moves onto next turning cycle step
            data.setChangeDirAlgo(4);
            return action.changeDirection("E");
        }
        //if drone is searching from righ to left, drone turns west
        else{
            data.setBeforeTurnDir(data.getCurrDirection());
            //moves onto next turning cycle step
            data.setChangeDirAlgo(4);
            return action.changeDirection("W");
        }

    }

    //second part of turning step, completes the turning process
    public String secondTurn(String currentDirection){
        //moves onto next turning cycle step
        data.setChangeDirAlgo(5);

        //if drone was originally facing south, it turns north
        if ((currentDirection.equals("E")|| currentDirection.equals("W"))  && south==1){
            data.setBeforeTurnDir(data.getCurrDirection());
            //updates which direction the drone is currently facing
            data.setNorthAlgo(1);
            data.setSouthAlgo(0);
            return action.changeDirection("N");
        }
        //if drone was originally facing north, it faces south
        else{
            data.setBeforeTurnDir(data.getCurrDirection());
            //updates which direction the drone is currently facing
            data.setNorthAlgo(0);
            data.setSouthAlgo(1);
            return action.changeDirection("S");
        }
    }

    //drone echoes in new direction to make sure that there is land ahead
    public String fifthDirStep(String currentDirection){
        //moves onto next turning cycle step
        data.setChangeDirAlgo(6);
        return action.echo(currentDirection);

    }

    
    //checks if there is land to explore was turn is accomplished
    public String sixthDirStep(String currentDirection){
        //if there is land ahead, the drone continue the explore the remaining island
        if(rangeCheck>=0){
            return turnSuccess();
        }
        //if there is no land, rangeCheck<0, this means the drone has reach the island bound and must either change 
        //the directions of the search or stop
        else{
            return beyondMapBounds(currentDirection, hasChangedDir);
        }
    }

    //sets/resets variables to continue islandSearch
    public String turnSuccess(){
        //check for land in turning direction is now done since turn was successful
        data.setCheckDone(true);
        //resets step tracking variables
        data.setCountAlgo(0);
        data.setChangeDirAlgo(0);
        return action.scan();
    }

    //sets/resets variables to end island in current direction
    public String beyondMapBounds(String currentDirection, boolean hasChangedDir){
        data.setInterTurn(true);
        data.setOnGround(false);
        //data.setFly(1);
        //data.setSignal(0);
        //data.setScanned(1);
        data.setPhase(0);
        data.setLastDirection(Direction.left(currentDirection));

        //if the drone has not already changed direction of the island search, it turns back and scans the other half of the island 
        //that it missed in the first search iteration
        if(!hasChangedDir){
            return action.scan();
        }
        //if the drone has already search the island both ways, it ends the island search.
        else{
            return action.stop();
        }
    }
}


