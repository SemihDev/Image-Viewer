package imageViewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import imageViewer.Var;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel {
	
	public static Color white = new Color(16777215);
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Var var = Var.var;
		g.setColor(white);
		g.fillRect(var.imageX, var.imageY, var.screenWidth, var.screenHeigth);
		
		g.setColor(var.brushColor);
		g.fillRect(var.brush.x - 5, var.brush.y - 63, Var.SCALE, Var.SCALE);
		
		for (Point point : var.brushParts) {
			g.fillRect(point.x - 5, point.y - 63, Var.SCALE, Var.SCALE);
		}
	}
}
