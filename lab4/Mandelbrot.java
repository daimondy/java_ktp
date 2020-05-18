import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator{

	//Константа с максимальным числом интераций
	public static final int MAX_ITERATIONS = 2000;

	/*Позволяет генератору фракталов определить наиболее интересную
	область для фрактала*/
	public void getInitialRange(Rectangle2D.Double range)
	{
		//Диапазон фрактала
		range.x = -2;
		range.y = -1.5;
		
		range.width = 3;
		range.height = 3;
	}
	
	//итеративная функция для фракталов
	public int numIterations(double x, double y)
	{
		int count = 0;
		
		double re = x;
		double im = y;

		/*используем умножение, чтобы избежать вычислений 
		квадратного корня*/
		double re2 = re*re; 
		double im2 = im*im;
		
		double z_n2 = 0;
		
		while(count < MAX_ITERATIONS && z_n2 < 4)
		{
			im = (2 * re * im) + y;
			re = (re2 - im2) + x;
			
			re2  = re*re;
			im2  = im*im;
			
			z_n2 = re2 + im2;
			++count;
		}
		
		//Возвращаем -1, что точка не выходит за границы
		return count < MAX_ITERATIONS ? count : -1;
	}
}
