package ca.mcmaster.se2aa4.island.team120;


public class NavigationSystem implements MissionType{
    FindIsland island = new FindIsland();
    GridSearch algoRun = new GridSearch();
    InterTurn interlace = new InterTurn();
    
    StartPoint start = new StartPoint(); 
    Data data = new Data(); 
    Coordinates coords = new Coordinates();
    Actions action= new Actions();

    private static boolean groundFound;
    private static boolean checkDone;
    private static String currentDirection;

    public String run(int batteryLevel, int startingBatteryLevel){ 
        
        //initializes variables
        groundFound = data.getGroundFound();
        checkDone = data.getCheckDone();
        currentDirection = data.getCurrDirection();

        if(!(data.getTop())){
            return start.FourCorners(groundFound);
        }
        else if (!data.getOnGround() && !data.getInterTurn()){
            return island.Finder(); 
        }
        else if(data.getInterTurn()){
            data.setHasChangedDir(true);
            return interlace.Turn();
        }
        else if(!(data.getInterTurn()) && data.getHasChangedDir()){
            return algoRun.search(currentDirection, batteryLevel, startingBatteryLevel, checkDone); 
        }
        else if (!(data.getInterTurn()) && !(data.getHasChangedDir())){
            return algoRun.search(currentDirection, batteryLevel, startingBatteryLevel, checkDone); 
        }
        else{
            return action.stop();
        }
    }
}
