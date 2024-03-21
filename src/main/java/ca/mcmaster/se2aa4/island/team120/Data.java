package ca.mcmaster.se2aa4.island.team120;

public class Data {
    private static String last;
    private static int signal;
    private static int fly;
    private static int scanned;
    private static String current;
    private static int count;
    private static int changeDir;
    private static int north=0;
    private static int south=1;
    private static boolean turn = false;
    private static boolean ground = false;

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
}
