package ca.mcmaster.se2aa4.island.team120;


public class NavigationSystem implements MissionType{
    FindIsland island = new FindIsland();
    GridSearch algoRun = new GridSearch();
    InterTurn interlace = new InterTurn();
    
    StartPoint start = new StartPoint(); 
    Data data = new Data(); 
    Coordinates coords = new Coordinates();
    Actions action= new Actions();

    private static boolean interTurn;
    private static boolean hasChangedDir;
    private static boolean onGround;
    private static String currentDirection;
    private static boolean groundFound;
    private static int range;
    private static boolean checkDone;

    public String run(int batteryLevel, int startingBatteryLevel){ 
        
        //initializes variables
        interTurn = data.getInterTurn();
        onGround = data.getOnGround();
        hasChangedDir= data.getHasChangedDir();
        currentDirection = data.getCurrDirection();
        groundFound = data.getGroundFound();
        range = data.getRange();
        checkDone = data.getCheckDone();

        if(!(data.getTop())){
            return start.fourCorners(range, groundFound);
        }
        else if (!onGround && !interTurn){
            return island.finder(); 
        }
        else if(interTurn){
            data.setHasChangedDir(true);
            
            return interlace.turn();
        }
        else if(!(interTurn) && hasChangedDir){
            return algoRun.search(batteryLevel, startingBatteryLevel); 
        }
        else if (!(interTurn) && !(hasChangedDir)){
            return algoRun.search(batteryLevel, startingBatteryLevel); 
        }
        else{
            return action.stop();
        }
    }
}
