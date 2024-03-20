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
    private Integer range=0;
    private String creeks;
    private String biomes;
    private Integer fly = 1;
    private Integer signal = 0;
    private Boolean groundFound = false;
    private String lastChecked;
    private String newDirection;
    private Boolean onGround = false;
    private Integer scanned = 1;
    private Boolean lost = false;
    private Integer startingBatteryLevel;

    private int x;
    private int y; 
    
    Actions actions = new Actions();
    LastChecked lastDirection = new LastChecked();
    Coordinates update = new Coordinates();

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));

        //currentDirection = info.getString("heading");
        lastDirection.setCurrDirection(info.getString("heading"));

        //Data data = new Data();
        lastChecked = currentDirection;

        batteryLevel = info.getInt("budget");
        startingBatteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", currentDirection);
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision() {
        lastChecked= lastDirection.getLastDirection();
        signal= lastDirection.getSignal();
        fly= lastDirection.getFly();
        scanned= lastDirection.getScanned();
        currentDirection = lastDirection.getCurrDirection();

        NavigationSystem decisionMaker = new NavigationSystem();
        String decision = decisionMaker.run(currentDirection, lastChecked, fly, signal, newDirection, onGround, groundFound, scanned, lost, range, batteryLevel, startingBatteryLevel);
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
        logger.info("YUMMY");

        Radar radar = new Radar();
        PhotoScanner scan= new PhotoScanner(extraInfo);
        //lastChecked = FindIsland.returnLastChecked();
        logger.info("OH MY GOD NEW {}", lastChecked);

        if (!radar.isEchoed(extraInfo)){
            //range = -1;
            logger.info("OUT OF RANGE");
            logger.info("CURR DIR {}", currentDirection);
            logger.info("LAST CHECKED {}",lastChecked);
            //groundFound = false;
        }else{
            range = extraInfo.getInt("range");
            if (range == 0){
                onGround = true;
            }
            logger.info("YOU'RE {} AWAY", range);
            logger.info("SWITCHING DIRECTION...");
            newDirection = lastDirection.getLastDirection();
            //currentDirection = lastChecked;
            logger.info("NEW DIRECTION {}", newDirection);
            groundFound = true; 
        }

        if(scan.isScanned()){
            //CHECK IF DRONE IS IN AN OCEAN ON SCAN, SET A NEW DIRECTION FOR A LOST
            if(!scan.verifyBiome()){
                logger.info("IN THE OCEAN");
                if (onGround){
                    newDirection = Direction.left(currentDirection);
                    logger.info("NEW DIRECTION LOST {}", newDirection);
                    onGround = false;
                    lost = true;
                }
            }
            else{
                JSONArray biomes = extraInfo.getJSONArray("biomes");
                logger.info("YOU'RE ON {}", biomes);
            }

            if(!scan.isCreek() && !scan.isSite()){
                logger.info("NOT A CREEK OR EMERGENCY SITE, WE ARE ON WATAHHH!");
            }
            else if(!scan.isSite()){
                logger.info("NOT AN EMERGENCY SITE!");
                logger.info("MUST BE ON A CREEK");
            }
            else{
                logger.info("NOT A CREEK");
                logger.info("MUST BE ON AN EMERGENCY SITE!");
            }
        }
    }

    @Override
    public String deliverFinalReport() {//code does not run to this point? how does bot work exactly
        return "no creek found";
    }
}