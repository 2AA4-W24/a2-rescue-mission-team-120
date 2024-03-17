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
                y_coords();
                
            case "W":
                x -=1;
                logger.info(x);
                x_coords();
            case "S":
                y -=1;
                logger.info(y);
                y_coords();
            case "E":
                x+=1;
                logger.info(x);
                x_coords();
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
