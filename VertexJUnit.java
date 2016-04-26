package assignment13;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VertexJUnit {
	
	@Before
	public void setUp() throws Exception {
		AirportVertex test = new AirportVertex("HAM");
		AirportVertex test2 = new AirportVertex("SAN");
		FlightEdge flight1 =  new FlightEdge(test, test2, 1000);
		flight1.addFlight("DOOD", 0, 0, 180, 1000);
	}
	
	
	//This method test the addFlight and containsFlight method
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
}
