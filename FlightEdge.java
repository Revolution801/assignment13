package assignment13;

public class FlightEdge {
	
	private AirportVertex origin;
	private AirportVertex destination;
	private ChainingHashTable<String> carriers = new ChainingHashTable<>(10); 
	private double averageTime = 0;
	private double averageCost = 0;
	private int distance = 0;
	private double cancelledProbability = 0;
	private double averageDelay = 0;
	private int numFlights = 0;
	
	public FlightEdge(AirportVertex _origin, AirportVertex _destination, int _distance){
		origin = _origin;
		destination = _destination;
		distance = _distance;
	}
	
	public void addFlight(String carrier, int delay, int cancelled, int _time, double _cost) {
		if(!carriers.contains(carrier)) {
			carriers.add(carrier);
		}
		
		averageDelay = ((averageDelay * numFlights) + delay) / (numFlights + 1);
		averageTime = ((averageTime * numFlights) + _time) / (numFlights + 1);
		averageCost = ((averageCost * numFlights) + _cost) / (numFlights + 1);
		cancelledProbability = ((cancelledProbability * numFlights) + cancelled) / (numFlights + 1);
		numFlights++;
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
	 * 
	 * @param _carrier
	 * @return
	 */
	public boolean isCarrierOffered(String _carrier){
		return carriers.contains(_carrier);
	}

	/**
	 * @return the averageTime
	 */
	public double getAverageTime() {
		return averageTime;
	}

	/**
	 * @return the averageCost
	 */
	public double getAverageCost() {
		return averageCost;
	}

	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * @return the cancelledProbability
	 */
	public double getCancelledProbability() {
		return cancelledProbability;
	}

	/**
	 * @return the averageDelay
	 */
	public double getAverageDelay() {
		return averageDelay;
	}
	
	@Override
	public boolean equals(Object _other) {
		if(!(_other instanceof FlightEdge)){
			return false;
		}
		FlightEdge other = (FlightEdge) _other;
		
		if(this.origin.equals(other.origin) && this.destination.equals(other.destination)) {
			return true;
		}
		
		return false;
	}
}
