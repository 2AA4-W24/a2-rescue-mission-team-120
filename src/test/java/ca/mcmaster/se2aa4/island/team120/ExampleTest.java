package ca.mcmaster.se2aa4.island.team120;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;


//ACTIONS, COORDINATES, DATA, DIRECTION, GRIDSEARCH TESTING DONE
public class ExampleTest {
    /*
    @Test
    public void testGridSearch() { 
        Actions action = new Actions();
        GridSearch algoRun= new GridSearch(); 
        //test for battery level above 17.5% of starting battery level
        int batteryLevel = 300;
        int startingBatteryLevel = 1000;
        assertEquals(action.fly(), algoRun.search(batteryLevel, startingBatteryLevel));
        //test for battery level below 17.5% of starting battery level
        batteryLevel = 100;
        assertEquals(action.stop(), algoRun.search(batteryLevel, startingBatteryLevel));
    }

    @Test
    public void testFly() {
        Actions actions = new Actions();

        //execute fly method action
        String result = actions.fly();
        //actions.fly();

        assertEquals("{\"action\":\"fly\"}", result);

    }

    @Test
    public void testChangeDirection() {
        Actions actions = new Actions();
        Coordinates coords = new Coordinates();

        String result = actions.changeDirection("south");

        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"south\"}}", result);
        assertEquals(0, coords.x_coords());
        assertEquals(0, coords.y_coords());
    }

    @Test
    public void testSetAndGetCurrDirection() {
        Data data = new Data();
        //test set and get methods for LastDirection
        data.setCurrDirection("E");
        assertEquals("E", data.getCurrDirection());
    }

    @Test
    public void testSetAndGetSignal() {
        Data data = new Data();
        //test set and get methods for Signal
        data.setSignal(10);
        assertEquals(10, data.getSignal());
        
    }

    @Test
    public void testRight() {
        //test right method for each direction
        assertEquals("E", Direction.right("N"));
        assertEquals("S", Direction.right("E"));
        assertEquals("W", Direction.right("S"));
        assertEquals("N", Direction.right("W"));
    }

    @Test
    public void testLeft() {
        //test left method for each direction
        assertEquals("W", Direction.left("N"));
        assertEquals("S", Direction.left("W"));
        assertEquals("E", Direction.left("S"));
        assertEquals("N", Direction.left("E"));
    }

    @Test
    void testCheckGround() {
        FindIsland findIsland = new FindIsland();
        String rightDir = "E";
        String leftDir = "W";

        //check if algorithm correctly passes variables
        findIsland.checkGround(rightDir, leftDir);
        assertEquals(rightDir, findIsland.data.getLastDirection());

        findIsland.checkGround(rightDir, leftDir);
        assertEquals(leftDir, findIsland.data.getLastDirection());

        findIsland.checkGround(rightDir, leftDir);
        assertEquals(findIsland.data.getCurrDirection(), findIsland.data.getLastDirection());
    }

    @Test
    void testGetInPos() {
        FindIsland findIsland = new FindIsland();
        String rightDir = "E";
        String leftDir = "W";
        
        //test if it gets in position based on the initial direction it's in
        findIsland.data.setInitialEastWest("E");
        findIsland.getInPos(rightDir, leftDir);
        assertEquals(rightDir, findIsland.data.getNewDirection());

        findIsland.data.setInitialEastWest("W");
        findIsland.getInPos(rightDir, leftDir);
        assertEquals(leftDir, findIsland.data.getNewDirection());
    }
    
    @Test
    public void testStartPointCount0() {
        // Create an instance of StartPoint
        StartPoint startPoint = new StartPoint();

        // Set up the count to 0 and current direction to North
        startPoint.data.setStage(0);
        startPoint.data.setCurrDirection("E");

        // Expected action when count is 0 and current direction is east
        String expectedAction = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}";

        // Invoke method under test
        String result = startPoint.fourCorners();

        // Verify that the method returns the expected action
        assertEquals(expectedAction, result);
    }

    @Test    
    public void testTracker() {
        Tracker tracker = new Tracker();

        // Test adding a Creek
        tracker.POI("Creek", "creek1");

        assertEquals(1, Tracker.getNumCreeks());
        assertTrue(Tracker.x_coords.containsKey("creek1"));
        assertTrue(Tracker.y_coords.containsKey("creek1"));

        // Test adding an Emergency site
        tracker.POI("Emergency", "emergency1");
        assertEquals("Emergency X coordinate: 0        Emergency y coordinate: 0", Tracker.getEmergencySite());
    }

    @Test

    public void testScanner() {
        JSONObject scannedResponse = new JSONObject();
        scannedResponse.put("creeks", new JSONArray());

        PhotoScanner scanner = new PhotoScanner(scannedResponse);

        assertTrue(scanner.isScanned());
    }

    @Test

    public void testRadar(){
        JSONObject groundResponse = new JSONObject();
        groundResponse.put("found", "GROUND");

        Radar radarTest = new Radar(groundResponse);
        assertTrue(radarTest.isGround());//checks if returns true
    }*/
}
