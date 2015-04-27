import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 * Lyft programming challenge: New Grad Software Engineer
 * 
 * @author Karthik Subramanya
 *
 *         This class calculates the shortest detour distance between four
 *         points given by its latitute and longitude as described by the
 *         problem below:
 * 
 *         Calculate the detour distance between two different rides. Given four
 *         latitude / longitude pairs, where driver one is traveling from point
 *         A to point B and driver two is traveling from point C to point D,
 *         write a function (in your language of choice) to calculate the
 *         shorter of the detour distances the drivers would need to take to
 *         pick-up and drop-off the other driver.
 */
public class DetourDistance {

	/**
	 * Entry point to the program. Reads the latitude and longitude of the four
	 * points.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);

		System.out.println("Please enter latitude of pointA:");
		double latA = in.nextDouble();
		System.out.println("Please enter longitude of pointA:");
		double longA = in.nextDouble();

		System.out.println("Please enter latitude of pointB:");
		double latB = in.nextDouble();
		System.out.println("Please enter longitude of pointB:");
		double longB = in.nextDouble();

		System.out.println("Please enter latitude of pointC:");
		double latC = in.nextDouble();
		System.out.println("Please enter longitude of pointC:");
		double longC = in.nextDouble();

		System.out.println("Please enter latitude of pointD:");
		double latD = in.nextDouble();
		System.out.println("Please enter longitude of pointD:");
		double longD = in.nextDouble();

		double distance = calculateShortestDetourDistance(latA, longA, latB,
				longB, latC, longC, latD, longD);
		if (distance == 0) {
			System.out.println("ERROR: Invalid coordinates");
		} else {
			System.out
					.println("The shortest distance between the given points is: "
							+ round(distance) + " miles.");
		}
		in.close();
	}

	/**
	 * Calculates the shortest detour distance between the given four points
	 * 
	 * @param latA
	 *            - Latitude of point A
	 * @param longA
	 *            - Longitude of point A
	 * @param latB
	 *            - Latitude of point B
	 * @param longB
	 *            - Longitude of point B
	 * @param latC
	 *            - Latitude of point C
	 * @param longC
	 *            - Longitude of point C
	 * @param latD
	 *            - Latitude of point D
	 * @param longD
	 *            - Longitude of point D
	 * @return - the shortest detour distance as described in the problem
	 *         statement
	 */
	public static double calculateShortestDetourDistance(double latA,
			double longA, double latB, double longB, double latC, double longC,
			double latD, double longD) {

		if (!validateLatitudes(latA, latB, latC, latD)) {
			System.out.println("ERROR:: Invalid latitude!");
			return 0;
		}
		if (!validateLongitudes(longA, longB, longC, longD)) {
			System.out.println("ERROR:: Invalid longitude!");
			return 0;
		}
		// Calculate distance for driver1 who picks driver2 and drops him off
		// A -> C -> D -> B
		double driver1 = distance(latA, longA, latC, longC) // driver1 starts at A and picks driver2 at C
				+ distance(latC, longC, latD, longD) // driver1 drops driver2 at D(from C)
				+ distance(latD, longD, latB, longB); // driver1 now travels to his destination B from D

		// Calculate distance for driver2 who picks driver1 and drops him off
		// C -> A -> B -> D
		double driver2 = distance(latC, longC, latA, longA) // driver2 starts at C and picks driver1 at A
				+ distance(latA, longA, latB, longB) // driver2 drops driver1 at B(from A)
				+ distance(latB, longB, latD, longD); // driver2 now travels to his destination D from B

		return (driver1 < driver2 ? driver1 : driver2); // returns the shortest detour distance
	}

	/**
	 * Calculates the distance between two points given its latitude and
	 * longitude based on the Haversine formula.
	 * 
	 * @param latitude1
	 *            - Latitude of point 1
	 * @param longitude1
	 *            - Longitude of point 1
	 * @param latitude2
	 *            - Latitude of point 2
	 * @param longitude2
	 *            - Longitude of point 2
	 * @return - The distance between points 1 and 2
	 */
	public static double distance(double latitude1, double longitude1,
			double latitude2, double longitude2) {
		double dLongitude = longitude1 - longitude2;
		double distance = Math.sin(degreesToRadians(latitude1))
				* Math.sin(degreesToRadians(latitude2))
				+ Math.cos(degreesToRadians(latitude1))
				* Math.cos(degreesToRadians(latitude2))
				* Math.cos(degreesToRadians(dLongitude));
		distance = Math.acos(distance);
		distance = radiansToDegrees(distance);
		distance = distance * 60 * 1.1515;
		return distance;
	}

	/**
	 * Converts decimal degrees to radians
	 * 
	 * @param degrees
	 *            - The decimal degree
	 * @return - The decimal degrees in radians
	 */
	public static double degreesToRadians(double degrees) {
		return (degrees * Math.PI / 180.0);
	}

	/**
	 * Converts radians to decimal degrees
	 * 
	 * @param radians
	 *            - The radians
	 * @return - The radians in decimal degrees
	 */
	public static double radiansToDegrees(double radians) {
		return (radians * 180.0 / Math.PI);
	}

	/**
	 * Validates any number of latitudes given
	 * 
	 * @param latitudes
	 *            - an array containing latitudes to be validated
	 * @return - true: if latitudes are valid false: if any latitude is invalid
	 */
	public static boolean validateLatitudes(double... latitudes) {
		for (double latitude : latitudes) {
			if (!(-90.0 <= latitude && latitude <= 90.0))
				return false;
		}
		return true;
	}

	/**
	 * Validates any number of longitudes given
	 * 
	 * @param longitudes
	 *            - an array containing latitudes to be validated
	 * @return - true: if latitudes are valid false: if any latitude is invalid
	 */
	public static boolean validateLongitudes(double... longitudes) {
		for (double longitude : longitudes) {
			if (!(-180.0 <= longitude && longitude <= 180.0))
				return false;
		}
		return true;
	}

	/**
	 * Round off the given double to 2 places after decimal
	 * 
	 * @param value
	 *            - the double number
	 * @return - rounded off double value
	 */
	public static double round(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP); // Round to 2 digits after decimal point, towards the nearest neighbour
		return bd.doubleValue();

	}
}
