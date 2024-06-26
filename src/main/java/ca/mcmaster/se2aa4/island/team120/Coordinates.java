package ca.mcmaster.se2aa4.island.team120;

public class Coordinates {
    private static int x = 0; 
    private static int y = 0; 
    private static int grid = 3; 

    // update coordinates of the drone whenever it flies
    public void flyLocation(String currentDirection){
        switch (currentDirection){
            case "N":
                y +=grid;
                break;
            case "W":
                x -=grid;
                break;
            case "S":
                y -= grid;
                break;
            case "E":
                x+=grid;
                break;
            default:
                throw new IllegalStateException("INVALID DIRECTION");
        }
    } 
    
    // update coordinates of the drone whenever drone changes heading based on direction before turn and after
    public void turnLocation(String beforeTurn, String currentDirection){
        if (beforeTurn.equals("N")){
            if (currentDirection.equals("E")){
                y += grid;
                x += grid;
            }
            else if (currentDirection.equals("W")){
                y += grid;
                x -= grid;
            }
        }
        else if (beforeTurn.equals("E")){
            if (currentDirection.equals("N")){
                y += grid;
                x += grid;
            }
            else if (currentDirection.equals("S")){
                y -= grid;
                x += grid;
            }
        }
        else if (beforeTurn.equals("S")){
            if (currentDirection.equals("E")){
                y -= grid;
                x += grid;
            }
            else if (currentDirection.equals("W")){
                y -= grid;
                x -= grid;
            }
        }
        else if (beforeTurn.equals("W")){
            if (currentDirection.equals("N")){
                y += grid;
                x -= grid;
            }
            else if (currentDirection.equals("S")){
                y -= grid;
                x -= grid;
            }
        }
    }

    public int x_coords(){
        return x;
    }

    public int y_coords(){
        return y;
    }

}

