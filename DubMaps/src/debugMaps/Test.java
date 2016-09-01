package debugMaps;
import java.util.ArrayList;

import dubMaps.*;

public class Test {
	public static void main(String[] args) {
		System.out.println("This is a test\nme too thanks");
		CampusGraph g = new CampusGraph();
		Node<CampusLocation> n1 = new Node<CampusLocation>("test");
		Node<CampusLocation> n2 = new Node<CampusLocation>("test");
		
		g.add(n1);
		if (g.contains(n2)) {
			System.out.println("yes");
		} else {
			System.out.println("nah");
		}
	}
}
