package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.lang.invoke.StringConcatFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

//IMPLEMENTATION OF DIFFERENT ALGORITHMS 

public class BFS {
    private final Logger logger = LogManager.getLogger();
    
    //JSONObject decision= new JSONObject();
    /*Actions action= new Actions();
    Direction direction= new Direction();
    LastChecked data = new LastChecked();
    

    int count=0;
    int batteryLevel;
    int changeDir=0;

    boolean turned= false;
    private Coordinates coords; 
    private Queue<int[]> Queue; 
    //private boolean onGround;
    private String curr = data.getCurrDirection();*/ 
    

    public String search(int batteryLevel, int startingBatteryLevel){
        return null; 
        /*String decision="";

        //this.onGround = onGround;
        this.batteryLevel= batteryLevel;

        //curr = data.setCurrDirection(newDirection); 
        
        int current_x = coords.x_coords();
        int current_y = coords.y_coords(); 

        Queue.offer(new int[]{(current_x), (current_y)}); 

        while(batteryLevel> 0.25*startingBatteryLevel){
            while(!(Queue.isEmpty())){
                int[] node = Queue.poll(); 
                //check all directions 

                //move (fly) and scan - need to fly to location - get back to start spot and then do next
                // we can do - left(x-1), back(x+1), up(y+1), back(y-1), down(y-1), back(y+1), right(x+1)
                //if scan - ocean check is not true add to queue 
                //also scan will automatically store if it's a creek or emergency site so just keep moving until battery done or queue emepty 
                // if ocean don't add to queue 
                //repeat with next item of queue 
                
                //CHECK EAST 
                if(curr == "N"){
                    decision = action.changeDirection("E");
                    decision = action.fly();
                    coords.location(curr); 
                    decision = action.scan();
                    if (scan == land){
                        Queue.offer(new int[]{(current_x), (current_y)}); 
                    }
                    decision = action.changeDirection("N");
                    decision = action.changeDirection("W");
                    return decision; 
                }else if(curr== "S"){
                    decision = action.changeDirection("E");
                    decision = action.fly();
                    coords.location(curr); 
                    decision = action.scan();
                    if (scan == land){
                        Queue.offer(new int[]{(current_x), (current_y)}); 
                    }
                    decision = action.changeDirection("N");
                    decision = action.changeDirection("W");
                    return decision; 
        
                }else if(curr == "E"){
                    decision = action.fly();
                    coords.location(curr); 
                    decision =action.scan();
                    if (scan == land){
                        Queue.offer(new int[]{(current_x), (current_y)}); 
                    }
                    decision = action.changeDirection("N");
                    decision = action.changeDirection("W");
                    return decision; 

                }else if (curr == "W"){
                    decision = action.changeDirection("N");
                    decision = action.changeDirection("E");
                    decision = action.fly();
                    coords.location(curr); 
                    decision = action.scan();
                    if (scan == land){
                        Queue.offer(new int[]{(current_x), (current_y)}); 
                    }
                    decision = action.changeDirection("N");
                    decision = action.changeDirection("W");
                    return decision; 
                }

                //CHECK NORTH

                //CHECK SOUTH

                //CHECK WEST 
                
            }
        }
        return decision; */
    }
    /*check_east();
                check_north(); 
                check_south(); 
                check_west(); */


    public void check_east(){

    }

    public void check_north(){

        
    }

    public void check_south(){
        
    }

    public void check_west(){
        
    }

}







