package ca.mcmaster.se2aa4.island.team120;
import org.json.JSONObject;
import org.json.JSONArray;

public class PhotoScanner {

    private JSONObject response;
    Tracker track= new Tracker();

    public PhotoScanner(JSONObject response){
        this.response= response;
    }

    //checks if scan action has been executed
    public boolean isScanned(){
        if(response.has("creeks")){
            return true;
        }
        return false;
    }

    //checks if currently scanned tile has a creek
    public boolean isCreek(){
        if(isScanned()){
            //checks if scan contains a creek
            if(response.getJSONArray("creeks").length()!=0){
                //iterates all creeks from the scan
                for(int i =0; i < (response.getJSONArray("creeks").length()); i++){
                    JSONArray id_arr = response.getJSONArray("creeks");
                    String id= id_arr.getString(i);
                    //stores each creeks information found from the drone scan
                    track.POI("Creek", id);
                }
                return true;
            }
        }
        return false;
    }

    //checks if currently scanned tile has an emergency site
    public boolean isSite(){
        if(isScanned()){
            //checks if scan contains a creek
            if(response.getJSONArray("sites").length()!=0){
                JSONArray id_arr = response.getJSONArray("sites");
                String id= id_arr.getString(0);
                //stores emergency site information
                track.POI("Emergency", id);
                return true;
            }
        }
        return false;
    }
}


