package lab5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import lab5.fractal.*;

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

	private class FractalHandler implements ActionListener 
	{ 
		public void actionPerformed(ActionEvent e) 
		{ 
			String cmd = e.getActionCommand(); 

			if (e.getSource() == _fractalChooser) 
			{ 
				String selectedItem = _fractalChooser.getSelectedItem().toString();

				if(selectedItem.equals(Mandelbrot.nameString()))
				{
					_gen = new Mandelbrot();
				}
				else if(selectedItem.equals(Tricorn.nameString()))
				{
					_gen = new Tricorn();
				}
				else if(selectedItem.equals(BurningShip.nameString()))
				{
					_gen = new BurningShip();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Error: fractalChooser unknown choice");
					return;
				}
				
				_range = new Rectangle2D.Double();
				_gen.getInitialRange(_range);
				
				drawFractal();
			} 
			else if (cmd.equals("reset")) 
			{ 
				_range = new Rectangle2D.Double();
				_gen.getInitialRange(_range);
				
				drawFractal();
			} 
			else if (cmd.equals("save")) 
			{ 
				JFileChooser chooser = new JFileChooser();
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
				chooser.setFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);
				
				if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					try 
					{
						File fd = chooser.getSelectedFile();
						String filePath = fd.getPath();
						if(!filePath.toLowerCase().endsWith(".png"))
						{
							fd = new File(filePath + ".png");
						}
						
						ImageIO.write(_image.getImage(), "png", fd);
					} 
					catch (IOException exc) 
					{
						JOptionPane.showMessageDialog(null, "Error: couldn't save file ( " + exc.getMessage() + " )");
						
						exc.printStackTrace();
					}
				}
			} 
			else
			{
				JOptionPane.showMessageDialog(null, "Error: FractalHandler unknown action");
			}
		} 
	}

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
		_frame = new JFrame("Fractal Explorer");
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = _frame.getContentPane();

		contentPane.setLayout(new BorderLayout());
		
		FractalHandler handler = new FractalHandler();
		
		//Панель выбора фрактала
		JPanel fractalPanel = new JPanel();
		
		JLabel panelLabel = new JLabel("Fractal: ");
		fractalPanel.add(panelLabel);
		
		_fractalChooser = new JComboBox<String>();
		_fractalChooser.addItem(Mandelbrot.nameString());
		_fractalChooser.addItem(Tricorn.nameString());
		_fractalChooser.addItem(BurningShip.nameString());
		_fractalChooser.addActionListener(handler);
		
		fractalPanel.add(_fractalChooser);
		
		contentPane.add(fractalPanel, BorderLayout.NORTH);

		//изображение
		_image = new JImageDisplay(_displaySize, _displaySize);
		contentPane.add(_image, BorderLayout.CENTER);
		
		//Панель выбора фрактала
		JPanel buttonsPanel = new JPanel();
		
		//Кнопка сохранения
		JButton saveButton = new JButton("Save Image");
		saveButton.setActionCommand("save"); 
		saveButton.addActionListener(handler);
		buttonsPanel.add(saveButton);
		
		//Кнопка сброса
		JButton resetButton = new JButton("Reset Display");
		resetButton.setActionCommand("reset"); 
		resetButton.addActionListener(handler);
		buttonsPanel.add(resetButton);
		
		contentPane.add(buttonsPanel, BorderLayout.SOUTH);
		
		contentPane.addMouseListener(new MouseHandler());
		
		_frame.pack();
		_frame.setVisible(true);
		_frame.setResizable(false);
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
	
	private double getFractaleXcoord(int x)
	{
		return FractalGenerator.getCoord(_range.x, _range.x + _range.width, _displaySize, x);
	}
	
	private double getFractaleYcoord(int y)
	{
		return FractalGenerator.getCoord(_range.y, _range.y + _range.height, _displaySize, y);
	}
	
	//Запуск для FractalExplorer
	public static void main(String[] args) 
	{
		FractalExplorer explorer = new FractalExplorer (800);
		explorer.createAndShowGUI();
		explorer.drawFractal();
	} 
}