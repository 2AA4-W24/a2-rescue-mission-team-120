package ca.mcmaster.se2aa4.island.team120;

public class Data {
    private static String last;
    private static int signal;
    private static int fly;
    private static int scanned;
    private static String current;
    private static int stage;
    private static int count;
    private static int changeDir;
    private static int north=0;
    private static int south=1;
    private static boolean checkDone= true;
    private static boolean top = false;
    private static int range_x_right = 0;
    private static int range_x_left = 0;
    private static int range_y_below = 0; 
    private static int range_y_above = 0; 
    private static int range; 


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

    

}
