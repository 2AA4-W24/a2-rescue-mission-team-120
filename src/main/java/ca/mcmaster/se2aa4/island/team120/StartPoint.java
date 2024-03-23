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
    public static int range; 

    int range_x_right;
    int range_x_left;
    int range_y_below; 
    int range_y_above; 

    String start_dir; 

    
    public String FourCorners(boolean groundFound){
        range = data.getRange();
        int count = data.getStage(); 
        logger.info("Count: "+ count);  

        if(count==0){
            if((data.getCurrDirection().charAt(0) != 'W')){
                return action.echo("E");
            }else{
                return action.scan();
            }
        }else if (count==1){
            if((data.getCurrDirection().charAt(0) != 'W')){
                data.setRange_x_right(range);
                logger.info(range);
            }
            if((data.getCurrDirection().charAt(0) != 'E')){
                return action.echo("W"); //TURN RANGE INTO DATA POINT!!
            }else{
                return action.scan();
            }

        }else if (count == 2){
            if((data.getCurrDirection().charAt(0) != 'E')){
                data.setRange_x_left(range);
                logger.info(range);
            }
            if((data.getCurrDirection().charAt(0) != 'S')){
                return action.echo("N");
            }else{
                return action.scan();
            }

        }else if (count ==3){
            if((data.getCurrDirection().charAt(0) != 'S')){
                data.setRange_y_above(range);
                logger.info(range);
            }
            if((data.getCurrDirection().charAt(0) != 'N')){
                return action.echo("S");
            }else{
                return action.scan();
            }

        }else if (count == 4){

            if((data.getCurrDirection().charAt(0) != 'N')){
                data.setRange_y_below(range);
                logger.info(range);
            }

            int range_x_right = data.getRange_x_right();
            int range_x_left = data.getRange_x_left();
            int range_y_below = data.getRange_y_below(); 
            int range_y_above = data.getRange_y_above(); 

            return Inwards(range_x_left, range_x_right, range_y_above, range_y_below);
            
        }else if (count >=5){
            //metho for all this shit 
            int range_x_right = data.getRange_x_right();
            int range_x_left = data.getRange_x_left();
            int range_y_below = data.getRange_y_below(); 
            int range_y_above = data.getRange_y_above(); 

            return corners(range_x_right, range_x_left, range_y_above, range_y_below);
        }
        return null;
    }

    public String TopLeft(int range){
        //top left
        logger.info("Top Left");

        int range_x_left = data.getRange_x_left();
        int range_y_above = data.getRange_y_above(); 
        start_dir = data.getStart_dir(); 

        if (range_y_above == 0 && range_x_left ==0){
            data.setTop();
            return action.scan();
        }else{ 
            if (data.getStart_dir() == "E"){
                return LFR(range_y_above, start_dir); 
            }else if(data.getStart_dir() == "S"){//repeated with top right second parth 
                //turn west and go forward turn back south -stop one before 
                return RFL(range_x_left, start_dir);
            }
        }
        return null;
    }
  
    
    public String TopRight(int range){
        logger.info("Top right");
        
        int range_x_right = data.getRange_x_right();
        int range_y_above = data.getRange_y_above(); 
        start_dir = data.getStart_dir(); 

        if (range_y_above == 0 && range_x_right ==0){
            data.setTop();
            return action.scan();
        }else{ 
            if (data.getCurrDirection() == "W"){
                return RFL(range_y_above, start_dir);

            }else if(data.getCurrDirection() == "S"){
                return LFR(range_x_right, start_dir); 
            }
        }
        return null;
    }

    public String BotRight(int range){
        logger.info("Bot right");
        int count = data.getStage(); 
        
        int range_x_right = data.getRange_x_right();
        int range_y_below = data.getRange_y_below(); 
        start_dir = data.getStart_dir(); 

        if (range_y_below == 0 && range_x_right ==0){
            data.setTop();
            return action.scan();
        }else{ 
            if (data.getCurrDirection() == "W"){
                return RFL(range_y_below, start_dir);

            }else if(data.getCurrDirection() == "N"){//repeated 
                return LFR(range_x_right, start_dir); 
            }
        }
        return null;
    }

    public String BotLeft(int range){
        //top right
        logger.info("Bot left");

        int range_x_left = data.getRange_x_left();
        int range_y_below = data.getRange_y_below(); 
        start_dir = data.getStart_dir(); 

        if (range_y_below == 0 && range_x_left ==0){
            logger.info(range_y_below);
            data.setTop();
            return action.scan();
        }else{ 
            if (data.getCurrDirection() == "W"){//repeated with bot right first part
                return LFR(range_x_left, start_dir); 

            }else if(data.getCurrDirection() == "N"){
                return RFL(range_y_below, start_dir);
            }
        }
        return null;
    }

    public String Inwards(int range_x_left, int range_x_right, int range_y_above, int range_y_below){
        if((range_x_left ==0 && range_y_above ==0) || (range_x_left ==0 && range_y_below ==0) || range_x_left == 0){
            if(data.getCurrDirection().charAt(0) != 'E'){
                data.setBeforeTurnDir(data.getCurrDirection()); 
                data.setStart_dir("E");
                return action.changeDirection("E");
            }else{
                data.setStart_dir("E");
                return action.scan();
            }
        }else if (range_x_right ==0 && range_y_above ==0 || range_x_right ==0 && range_y_below ==0 || range_x_right ==0){
            if(data.getCurrDirection().charAt(0) != 'W'){
                data.setBeforeTurnDir(data.getCurrDirection()); 
                return action.changeDirection("W");
            }else{
                data.setStart_dir("W");
                return action.scan();
            }
        }else if (range_y_above == 0){
            if(data.getCurrDirection().charAt(0) != 'S'){
                data.setBeforeTurnDir(data.getCurrDirection()); 
                return action.changeDirection("S");
            }else{
                data.setStart_dir("S");
                return action.scan();
            }
        }else if (range_y_below ==0){
            if(data.getCurrDirection().charAt(0) != 'N'){
                data.setBeforeTurnDir(data.getCurrDirection()); 
                data.setStart_dir("N");
                return action.changeDirection("N");
            }else{
                data.setStart_dir("N");
                return action.scan();
            }
        }
        return null;
    }

    public String corners(int range_x_right, int range_x_left,int range_y_above,int range_y_below){
        if (range_x_right > range_x_left){
            if (range_y_below > range_y_above){
                data.setIsStartingLeft(true);
                return TopLeft(range);
            }else{
                return BotLeft(range);
            }
        }else{
            if (range_y_below > range_y_above){
                data.setIsStartingLeft(false);
                return TopRight(range);
                
            }else{
                return BotRight(range);
            }
        }
    }

    public String RFL(int range, String start_dir){
        logger.info("RFL");
        int count = data.getStage(); 
        if (count==5){
                data.setBeforeTurnDir(data.getCurrDirection()); 
                String Current = data.getCurrDirection();
                return action.changeDirection(Direction.right(Current));
        }else{
            logger.info(range);
            while(range > 3){
                if (data.getCurrDirection().charAt(0) == 'N'){
                    data.setRange_y_above((range-1)); 
                    return action.fly(); 
                }else if(data.getCurrDirection().charAt(0) == 'S'){
                    data.setRange_y_below((range-1)); 
                    return action.fly(); 
                }else if(data.getCurrDirection().charAt(0) == 'W'){
                    data.setRange_x_left((range-1)); 
                    return action.fly(); 
                }else if(data.getCurrDirection().charAt(0) == 'E'){
                    data.setRange_x_right((range-1)); 
                    return action.fly(); 
                }
            }
            if ((start_dir == "S" || start_dir == "N") && (data.getExtra() != true)){
                data.setBeforeTurnDir(data.getCurrDirection()); 
                String Current = data.getCurrDirection();
                data.setExtra(); 
                return action.changeDirection(Direction.left(Current));
            }
            data.setTop();
            data.setBeforeTurnDir(data.getCurrDirection()); 
            String Current = data.getCurrDirection();
            return action.changeDirection(Direction.left(Current));
        }
    }


    public String LFR(int range, String start_dir){
        logger.info("LFR");
        int count = data.getStage(); 
        if (count == 5){
            data.setBeforeTurnDir(data.getCurrDirection()); 
            String Current = data.getCurrDirection();
            return action.changeDirection(Direction.left(Current));
        }else{
            while(range > 3){
                if (data.getCurrDirection().charAt(0) == 'N'){
                    data.setRange_y_above((range-1)); 
                }else if(data.getCurrDirection().charAt(0) == 'S'){
                    data.setRange_y_below((range-1)); 
                }else if(data.getCurrDirection().charAt(0) == 'W'){
                    data.setRange_x_left((range-1)); 
                }else if(data.getCurrDirection().charAt(0) == 'E'){
                    data.setRange_x_right((range-1)); 
                }
                return action.fly(); 
            }
            if ((start_dir == "S" || start_dir == "N") && (data.getExtra() != true)){
                data.setBeforeTurnDir(data.getCurrDirection()); 
                String Current = data.getCurrDirection();
                data.setExtra(); 
                return action.changeDirection(Direction.right(Current));
            }
            data.setTop();
            data.setBeforeTurnDir(data.getCurrDirection()); 
            String Current = data.getCurrDirection();
            return action.changeDirection(Direction.right(Current));
        }
    }
}