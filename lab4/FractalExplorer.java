import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;

public class FractalExplorer {
	
	//Ширина и высота экрана
	private int _displaySize;
	
	/*Ссылка для обновления отображения в разных методах 
	в процессе вычсиления фракталов*/
	private JImageDisplay _image;
	
	//Ссылка на базовый класс для отображения других видов фракталов в будущем
	private FractalGenerator _generate;
	
	//Диапазон комплексной плоскости
	private Rectangle2D.Double _range;

	private class ResetHandler implements ActionListener 
	{ 
		public void actionPerformed(ActionEvent e) 
		{ 
			_range = new Rectangle2D.Double();
			_generate.getInitialRange(_range);
			
			drawFractal();
		} 
	}
	
	//Обработка событий мыши
	private class MouseHandler extends MouseAdapter 
	{ 
		public void mouseClicked(MouseEvent e)
		{ 
			double xCoord = FractalGenerator.getCoord(_range.x, _range.x + _range.width, _displaySize, e.getX());
			double yCoord = FractalGenerator.getCoord(_range.y, _range.y + _range.height, _displaySize, e.getY());
			
			_generate.recenterAndZoomRange(_range,xCoord, yCoord, 0.5);
			
			drawFractal();
		} 
	}
	
	public FractalExplorer(int displaySize)
	{
		_displaySize = displaySize;
		
		_generate = new Mandelbrot();
		
		_range = new Rectangle2D.Double();
		_generate.getInitialRange(_range);
	}

	//инициализация графического интерфейса
	public void createAndShowGUI()
	{
		JFrame frame = new JFrame("Fractal");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();

		contentPane.setLayout(new BorderLayout());

		_image = new JImageDisplay(_displaySize, _displaySize);
		contentPane.add(_image, BorderLayout.CENTER);
		
		JButton resetButton = new JButton("Reset display");
		resetButton.addActionListener(new ResetHandler());
		contentPane.add(resetButton, BorderLayout.SOUTH);
		
		contentPane.addMouseListener(new MouseHandler());
		
		//Размещение окна, его видимость, запрет на изменение размера
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	//Вывод на экран фракталов
	private void drawFractal()
	{
		double xCoord = 0;
		double yCoord = 0;
		
		float numIters = 0;
		float hue = 0;
		
		int rgbColor = 0;
		
		for(int x = 0 ; x < _displaySize ; ++x)
		{
			//x - пиксельная координата, xCoord - координата в пространстве фрактала
			xCoord = FractalGenerator.getCoord(_range.x, _range.x + _range.width, _displaySize, x);
			
			for(int y = 0 ; y < _displaySize ; ++y)
			{
				//y - пиксельная координата, yCoord - координата в пространстве фрактала
				yCoord = FractalGenerator.getCoord(_range.y, _range.y + _range.height, _displaySize, y);
				
				numIters = _generate.numIterations(xCoord, yCoord);
				if(numIters < 0)
				{
					rgbColor = 0;
				}
				else
				{
					hue = 0.7f + numIters / 200f;
					rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
				}
				
				//Обновление с соотвествии с цветом для пикселя
				_image.drawPixel(x, y, rgbColor);
			}
		}
		
		//Перерисовка
		_image.repaint();
	}
	
	//Запуск для FractalExplorer
	public static void main(String[] args) 
	{
		FractalExplorer explorer = new FractalExplorer (800);
		explorer.createAndShowGUI();
		explorer.drawFractal();
	} 
}