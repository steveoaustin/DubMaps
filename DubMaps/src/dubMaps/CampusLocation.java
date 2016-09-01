package dubMaps;

/**
 * Representation invariant: shortName, longName, x, and y are not null 
 * 
 * Abstraction function: Campus location represents a location of the uw campus with location 
 * (x,y), full name longName, and abbreviated name shortName
 */
public class CampusLocation implements Comparable<CampusLocation>{
	private final String shortName;
	private final String longName;
	private final double x;
	private final double y;
	
	/**
	 * Instantiates a new CampusLocation object
	 * @modifies this
	 * @requires all params are not null
	 * @param shortName The locations abbreviated name
	 * @param longName The locations full name
	 * @param x The locations x position
	 * @param y The location y position
	 */
	public CampusLocation(String shortName, String longName, double x, double y) {
		this.shortName = shortName;
		this.longName = longName;
		this.x = x;
		this.y = y;
		checkRep();
	}
	
	/**
	 * returns the locations abbreviated name
	 * @return Abbreviated name
	 */
	public String getName() {
		return shortName;
	}
	
	/**
	 * returns the locations full name
	 * @return longName Location's full name
	 */
	public String getLongName() {
		return longName;
	}
	
	/**
	 * returns the location's x-coordinate
	 * @return x X-coordinate
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * returns the location's y-coordinate
	 * @return y Y-coordinate
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Returns a string representation of the location data
	 */
	public String toString() {
		return(shortName + " " + longName + " " + x + " " + y + "\n");
	}
	
	/**
	 * Returns an integer representing the location's shortName's lexicographical value
	 */
	public int compareTo(CampusLocation other) {
		return this.getName().compareTo(other.getName());
	}
	
	@Override
	/**
	 * Standard hashCode function
	 */
	public int hashCode() {
		String xString = String.valueOf(x);
		String yString = String.valueOf(y);
		//combines x and y via string concatenation for a more unique value
		String total = xString + yString; 
		return total.hashCode();
	}
	
	/**
	 * Standard equals function: equality based on matching xy coordinates 
	 */
	public boolean equals(Object o) {
		if(!(o instanceof CampusLocation)) {
			return false;
		}
		CampusLocation other = (CampusLocation) o;
		return (this.x == other.x && this.y == other.y);
	}
	
	//verifies the representation invariant
	private void checkRep() {
		assert(shortName != null);
		assert(longName != null);
		assert(x != 0.0);
		assert(y != 0.0);
	}
}




