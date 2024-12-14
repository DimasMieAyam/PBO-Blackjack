import java.awt.Image;
import javax.swing.ImageIcon;

public class Cards {

	ImageIcon front = new ImageIcon();
	ImageIcon spade[] = new ImageIcon[15];
	ImageIcon heart[] = new ImageIcon[15];
	ImageIcon club[] = new ImageIcon[15];
	ImageIcon diamond[] = new ImageIcon[15];

	public Cards() {
        // FRONT
        front = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu2/front.png")), 150, 213);

<<<<<<< HEAD
        // JOKER
        for (int num = 1; num < 3; num++) {
            joker[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu2/" + num + "Joker.png")),150, 213);
        }

        // SPADE
        for (int num = 1; num < 14; num++) {
=======
        // SPADE
        for (int num = 1; num < 15; num++) {
>>>>>>> e0b8cd212595a7422d12fb739be65b674d6344c3
            spade[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu2/" + num + "S.png")), 150, 213);
        }

        // HEART
<<<<<<< HEAD
        for (int num = 1; num < 14; num++) {
=======
        for (int num = 1; num < 15; num++) {
>>>>>>> e0b8cd212595a7422d12fb739be65b674d6344c3
            heart[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu2/" + num + "H.png")), 150, 213);
        }

        // CLUB
<<<<<<< HEAD
        for (int num = 1; num < 14; num++) {
=======
        for (int num = 1; num < 15; num++) {
>>>>>>> e0b8cd212595a7422d12fb739be65b674d6344c3
            club[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu2/" + num + "C.png")),150, 213);
        }

        // DIAMOND
<<<<<<< HEAD
        for (int num = 1; num < 14; num++) {
=======
        for (int num = 1; num < 15; num++) {
>>>>>>> e0b8cd212595a7422d12fb739be65b674d6344c3
            diamond[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu2/" + num + "D.png")), 150, 213);
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