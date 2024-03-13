package ca.mcmaster.se2aa4.island.team120;


public class Coordinates {
    private static int x = 0; 
    private static int y = 0; 

    public void location(String CurrentDirection){
        switch (CurrentDirection){
            case "N":
                y +=1;
                y_coords();
                
            case "W":
                x -=1;
                x_coords();
            case "S":
                y -=1;
                y_coords();
            case "E":
                x+=1;
                x_coords();
            default:
                throw new IllegalStateException("INVALID DIRECTION");
        }
    } 

    public int x_coords(){
        return x;
    }

    public int y_coords(){
        return y;
    }


}
