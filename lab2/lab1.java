import java.util.Scanner;

class lab1{
	public static void main(String[] args){
		// Переменные
		boolean check = true;
		double perimeter, area;
		Scanner in = new Scanner(System.in);

		// Определение
		Point3d dot1 = new Point3d(0,0,0);
		Point3d dot2 = new Point3d(0,0,0);
		Point3d dot3 = new Point3d(0,0,0);
		
		// Ввод и проверка данных
		System.out.println("Vvedite 3 koordinati v formate x,y,z");
		setDots(dot1,dot2,dot3);
		while (check == true) {
			if (Point3d.isEqual(dot1,dot2) || Point3d.isEqual(dot2,dot3) || Point3d.isEqual(dot1,dot3)) {
				System.out.println("Odna ili neskolko tochek sovpadayut. Try again");
				setDots(dot1,dot2,dot3);
			}
			else
				check = false;
		}

		// Поиск площади
		area = Point3d.compuleArea(dot1,dot2,dot3);

		// Вывод
		System.out.println("Ploshad: " + String.valueOf(area));
	}

	// Ввод данных
	public static String[] getValues() {
		Scanner in = new Scanner(System.in);
		return in.nextLine().split(",");
	}

	// Установка координат точке
	public static void setCoords(String[] values, Point3d dot) {
		dot.setX(Double.parseDouble(values[0]));
		dot.setY(Double.parseDouble(values[1]));
		dot.setZ(Double.parseDouble(values[2]));
	}

	// Установка координат точкам
	public static void setDots(Point3d dot1, Point3d dot2, Point3d dot3){
		String[] coords;
		coords = getValues();
		setCoords(coords,dot1);
		coords = getValues();
		setCoords(coords,dot2);
		coords = getValues();
		setCoords(coords,dot3);
	}
}