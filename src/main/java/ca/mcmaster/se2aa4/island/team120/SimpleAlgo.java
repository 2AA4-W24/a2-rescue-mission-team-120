package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

public class SimpleAlgo {
    //going to place our orginal algo for creeks - just going up and down the island no face implementations 
    //NEED TO CREATE INTERFACE FOR ALL ALGOS THAT GO IN NAVIGATION SYSTEM 
    private final Logger logger = LogManager.getLogger();
    private boolean onGround;
    //JSONObject decision= new JSONObject();
    Actions action= new Actions();
    int count=0;
    int batteryLevel;
    int changeDir=0;
    Direction direction= new Direction();
    boolean turned= false;


    public String search(boolean onGround, String currentDirection, int range, int batteryLevel, int startingBatteryLevel){
        String decision="";

        this.onGround = onGround;
        this.batteryLevel= batteryLevel;

        //once we reach top and want to start search

        while(batteryLevel> 0.25*startingBatteryLevel){
            if(count==0 && range>=0){
                decision= action.scan();
                logger.info("** Decision: {}",decision.toString());
                count=1;
                return decision;
            }
            else if(count==1 && range>=0 && changeDir!=3){
                decision= action.echo(currentDirection);
                logger.info("** Decision: {}",decision.toString());
                count=2;
                return decision;
            }
            else if(count==2 && range>=0){
                decision= action.fly();
                logger.info("** Decision: {}",decision.toString());
                count=0;
                return decision;
            }
        
            else if(range<0 && changeDir== 0){
                
                if (currentDirection.equals("E")){
                    decision= action.changeDirection("S");
                }
                else if (currentDirection.equals("W")){
                    decision= action.changeDirection("N");
                }
                changeDir=1;
                logger.info("** Decision: {}",decision.toString());
                return decision;
            }
            else if(range<0 && changeDir== 1){
                if (currentDirection.equals("S")){
                    decision= action.changeDirection("W");
                }
                else if (currentDirection.equals("N")){
                    decision= action.changeDirection("E");
                }
                changeDir=2;
                logger.info("** Decision: {}",decision.toString());
                return decision;
            }
            else if(range<0 && changeDir== 2){
                decision= action.echo(currentDirection);
                logger.info("** Decision: {}",decision.toString());
                changeDir=3;
                
                return decision;
            }
            else if(range>=0 && changeDir==3 && count==0){
                changeDir=0;
                logger.info("TURN SUCCESS");
                turned= true;
                decision= action.scan();
                logger.info("** Decision: {}",decision.toString());
                count=1;

                return decision;
            }

            else if(range<0 && changeDir== 3){
                decision= action.echo(currentDirection);
                logger.info("** Decision: {}",decision.toString());
                changeDir=0;
                logger.info("BEYOND MAP BOUNDS");
                decision= action.stop();
                logger.info("** Decision: {}",decision.toString());
                
                return decision;
            }
            
        }
        
        return decision.toString();
    }



}
