import java.util.HashMap;
public class AStarState
{
	/** This is a reference to the map that the A* algorithm is navigating. **/
	private Map2D map;

	/**  collections of open waypoints **/
	private HashMap<Location, Waypoint> openWaypoints;
	
	/**  collections of closed waypoints **/
	private HashMap<Location, Waypoint> closedWaypoints; 

	/**
	 * Initialize a new state object for the A* pathfinding algorithm to use.
	 **/
	public AStarState(Map2D map)
	{
		if (map == null)
			throw new NullPointerException("Karta ne moshet bit pustoi");

		this.map = map;
		
		openWaypoints = new HashMap<Location, Waypoint>();
		closedWaypoints = new HashMap<Location, Waypoint>();
	}

	/** Returns the map that the A* pathfinder is navigating. **/
	public Map2D getMap()
	{
		return map;
	}

	/**
	 * This method scans through all open waypoints, and returns the waypoint
	 * with the minimum total cost.  If there are no open waypoints, this method
	 * returns <code>null</code>.
	 **/
	public Waypoint getMinOpenWaypoint()
	{
		Waypoint rez = null;
		
		float min = Float.POSITIVE_INFINITY;
		float totalCost = 0;
		
		for(Waypoint o : openWaypoints.values())
		{
			totalCost = o.getTotalCost();
			
			if(min > totalCost)
			{
				min = totalCost;
				rez = o;
			}
		}
		
		return rez;
	}

	public boolean addOpenWaypoint(Waypoint newWP)
	{
		Waypoint other = openWaypoints.get(newWP.loc);
		
		if(other == null || newWP.getPreviousCost() < other.getPreviousCost())
		{
			openWaypoints.put(newWP.loc, newWP);
			return true;
		}

		return false;
	}


	/** Returns the current number of open waypoints. **/
	public int numOpenWaypoints()
	{
		return openWaypoints.size();
	}


	/**
	 * This method moves the waypoint at the specified location from the
	 * open list to the closed list.
	 **/
	public void closeWaypoint(Location loc)
	{
		Waypoint point = openWaypoints.remove(loc);
		
		if(point != null)
		{
			closedWaypoints.put(loc, point);
		}
	}

	/**
	 * Returns true if the collection of closed waypoints contains a waypoint
	 * for the specified location.
	 **/
	public boolean isLocationClosed(Location loc)
	{
		return closedWaypoints.containsKey(loc);
	}
}

