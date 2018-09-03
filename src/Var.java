package imageViewer;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Var {
	
	public static Var var = new Var();
	
	public int screenWidth, screenHeigth, imageX, imageY;
	
	public static int SCALE = 5;
	
	public String imagePath;
	
	public Image image;
	
	public Point brush;
	
	public ArrayList<Point> brushParts = new ArrayList<Point>();
	
	public Color brushColor = new Color(0);
	
	public Var() {
		screenWidth = 800;
		screenHeigth = 600;
		imageX = 0;
		imageY = 0;
		brush = new Point(0, -1);
	}
	
	public void readImage() {
		try {
			if (imagePath != null) {
				image = ImageIO.read(new FileInputStream(imagePath));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
