package imageViewer;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import imageViewer.Var;

// GUI Interface

public class ImageViewer implements MouseMotionListener, MouseListener {
	
	public Var var = Var.var;
	
	public static ImageViewer imageViewer;
	
	public ImagePanel imagePanel;
	
	public RenderPanel renderPanel;
	
	public JFrame jframe;
	
	public JMenuBar jMenuBar;
	
	public JMenu jMenu, changeMode;
	
	public JMenuItem close, open, imageView, paint, clean;
	
	public ButtonGroup group;
	
	public static void main(String[] args) {
		imageViewer = new ImageViewer();
	}
	
	public ImageViewer() {
		jframe = new JFrame("Image Viewer");
		jframe.setSize(var.screenWidth, var.screenHeigth);
		jframe.setLocationRelativeTo(null);
		jframe.addMouseMotionListener(this);
		jframe.addMouseListener(this);
		
		jMenuBar = new JMenuBar();
		jMenu = new JMenu("File");
		changeMode = new JMenu("Mode");
		jframe.add(jMenuBar, BorderLayout.NORTH);
		
		open = new JMenuItem("Open");
		clean = new JMenuItem("Clean Paint");
		close = new JMenuItem("Close");
		jMenu.add(open);
		jMenu.addSeparator();
		jMenu.add(clean);
		jMenu.addSeparator();
		jMenu.add(close);
		jMenuBar.add(jMenu);
		
		group = new ButtonGroup();
		imageView = new JRadioButtonMenuItem("ImageViewer");
		paint = new JRadioButtonMenuItem("Paint");
		imageView.setSelected(true);
		group.add(imageView);
		group.add(paint);
		changeMode.add(imageView);
		changeMode.add(paint);
		jMenuBar.add(changeMode);
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(imagePanel = new ImagePanel(var.image));
		
		open.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new FileChooser();
			}
		});
		
		clean.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				var.brushParts.clear();
			}
		});
		
		close.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		imageView.addItemListener(new java.awt.event.ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (imageView.isSelected()) {
					jframe.getContentPane().remove(renderPanel);
					jframe.getContentPane().add(imagePanel = new ImagePanel(var.image));
					jframe.invalidate();
					jframe.validate();
				}
			}
		});
		
		paint.addItemListener(new java.awt.event.ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (paint.isSelected()) {
					jframe.getContentPane().remove(imagePanel);
					jframe.getContentPane().add(renderPanel = new RenderPanel());
					jframe.invalidate();
					jframe.validate();
					jframe.setSize(var.screenWidth, var.screenHeigth);
				}
			}
		});
		showImage(null);
	}
	
	public void showImage(String filePath) {
		var.imagePath = filePath;
		var.readImage();
		imagePanel.setImage(var.image);
		jframe.setResizable(false);
		jframe.setVisible(true);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		int i = e.getModifiers();
		
		if (i == InputEvent.BUTTON1_MASK) {
			var.brushParts.add(new Point(x, y));
		}
		else {
			var.brush.setLocation(x, y);
		}
		
		if (renderPanel != null) {
			renderPanel.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		var.brush.setLocation(x, y);
		
		if (renderPanel != null) {
			renderPanel.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		int i = e.getModifiers();
		
		if (i == InputEvent.BUTTON1_MASK) {
			var.brushParts.add(new Point(x, y));
		}
		
		if (renderPanel != null) {
			renderPanel.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
}
