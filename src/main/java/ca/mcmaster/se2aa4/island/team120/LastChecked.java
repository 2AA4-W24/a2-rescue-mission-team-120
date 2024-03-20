package ca.mcmaster.se2aa4.island.team120;

public class LastChecked {
    private static String last;
    private static int signal;
    private static int fly;
    private static int scanned;

    public void setLastDirection(String lastDirection){
        last= lastDirection;
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
}
