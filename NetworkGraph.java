/**
 * 
 */
package assignment13;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * <p>This class represents a graph of flights and airports along with specific
 * data about those flights. It is recommended to create an airport class and a
 * flight class to represent nodes and edges respectively. There are other ways
 * to accomplish this and you are free to explore those.</p>
 * 
 * <p>Testing will be done with different criteria for the "best" path so be
 * sure to keep all information from the given file. Also, before implementing
 * this class (or any other) draw a diagram of how each class will work in
 * relation to the others. Creating such a diagram will help avoid headache and
 * confusion later.</p>
 * 
 * <p>Be aware also that you are free to add any member variables or methods
 * needed to completed the task at hand</p>
 * 
 * @author CS2420 Teaching Staff - Spring 2016
 */
public class NetworkGraph {

	LinkedList<AirportVertex> airportGraph;
	
	/**
	 * <p>Constructs a NetworkGraph object and populates it with the information
	 * contained in the given file. See the sample files or a randomly generated
	 * one for the proper file format.</p>
	 * 
	 * <p>You will notice that in the given files there are duplicate flights with
	 * some differing information. That information should be averaged and stored
	 * properly. For example some times flights are canceled and that is
	 * represented with a 1 if it is and a 0 if it is not. When several of the same
	 * flights are reported totals should be added up and then reported as an
	 * average or a probability (value between 0-1 inclusive).</p>
	 * 
	 * @param flightInfoPath - The path to the file containing flight data. This
	 * should be a *.csv(comma separated value) file
	 * 
	 * @throws FileNotFoundException The only exception that can be thrown in
	 * this assignment and only if a file is not found.
	 */
	public NetworkGraph(String flightInfoPath) throws FileNotFoundException {
		airportGraph = new LinkedList<>();
		
		BufferedReader br = null;
		String line = "";
		String delimiter = ",";
		String[] separatedLine;
		String origin, destination, carrier;
		int delay, cancelled, time, distance;
		double cost;
		
		try {
			br = new BufferedReader(new FileReader(flightInfoPath));
			br.readLine();
			
			while ((line = br.readLine()) != null) {
				// Split line into string array delimited by commas
				separatedLine = line.split(delimiter);
				
				// Extract data
				origin = separatedLine[0];
				destination = separatedLine[1];
				carrier = separatedLine[2];
				delay = Integer.parseInt(separatedLine[3]);
				cancelled = Integer.parseInt(separatedLine[4]);
				time = Integer.parseInt(separatedLine[5]);
				distance = Integer.parseInt(separatedLine[6]);
				cost = Double.parseDouble(separatedLine[7]);
				
				// Create new vertex
				AirportVertex airport = new AirportVertex(origin);
				
				// Only add vertex to graph if it doesn't already exist
				if(!airportGraph.contains(airport)) {
					airportGraph.add(airport);
				} else {
					// get the airport object to update if it already exists
					int airportIndex = airportGraph.indexOf(airport);
					airport = airportGraph.get(airportIndex);
				}

				AirportVertex newDestination = new AirportVertex(destination);
				if(!airportGraph.contains(newDestination)) {
					airportGraph.add(newDestination);
				} else {
					// get the airport object to update if it already exists
					int airportIndex = airportGraph.indexOf(newDestination);
					newDestination = airportGraph.get(airportIndex);
				}
				
				// Create new edge
				FlightEdge flight = new FlightEdge(airport, newDestination, distance);
				
				// Check if flight already exists for that airport
				if(!airport.containsFlight(flight)) {
					airport.addFlight(flight);
				} else {
					// Get the flight object to update if it already exists
					flight = airport.getFlight(flight);
				}
				
				// Add new flight data to the flight
				flight.addFlight(carrier, delay, cancelled, time, cost);
			}
			
			br.close();
		} catch (Exception e) {
			throw new FileNotFoundException();
		}
	}

	/**
	 * This method returns a BestPath object containing information about the best
	 * way to fly to the destination from the origin. "Best" is defined by the
	 * FlightCriteria parameter <code>enum</code>. This method should throw no
	 * exceptions and simply return a BestPath object with information dictating
	 * the result. For example, if a destination or origin is not contained in
	 * this instance of NetworkGraph simply return a BestPath with no path (not a
	 * <code>null</code> path). If origin or destination are <code>null</code>
	 * return a BestPath object with null as origin or destination (which ever is
	 * <code>null</code>.
	 * 
	 * @param origin - The starting location to find a path from. This should be a
	 * 3 character long string denoting an airport.
	 * 
	 * @param destination - The destination location from the starting airport.
	 * Again, this should be a 3 character long string denoting an airport.
	 * 
	 * @param criteria - This enum dictates the definition of "best". Based on this
	 * value a path should be generated and return.
	 * 
	 * @return - An object containing path information including origin, destination,
	 * and everything in between.
	 */
	public BestPath getBestPath(String origin, String destination, FlightCriteria criteria) {
		
		PriorityQueue pq = new PriorityQueue();
		AirportVertex start = airportGraph.get(airportGraph.indexOf(origin));
		AirportVertex goal = airportGraph.get(airportGraph.indexOf(destination));
		AirportVertex current;
		BestPath path = new BestPath();
			
			pq.add(start);
		
			do {
				current = pq.deleteMin();
				
				if(current.equals(goal)) {
					return buildPath(start, goal);
				}
				
				current.setAsVisited();
				
				for (FlightEdge flight : current.getAllFlights()) {
					double newCost = flight.getDestination().getCost() + flightCost(flight, criteria);
					
					if(flight.getDestination().getCost() > newCost) {
						pq.remove(pq.indexOf(flight.getDestination()));
						flight.getDestination().setCost(newCost);
						flight.getDestination().setPrevious(flight.getOrigin());
						pq.add(flight.getDestination());
					}
					
				} 
				
			} while(pq.size() > 0);
		return null;
	}
	
	private BestPath buildPath(AirportVertex start, AirportVertex goal) {
		
		LinkedList<String> link = new LinkedList<>();
		AirportVertex current = goal;
		while(current.getPrevious()!=null){
			link.addFirst(current.getAirportName());
			current = current.getPrevious();
		}
		
		if(current.equals(start)){
			link.addFirst(current.getAirportName());
		}		
		
		BestPath best = new BestPath();
		
		for (String airport : link) {
			best.add(airport);
		}
		
		return best;
	}

	/**
	 * <p>This overloaded method should do the same as the one above only when looking for paths
	 * skip the ones that don't match the given airliner.</p>
	 * 
	 * @param origin - The starting location to find a path from. This should be a
	 * 3 character long string denoting an airport.
	 * 
	 * @param destination - The destination location from the starting airport.
	 * Again, this should be a 3 character long string denoting an airport.
	 * 
	 * @param criteria - This enum dictates the definition of "best". Based on this
	 * value a path should be generated and return.
	 * 
	 * @param airliner - a string dictating the airliner the user wants to use exclusively. Meaning
	 * no flights from other airliners will be considered.
	 * 
	 * @return - An object containing path information including origin, destination,
	 * and everything in between.
	 */
	public BestPath getBestPath(String origin, String destination, FlightCriteria criteria, String airliner) {
		//TODO:
		return null;
	}
	
	private double flightCost(FlightEdge flight, FlightCriteria criteria) {
		switch(criteria) {
			case COST:
				return flight.getAverageCost();
			case DELAY:
				return flight.getAverageDelay();
			case DISTANCE:
				return flight.getDistance();
			case CANCELED:
				return flight.getCancelledProbability();
			case TIME:
				return flight.getAverageTime();
		}
		return 0;
	}

}

