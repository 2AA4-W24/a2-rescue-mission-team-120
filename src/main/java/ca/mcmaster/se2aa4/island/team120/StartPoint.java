package ca.mcmaster.se2aa4.island.team120;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


public class StartPoint{
    private final Logger logger = LogManager.getLogger();

    Actions action= new Actions();
    Coordinates coords = new Coordinates(); 
    Direction dir = new Direction(); 
    Data data = new Data(); 

    public int x_size; 
    public int y_size; 

    int range_x_right = 0;
    int range_x_left = 0;
    int range_y_below = 0; 
    int range_y_above = 0; 
    
    public String FourCorners(int range, boolean groundFound){
        //call initial echo in directions 
        //echo east and west 
        //echo north and south 
        //add and get y
        // add and get x 
        //if more space to get to west (left) then go to top right
        //if more space needed to go to right then go to left 
        //if same go left 
        int count = data.getStage(); 
        logger.info(count);  //GET ECHO UNTURN SO NEED WAY TO CHECK TURN ECHO AND THEN COME BACK FOR ALL 
        logger.info(data.getCurrDirection());

        if(count==0){
            if((data.getCurrDirection().charAt(0) == 'E') || (data.getCurrDirection().charAt(0) == 'S') || (data.getCurrDirection().charAt(0) == 'N')){
                logger.info(data.getCurrDirection());
                return action.echo("E");//as long as current direction not west 
            }else{
                logger.info(data.getCurrDirection());
                logger.info(count); 
                return action.scan();
            }
        }else if (count==1){
            if((data.getCurrDirection().charAt(0) == 'E') || (data.getCurrDirection().charAt(0) == 'S') || (data.getCurrDirection().charAt(0) == 'N')){
                range_x_right = range; 
            }

            if((data.getCurrDirection().charAt(0) == 'W') || (data.getCurrDirection().charAt(0) == 'S') || (data.getCurrDirection().charAt(0) == 'N')){
                return action.echo("W"); //TURN RANGE INTO DATA POINT!!
            }else{
                return action.scan();
            }

        }else if (count == 2){
            if((data.getCurrDirection().charAt(0) == 'W') || (data.getCurrDirection().charAt(0) == 'S') || (data.getCurrDirection().charAt(0) == 'N')){
                range_x_left = range;//no echo so no rescan
                logger.info(range_x_left);
            }

            if((data.getCurrDirection().charAt(0) == 'E') || (data.getCurrDirection().charAt(0) == 'W') || (data.getCurrDirection().charAt(0) == 'N')){
                return action.echo("N");//as long as current direction not south
            }else{
                return action.scan();
            }

        }else if (count ==3){
            if((data.getCurrDirection().charAt(0) == 'E') || (data.getCurrDirection().charAt(0) == 'W') || (data.getCurrDirection().charAt(0) == 'N')){
                range_y_above = range;
            }

            if((data.getCurrDirection().charAt(0) == 'E') || (data.getCurrDirection().charAt(0) == 'S') || (data.getCurrDirection().charAt(0) == 'W')){
                return action.echo("S");//as long as current direction not north 
            }else{
                return action.scan();
            }

        }else if (count == 4){

            if((data.getCurrDirection().charAt(0) == 'E') || (data.getCurrDirection().charAt(0) == 'S') || (data.getCurrDirection().charAt(0) == 'W')){
                range_y_below = range; //get final range 
            }


            logger.info(range_y_above);
            logger.info(range_x_left);
            logger.info(range_y_below);
            logger.info(range_x_right);

            if (range_x_right >= range_x_left){
                if (range_y_below >= range_y_above){
                    data.setStage(0); 
                    return TopLeft(range, groundFound);
                }else{
                    data.setStage(0); 
                    return BotLeft(range, groundFound);
                }

            }else{
                if (range_y_below > range_y_above){
                    data.setStage(0); 
                    return TopRight(range, groundFound);
                    
                }else{
                    data.setStage(0); 
                    return BotRight(range, groundFound);
                }
            }

        }
        return null;
    }
    //start edges 
    //wesr-> top left or bot lef - facing east, your west echo is 0
    //east-> top right or bot right - facing west, your east echo = 0
    //north -> top right or top left - facing south, north echo zero
    //south -> bot right or bot left - facing north, south echo zero

    //east - fly forward, turn north or south, fly up/down, stop one before and turn (back to orginal direction - so east)
    public String TopLeft(int range, boolean groundFound){
        //top left
        //if echo to west was 0 - just go up and stop 1 square before top right and turn east 
        //if echo was not 0, make sure direction is west and move that many - 1 steps (then turn north)

        logger.info("Top Left");
        int count = data.getStage(); 
        logger.info(count);
        logger.info(range_y_above);
        logger.info(range_x_left);
        
        if (range_y_above == 0 && range_x_left ==0){
            logger.info(range_y_above);
            logger.info ("hi!");
            data.setTop();
            return action.scan();
        }else{ 
            if (data.getCurrDirection() == "E"){
                //turn north fly up turn back east - stop one before edge 
                if (count==0){
                    logger.info(count);
                    action.changeDirection("N");
                }else{
                    logger.info(count);
                    while(range_y_above > 1){
                        logger.info(range_y_above);
                        range_y_above--;
                        logger.info(range_y_above);
                        return action.fly(); 
                    }
                    logger.info(range_y_above);
                    data.setTop();
                    return action.changeDirection("E");
                }
            }else if(data.getCurrDirection() == "S"){
                //turn west and go forward turn back south -stop one before 
                if (count==0){
                    action.changeDirection("W");
                }else{
                    while(range_x_left> 1){
                        logger.info(range_x_left);
                        range_x_left--;
                        logger.info(range_x_left);
                        return action.fly(); 
                    }
                    logger.info(range_x_left);
                    data.setTop();
                    return action.changeDirection("S");
                }
            }
        }
        return null;
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

    public String BotRight(int range, boolean groundFound){
        //top right
        //if echo to east was 0 - just go up and stop 1 square before top left and turn west
        //if echo was not 0, make sure direction is est and move that many - 1 steps (then turn north)
        logger.info("Bot right");
        int count = data.getStage(); 
        logger.info(count);

        return null; 
    }

    public String BotLeft(int range, boolean groundFound){
        //top right
        //if echo to east was 0 - just go up and stop 1 square before top left and turn west
        //if echo was not 0, make sure direction is est and move that many - 1 steps (then turn north)
        logger.info("Bot left");
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