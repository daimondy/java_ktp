class Point2d {
	private double xCoord; 
	private double yCoord;


	public Point2d (double x, double y){
		xCoord = x; //Координата x
		yCoord = y;//Координата y
	}

	//Возвращение координаты x
	public double getX (){ 
		return xCoord;
	}

	//Возвращение координаты y
	public double getY (){ 
		return yCoord;
	}

	//Установка координаты x
	public void setX (double val){ 
		xCoord = val;
	}

	//Установка координаты y
	public void setY (double val){
		yCoord = val;
	}

	//Проверка
	public static boolean isEqual (Point2d val1, Point2d val2) {
		if ((val1.getX() == val2.getX()) && (val1.getY() == val2.getY()))
			return true;
		else
			return false;
	}
}