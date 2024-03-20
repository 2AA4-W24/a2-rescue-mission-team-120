package ca.mcmaster.se2aa4.island.team120;

public class NavigationSystem {
    //place where we keep steps
    //first call to search for ground
    //then call to search on island to find POI's 

    FindIsland island = new FindIsland();
    SimpleAlgo run = new SimpleAlgo();
    Actions action= new Actions();
    
    Coordinates coords = new Coordinates(); 
    Direction dir = new Direction(); 

    int x; 
    int y; 

    int range_x;
    int range_y; 

    public String run(String currentDirection, String lastChecked, int fly, int signal, String newDirection, boolean onGround, boolean groundFound, int scanned, boolean lost, int range, int batteryLevel, int startingBatteryLevel){  g
        TopLeft();

        if (!onGround){
            return island.Finder(newDirection, onGround, groundFound, lost); 
        }else{
            return run.search(onGround, currentDirection, range, batteryLevel, startingBatteryLevel); 
        }
        return null;
    }

    public void TopLeft(){
        int x = coords.x_coords(); 
        int y = coords.y_coords(); 

        //current position = 0,0 
        //echo up
        //echo down
        //echo left
        //echo right 


        //range in echo up + down = y
        //range in echo left + right = x 

        //from spot keep fly up for range 
        //from spot keep fly left/east for range 
        //update coords 


    }

}
