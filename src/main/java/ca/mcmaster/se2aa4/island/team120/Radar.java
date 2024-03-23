package ca.mcmaster.se2aa4.island.team120;
import org.json.JSONObject;

public class Radar{
    private JSONObject response;

    public Radar(JSONObject response){
        this.response = response;
    }

    // if response from action contains found, allow for echo
    // methods to be accessed and called
    public boolean isEchoed(){
        if (response.has("found")){
            return true;
        }
        return false;
    }

    // checks if an echo in a direction has ground, if there's ground
    // pass boolean true 
    public boolean isGround(){
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


