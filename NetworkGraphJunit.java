package assignment13;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for our NetworkGraph class
 * 
 * @author Kent Allen, Alec Becker
 */
public class NetworkGraphJunit {
	NetworkGraph graph;
	
	@Before
	public void setUp() throws Exception {
		graph = new NetworkGraph("simpleAirportData.csv");
	}
	
	@Test
	public void shortPathTest() {
		BestPath shouldBe = new BestPath();
		BestPath actuallyIs = new BestPath();
		
		shouldBe.add("IHE");
		shouldBe.add("PGH");
		shouldBe.add("ODC");
		shouldBe.setLength(1017.56);
		
		actuallyIs = graph.getBestPath("IHE", "ODC", FlightCriteria.COST);
		
		assertTrue(shouldBe.equals(actuallyIs));
	}
	
	@Test
	public void mediumPathTest() {
		BestPath shouldBe = new BestPath();
		BestPath actuallyIs = new BestPath();
		
		shouldBe.add("IHE");
		shouldBe.add("PGH");
		shouldBe.add("ODC");
		shouldBe.add("TIJ");
		shouldBe.setLength(1332.64);
		
		actuallyIs = graph.getBestPath("IHE", "TIJ", FlightCriteria.COST);
		
		assertTrue(shouldBe.equals(actuallyIs));
	}
	
	@Test
	public void noPossiblePath() {
		BestPath shouldBe = new BestPath();
		BestPath actuallyIs = new BestPath();
		
		actuallyIs = graph.getBestPath("IHE", "AGG", FlightCriteria.COST);
		
		assertTrue(shouldBe.equals(actuallyIs));
	}
	
	@Test
	public void shortPathTestCarrierSpecified() {
		BestPath shouldBe = new BestPath();
		BestPath actuallyIs = new BestPath();
		
		shouldBe.add("IHE");
		shouldBe.add("PGH");
		shouldBe.add("VWQ");
		shouldBe.setLength(822.83);
		
		actuallyIs = graph.getBestPath("IHE", "VWQ", FlightCriteria.COST, "UA");
		
		assertTrue(shouldBe.equals(actuallyIs));
	}
	
	@Test
	public void noPossiblePathCarrierSpecified() {
		BestPath shouldBe = new BestPath();
		BestPath actuallyIs = new BestPath();
		
		actuallyIs = graph.getBestPath("IHE", "TIJ", FlightCriteria.COST, "UA");
		
		assertTrue(shouldBe.equals(actuallyIs));
	}
	
	@Test
	public void shortPathTestNegativeValues() {
		BestPath shouldBe = new BestPath();
		BestPath actuallyIs = new BestPath();
		
		shouldBe.add("ODC");
		shouldBe.add("PGH");
		shouldBe.add("IHE");
		shouldBe.setLength(432);
		
		actuallyIs = graph.getBestPath("ODC", "IHE", FlightCriteria.TIME);
		
		assertTrue(shouldBe.equals(actuallyIs));
	}
	
	public void noDestinationsCarrierSpecified() {
		BestPath shouldBe = new BestPath();
		BestPath actuallyIs = new BestPath();
		
		actuallyIs = graph.getBestPath("SAN", "SFO", FlightCriteria.COST, "UA");
		
		assertTrue(shouldBe.equals(actuallyIs));
	}
	
	@Test
	public void noDestinations() {
		BestPath shouldBe = new BestPath();
		BestPath actuallyIs = new BestPath();
		
		actuallyIs = graph.getBestPath("SAN", "SFO", FlightCriteria.COST);
		
		assertTrue(shouldBe.equals(actuallyIs));
	}
	
	@Test
	public void originIsDestination() {
		BestPath actuallyIs;
		BestPath shouldBe = new BestPath();
		
		actuallyIs = graph.getBestPath("IHE", "IHE", FlightCriteria.COST);
		
		assertTrue(shouldBe.equals(actuallyIs));
	}
}
