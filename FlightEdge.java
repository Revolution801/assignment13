package assignment13;

/**
 * This class represents the edges of our graph. Each connection between two
 * airports is represented by only one edge, which can contain multiple
 * carriers. Each edge keeps track of the running average between all flights
 * of: - Average Time - Average Cost - Average Flight Delay - Probability of
 * Flight Being Cancelled
 * 
 * @author Kent Allen, Alec (H.A.M.) Becker
 */
public class FlightEdge {
	
	private AirportVertex origin;
	private AirportVertex destination;
	private ChainingHashTable<String> carriers = new ChainingHashTable<>(10);
	private double averageTime = 0;
	private double averageCost = 0;
	private int distance = 0;
	private double cancelledProbability = 0;
	private double averageDelay = 0;
	private int numTime, numCost, numDelay, numCancelled = 0;
	
	/**
	 * Constructor for FlightEdge. Origin, destination, and distance are set
	 * here since they will not change.
	 * 
	 * @param _origin
	 *            - The start airport
	 * @param _destination
	 *            - The end airport
	 * @param _distance
	 *            - The distance between the two airports
	 */
	public FlightEdge(AirportVertex _origin, AirportVertex _destination, int _distance) {
		origin = _origin;
		destination = _destination;
		distance = _distance;
	}
	
	/**
	 * Adds a new flight to the FlightEdge. This also calculates the running
	 * averages
	 * 
	 * @param carrier
	 *            - The carrier providing the flight
	 * @param delay
	 *            - The time delay of the flight
	 * @param cancelled
	 *            - 1 if the flight was cancelled, 0 otherwise
	 * @param _time
	 *            - The time the flight takes
	 * @param _cost
	 *            - The cost of the flight
	 */
	public void addFlight(String carrier, int delay, int cancelled, int _time, double _cost) {
		if(!carriers.contains(carrier)) {
			carriers.add(carrier);
		}
		
		// If any of the following data is negative, set the value to -1 to
		// signal to ignore the flight in NetworkGraph
		if(delay >= 0) {
			averageDelay = ((averageDelay * numDelay) + delay) / (++numDelay);
		} else {
			averageDelay = -1;
		}
		
		if(_time >= 0) {
			averageTime = ((averageTime * numTime) + _time) / (++numTime);
		} else {
			averageTime = -1;
		}
		
		if(_cost >= 0) {
			averageCost = ((averageCost * numCost) + _cost) / (++numCost);
		} else {
			averageCost = -1;
		}
		
		if(cancelled >= 0) {
			cancelledProbability = ((cancelledProbability * numCancelled) + cancelled) / (++numCancelled);
		} else {
			cancelledProbability = -1;
		}
	}
	
	/**
	 * @return the origin
	 */
	public AirportVertex getOrigin() {
		return origin;
	}
	
	/**
	 * @return the destination
	 */
	public AirportVertex getDestination() {
		return destination;
	}
	
	/**
	 * @param _carrier
	 *            - The carrier in question
	 * @return true if the carrier offers this flight, false otherwise
	 */
	public boolean isCarrierOffered(String _carrier) {
		return carriers.contains(_carrier);
	}
	
	/**
	 * @return the average time
	 */
	public double getAverageTime() {
		return averageTime;
	}
	
	/**
	 * @return the average cost
	 */
	public double getAverageCost() {
		return averageCost;
	}
	
	/**
	 * @return the distance of the flight
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * @return the cancelled probability
	 */
	public double getCancelledProbability() {
		return cancelledProbability;
	}
	
	/**
	 * @return the average delay
	 */
	public double getAverageDelay() {
		return averageDelay;
	}
	
	@Override
	public boolean equals(Object _other) {
		if(!(_other instanceof FlightEdge)) {
			return false;
		}
		FlightEdge other = (FlightEdge) _other;
		
		if(this.origin.equals(other.origin) && this.destination.equals(other.destination)) {
			return true;
		}
		
		return false;
	}
}
