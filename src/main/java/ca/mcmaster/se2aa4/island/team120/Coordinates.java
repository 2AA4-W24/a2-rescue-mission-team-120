package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Coordinates {
    private static int x = 0; 
    private static int y = 0; 
    private static int grid = 3; 

    private final Logger logger = LogManager.getLogger();

    public void location(String CurrentDirection){
        switch (CurrentDirection){
            case "N":
                y +=grid;
                logger.info(y);
                break;
            case "W":
                x -=grid;
                logger.info(x);
                break;
            case "S":
                y -= grid;
                logger.info(y);
                break;
            case "E":
                x+=grid;
                logger.info(x);
                break;
            default:
                throw new IllegalStateException("INVALID DIRECTION");
        }
    } 

    public int x_coords(){
        return x;
    }

    public int y_coords(){
        return y;
    }

}

