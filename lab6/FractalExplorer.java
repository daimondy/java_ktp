import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FractalExplorer {

	//Ширина и высота экрана
	private int _displaySize;

	/*Ссылка для обновления отображения в разных методах 
	в процессе вычсиления фракталов*/
	private JImageDisplay _image;
	
	//Поддержка мульти фракталов
	private JComboBox<String> _fractalChooser;
	
	//Ссылка на базовый класс для отображения других видов фракталов в будущем
	private FractalGenerator _generate;

	//кнопка сохранения
	private JButton _saveButton;
	
	//кнопка сброса
	private JButton _resetButton;

	/*поле отслеживания количество оставшихся строк, которые 
	должны быть завершены*/
	private int _rowsRemaining;
	
	//указание диапазона комплексной плоскости, которую мы сейчас показываем
	Rectangle2D.Double _range;

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
					_generate = new Mandelbrot();
				}
				else if(selectedItem.equals(Tricorn.nameString()))
				{
					_generate = new Tricorn();
				}
				else if(selectedItem.equals(BurningShip.nameString()))
				{
					_generate = new BurningShip();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Error: fractalChooser unknown choice");
					return;
				}
				
				_range = new Rectangle2D.Double();
				_generate.getInitialRange(_range);
				
				drawFractal();
			} 
			else if (cmd.equals("reset")) 
			{ 
				_range = new Rectangle2D.Double();
				_generate.getInitialRange(_range);
				
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
	
	//Обработка событий мыши
	private class MouseHandler extends MouseAdapter 
	{ 
		public void mouseClicked(MouseEvent e)
		{ 
			if(_rowsRemaining > 0)
				return;
			double xCoord = getFractaleXcoord(e.getX());
			double yCoord = getFractaleYcoord(e.getY());
			
			_generate.recenterAndZoomRange(_range,xCoord, yCoord, 0.5);
			
			drawFractal();
		} 
	}
	
	private class FractalWorker extends SwingWorker<Object, Object> {
		
		//y-координата строки, которая будет вычислена
		private int _y;
		
		//содержит вычисленные значения RGB для каждого пикселя в этой строке
		private int[] _rgbVals;
		
		public FractalWorker(int y)
		{
			_y = y;
		}
		
		//хранит каждое значение RGB в соответствующем элементе целочисленного массива
		protected Object doInBackground()
		{
			_rgbVals = new int[_displaySize];
			
			int numIters;
			int rgbColor;
			
			double yCoord = getFractaleYcoord(_y);
			double xCoord;
			float hue;
			
			for(int x = 0 ; x < _displaySize ; ++x)
			{
				xCoord = getFractaleXcoord(x);
				
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
				
				_rgbVals[x] = rgbColor;
			}
			
			return null;
		}
		
		protected void done()
		{
			for(int x = 0 ; x < _displaySize ; ++x)
			{
				_image.drawPixel(x, _y, _rgbVals[x]);
			}
			
			_image.repaint(0, 0, _y, _displaySize, 1);
			
			if((--_rowsRemaining) < 1)
			{
				enableUI(true);
			}
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
		JFrame frame = new JFrame("Fractal Explorer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();
		
		contentPane.setLayout(new BorderLayout());
		
		FractalHandler handler = new FractalHandler();
		
		//выбор панели с фракталами
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
		
		//выбор панели с фракталами
		JPanel buttonsPanel = new JPanel();
		
		//кнопка сохранения
		_saveButton = new JButton("Save Image");
		_saveButton.setActionCommand("save"); 
		_saveButton.addActionListener(handler);
		buttonsPanel.add(_saveButton);
		
		//кнопка сброса
		_resetButton = new JButton("Reset Display");
		_resetButton.setActionCommand("reset"); 
		_resetButton.addActionListener(handler);
		buttonsPanel.add(_resetButton);
		
		contentPane.add(buttonsPanel, BorderLayout.SOUTH);
		
		contentPane.addMouseListener(new MouseHandler());
		
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	//Вывод на экран фракталов
	public void drawFractal()
	{

		this.enableUI(false);
		_rowsRemaining = _displaySize;
		
		for(int y = 0 ; y < _displaySize ; ++y)
		{
			FractalWorker worker = new FractalWorker(y);
			worker.execute();
		}
		//Перерисовка
		_image.repaint();
	}
	
	private void enableUI(boolean val)
	{
		_fractalChooser.setEnabled(val);
		
		_saveButton.setEnabled(val);
		_resetButton.setEnabled(val);
	}

	private double getFractaleXcoord(int x)
	{
		return FractalGenerator.getCoord(_range.x, _range.x + _range.width, _displaySize, x);
	}
	

	private double getFractaleYcoord(int y)
	{
		return FractalGenerator.getCoord(_range.y, _range.y + _range.height, _displaySize, y);
	}
	
	//zапуск для FractalExplorer
	public static void main(String[] args) 
	{
		FractalExplorer explorer = new FractalExplorer (400);
		explorer.createAndShowGUI();
		explorer.drawFractal();
	} 
}
