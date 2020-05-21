import java.awt.geom.Rectangle2D;

import lab5.FractalGenerator;

public class BurningShip extends FractalGenerator{

	public static final int MAX_ITERATIONS = 2000;
	
	/*Устанавливаем начаный диапазон от (-2;-2.5) до (2;1.5)
	Значения x, y будут равны -2 и -2.5
	Ширина и высота равны 4*/
	public void getInitialRange(Rectangle2D.Double range)
	{
		range.x = -2;
		range.y = -2.5;
		
		range.width = 4;
		range.height = 4;
	}

	/*Для фрактала Мандельброта функция z_n = (|Re(z_(n-1))| + i* |Im(z_(n-1))|)^2 + c, 
	c-это особая точка во фрактале, которую мы показываем. 
	Это вычисление повторяется до тех пор, пока либо |z| > 2
	или до тех пор, пока число итераций не достигнет максимального значения,
	например 2000*/
	public int numIterations(double x, double y)
	{
		int count = 0;
		
		double re = x;
		double im = y;
		
		double re2 = re*re;
		double im2 = im*im;
		
		double z_n2 = 0;
		
		while(count < MAX_ITERATIONS && z_n2 < 4)
		{
			im = Math.abs(2 * re * im) + y;
			re = (re2 - im2) + x;
			
			re2  = re*re;
			im2  = im*im;
			
			z_n2 = re2 + im2;
			++count;
		}
		
		return count < MAX_ITERATIONS ? count : -1;
	}
	
	public static String nameString()
	{
		return "Burning Ship";
	}
	
}
