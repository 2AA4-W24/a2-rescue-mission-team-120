package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavigationSystem implements MissionType{
    private final Logger logger = LogManager.getLogger();

    FindIsland island = new FindIsland();
    GridSearch algoRun = new GridSearch();
    InterTurn interlace = new InterTurn();
    
    StartPoint start = new StartPoint(); 
    Data data = new Data(); 
    Coordinates coords = new Coordinates();
    Actions action= new Actions();

    public String run(int batteryLevel, int startingBatteryLevel){ 
        logger.info("CURRENT DIRECTION: "+ data.getCurrDirection());
        while(batteryLevel>= 0.175*startingBatteryLevel){
            if(!(data.getTop())){
                return start.fourCorners();
            }
            else if (!(data.getOnGround()) && !(data.getNoIsland()) && !(data.getInterTurn())){
                logger.info("GORILLA {}", data.getNewDirection());

                logger.info("GORILLA {}", data.getCurrDirection());

                logger.info("running finder");
                return island.finder(); 
            }
            else if(data.getInterTurn()){
                logger.info("interlace");
                data.setHasChangedDir(true);
                return interlace.turn();
            }
            else if (!(data.getInterTurn()) && !(data.getHasChangedDir())){
                data.setNoIsland(true);
                logger.info("algo search");
                return algoRun.search(batteryLevel, startingBatteryLevel); 
            }
            else if(!(data.getInterTurn()) && data.getHasChangedDir()){
                logger.info("algo search backwards");
                return algoRun.search(batteryLevel, startingBatteryLevel); 
            }

            else{
                logger.info("stop");
                return action.stop();
            }
        }
        //battery below set threshold
        return action.stop();
    }
}
