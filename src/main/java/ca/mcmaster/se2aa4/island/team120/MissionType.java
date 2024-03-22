package ca.mcmaster.se2aa4.island.team120;

import java.io.IOException;

public interface MissionType {
    //THIS WILL ALLOW YOU TO HAVE A RUN METHOD FOR BOTH ARIEAL AND LAND RESCUES 
    String run(String currentDirection, String newDirection, boolean onGround, boolean groundFound, int scanned, int range, int rangeCheck, int batteryLevel, int startingBatteryLevel, boolean checkDone);
}