package debugMaps;
import dubMaps.CampusGraph;
import dubMaps.CampusLocation;
import dubMaps.CampusParser;
import dubMaps.Edge;
import dubMaps.Node;

public class Test {
	public static void main(String[] args) {		
		CampusParser c = new CampusParser("src/data/campus_buildings.dat",
				"src/data/campus_paths.dat");
		CampusGraph g = c.getGraph();
		//System.out.println(g.toString());
		Node<CampusLocation> n1 = g.getNode("CSE");
		Node<CampusLocation> n2 = g.getNode("LOW");
		
		for (Edge<CampusLocation> e: g.getPath(n1, n2)) {
			System.out.println(e.toString());
		}
	}
}
