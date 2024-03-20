package ca.mcmaster.se2aa4.island.team120;

public class Configuration {
    public Integer batteryLevel;
    public String currentDirection;
    public Integer range;
    public Integer fly;
    public Integer signal;
    public String lastChecked;
    public Boolean groundFound;
    public String newDirection;
    public Boolean onGround;
    public Integer scanned;
    public Boolean lost;

    // Constructor
    public Configuration() {
        // Initialize variables
        batteryLevel = 0;
        currentDirection = "";
        range = 0;
        fly = 1;
        signal = 0;
        lastChecked = "";
        groundFound = false;
        newDirection = "";
        onGround = false;
        scanned = 1;
        lost = false;
    }
}

