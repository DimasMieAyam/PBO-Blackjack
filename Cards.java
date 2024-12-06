import java.awt.Image;
import javax.swing.ImageIcon;

public class Cards {

	ImageIcon front = new ImageIcon();
	ImageIcon joker[] = new ImageIcon[3];
	ImageIcon spade[] = new ImageIcon[14];
	ImageIcon heart[] = new ImageIcon[14];
	ImageIcon club[] = new ImageIcon[14];
	ImageIcon diamond[] = new ImageIcon[14];

	public Cards() {
        // FRONT
        front = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu1/front.png")), 150, 213);

        // JOKER
        for (int num = 1; num < 3; num++) {
            joker[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu1/" + num + "Joker.png")),150, 213);
        }

        // SPADE
        for (int num = 1; num < 14; num++) {
            spade[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu1/" + num + "S.png")), 150, 213);
        }

        // HEART
        for (int num = 1; num < 14; num++) {
            heart[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu1/" + num + "H.png")), 150, 213);
        }

        // CLUB
        for (int num = 1; num < 14; num++) {
            club[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu1/" + num + "C.png")),150, 213);
        }

        // DIAMOND
        for (int num = 1; num < 14; num++) {
            diamond[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu1/" + num + "D.png")), 150, 213);
        }
    }

    // Method untuk meresize ImageIcon
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        if (icon != null) {
            Image img = icon.getImage();
            Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        }
        return null;
    }
}