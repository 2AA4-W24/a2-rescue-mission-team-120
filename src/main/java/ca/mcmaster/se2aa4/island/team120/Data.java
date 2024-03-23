package ca.mcmaster.se2aa4.island.team120;

public class Data {
    private static String last;
    private static int signal;
    private static int fly;
    private static int scanned;
    private static String current;
    private static int stage = 0;
    private static int count;
    private static int changeDir;
    private static String newDir;
    private static int north=0;
    private static int south=1;
    private static boolean turn = false;
    private static boolean ground = false;
    private static boolean reached = false;
    private static boolean goNorth = false;
    private static boolean goSouth = false;
    private static boolean checkDone= true;
    private static boolean top;
    private static boolean extra;
    private static int range_x_right = 0;
    private static int range_x_left = 0;
    private static int range_y_below = 0; 
    private static int range_y_above = 0; 
    private static int range; 
    private static boolean isStartingLeft;
    public static boolean hasChangedDir=false;
    private static boolean inPos = true;
    private static int counting = 0;
    private static String beforeTurn;
    private static int rangeChecker = -1;
    private static boolean groundLocated;
    private static int phase = 0;
    private static String start_dir; 
    private static String initDir;



    public void setLastDirection(String lastDirection){
        last = lastDirection;
    }
    public String getLastDirection(){
        return last;
    }
    

    public void setSignal(int lastSignal){
        signal= lastSignal;
    }

    public int getSignal(){
        return signal;
    }

    
    public void setFly(int lastFly){
        fly= lastFly;
    }

    public int getFly(){
        return fly;
    }


    public void setScanned(int lastScanned){
        scanned= lastScanned;
    }

    public int getScanned(){
        return scanned;
    }


    public void setCurrDirection(String currentDirection){
        current = currentDirection;
    }

    public String getCurrDirection(){
        return current;
    }

    public void setNewDirection(String newDirection){
        newDir = newDirection;
    }

    public String getNewDirection(){
        return newDir;
    }

    public int getCountAlgo(){
        return count;
    }

    public void setCountAlgo(int nextCountAlgo){
        count=nextCountAlgo;
    }


    public int getChangeDirAlgo(){
        return changeDir;
    }

    public void setChangeDirAlgo(int nextChangeDirAlgo){
        changeDir= nextChangeDirAlgo;
    }


    public int getSouthAlgo(){
        return south;
    }

    public void setSouthAlgo(int newSouth){
        south= newSouth;
    }


    public int getNorthAlgo(){
        return north;
    }

    public void setNorthAlgo(int newNorth){
        north= newNorth;
    }

    public boolean getInterTurn(){
        return turn;
    }

    public void setInterTurn(boolean interTurn){
        turn = interTurn;
    }

    public boolean getOnGround(){
        return ground;
    }

    public void setOnGround(boolean onGround){
        ground = onGround;
    }

    public boolean getReachGround(){
        return reached;
    }

    public void setReachGround(boolean reachGround){
        reached = reachGround;
    }


    public boolean getGoNorth(){
        return goNorth;
    }

    public void setGoNorth(boolean north){
        goNorth = north;
    }

    public boolean getGoSouth(){
        return goSouth;
    }

    public void setGoSouth(boolean south){
        goSouth = south;
    }

    public boolean getCheckDone(){
        return checkDone;
    }

    public void setCheckDone(boolean newCheckDone){
        checkDone= newCheckDone;
    }

    public int getStage(){
        return stage;
    }

    public void setStage(int count){
        stage = count;
    }

    public boolean getTop(){
        return top; 
    }
    public void setTop(){
        top = true; 
    }

    public boolean getExtra(){
        return extra; 
    }
    public void setExtra(){
        extra = true; 
    }



    public int getRange_x_left(){
        return range_x_left;
    }

    public void setRange_x_left(int range){
        range_x_left = range;
    }

    public int getRange_x_right(){
        return range_x_right;
    }

    public void setRange_x_right(int range){
        range_x_right = range;
    }

    public int getRange_y_above(){
        return range_y_above;
    }

    public void setRange_y_above(int range){
        range_y_above = range;
    }

    public int getRange_y_below(){
        return range_y_below;
    }

    public void setRange_y_below(int range){
        range_y_below = range;
    }

    public boolean getIsStartingLeft(){
        return isStartingLeft;
    }
    
    public void setIsStartingLeft(boolean newIsStartingLeft){
        isStartingLeft= newIsStartingLeft;
    }

    public void setHasChangedDir(boolean newHasChangedDir){
        hasChangedDir= newHasChangedDir;
    }

    public boolean getHasChangedDir(){
        return hasChangedDir;
    }


    public boolean getNotInPos(){
        return inPos;
    }

    public void setNotInPos(boolean notInPos){
        inPos = notInPos;
    }

    public int getCounter(){
        return counting;
    }

    public void setCounter(int counter){
        counting = counter;
    }

    public String getBeforeTurnDir(){
        return beforeTurn;
    }

    public void setBeforeTurnDir(String beforeTurnDirection){
        beforeTurn = beforeTurnDirection;
    }

    //

    public int getRangeCheck(){
        return rangeChecker;
    }

    public void setRangeCheck(int rangeCheck){
        rangeChecker = rangeCheck;
    }

    public boolean getGroundFound(){
        return groundLocated;
    }

    public void setGroundFound(boolean groundFound){
        groundLocated = groundFound;
    }

    public int getRange(){
        return range;
    }

    public void setRange(int rangeInt){
        range = rangeInt;
    }

    public void setStart_dir(String dir){
        start_dir = dir;
    }

    public String getStart_dir(){
        return start_dir;
    }

    public String getInitialEastWest(){
        return initDir;
    }

    public void setInitialEastWest(String init){
        initDir = init;
    }

    public int getPhase(){
        return phase;
    }

    public void setPhase(int newPhase){
        phase = newPhase;
    }
}
