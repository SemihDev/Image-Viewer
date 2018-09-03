package imageViewer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

import imageViewer.Var;
import imageViewer.ImageViewer;

@SuppressWarnings("serial")
public class ImagePanel extends Panel {
	
	private Image image;
	
	public ImagePanel(Image image) {
		setImage(image);
	}
	
	public void setImage(Image image) {
		this.image = image;
		repaint();
	}
	
	public Dimension getImageSize() {
		return new Dimension(image.getWidth(this), image.getHeight(this));
	}
	
	public void paint(Graphics g) {
		Var var = Var.var;
		
		super.paint(g);
		if (image != null && var != null) {
			ImageViewer imageViewer = ImageViewer.imageViewer;
			
			imageViewer.jframe.setSize(image.getWidth(this), (image.getHeight(this) + 63));
			g.drawImage(image, var.imageX,  var.imageY, this);
		}
	}
}
