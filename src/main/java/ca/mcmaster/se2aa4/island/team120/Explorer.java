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
    private String currentDirection; //so we can know from parsing info what our starter direction is 
    private Integer range = 0;
    private String creeks;
    private String biomes;
    private Integer fly;
    private Integer signal;
    private Boolean groundFound = false;
    private String lastChecked;
    private String newDirection;
    private Boolean onGround = false;
    private Integer scanned = 1;
    private Integer startingBatteryLevel;
    private Integer rangeCheck = 0;

    private int x;
    private int y; 
    
    Actions actions = new Actions();
    Data data = new Data();
    Coordinates update = new Coordinates();


    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));

        data.setCurrDirection(info.getString("heading"));
        data.setLastDirection(data.getCurrDirection());
        data.setFly(1);
        data.setSignal(0);
        data.setScanned(1);

        batteryLevel = info.getInt("budget");
        startingBatteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", currentDirection);
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision() {
        signal= data.getSignal();
        fly= data.getFly();
        scanned= data.getScanned();
        currentDirection = data.getCurrDirection();
        lastChecked= data.getLastDirection();

        NavigationSystem decisionMaker = new NavigationSystem();
        String decision = decisionMaker.run(currentDirection, lastChecked, fly, signal, newDirection, onGround, groundFound, scanned, range, rangeCheck, batteryLevel, startingBatteryLevel);
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
       
        logger.info("The cost of the action was {}", cost);
        
        batteryLevel -= cost; 
        logger.info("Remaining battery{}", batteryLevel);

        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        
        if (batteryLevel==0){
            deliverFinalReport();
        }
       
        //check what direction is being echoed in
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);

        
       

        Radar radar = new Radar(extraInfo);
        PhotoScanner scan= new PhotoScanner(extraInfo);
        //lastChecked = FindIsland.returnLastChecked();
    


        if (!radar.isEchoed()){
            groundFound = false;
        }else{
            if(radar.isGround()){
                if (range == 0){
                    onGround = true;
                }
                range = extraInfo.getInt("range");
                rangeCheck = 1;
                newDirection = data.getLastDirection();
                groundFound = true; 
            }else{
                // out of range range
                range = extraInfo.getInt("range");
                rangeCheck = -1;
                groundFound = false;
            }
        }

        if(scan.isScanned()){
            //CHECK IF DRONE IS IN AN OCEAN ON SCAN, SET A NEW DIRECTION FOR A LOST
            if(!scan.verifyBiome()){
                logger.info("IN THE OCEAN");
                //if previously on island but now no longer on the island, update onGround to look for ground again
                if (onGround){
                    newDirection = Direction.left(currentDirection);
                    logger.info("NEW DIRECTION LOST {}", newDirection);
                }
            }
            else{
                JSONArray biomes = extraInfo.getJSONArray("biomes");
                logger.info("YOU'RE ON {}", biomes);
            }
        }
    }

    @Override
    public String deliverFinalReport() {
        logger.info("hello");
        logger.info(Tracker.getNumCreeks());
        return "no creek found";
    }
}