import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent{

	private BufferedImage _image;

	//Принимает целочисленные значения ширины и высоты
	public JImageDisplay(int width, int height)
	{
		//инициирует объект новым изображением с шириной и высотой
		_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//Включения изображения в пользовательский интерфейс
		Dimension dim = new Dimension(width, height);
		super.setPreferredSize(dim);
	}
	
	protected void paintComponent(Graphics p)
	{
		super.paintComponent(p);
		//Передаем null ImageObserver, тк не нужна его функция
		p.drawImage(_image, 0, 0, _image.getWidth(), _image.getHeight(), null);
	}
	//устанавливаем все пиксели в изображениях в черный (RGB = 0)
	public void clearImage()
	{
		for(int j = 0 ; j < _image.getHeight() ; ++j)
		{
			for(int i = 0 ; i < _image.getWidth() ; ++i)
			{
				this.drawPixel(i, j, 0);
			}
		}
	}
	//Устанавливает пиксели в определенный цвет
	public void drawPixel(int x, int y, int rgbColor)
	{
		_image.setRGB(x, y, rgbColor);
	}
}