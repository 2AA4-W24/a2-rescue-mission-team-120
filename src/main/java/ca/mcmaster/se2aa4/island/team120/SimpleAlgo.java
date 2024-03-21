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
 
    Actions action= new Actions();

    private int changeDir;
    private int count;
    Direction direction= new Direction();
    Data data= new Data();
    private int south;
    private int north;

    private boolean left;
    private boolean checkDone;



    public String search(String currentDirection, int rangeCheck, int batteryLevel, int startingBatteryLevel, boolean left, boolean checkDone){
        String decision="";
        this.changeDir= data.getChangeDirAlgo();
        this.count= data.getCountAlgo();
        this.south= data.getSouthAlgo();
        this.north= data.getNorthAlgo();
        this.left= left;
        this.checkDone= checkDone;
   

        //once we reach left most island and want to start search
        while(batteryLevel> 0.25*startingBatteryLevel){
            if(count==0 && rangeCheck>=0){
                decision= action.scan();
                data.setCountAlgo(1);
                return decision;
            }
            else if(count==1 && rangeCheck>=0 && changeDir!=3){
                decision= action.echo(data.getCurrDirection());
           
                data.setCountAlgo(2);
                return decision;
            }
            else if(count==2 && rangeCheck>=0){
                decision= action.fly();
               
                data.setCountAlgo(0);
                return decision;
            }

            //fly ahead once
            //scan left if facing up
            //scan right if facing down
        
            else if(rangeCheck<0 && changeDir== 0 &&  && !checkDone){
                logger.info("PREPARING FOR TURN. MOVING AHEAD")
                decision= action.fly();
                return decision;
            }
            else if(rangeCheck<0 && changeDir== 1 && south==1 && !checkDone){
                logger.info("PREPARING FOR SCANNING LEFT.")
                decision= action.echo(Direction.left(currentDirection));
                return decision;
            }
            else if(rangeCheck<0 && changeDir== 1 && north==1 && !checkDone){
                logger.info("PREPARING FOR SCANNING LEFT.")
                decision= action.echo(Direction.right(currentDirection));
                return decision;
            }
            else if(rangeCheck<0 && changeDir== 2 && south==1 && !checkDone){
                logger.info("CHECK SCAN")
                decision= action.echo(Direction.right(currentDirection));
                return decision;
            }


            else if(rangeCheck<0 && changeDir== 0 && left && checkDone){
                logger.info("TURN STARTING");
                decision= action.changeDirection("E");
                data.setChangeDirAlgo(1);
      
                return decision;
            }
            else if (rangeCheck<0 && changeDir== 0 && !(left)){
                logger.info("TURN STARTING");
                decision= action.changeDirection("W");
                data.setChangeDirAlgo(1);

                return decision;
            }
            else if(rangeCheck<0 && changeDir== 1){
                logger.info("HELLO SECOND DIR STEP");
                if (currentDirection.equals("E") && south==1){
                    decision= action.changeDirection("N");
                    data.setNorthAlgo(1);
                    data.setSouthAlgo(0);
                }
                else if (currentDirection.equals("E") && north==1){
                    decision= action.changeDirection("S");
                    data.setNorthAlgo(0);
                    data.setSouthAlgo(1);
                }
                else if (currentDirection.equals("W") && south==1){
                    decision= action.changeDirection("N");
                    data.setNorthAlgo(1);
                    data.setSouthAlgo(0);
                }
                else if (currentDirection.equals("W") && north==1){
                    decision= action.changeDirection("S");
                    data.setNorthAlgo(0);
                    data.setSouthAlgo(1);
                }
                
                data.setChangeDirAlgo(2);
         
                return decision;
            }
            else if(rangeCheck<0 && changeDir== 2){
                logger.info("THIRD DIR STEP, IN CORRECT POS");
                decision= action.echo(currentDirection);
                data.setChangeDirAlgo(3);
                
                return decision;
            }
            else if(rangeCheck>=0 && changeDir==3){
                
                logger.info("TURN SUCCESS");
             
                decision= action.scan();

                data.setCountAlgo(1);
                data.setChangeDirAlgo(0);

                return decision;
            }

            else if(rangeCheck<0 && changeDir== 3){
                logger.info("BEYOND MAP BOUNDS");
                decision= action.stop();
                return decision;
            }
            
        }

        logger.info("BATTER LEVEL BELOW THRESHOLD");
        return action.stop();
    }

    
    


}
