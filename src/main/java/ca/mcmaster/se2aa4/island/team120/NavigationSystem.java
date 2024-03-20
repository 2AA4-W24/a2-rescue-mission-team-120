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

    public String run(String currentDirection, String lastChecked, int fly, int signal, String newDirection, boolean onGround, boolean groundFound, int scanned, boolean lost, int range, int batteryLevel, int startingBatteryLevel){  
        if (Top == false){
            logger.info("running top");
            return TopLeft(range, groundFound);
        }
        else{
            if (!onGround){
                logger.info("running island");
                return island.Finder(newDirection, onGround, groundFound, lost); 
            }else{
                return run.search(onGround, currentDirection, range, batteryLevel, startingBatteryLevel); 
            }

        }
    }

    public String TopLeft(int range, boolean groundFound){
        //int x = coords.x_coords(); 
        //int y = coords.y_coords(); 
    
        //current position = 0,0 
        //echo up
        //echo down
        //echo left
        //echo right 
        //int turn = 0; 
        
        logger.info("for");
        logger.info("flag");

        int count = data.getStage(); 

        updateRanges(count, range, groundFound);

        // Continue actions based on the current count
        switch (count) {
            case 0:
                return action.echo(data.getCurrDirection());
            case 1:
                return action.echo(Direction.left(data.getCurrDirection()));
            case 2:
                return action.echo(Direction.right(data.getCurrDirection()));
            case 3:
                String current = Direction.left(data.getCurrDirection());
                data.setCurrDirection(current);
                logger.info("I am here", current);
                return action.changeDirection(current);
            case 4:
                logger.info("I am here2");
                return action.echo(Direction.left(data.getCurrDirection()));
                    
            case 5:
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
                }
        }return null;
            
        //range in echo up + down = y
        //range in echo left + right = x 

        //from spot keep fly up for range 
        //from spot keep fly left/east for range 
        //update coords 
    }

    private void updateRanges(int count, int range, boolean groundFound) {
        if (count == 1 && !groundFound) {
            range_x_right += range;
        } else if (count == 2 && !groundFound) {
            range_y_above += range;
        } else if (count == 3 && !groundFound) {
            range_y_below += range;
        }

    }
}
