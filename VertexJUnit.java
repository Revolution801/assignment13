package assignment13;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for our AirportVertex class
 * 
 * @author Kent Allen, Alec Becker
 */
public class VertexJUnit {
	AirportVertex test;
	AirportVertex test2;
	AirportVertex test3;
	FlightEdge flight1;
	
	@Before
	public void setUp() throws Exception {
		test = new AirportVertex("HAM");
		test2 = new AirportVertex("SAN");
		flight1 = new FlightEdge(test, test2, 1000);
		flight1.addFlight("DOOD", 0, 0, 180, 1000);
		
	}
	
	// This method test the addFlight and containsFlight method
	@Test
	public void addandContainsFlight() {
		assertTrue(test.addFlight(flight1));
		assertTrue(test.containsFlight(flight1));
	}
	
	// This method test the getFlight
	@Test
	public void getFlight() {
		test.addFlight(flight1);
		assertEquals(flight1, test.getFlight(flight1));
	}
	
	// This method test the getAlLFlights method with a flight
	@Test
	public void getallFlights() {
		test.addFlight(flight1);
		assertTrue(test.getAllFlights().contains(flight1));
	}
	
	// This method test the getAllFlights without a flight
	@Test
	public void getallFlightsNone() {
		assertFalse(test.getAllFlights().contains(flight1));
	}
	
	// This method test the getAlLFlights method with "DOOD" specific airline
	// and does contain a flight
	@Test
	public void getallFlightsCarrier() {
		test.addFlight(flight1);
		assertTrue(test.getAllFlightsCarrierSpecific("DOOD").contains(flight1));
	}
	
	// This method test the getAlLFlights method with "DOOD" specific airline
	// and does contain a flight
	@Test
	public void getallFlightsCarrierFalse() {
		test.addFlight(flight1);
		assertFalse(test.getAllFlightsCarrierSpecific("CHILL").contains(flight1));
	}
	
}
