package ca.mcmaster.se2aa4.island.team120;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;


//ACTIONS, COORDINATES, DATA, DIRECTION, GRIDSEARCH TESTING DONE
public class ExampleTest {
 
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
        Coordinates coordinates = new Coordinates();

        //execute fly method action
        String result = actions.fly();
        actions.coords = coordinates;
        actions.fly();

        assertEquals("{\"action\":\"fly\"}", result);
        assertEquals(0, coordinates.x_coords());
        assertEquals(6, coordinates.y_coords());
    }

    @Test
    public void testChangeDirection() {
        Actions actions = new Actions();
        Coordinates coordinates = new Coordinates();

        String result = actions.changeDirection("north");
        actions.coords = coordinates;
        actions.changeDirection("east");

        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"north\"}}", result);
        assertEquals(3, coordinates.x_coords());
        assertEquals(0, coordinates.y_coords());
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
    public void testStartPointCount() {
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

    public void TestNavSystem() {
        NavigationSystem navigationSystem = new NavigationSystem();
        StartPoint startPoint = new StartPoint();

        // Simulate different scenarios by setting data flags
        navigationSystem.data.getTop();
        startPoint.data.setStage(0);
        startPoint.data.setCurrDirection("E");
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}", navigationSystem.run(100, 1000));

        navigationSystem.data.setTop();
        navigationSystem.data.setOnGround(false);
        navigationSystem.data.setInterTurn(false);
        navigationSystem.data.setLastDirection("E");
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"S\"}}", navigationSystem.run(100, 1000));

    }

    @Test    
    public void testTracker() {
        Tracker tracker = new Tracker();

        // Testing POI 
        tracker.POI("Creek", "creek1");

        assertTrue(Tracker.x_coords.containsKey("creek1"));
        assertTrue(Tracker.y_coords.containsKey("creek1"));

        // Testing class emergency site 
        tracker.POI("Emergency", "emergency1");
        assertEquals("Emergency X coordinate: 0        Emergency y coordinate: 0", Tracker.getEmergencySite());

        // Testing class current creek
        assertEquals("creek1",Tracker.CurrentClosest()); 
        
        //Testing closest coords
        assertEquals("Closest Creek X coordinate: 0        Closest Creek y coordinate: 0", Tracker.getClosetCreekCoords());

    }

    @Test

    public void testScanner() {
        //scanner test
        JSONObject scannedResponse = new JSONObject();
        scannedResponse.put("creeks", new JSONArray());

        PhotoScanner scanner = new PhotoScanner(scannedResponse);

        assertTrue(scanner.isScanned());

        //is creek test 
        JSONObject NoCreeks = new JSONObject();
        PhotoScanner isCreek= new PhotoScanner(NoCreeks);
        assertFalse(isCreek.isCreek());

        //is site test
        JSONObject NoSites = new JSONObject();
        PhotoScanner isSite = new PhotoScanner(NoSites);
        assertFalse(isSite.isSite());

    }

    @Test

    public void testRadar(){
        JSONObject groundResponse = new JSONObject();
        groundResponse.put("found", "GROUND");

        //is ground method
        Radar radarTest = new Radar(groundResponse);
        assertTrue(radarTest.isGround());//checks if returns true

        //is echoed method
        assertTrue(radarTest.isEchoed());//checks if returns true
    }
}
