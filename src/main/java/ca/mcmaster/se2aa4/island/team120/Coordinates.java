package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Coordinates {
    private static int x = 0; 
    private static int y = 0; 

    private final Logger logger = LogManager.getLogger();

    public void location(String CurrentDirection){
        switch (CurrentDirection){
            case "N":
                y +=1;
                logger.info(y);
                break;
            case "W":
                x -=1;
                logger.info(x);
                break;
            case "S":
                y -=1;
                logger.info(y);
                break;
            case "E":
                x+=1;
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

