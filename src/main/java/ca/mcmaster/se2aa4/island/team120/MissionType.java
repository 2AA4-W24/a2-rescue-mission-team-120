package ca.mcmaster.se2aa4.island.team120;

import java.io.IOException;

public interface MissionType {
    //THIS WILL ALLOW YOU TO HAVE A RUN METHOD FOR BOTH ARIEAL AND LAND RESCUES 
    String run(int batteryLevel, int startingBatteryLevel);
}