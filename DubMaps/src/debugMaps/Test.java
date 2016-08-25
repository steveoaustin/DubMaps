package debugMaps;
import dubMaps.*;

public class Test {
	public static void main(String[] args) {
		System.out.println("This is a test\nme too thanks");
		Graph<String> g = new Graph<String>();
		Node<String> n = new Node<String>("Test node");
		g.add(n);
	    System.out.println(g.getNode("Test node").toString());
	}
}
