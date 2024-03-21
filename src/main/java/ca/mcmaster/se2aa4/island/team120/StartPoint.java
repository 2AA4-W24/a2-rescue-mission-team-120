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

    int range_x_right;
    int range_x_left;
    int range_y_below; 
    int range_y_above; 
    
    public String FourCorners(int range, boolean groundFound){
        int count = data.getStage(); 
        logger.info(count);  

        if(count==0){
            if((data.getCurrDirection().charAt(0) != 'W')){
                return action.echo("E");
            }else{
                return action.scan();
            }
        }else if (count==1){
            if((data.getCurrDirection().charAt(0) != 'W')){
                data.setRange_x_right(range);
            }

            if((data.getCurrDirection().charAt(0) != 'E')){
                return action.echo("W"); //TURN RANGE INTO DATA POINT!!
            }else{
                return action.scan();
            }

        }else if (count == 2){
            if((data.getCurrDirection().charAt(0) != 'E')){
                data.setRange_x_left(range);
            }

            if((data.getCurrDirection().charAt(0) != 'S')){
                return action.echo("N");
            }else{
                return action.scan();
            }

        }else if (count ==3){
            if((data.getCurrDirection().charAt(0) != 'S')){
                data.setRange_y_above(range);
            }

            if((data.getCurrDirection().charAt(0) != 'N')){
                return action.echo("S");
            }else{
                return action.scan();
            }

        }else if (count == 4){

            if((data.getCurrDirection().charAt(0) != 'N')){
                data.setRange_y_below(range);
            }

            return Inwards(range_x_left, range_x_right, range_y_above, range_y_below);

        }else if (count == 5){
            int range_x_right = data.getRange_x_right();
            int range_x_left = data.getRange_x_left();
            int range_y_below = data.getRange_y_below(); 
            int range_y_above = data.getRange_y_above(); 

            logger.info(range_y_above);
            logger.info(range_x_left);
            logger.info(range_y_below);
            logger.info(range_x_right);

            if (range_x_right > range_x_left){
                if (range_y_below > range_y_above){
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
        logger.info("Top Left");
        int count = data.getStage(); 

        int range_x_left = data.getRange_x_left();
        int range_y_above = data.getRange_y_above(); 

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
                        data.setRange_y_above(range_y_above--); 
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
                        data.setRange_x_left(range_x_left--); 
                        return action.fly(); 
                    }
                    if(range_x_left==1){
                        return action.changeDirection("N");
                    }
                    data.setTop();
                    return action.changeDirection("E");
                }
            }
        }
        return null;
    }
  
    
    public String TopRight(int range, boolean groundFound){
        //top right
        logger.info("Top right");
        int count = data.getStage(); 
        logger.info(count);
        
        int range_x_right = data.getRange_x_right();
        int range_y_above = data.getRange_y_above(); 

        if (range_y_above == 0 && range_x_right ==0){
            logger.info(range_y_above);
            logger.info ("hi!");
            data.setTop();
            return action.scan();
        }else{ 
            if (data.getCurrDirection() == "W"){
                if (count==0){
                    logger.info(count);
                    action.changeDirection("N");
                }else{
                    logger.info(count);
                    while(range_y_above > 1){
                        data.setRange_y_above(range_y_above--); 
                        return action.fly(); 
                    }
                    logger.info(range_y_above);
                    data.setTop();
                    return action.changeDirection("W");
                }

            }else if(data.getCurrDirection() == "S"){
                if (count==0){
                    action.changeDirection("E");
                }else{
                    while(range_x_right> 1){
                        data.setRange_x_right(range_x_right--); 
                        return action.fly(); 
                    }
                    if(range_x_right==1){
                        return action.changeDirection("N");
                    }
                    data.setTop();
                    return action.changeDirection("W");
                }
            }
        }
        return null;
    }

    public String BotRight(int range, boolean groundFound){
        //top right
        logger.info("Bot right");
        int count = data.getStage(); 
        logger.info(count);

        
        int range_x_right = data.getRange_x_right();
        int range_y_below = data.getRange_y_below(); 

        if (range_y_below == 0 && range_x_right ==0){
            logger.info(range_y_below);
            logger.info ("hi!");
            data.setTop();
            return action.scan();
        }else{ 
            if (data.getCurrDirection() == "W"){
                if (count==0){
                    logger.info(count);
                    action.changeDirection("S");
                }else{
                    logger.info(count);
                    while(range_y_below > 1){
                        data.setRange_y_below(range_y_below--); 
                        return action.fly(); 
                    }
                    logger.info(range_y_below);
                    data.setTop();
                    return action.changeDirection("W");
                }

            }else if(data.getCurrDirection() == "N"){
                if (count==0){
                    action.changeDirection("E");
                }else{
                    while(range_x_right> 1){
                        data.setRange_x_right(range_x_right--); 
                        return action.fly(); 
                    }
                    if(range_x_right==1){
                        return action.changeDirection("S");
                    }
                    data.setTop();
                    return action.changeDirection("W");
                }
            }
        }
        return null;
    }

    public String BotLeft(int range, boolean groundFound){
        //top right
        logger.info("Bot left");
        int count = data.getStage(); 
        logger.info(count);

        int range_x_left = data.getRange_x_left();
        int range_y_below = data.getRange_y_below(); 

        if (range_y_below == 0 && range_x_left ==0){
            logger.info(range_y_below);
            logger.info ("hi!");
            data.setTop();
            return action.scan();
        }else{ 
            if (data.getCurrDirection() == "W"){
                if (count==0){
                    logger.info(count);
                    action.changeDirection("S");
                }else{
                    logger.info(count);
                    while(range_y_below > 1){
                        data.setRange_y_below(range_y_below--); 
                        return action.fly(); 
                    }
                    logger.info(range_y_below);
                    data.setTop();
                    return action.changeDirection("W");
                }

            }else if(data.getCurrDirection() == "N"){
                if (count==0){
                    action.changeDirection("W");
                }else{
                    while(range_x_left> 1){
                        data.setRange_x_left(range_x_left--); 
                        return action.fly(); 
                    }
                    if(range_x_left==1){
                        return action.changeDirection("S");
                    }
                    data.setTop();
                    return action.changeDirection("E");
                }
            }
        }
        return null;
    }

    public String Inwards(int range_x_left, int range_x_right, int range_y_above, int range_y_below){
        if((range_x_left ==0 && range_y_above ==0) || (range_x_left ==0 && range_y_below ==0)){
            if(data.getCurrDirection().charAt(0) != 'E'){
                return action.changeDirection("E");
            }else{
                return action.scan();
            }

        }else if (range_x_right ==0 && range_y_above ==0 || range_x_right ==0 && range_y_below ==0){
            if(data.getCurrDirection().charAt(0) != 'W'){
                return action.changeDirection("W");
            }else{
                return action.scan();
            }

        }else if (range_x_left == 0){
            if(data.getCurrDirection().charAt(0) != 'E'){
                return action.changeDirection("E");
            }else{
                return action.scan();
            }

        }else if (range_x_right ==0){
            if(data.getCurrDirection().charAt(0) != 'W'){
                return action.changeDirection("W");
            }else{
                return action.scan();
            }

        }else if (range_y_above ==0){
            if(data.getCurrDirection().charAt(0) != 'S'){
                return action.changeDirection("S");
            }else{
                return action.scan();
            }

        }else if (range_y_below ==0){
            if(data.getCurrDirection().charAt(0) != 'N'){
                return action.changeDirection("N");
            }else{
                return action.scan();
            }
        }
        return null; 
    }
}

