import java.awt.Image;
import javax.swing.ImageIcon;

public class Cards {

	ImageIcon front = new ImageIcon();
	ImageIcon spade[] = new ImageIcon[15];
	ImageIcon heart[] = new ImageIcon[15];
	ImageIcon club[] = new ImageIcon[15];
	ImageIcon diamond[] = new ImageIcon[15];

    String cardPath = "res/gambar/kartu2/"; //path kartu, dapat berubah di theme

	public Cards() {
        // FRONT
        front = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu1/front.png")), 150, 213);

        Thread prepareSpade = new Thread(() -> {
			// SPADE
            for (int num = 1; num < 15; num++) {
                spade[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource(cardPath + num + "S.png")), 150, 213);
            }
		});

        Thread prepareHeart = new Thread(() -> {
			// HEART
            for (int num = 1; num < 15; num++) {
                heart[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource(cardPath + num + "H.png")), 150, 213);
            }
		});
        
        Thread prepareClub = new Thread(() -> {
			// CLUB
            for (int num = 1; num < 15; num++) {
                club[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource(cardPath + num + "C.png")),150, 213);
            }
		});

        Thread prepareDiamond = new Thread(() -> {
			// DIAMOND
            for (int num = 1; num < 15; num++) {
                diamond[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource(cardPath + num + "D.png")), 150, 213);
            }
		});

        prepareSpade.start();
        prepareHeart.start();
        prepareClub.start();
        prepareDiamond.start();
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

    public void loadCards() {
        Thread prepareSpade = new Thread(() -> {
			// SPADE
            for (int num = 1; num < 15; num++) {
                spade[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource(cardPath + num + "S.png")), 150, 213);
            }
		});

        Thread prepareHeart = new Thread(() -> {
			// HEART
            for (int num = 1; num < 15; num++) {
                heart[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource(cardPath + num + "H.png")), 150, 213);
            }
		});
        
        Thread prepareClub = new Thread(() -> {
			// CLUB
            for (int num = 1; num < 15; num++) {
                club[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource(cardPath + num + "C.png")),150, 213);
            }
		});

        Thread prepareDiamond = new Thread(() -> {
			// DIAMOND
            for (int num = 1; num < 15; num++) {
                diamond[num] = resizeIcon(new ImageIcon(getClass().getClassLoader().getResource(cardPath + num + "D.png")), 150, 213);
            }
		});

        prepareSpade.start();
        prepareHeart.start();
        prepareClub.start();
        prepareDiamond.start();
    }
}