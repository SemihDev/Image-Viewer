package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import static display.Window.window;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel {
	
	public boolean finished = false;
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (window.rgbPaint != null)  {
			g.drawImage(window.rgbPaint, 0, 0, this);
			
			if (window.openFile) {
				window.jframe.setSize(window.rgbPaint.getWidth(), window.rgbPaint.getHeight() + 63);
				window.openFile = false;
			}
		}
		else {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, window.x, window.y);
		}
		
		if (window.color == Window.BLACK) {
			g.setColor(Color.BLACK);
		}
		else if (window.color == Window.RED) {
			g.setColor(Color.RED);
		}
		else if (window.color == Window.GREEN) {
			g.setColor(Color.GREEN);
		}
		else if (window.color == Window.BLUE)  {
			g.setColor(Color.BLUE);
		}
		else if (window.color == Window.YELLOW) {
			g.setColor(Color.YELLOW);
		}
		else if (window.color == Window.CYAN) {
			g.setColor(Color.CYAN);
		}
		else {
			g.setColor(Color.WHITE);
		}
		g.fillRect(window.brush.x, window.brush.y, window.size, window.size);
		
		for (Point point : window.brushParts) {
			g.fillRect(point.x, point.y, window.size, window.size);
		}
		
		finished = true;
	}
}
