package dubMaps;

import java.util.ArrayList;
import java.util.List;

public class UIManager {
	private List<int[]> path;
	private final MapManager map; 
	
	public UIManager(MapManager map) {
		path = new ArrayList<int[]>();
		this.map = map;
	}
	
	public List<int[]> getPath(List<Edge<CampusLocation>> unscaledPath) {
		path = new ArrayList<int[]>();
		double xRatio, yRatio;
		
		xRatio = (1.0 * map.getMap().getWidth()) / (1.0 * map.getWidth());
		yRatio = (1.0 * map.getMap().getHeight()) / (1.0 * map.getHeight());
		
		// scale each path segment to fit the current display size
		for(Edge<CampusLocation> e: unscaledPath)
			path.add(new int[] { 
			    (int) (e.getParent().getLocation().getX() / xRatio),
			    (int) (e.getParent().getLocation().getY() / yRatio),
			    (int) (e.getChild().getLocation().getX() / xRatio),
			    (int) (e.getChild().getLocation().getY() / yRatio) 
			});
			
		return path;
	}
}
