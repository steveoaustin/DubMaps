package debugMaps;
import java.util.ArrayList;

import dubMaps.Graph;
import dubMaps.Node;

public class Test {
	public static void main(String[] args) {
		System.out.println("This is a test\nme too thanks");
		Graph<String> g = new Graph<String>();
		Node<String> n = new Node<String>("Test node");
		g.add(n);
	    System.out.println(g.getNode("Test node").toString());
	    
	    ArrayList<Node<String>> list = new ArrayList<Node<String>>();
	    
	    for (int i = 0; i < 100; i++) {
	    	list.add(new Node<String>());
	    }
	    
	    for (Node<String> node: list) {
	    	System.out.println(node.toString());
	    }
	}
}
