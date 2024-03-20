package ca.mcmaster.se2aa4.island.team120;

public class Data {
    private static String last;
    private static int signal;
    private static int fly;
    private static int scanned;
    private static String current;
    private static int stage;

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

    public int getStage(){
        return stage;
    }

    public void setStage(int count){
        stage = count;
    }
}
