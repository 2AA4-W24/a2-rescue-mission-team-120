package ca.mcmaster.se2aa4.island.team120;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


public class StartPoint{
    //initlaize needed general variables 
    private final Logger logger = LogManager.getLogger();

    Actions action= new Actions();
    Coordinates coords = new Coordinates(); 
    Direction dir = new Direction(); 
    Data data = new Data(); 

    public int x_size; 
    public int y_size; 
    public static int range; 


    String start_dir; 

    //run through count casses to scan intial surrondings and make a decision based o nthat 
    public String fourCorners(boolean groundFound){
        range = data.getRange();
        logger.info(range);
        int count = data.getStage(); 
        logger.info("Count: "+ count);  

        if(count==0){//scan east 

            data.setStart_dir((data.getCurrDirection()));

            if((data.getCurrDirection().charAt(0) != 'W')){
                return action.echo("E");
            }else{
                return action.scan();
            }
        }else if (count==1){//scan west 
            if((data.getCurrDirection().charAt(0) != 'W')){
                data.setRange_x_right(range);
            }
            if((data.getCurrDirection().charAt(0) != 'E')){
                return action.echo("W"); 
            }else{
                return action.scan();
            }

        }else if (count == 2){//scan north 
            if((data.getCurrDirection().charAt(0) != 'E')){
                data.setRange_x_left(range);
            }
            if((data.getCurrDirection().charAt(0) != 'S')){
                return action.echo("N");
            }else{
                return action.scan();
            }

        }else if (count ==3){//scan south
            if((data.getCurrDirection().charAt(0) != 'S')){
                data.setRange_y_above(range);
                logger.info(range);
            }
            if((data.getCurrDirection().charAt(0) != 'N')){
                return action.echo("S");
            }else{
                return action.scan();
            }

        }else if (count == 4){//setting last range 
            if((data.getCurrDirection().charAt(0) != 'N')){
                logger.info(range);
                data.setRange_y_below(range);
            }
            return action.scan();

        }else if (count>=5){//moving in direction needded 
            int range_x_right = data.getRange_x_right();
            int range_x_left = data.getRange_x_left();
            int range_y_below = data.getRange_y_below(); 
            int range_y_above = data.getRange_y_above(); 

            logger.info(range_x_right);
            logger.info(range_x_left);
            logger.info(range_y_below);
            logger.info(range_y_above);
            //choosing corner to go to 
            return corners(range_x_right, range_x_left, range_y_above, range_y_below);
        }
        return null;
    }

    public String topLeft(){//top left logic 
        //top left
        data.setIsStartingLeft(true);
        logger.info("Top Left");

        int range_x_right = data.getRange_x_right();
        int range_x_left = data.getRange_x_left();
        int range_y_below = data.getRange_y_below(); 
        int range_y_above = data.getRange_y_above(); 
        
        start_dir = data.getStart_dir(); 

        if ((range_y_above == 0) && (range_x_left==0)){//if already in corner 
            return Inwards(range_x_left,range_x_right, range_y_above, range_y_below);
        }else{ 
            if (data.getStart_dir().charAt(0) =='E'){
                return LFR(range_y_above, start_dir); 
            }else if(data.getStart_dir().charAt(0) == 'S'){
                return RFL(range_x_left, start_dir);
            }
        }
        return null;
    }
  
    
    public String topRight(){//top right logic 
        data.setIsStartingLeft(false);
        logger.info("Top right");
        
        int range_x_right = data.getRange_x_right();
        int range_x_left = data.getRange_x_left();
        int range_y_below = data.getRange_y_below(); 
        int range_y_above = data.getRange_y_above(); 
        
        start_dir = data.getStart_dir(); 

        logger.info(range_x_right);
        logger.info(range_y_above);

        if ((range_y_above == 0) && (range_x_right ==0)){//already in corner 
            return Inwards(range_x_left,range_x_right, range_y_above, range_y_below);
        }else{ 
            if (data.getStart_dir().charAt(0) == 'W'){
                return RFL(range_y_above, start_dir);

            }else if(data.getStart_dir().charAt(0) == 'S'){
                return LFR(range_x_right, start_dir); 
            }
        }
        return null;
    }

    public String botRight(){//bottom right logic 
        logger.info("Bot right");
        data.setIsStartingLeft(false);
        int range_x_right = data.getRange_x_right();
        int range_x_left = data.getRange_x_left();
        int range_y_below = data.getRange_y_below(); 
        int range_y_above = data.getRange_y_above(); 
        
        start_dir = data.getStart_dir(); 

        logger.info(range_x_right);
        logger.info(range_y_below);

        if ((range_y_below == 0) && (range_x_right ==0)){//already in corner 
            return Inwards(range_x_left,range_x_right, range_y_above, range_y_below);
        }else{ 
            logger.info(start_dir);
            if (data.getStart_dir().charAt(0) == 'W'){
                return LFR(range_y_below, start_dir);

            }else if(data.getStart_dir().charAt(0) == 'N'){
                return RFL(range_x_right, start_dir); 
            }
        }
        return null;
    }

    public String botLeft(){//bottom left logic 
        //top right
        logger.info("Bot left");
        data.setIsStartingLeft(true);

        int range_x_right = data.getRange_x_right();
        int range_x_left = data.getRange_x_left();
        int range_y_below = data.getRange_y_below(); 
        int range_y_above = data.getRange_y_above(); 
        
        start_dir = data.getStart_dir(); 

        if ((range_y_below == 0) && (range_x_left ==0)){//already in corner 
            return Inwards(range_x_left,range_x_right, range_y_above, range_y_below);
        }else{ 
            if (data.getStart_dir().charAt(0) == 'E'){
                return RFL(range_y_below, start_dir); 

            }else if(data.getStart_dir().charAt(0) == 'N'){
                return LFR(range_x_left, start_dir);
            }
        }
        return null;
    }

    //make sure starting point of corners is east or west 
    public String Inwards(int range_x_left, int range_x_right, int range_y_above, int range_y_below){

        if(((range_x_left ==0) && (range_y_above ==0)) || ((range_x_left ==0) && (range_y_below ==0))){
            if(data.getCurrDirection().charAt(0) != 'E'){
                data.setTop();
                data.setBeforeTurnDir(data.getCurrDirection()); 
                data.setInitialEastWest("E");
                return action.changeDirection("E");
            }else{
                data.setTop();
                data.setInitialEastWest("E");
                return action.scan();
            }
        }else if (((range_x_right ==0) && (range_y_above ==0)) || ((range_x_right ==0) && (range_y_below ==0))){

            if(data.getCurrDirection().charAt(0) != 'W'){
                data.setTop();
                data.setBeforeTurnDir(data.getCurrDirection()); 
                data.setInitialEastWest("W");
                return action.changeDirection("W");

            }else{
                data.setTop();
                data.setInitialEastWest("W");
                return action.scan();
            }
        }
        return action.scan();
    }

//based on location assign closest corner for traversal - ensures no part of the map cut off 
    public String corners(int range_x_right, int range_x_left,int range_y_above,int range_y_below){
        if (range_x_right > range_x_left){
            if (range_y_below > range_y_above){
                data.setIsStartingLeft(true);
                return topLeft();
            }else{
                return botLeft();
            }
        }else{
            if (range_y_below > range_y_above){
                data.setIsStartingLeft(false);
                return topRight();
                
            }else{
                return botRight();
            }
        }
    }

//right, fly (while loop), left turn (+condition for ending E/W)
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
                int update = range-1; 
                if (data.getCurrDirection().charAt(0) == 'N'){
                    data.setRange_y_above(update); 
                    return action.fly(); 
                }else if(data.getCurrDirection().charAt(0) == 'S'){
                    data.setRange_y_below((update)); 
                    logger.info(range);
                    return action.fly(); 
                }else if(data.getCurrDirection().charAt(0) == 'W'){
                    data.setRange_x_left(update); 
                    return action.fly(); 
                }else if(data.getCurrDirection().charAt(0) == 'E'){
                    data.setRange_x_right(update);
                    return action.fly();  
                }
            }
            if ((start_dir.charAt(0) == 'S' || start_dir.charAt(0) == 'N') && (data.getExtra() != true)){
                data.setBeforeTurnDir(data.getCurrDirection()); 
                String Current = data.getCurrDirection();
                data.setExtra(); 
                return action.changeDirection(Direction.left(Current));
            }
            data.setTop();
            data.setInitialEastWest(data.getCurrDirection());
            data.setBeforeTurnDir(data.getCurrDirection()); 
            String Current = data.getCurrDirection();
            logger.info(Current);
            return action.changeDirection(Direction.left(Current));
        }
    }

//left, fly (while loop), right turn (+condition for ending E/W)
    public String LFR(int range, String start_dir){
        logger.info("LFR");
        int count = data.getStage(); 
        if (count == 5){
            data.setBeforeTurnDir(data.getCurrDirection()); 
            String Current = data.getCurrDirection();
            return action.changeDirection(Direction.left(Current));
        }else{
            logger.info(range);
            while(range > 3){
                int update = range-1; 
                if (data.getCurrDirection().charAt(0) == 'N'){
                    data.setRange_y_above(update); 
                    return action.fly(); 
                }else if(data.getCurrDirection().charAt(0) == 'S'){
                    data.setRange_y_below((update)); 
                    logger.info(range);
                    return action.fly(); 
                }else if(data.getCurrDirection().charAt(0) == 'W'){
                    data.setRange_x_left(update); 
                    return action.fly(); 
                }else if(data.getCurrDirection().charAt(0) == 'E'){
                    data.setRange_x_right(update);
                    return action.fly();  
                }
            }
            if ((start_dir.charAt(0) == 'S' || start_dir.charAt(0) == 'N') && (data.getExtra() != true)){
                data.setBeforeTurnDir(data.getCurrDirection()); 
                String Current = data.getCurrDirection();
                data.setExtra(); 
                return action.changeDirection(Direction.right(Current));
            }
            data.setTop();
            //checks if initially going 
            data.setInitialEastWest(data.getCurrDirection());
            data.setBeforeTurnDir(data.getCurrDirection()); 
            String Current = data.getCurrDirection();
            logger.info(Current);
            return action.changeDirection(Direction.right(Current));
        }
    }
}