package ca.mcmaster.se2aa4.island.team120;

public enum Direction{
    NORTH,
    EAST,
    SOUTH,
    WEST;
    // casing for directions on a map
    // test: get back direction.north
    // heading --> request drone to turn right or left by indicating new direction / applies inertia
    //

    public Direction newDirection(Direction goalDir){
        //pass goal direction as a parameter, check to turn right
        //if goal direction is to the left of current direction, call turn left; 
        //if to the right, turn right; 
        //if none, just turn right and repeat;
        //rightDir = turnRight();
        //leftDir = turnLeft();
        return null;
    }

    public Direction turnRight(){
        // case and return new direction
        switch (this){
            case NORTH -> {
                return EAST;
            }
            case EAST -> {
                return SOUTH;
            }
            case SOUTH -> {
                return WEST;
            }
            case WEST -> {
                return NORTH;
            }
        }
        throw new IllegalStateException("ERROR");
    }

    public Direction turnLeft(){
        return null;
    }
}