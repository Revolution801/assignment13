package assignment13;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This class is a simple JUnit test for our FlightEdge class. We feel that this
 * testing is exhaustive since the class mainly contains getter methods.
 * 
 * @author Kent Allen, Alec Becker
 */
public class EdgeJunit {
	FlightEdge edge;
	
	@Before
	public void setUp() {
		edge = new FlightEdge(new AirportVertex("SLC"), new AirportVertex("DEN"), 1234);
		edge.addFlight("DEL", 10, 0, 60, 100);
		edge.addFlight("AMA", 30, 1, 62, 300);
	}
	
	@Test
	public void trivialTests() {
		assertEquals("SLC", edge.getOrigin().getAirportName());
		assertEquals("DEN", edge.getDestination().getAirportName());
		assertEquals(1234, edge.getDistance());
		assertEquals(20.0, edge.getAverageDelay(), 0);
		assertEquals(0.5, edge.getCancelledProbability(), 0);
		assertEquals(61, edge.getAverageTime(), 0);
		assertEquals(200, edge.getAverageCost(), 0);
		assertTrue(edge.isCarrierOffered("DEL"));
		assertFalse(edge.isCarrierOffered("HAM"));
	}
}
