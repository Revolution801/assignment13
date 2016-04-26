package assignment13;

import java.util.LinkedList;

/**
 * @author Kent
 *
 */
public class AirportVertex implements Comparable<AirportVertex> {
	
	private boolean visited = false;
	private String origin = "";
	private LinkedList<FlightEdge> flights = new LinkedList<>();
	private AirportVertex previous = null;
	private double cost;
	
	/**
	 * 
	 * @param airport
	 */
	public AirportVertex(String airport) {
		origin = airport;
		cost = Double.MAX_VALUE;
	}
	
	/**
	 * 
	 * @param flight
	 */
	public void addFlight(FlightEdge flight) {
		if(!flights.contains(flight)) {
			flights.add(flight);
		}
	}
	
	/**
	 * 
	 * @param flight
	 * @return
	 */
	public boolean containsFlight(FlightEdge flight) {
		return flights.contains(flight);
	}
	
	/**
	 * 
	 * @param flight
	 * @return
	 */
	public FlightEdge getFlight(FlightEdge flight) {
		int flightIndex = flights.indexOf(flight);
		
		if(flightIndex >= 0) {
			return flights.get(flights.indexOf(flight));
		}
		
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public LinkedList<FlightEdge> getAllFlights() {
		return flights;
	}
	
	/**
	 * 
	 * @param carrier
	 * @return
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
	 * 
	 * @return
	 */
	public boolean isVisited() {
		return visited;
	}
	
	/**
	 * 
	 */
	public void setAsVisited() {
		visited = true;
	}
	
	/**
	 * 
	 */
	public void setAsUnvisited() {
		visited = false;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getAirportName() {
		return origin;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean equals(Object _other) {
		if(!(_other instanceof AirportVertex)) {
			return false;
		}
		AirportVertex other = (AirportVertex) _other;
		return this.origin.equals(other.origin);
	}
	
	/**
	 * 
	 * @param _previous
	 */
	public void setPrevious(AirportVertex _previous) {
		previous = _previous;
	}
	
	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(double _cost) {
		cost = _cost;
	}
	
	public AirportVertex getPrevious() {
		return previous;
	}
	
	/**
	 * 
	 */
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
