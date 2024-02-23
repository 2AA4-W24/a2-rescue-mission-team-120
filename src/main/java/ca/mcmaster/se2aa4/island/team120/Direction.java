package ca.mcmaster.se2aa4.island.team120;

public enum Direction{
    N,
    E,
    S,
    W;
    // casing for directions on a map
    // test: get back direction.north
    // heading --> request drone to turn right or left by indicating new direction / applies inertia

    public Direction rightDirection(){
        switch (this){
            case N:
                return E;
            case E:
                return S;
            case S:
                return W;
            case W:
                return N;
        }
        throw new IllegalStateException("NO RIGHT DIR");
    }

    public Direction leftDirection(){
        switch (this){
            case N:
                return W;
            case W:
                return S;
            case S:
                return E;
            case E:
                return N;
        }
        throw new IllegalStateException("NO LEFT DIR");
    }
}