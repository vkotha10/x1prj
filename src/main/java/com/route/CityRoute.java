package com.route;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

/**
 * Loads the route map from file Checks whether route exists for a given origin
 * and destination; Case Sensitive if exists returns "YES"; otherwise "NO"
 */
public class CityRoute {

	// Map for routes data from file is read into
	private Map<String, LinkedList<String>> routeMap = new HashMap<String, LinkedList<String>>();

	/**
	 * Reads data from file and creates route map with those values; map has values
	 * for both ways - Ex: Newark to Boston and Boston to Newark
	 * 
	 * @throws Exception
	 */
	public void readData() throws Exception {

		String fileName = "city.txt";

		try {

			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(fileName).getFile());
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] city = line.split(",");
				String city2 = city[1].trim();
				addRoute(city[0], city2);
				addRoute(city2, city[0]);
			}

			scanner.close();
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Adds a route to the map
	 * 
	 * @param city1 origin city
	 * @param city2 destination city
	 * @throws Exception
	 */
	private void addRoute(String city1, String city2) throws Exception {

		try {
			// Adds the the city to neighboring cities list;
			// if list does not exist, creates new one
			LinkedList<String> neighborCity = routeMap.get(city1);
			if (neighborCity == null) {
				neighborCity = new LinkedList<String>();
				routeMap.put(city1, neighborCity);
			}

			neighborCity.add(city2);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Returns all the neighboring cities for a city
	 * 
	 * @param city city for which neighboring cities retrieved
	 * @return     list of neighboring cities for a city
	 * @throws Exception
	 */
	private LinkedList<String> getNeighborCities(String city) throws Exception {

		LinkedList<String> neighborCities;
		try {
			neighborCities = routeMap.get(city);
			if (neighborCities == null) {
				return new LinkedList<String>();
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return neighborCities;
	}

	/**
	 * Checks whether route exists for a origin and a destination
	 * 
	 * @param origin        origin city
	 * @param destination   destination city
	 * @return route exists "YES"; otherwise "NO"
	 * @throws Exception
	 */
	public String checkRouteExists(String origin, String destination) throws Exception {

		boolean routeExists = false;
		LinkedList<String> visited = new LinkedList<String>();

		try {
			routeExists = checkExists(visited, origin, destination);

			if (routeExists) {
				return "YES";
			} else {
				routeExists = checkExists(visited, destination, origin);
			}

			if (routeExists) {
				return "YES";
			} else {
				return "NO";
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Checks whether the route exists going through the route map;
	 * 
	 * @param visited     Keeps track of keys visited
	 * @param origin      origin city
	 * @param destination destination city
	 * @return returns true if route exists; otherwise false
	 * @throws Exception
	 */
	private boolean checkExists(LinkedList<String> visited, String origin, String destination) throws Exception {

		boolean flag = false;
		LinkedList<String> cities = getNeighborCities(origin);
		LinkedList<String> cityKeys = new LinkedList<String>();

		try {

			// for each neighboring city
			for (String city : cities) {

				if (visited.contains(city) && (!city.equals(destination))) {
					continue;
				}

				if (city.equals(destination)) {
					return true;
				}

				cityKeys.add(city);
			}

			if (!visited.contains(origin)) {
				visited.add(origin);
			}

			// Goes through all the keys of the neighboring cities
			// stored in citiKeys
			for (String key : cityKeys) {
				if (checkExists(visited, key, destination)) {
					return true;
				}
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return flag;
	}
}
