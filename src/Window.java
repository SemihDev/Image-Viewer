package display;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author SemihDev
 *
 * Used Website
 * 
 * -https://de.wikibooks.org/wiki/Java_Standard:_Grafische_Oberfl%C3%A4chen_mit_Swing:_Top_Level_Container:_javax_swing_JOptionPane
 * -https://stackoverflow.com/questions/8202253/saving-a-java-2d-graphics-image-as-png-file
 */

public class Window implements MouseListener, MouseMotionListener, WindowListener {
	
	public static Window window;
	
	public static Credits credits;
	
	public JFrame jframe;
	
	public RenderPanel renderPanel;
	
	public JMenuBar jMenuBar;
	
	public JMenu file, changeMode, changeColor, brushSizeMode, misc;
	
	public JMenuItem open, close, black, red, green, blue, yellow, cyan, newFile, paint, save, remover, brushSizeTree,brushSizeFive, brushSizeTen, about, redo, undo;
	
	public ButtonGroup group, groupSize;
	
	public Point brush;
	
	public ArrayList<Point> brushParts = new ArrayList<Point>();
	
	public FileInputStream input;
	
	public final JFileChooser jFileChooser = new JFileChooser();
	
	public FileFilter pictureFilter;
	
	public int x = 640, y = 480, color, size = 5, times = 1, delete = 0;
	
	public String filePath;
	
	public JTextField xSize = new JTextField(), ySize = new JTextField();
	
	public JOptionPane createFile;
	
	public Object[] message = {
			"X-Size", xSize, "Y-Size", ySize
	};
	
	public Object[] options = {
			"Save File", "Don't save File", "Cancel"
	};
	
	public static final int BLACK = 0, RED = 1, GREEN = 2, BLUE = 3, YELLOW = 4, CYAN = 5, WHITE = 6;
	
	public boolean goPaint, openFile, askToSave, failed, openScreen;
	
	public BufferedImage rgbPaint;
	
	public ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	
	public Window() {
		jframe = new JFrame("ImageViewer");
		jframe.setSize(x, y);
		jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jframe.add(renderPanel = new RenderPanel());
		jframe.addMouseMotionListener(this);
		jframe.addMouseListener(this);
		jframe.addWindowListener(this);
		init();
		jframe.setResizable(false);
		jframe.setVisible(true);
	}
	
	public void init() {
		jMenuBar = new JMenuBar();
		file = new JMenu("File");
		changeMode = new JMenu("Mode");
		changeColor = new JMenu("Color");
		brushSizeMode = new JMenu("Size");
		misc = new JMenu("Misc");
		newFile = new JMenuItem("New File");
		open = new JMenuItem("Open");
		save = new JMenuItem("Save");
		close = new JMenuItem("Close");
		paint = new JCheckBoxMenuItem("Paint");
		redo = new JMenuItem("Redo");
		undo = new JMenuItem("Undo");
		remover = new JRadioButtonMenuItem("Remover");
		black = new JRadioButtonMenuItem("Black");
		red = new JRadioButtonMenuItem("Red");
		green = new JRadioButtonMenuItem("Green");
		blue = new JRadioButtonMenuItem("Blue");
		yellow = new JRadioButtonMenuItem("Yellow");
		cyan = new JRadioButtonMenuItem("Cyan");
		group = new ButtonGroup();
		brushSizeTen = new JRadioButtonMenuItem("10");
		brushSizeFive = new JRadioButtonMenuItem("5");
		brushSizeTree = new JRadioButtonMenuItem("3");
		groupSize = new ButtonGroup();
		about = new JMenuItem("About");
		pictureFilter = new FileNameExtensionFilter("Picture File (.JPG, .BMP, .PNG)", "JPG", "BMP", "PNG");
		createFile = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		goPaint = true;
		openFile = false;
		askToSave = false;
		openScreen = false;
		color = BLACK;
		
		brush = new Point(0, -10);
		
		jframe.add(jMenuBar, BorderLayout.NORTH);
		jMenuBar.add(file);
		file.add(newFile);
		file.add(open);
		file.add(save);
		file.add(close);
		
		jMenuBar.add(changeMode);
		changeMode.add(paint);
		changeMode.add(redo);
		changeMode.add(undo);
		changeMode.add(remover);
		group.add(remover);
		
		jMenuBar.add(changeColor);
		changeColor.add(black);
		changeColor.add(red);
		changeColor.add(green);
		changeColor.add(blue);
		changeColor.add(yellow);
		changeColor.add(cyan);
		group.add(black);
		group.add(red);
		group.add(green);
		group.add(blue);
		group.add(yellow);
		group.add(cyan);
		
		jMenuBar.add(brushSizeMode);
		brushSizeMode.add(brushSizeTen);
		brushSizeMode.add(brushSizeFive);
		brushSizeMode.add(brushSizeTree);
		groupSize.add(brushSizeTen);
		groupSize.add(brushSizeFive);
		groupSize.add(brushSizeTree);
		
		jMenuBar.add(misc);
		misc.add(about);
		
		paint.setSelected(true);
		black.setSelected(true);
		brushSizeFive.setSelected(true);
		jFileChooser.addChoosableFileFilter(pictureFilter);
		
		xSize.setText("300");
		ySize.setText("200");
		
		newFile.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createFile.createDialog(null, "New File").setVisible(true);
				
				if (createFile.getValue() != null) {
					if (createFile.getValue().hashCode() == JOptionPane.OK_OPTION) {
						failed = false;
						renderPanel.finished = false;
						
						try {
							x = Integer.parseInt(xSize.getText());
							y = Integer.parseInt(ySize.getText());
							
							jframe.setSize(x, y);
						}
						catch (NumberFormatException f) {
							failed = true;
							JOptionPane.showMessageDialog(null, "Number excepted", "Error", JOptionPane.ERROR_MESSAGE);
						}
						
						if (!failed) {
							if (rgbPaint != null) {
								rgbPaint.flush();
							}
							
							filePath = null;
							rgbPaint = null;
							
							for (int i = 0; i < images.size(); i++) {
								images.get(i).flush();
							}
							images.clear();
							brushParts.clear();
							
							delete = 0;
							times = 1;
						}
					}
				}
			}
		});
		
		open.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = jFileChooser.showOpenDialog(jFileChooser);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					if (jFileChooser.getSelectedFile() != null) {
						filePath = jFileChooser.getSelectedFile().getAbsolutePath();
						
						brush = new Point(0, -10);
						brushParts.clear();
						openFile = true;
						askToSave = false;
						renderPanel.finished = false;
						
						for (int i = 0; i < images.size(); i++) {
							images.get(i).flush();
						}
						images.clear();
						
						try {
							input = new FileInputStream(filePath);
							rgbPaint = ImageIO.read(input);
							input.close();
						}
						catch (IOException f) {
							JOptionPane.showMessageDialog(null, "Failed to load Image", "Error", JOptionPane.ERROR_MESSAGE);
						}
						delete = 0;
						times = 1;
						
						openScreen = true;
						renderPanel.repaint();
					}
				}
			}
		});
		
		save.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = jFileChooser.showSaveDialog(jFileChooser);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					if (jFileChooser.getSelectedFile() != null) {
						filePath = jFileChooser.getSelectedFile().getAbsolutePath();
						
						BufferedImage image = new BufferedImage(renderPanel.getWidth(), renderPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
						
						Graphics2D g = image.createGraphics();
						renderPanel.paintAll(g);
						
						try {
							ImageIO.write(image, "png", new File(filePath));
						}
						catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "Failed to save Image", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		
		close.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rgbPaint != null && askToSave) {
					int selected = JOptionPane.showOptionDialog(null, "File is not saved", "Close ImageViewer"
		                    , JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
		                    , null, options, options[0]);
					
					if (selected != JOptionPane.CANCEL_OPTION) {
						if (selected == JOptionPane.OK_OPTION) {
							save.doClick();
						}
						
						System.exit(0);
					}
				}
				else {
					System.exit(0);
				}
			}
		});
		
		paint.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goPaint = !goPaint;
				brush = new Point(0, -10);
			}
		});
		
		redo.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (times != 1) {
					times--;
					delete++;
					rgbPaint = images.get(images.size() - times);
					System.out.println(times + " Size: " + images.size());
					
					renderPanel.repaint();
				}
			}
		});
		
		undo.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (images.size() - times != 0 && !images.isEmpty()) {
					times++;
					delete--;
					rgbPaint = images.get(images.size() - times);
					System.out.println(times + " Size: " + images.size());
					
					renderPanel.repaint();
				}
			}
		});
		
		remover.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				color = WHITE;
			}
		});
		
		black.addItemListener(new java.awt.event.ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				color = BLACK;
			}
		});
		
		red.addItemListener(new java.awt.event.ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				color = RED;
			}
		});
		
		green.addItemListener(new java.awt.event.ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				color = GREEN;
			}
		});
		
		blue.addItemListener(new java.awt.event.ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				color = BLUE;
			}
		});
		
		yellow.addItemListener(new java.awt.event.ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				color = YELLOW;
			}
		});
		
		cyan.addItemListener(new java.awt.event.ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				color = CYAN;
			}
		});
		
		brushSizeTen.addItemListener(new java.awt.event.ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				size = 10;
			}
		});
		
		brushSizeFive.addItemListener(new java.awt.event.ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				size = 5;
			}
		});
		
		brushSizeTree.addItemListener(new java.awt.event.ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				size = 3;
			}
		});
		
		about.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				credits = new Credits();
			}
		});
	}
	
	public void screenShoot() {
		if (goPaint) {
			BufferedImage image = new BufferedImage(renderPanel.getWidth(), renderPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
			
			Graphics2D g = image.createGraphics();
			renderPanel.paintAll(g);
			g.dispose();
			
			if (times != 1) {
				System.out.println(delete);
				
				while (delete < images.size()) {
					images.get(delete).flush();
					images.remove(delete);
				}
				times = 1;
				System.out.println(times + " Size: " + images.size());
			}
			images.add(image);
			rgbPaint = images.get(images.size() - times);
			
			image.flush();
			brushParts.clear();
			delete++;
			
			renderPanel.repaint();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		int i = e.getModifiers();
		
		if (goPaint) {
			if (i == InputEvent.BUTTON1_MASK) {
				brushParts.add(new Point(x - 5, y - 63));
			}
			brush.setLocation(x - 5, y - 63);
			renderPanel.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if (goPaint) {
			brush.setLocation(x - 5, y - 63);
			renderPanel.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (goPaint) {
			brush = new Point(0, -10);
			renderPanel.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		int i = e.getModifiers();
		
		if (goPaint) {
			if (i == InputEvent.BUTTON1_MASK) {
				brushParts.add(new Point(x - 5, y - 63));
			}
			askToSave = true;
			
			renderPanel.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		screenShoot();
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		if (renderPanel.finished) {
			if (rgbPaint == null || openScreen) {
				screenShoot();
				openScreen = false;
			}
		}
		else {
			jframe.setState(Frame.ICONIFIED);
		}
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		close.doClick();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		if (!renderPanel.finished || openScreen) {
			jframe.setState(Frame.NORMAL);
		}
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		
	}
	
	public static void main(String[] args) {
		window = new Window();
	}
}
