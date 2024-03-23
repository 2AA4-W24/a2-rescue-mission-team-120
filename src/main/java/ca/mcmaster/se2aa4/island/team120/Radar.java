package ca.mcmaster.se2aa4.island.team120;
import org.json.JSONObject;

public class Radar{
    private JSONObject response;

    public Radar(JSONObject response){
        this.response = response;
    }

    //if response from action contains found, the echo action was executed
    public boolean isEchoed(){
        if (response.has("found")){
            return true;
        }
        return false;
    }

    //checks is current direction has ground
    public boolean isGround(){
        //if echo action was executed, checks the response and outputs whether ground was found or not
        if (isEchoed()){
            String found = response.getString("found");
            if ("GROUND".equals(found)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}


