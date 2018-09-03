package imageViewer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
	
	public final JFileChooser jFileChooser;
	
	public FileFilter pictureFilter;
	
	public String filePath;
	
	public int returnVal;
	
	public FileChooser() {
		jFileChooser = new JFileChooser();
		pictureFilter = new FileNameExtensionFilter("Picture File", "JPG", "BMP", "PNG");
		jFileChooser.setFileFilter(pictureFilter);
		jFileChooser.showOpenDialog(jFileChooser);
		filePath = null;
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (jFileChooser.getSelectedFile() != null) {
				filePath = jFileChooser.getSelectedFile().getAbsolutePath();
				
				ImageViewer imageViewer = ImageViewer.imageViewer;
				
				imageViewer.showImage(filePath);
			}
		}
	}
}
