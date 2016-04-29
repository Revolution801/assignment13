package assignment13;

import java.util.LinkedList;

/**
 * This Class was created to represent an airport for the NetworkGraph Class. It
 * carries an average cost so it can be used with Dijkstra's algorithm to find
 * the cheapest path.
 * 
 * @author Kent Allen, Alec Becker
 *
 */
public class AirportVertex implements Comparable<AirportVertex> {
	
	private boolean visited = false;
	private String origin = "";
	private LinkedList<FlightEdge> flights = new LinkedList<>();
	private AirportVertex previous = null;
	private double cost;
	
	/**
	 * Constructor of Airport vertex, which takes in a name and cost is
	 * initialized at at a max value. The cost is initialized at max value
	 * because that way when we the set the first cost, it will be automatically
	 * lower than the original cost.
	 * 
	 * @param airport
	 *            - name of airport to be constructed
	 */
	public AirportVertex(String airport) {
		origin = airport;
		cost = Double.MAX_VALUE;
	}
	
	/**
	 * This method adds a flight to the ArrayList of flights in the airport if
	 * it is not already contained.
	 * 
	 * @param flight
	 *            - a flight edge to be added to the airport vertex
	 */
	public boolean addFlight(FlightEdge flight) {
		if(!containsFlight(flight)) {
			flights.add(flight);
			return true;
		}
		return false;
	}
	
	/**
	 * This method checks to see if the airport already contains the flight edge
	 * 
	 * @param flight
	 *            - a flight edge to see if it is contained in the airport
	 *            vertex
	 * @return true if flight is contained.
	 */
	public boolean containsFlight(FlightEdge flight) {
		return flights.contains(flight);
	}
	
	/**
	 * Returns the specific flight edge object contained to this airport vertex,
	 * in order to update costs on the correct flight when importing data, or
	 * retrieving data from the correct edge.
	 * 
	 * @param flight
	 *            - flight edge object.
	 * @return flight - flight edge object contained in the airport vertex
	 *         object.
	 */
	public FlightEdge getFlight(FlightEdge flight) {
		int flightIndex = flights.indexOf(flight);
		
		if(flightIndex >= 0) {
			return flights.get(flights.indexOf(flight));
		}
		
		return null;
	}
	
	/**
	 * Returns the LinkedList of flights contained by the airport vertex.
	 * 
	 * @return flights - linked list of flights from airport vertex
	 */
	public LinkedList<FlightEdge> getAllFlights() {
		return flights;
	}
	
	/**
	 * Returns the LinkedList of flights contained by the airport vertex for a
	 * specific flight carrier.
	 * 
	 * @param carrier
	 *            - flight carrier
	 * @return carrierFlights - A LinkedList of flights.
	 */
	public LinkedList<FlightEdge> getAllFlightsCarrierSpecific(String carrier) {
		
		LinkedList<FlightEdge> carrierFlights = new LinkedList<>();
		
		for(FlightEdge element : flights) {
			if(element.isCarrierOffered(carrier)) {
				carrierFlights.add(element);
			}
		}
		return carrierFlights;
	}
	
	/**
	 * Checks to see if the airport vertex has been visited
	 * 
	 * @return true if airport vertex has been visited
	 */
	public boolean isVisited() {
		return visited;
	}
	
	/**
	 * Sets the airport vertex as visited.
	 * 
	 */
	public void setAsVisited() {
		visited = true;
	}
	
	/**
	 * Sets the airport vertex as unvisited.
	 * 
	 */
	public void setAsUnvisited() {
		visited = false;
	}
	
	/**
	 * Returns the airports name for the airport vertex object.
	 * 
	 * @return origin - name of airport
	 */
	public String getAirportName() {
		return origin;
	}
	
	@Override
	public boolean equals(Object _other) {
		if(!(_other instanceof AirportVertex)) {
			return false;
		}
		AirportVertex other = (AirportVertex) _other;
		return this.origin.equals(other.origin);
	}
	
	/**
	 * Sets the previous data member as the AirportVertex entered
	 * 
	 * @param _previous
	 *            - previous airport visited.
	 */
	public void setPrevious(AirportVertex _previous) {
		previous = _previous;
	}
	
	/**
	 * Gets the cost that it currently takes to get to the AirportVertex
	 * 
	 * @return cost - amount to get to AirportVertex
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * Sets the cost it currently takes to get to the AirpotVertex
	 * 
	 * @param cost
	 *            - the cost to set
	 */
	public void setCost(double _cost) {
		cost = _cost;
	}
	
	/**
	 * This method retrieves the previously visited Airport Vertex. Can return
	 * null if previous AirportVertex has not been visited.
	 * 
	 * @return previous - the previously visited AiportVertex
	 */
	
	public AirportVertex getPrevious() {
		return previous;
	}
	
	@Override
	public int compareTo(AirportVertex o) {
		
		if(this.cost - o.getCost() < 0) {
			return -1;
		} else if(this.cost - o.getCost() > 0) {
			return 1;
		}
		
		return 0;
	}
	
}
