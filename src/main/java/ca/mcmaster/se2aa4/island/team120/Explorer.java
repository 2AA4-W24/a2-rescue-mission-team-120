
package ca.mcmaster.se2aa4.island.team120;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Integer batteryLevel; //so we can track battery level 

    private Integer startingBatteryLevel;
    private Integer count = 0; 
    Data data = new Data();

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));

        data.setCurrDirection(info.getString("heading"));
        logger.info("heading {}", info.getString("heading"));
        logger.info("direction {}", data.getCurrDirection());

        data.setLastDirection(data.getCurrDirection());
        data.setNewDirection(data.getCurrDirection());

        data.setStage(0);

        batteryLevel = info.getInt("budget");
        startingBatteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", data.getCurrDirection());
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision() {
        NavigationSystem decisionMaker = new NavigationSystem();
        String decision = decisionMaker.run(batteryLevel, startingBatteryLevel);
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
       
        logger.info("The cost of the action was {}", cost);
        
        batteryLevel -= cost; 
        logger.info("Remaining battery {}", batteryLevel);

        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);

        count ++; 
        data.setStage(count);

        DecisionBoard updateInfo = new DecisionBoard();
        updateInfo.makeDecision(response);
    }

    @Override
    public String deliverFinalReport() {
        logger.info(Tracker.getNumCreeks());
        logger.info(Tracker.getEmergencySite());
        logger.info(Tracker.CurrentClosest());
        logger.info(Tracker.getClosetCreekCoords());
        return Tracker.getClosetCreekCoords();
    }
}