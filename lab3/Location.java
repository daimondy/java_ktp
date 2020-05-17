public class Location
{
	/** X coordinate of this location. **/
	public int xCoord;

	/** Y coordinate of this location. **/
	public int yCoord;


	/** Creates a new location with the specified integer coordinates. **/
	public Location(int x, int y)
	{
		xCoord = x;
		yCoord = y;
	}

	/** Creates a new location with coordinates (0, 0). **/
	public Location()
	{
		this(0, 0);
	}
	
	public boolean equals(Object obj) 
	{ 
		// Instanceof тестирует тип обьекта
		// Возврат определен как false если ссылка имеет значение null 
		if (obj instanceof Location) 
		{
			Location other = (Location) obj; 
			
			if (xCoord == other.xCoord && yCoord == other.yCoord) 
			{ 
					return true; 
			} 
		} 
		
		return false; 
	}
	
	public int hashCode() 
	{ 
		int result = 15; // Некоторое просто число 
		result = 17 * result + (xCoord * 11); 
		result = 23 * result + (yCoord * 13); 
		return result; 
	}
}
