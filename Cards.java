import javax.swing.ImageIcon;

public class Cards {

	ImageIcon front = new ImageIcon();
	ImageIcon joker[] = new ImageIcon[3];
	ImageIcon spade[] = new ImageIcon[14];
	ImageIcon heart[] = new ImageIcon[14];
	ImageIcon club[] = new ImageIcon[14];
	ImageIcon diamond[] = new ImageIcon[14];

	public Cards() {
		
		front = new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu/front.png"));

		// JOKER
		for(int num=1; num<3; num++) {
			joker[num]  = new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu/" + num + "Joker.png"));
		}

		// SPADE
		for(int num=1; num<14; num++) {
			spade[num]  = new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu/" + num + "S.png"));
		}
		
		// HEART
		for(int num=1; num<14; num++) {
			heart[num]  = new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu/" + num + "H.png"));
		}
		
		// CLUB
		for(int num=1; num<14; num++) {
			club[num]  = new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu/" + num + "C.png"));
		}
		
		// DIAMOND
		for(int num=1; num<14; num++) {
			diamond[num]  = new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu/" + num + "D.png"));
		}					
	}
}