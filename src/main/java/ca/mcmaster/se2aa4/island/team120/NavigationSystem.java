package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavigationSystem {
    //place where we keep steps
    //first call to search for ground
    //then call to search on island to find POI's 
    private final Logger logger = LogManager.getLogger();

    FindIsland island = new FindIsland();
    SimpleAlgo run = new SimpleAlgo();
    
    Actions action= new Actions();
    Coordinates coords = new Coordinates(); 
    Direction dir = new Direction(); 
    Data data = new Data(); 

    int x; 
    int y; 
    
    int range_x_right;
    int range_x_left;
    int range_y_below; 
    int range_y_above; 
    boolean Top = false; 
    boolean turn = false; 

    public String run(String currentDirection, String lastChecked, int fly, int signal, String newDirection, boolean onGround, boolean groundFound, int scanned, int range, int rangeCheck, int batteryLevel, int startingBatteryLevel){  
        if (Top == false){
            logger.info("running top");
            return TopLeft(range, groundFound);
        }
        else{
            if (!onGround){
                logger.info("running island");
                return island.Finder(newDirection, onGround, groundFound); 
            }else{
                return run.search(onGround, currentDirection, range, batteryLevel, startingBatteryLevel); 
            }

        }
    }

    public String TopLeft(int range, boolean groundFound){
        logger.info("for");
        logger.info("flag");

        int count = data.getStage(); 
        logger.info(count);

        updateRanges(count, range, groundFound);
        switch (count) {
            case 0:
                logger.info(count);

                return action.echo(data.getCurrDirection());
            case 1:
                logger.info(count);
                return action.echo(Direction.left(data.getCurrDirection()));
            case 2:
                logger.info(count);
                return action.echo(Direction.right(data.getCurrDirection()));
            case 3:
                logger.info(count);
                String current = Direction.left(data.getCurrDirection());
                data.setCurrDirection(current);
                logger.info("I am here", current);
                return action.changeDirection(current);
            case 4:
                logger.info(count);
                logger.info("I am here2");
                return action.echo(Direction.left(data.getCurrDirection()));
                    
            case 5:
                logger.info(count);
                range_x_left += range;
                logger.info("Range_x_left: {}", range_x_left);

            default: 
                if (count < range_y_below + 5) {
                    logger.info("Fly");
                    logger.info("Count: {}", count);
                    return action.fly();
                }else{
                    logger.info("Done");
                    Top = true;
                    return null; 
                }
            }
    }

    private void updateRanges(int count, int range, boolean groundFound) {
        if (count == 1 && !groundFound) {
            range_x_right += range;
        } else if (count == 2 && !groundFound) {
            range_y_above += range;
        } else if (count == 3 && !groundFound) {
            range_y_below += range;
        }else if (count ==4 && !groundFound){
            range_x_left += range;
        }
        
        int range_x = range_x_left + range_x_right;
        int range_y = range_y_above + range_y_below; 
    }
}

        //int x = coords.x_coords(); 
        //int y = coords.y_coords(); 
    
        //current position = 0,0 
        //echo up
        //echo down
        //echo left
        //echo right 
        //int turn = 0; 
                // Continue actions based on the current count
             //range in echo up + down = y
        //range in echo left + right = x 

        //from spot keep fly up for range 
        //from spot keep fly left/east for range 
        //update coords 