package ca.mcmaster.se2aa4.island.team120;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


public class Top{
    private final Logger logger = LogManager.getLogger();

    Actions action= new Actions();
    Coordinates coords = new Coordinates(); 
    Direction dir = new Direction(); 
    Data data = new Data(); 

    public int x_size; 
    public int y_size; 

    int range_x_right;
    int range_x_left;
    int range_y_below; 
    int range_y_above; 
    boolean Top = false; 
    boolean turn = false; 
    
    public String RightOrLeft(int range, boolean groundFound){
        //call initial echo in directions 
        //echo east and west 
        //echo north and south 
        //add and get y
        // add and get x 
        //if more space to get to west (left) then go to top right
        //if more space needed to go to right then go to left 
        //if same go left 
        int count = data.getStage(); 
        if(count==0){
            return action.echo("E");
        }else if (count==1){
            range_x_right = range; 
            return action.echo("W"); //TURN RANGE INTO DATA POINT!!

        }else if (count ==2){
            range_x_left = range;
            return action.echo("N");

        }else if (count ==3){
            range_y_above = range;
            return action.echo("S");

        }else if (count == 4){
            range_y_below = range; 
            if (range_x_right> range_x_left){
                return TopLeft(range, groundFound);
            }else{
                return TopRight(range, groundFound);
            }
        }
        return null;
    }

    public String TopLeft(int range, boolean groundFound){
        //top left
        //if echo to west was 0 - just go up and stop 1 square before top right and turn east 
        //if echo was not 0, make sure direction is west and move that many - 1 steps (then turn north)

        logger.info("Top Left");

        data.setStage(0); 
        int count = data.getStage(0); 
        logger.info(count);


    }
    
    
    public String TopRight(int range, boolean groundFound){
        //top right
        //if echo to east was 0 - just go up and stop 1 square before top left and turn west
        //if echo was not 0, make sure direction is est and move that many - 1 steps (then turn north)
        logger.info("Top right");
        int count = data.getStage(); 
        logger.info(count);

        return null; 
    }
    
    private void updateRanges(int range_x_left,int range_x_right,int range_y_above, int range_y_below) { 
        x_size = range_x_left + range_x_right;
        y_size = range_y_above + range_y_below; 
    }
}

/*        switch (count) {
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
        } */