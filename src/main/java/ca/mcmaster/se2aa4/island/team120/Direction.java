package ca.mcmaster.se2aa4.island.team120;

public class Direction{
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
                throw new IllegalArgumentException("ERROR READING DIRECTION");
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
                throw new IllegalArgumentException("ERROR READING DIRECTION");
        }
    }
}