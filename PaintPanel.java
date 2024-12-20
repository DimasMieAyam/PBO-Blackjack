import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

//Untuk menampilkan title screen yang muncul secara perlahan
public class PaintPanel extends JPanel implements ActionListener{
	
	public Timer timer = new Timer(30,this);
	float alphaValue = 0f;
	BufferedImage titleImage;
	String bgImagePath = "res/gambar/background3.jpg"; //gambar title screen, dapat berubah di theme
	
	UI ui;
	
	public PaintPanel(UI ui) {
		
		this.ui = ui;
		
		try {
			titleImage = ImageIO.read(getClass().getClassLoader().getResource(bgImagePath)); //gambar yang ada di title screen
			
		}catch(IOException e) {			
		}		
	}

	public void loadImage() {
		try {
			titleImage = ImageIO.read(getClass().getClassLoader().getResource(bgImagePath)); //gambar yang ada di title screen
			
		}catch(IOException e) {			
		}		
	}

	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
		if(alphaValue < 0.99) {
			g2d.setColor(Color.white);
		}
		else {
			g2d.setColor(Color.yellow);
		}
		
		g2d.setFont(new Font("Book Antiqua", Font.PLAIN, 110));
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		String text = "SUPER BLACKJACK";
		
		int stringLength = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
		int start = this.getWidth()/2 - stringLength/2;
		
		g.drawString(text, start, 100);		
		g.drawImage(titleImage, 340,180,500,250,null);//untuk mengatur ukuran gambar title
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		alphaValue = alphaValue +0.01f;
		
		if(alphaValue > 1) {
			alphaValue = 1;
			timer.stop();
			ui.startB.setVisible(true);
			ui.themeB.setVisible(true);
			ui.exitB.setVisible(true);
		}
		repaint();
	}
}