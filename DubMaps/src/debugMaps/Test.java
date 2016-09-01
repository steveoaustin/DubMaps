package debugMaps;
import dubMaps.CampusGraph;
import dubMaps.CampusParser;

public class Test {
	public static void main(String[] args) {
		
		CampusParser c = new CampusParser("src/data/campus_buildings.dat",
				"src/data/campus_paths.dat");
		CampusGraph g = c.getGraph();
		for (String s : g.getNodeStrings()) {
			System.out.println(s);
		}
		
		/*
		System.out.println("This is a test\nme too thanks");
		CampusGraph g = new CampusGraph();
		CampusLocation l1 = new CampusLocation("lick", "li-li-lick muh balls", 6, 9);
		CampusLocation l2 = new CampusLocation("lick", "li-li-lick muh balls", 6, 9);
		Node<CampusLocation> n1 = new Node<CampusLocation>(l1);
		Node<CampusLocation> n2 = new Node<CampusLocation>(l2);
		Node<CampusLocation> n3 = new Node<CampusLocation>(10, 20);
		g.add(n1);
		g.add(n3);
		if (g.contains(n2)) {
			System.out.println("yes");
		} else {
			System.out.println("nah");
		}
		
		for (String e : g.getNodeStrings()) {
			System.out.println(e);
		}
		*/
	}
}
