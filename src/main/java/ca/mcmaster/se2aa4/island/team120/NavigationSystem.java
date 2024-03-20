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
    Data data = new Data(); 

    int x; 
    int y; 

    int range_x;
    int range_y; 

    public String run(String currentDirection, String lastChecked, int fly, int signal, String newDirection, boolean onGround, boolean groundFound, int scanned, int range, int batteryLevel, int startingBatteryLevel){ 
        TopLeft();

        if (!onGround){
            return island.Finder(newDirection, onGround, groundFound); 
        }else{
            return run.search(onGround, currentDirection, range, batteryLevel, startingBatteryLevel); 
        }
    }

    public String TopLeft(){
        //int x = coords.x_coords(); 
        //int y = coords.y_coords(); 
    
        //current position = 0,0 
        //echo up
        //echo down
        //echo left
        //echo right 
        int turn = 0; 
        for (int i=0; i<4; i++){
            if (i ==0){ 
                //scan current direction 
                return action.echo(data.getCurrDirection()); 
            }else if(i ==1){
                //echo left 
                return action.echo(dir.left(data.getCurrDirection())); 
            }else if (i ==2){
                //echo right of current
                return action.echo(dir.right(data.getCurrDirection())); 
            }else if (i==3 || i ==4){
                //echo behind of current
                if (turn ==0){
                    turn +=1; 
                    return action.changeDirection(dir.left(data.getCurrDirection()));
                }else{
                    return action.echo(dir.left(data.getCurrDirection())); 
                }
            }
        }
        return null;
        //range in echo up + down = y
        //range in echo left + right = x 

        //from spot keep fly up for range 
        //from spot keep fly left/east for range 
        //update coords 
    }
}
