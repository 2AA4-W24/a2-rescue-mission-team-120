package ca.mcmaster.se2aa4.island.team120;


public class Coordinates {
    private static int x = 0; 
    private static int y = 0; 

    public void location(String CurrentDirection){
        switch (CurrentDirection){
            case "N":
                y +=3;
                break;
                
            case "W":
                x -=3;
                break;
            case "S":
                y -=3;
                break;
            case "E":
                x+=3;
                break;
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
