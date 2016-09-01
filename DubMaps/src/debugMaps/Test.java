package debugMaps;
import dubMaps.CampusGraph;
import dubMaps.CampusParser;

public class Test {
	public static void main(String[] args) {		
		CampusParser c = new CampusParser("src/data/campus_buildings.dat",
				"src/data/campus_paths.dat");
		CampusGraph g = c.getGraph();
		//System.out.println(g.toString());
		
	}
}
