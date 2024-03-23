package ca.mcmaster.se2aa4.island.team120;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


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



}
