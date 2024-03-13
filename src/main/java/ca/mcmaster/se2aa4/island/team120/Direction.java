package ca.mcmaster.se2aa4.island.team120;

public class Direction{
    private String heading;

    public static String right(String currDir){
        switch (currDir){
            case "N":
                return "E";
            case "E":
                return "S";
            case "S":
                return "W";
            case "W":
                return "N";
            default:
                throw new IllegalStateException("INVALID DIRECTION");
        }
    }

    public static String left(String currDir){
        switch (currDir){
            case "N":
                return "W";
            case "W":
                return "S";
            case "S":
                return "E";
            case "E":
                return "N";
            default:
                throw new IllegalStateException("INVALID DIRECTION");
        }
    }

    public static String UTurn(String currDir){
        switch (currDir){
            case "N":
                return "S";
            case "W":
                return "E";
            case "S":
                return "N";
            case "E":
                return "W";
            default:
                throw new IllegalStateException("INVALID DIRECTION");
        }
    }

    public void turnRight(String currDir){
        switch (currDir){
            case "N":
                this.heading = "E";
                break;
            case "E":
                this.heading = "S";
                break;
            case "S":
                this.heading = "W";
                break;
            case "W":
                this.heading = "N";
                break;
        }
    }

    public void turnLeft(String currDir){
        switch (currDir){
            case "N":
                this.heading = "W";
                break;
            case "E":
                this.heading = "N";
                break;
            case "S":
                this.heading = "E";
                break;
            case "W":
                this.heading = "S";
                break;
        }
    }
}
