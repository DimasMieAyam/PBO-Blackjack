import javax.swing.ImageIcon;

public class Cards {

	ImageIcon front = new ImageIcon();
	ImageIcon joker[] = new ImageIcon[3];
	ImageIcon spade[] = new ImageIcon[14];
	ImageIcon heart[] = new ImageIcon[14];
	ImageIcon club[] = new ImageIcon[14];
	ImageIcon diamond[] = new ImageIcon[14];

	public Cards() {
		
		front = new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu2/front.png"));

		// JOKER
		for(int num=1; num<3; num++) {
			joker[num]  = new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu2/" + num + "Joker.png"));
		}

		// SPADE
		for(int num=1; num<14; num++) {
			spade[num]  = new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu2/" + num + "S.png"));
		}
		
		// HEART
		for(int num=1; num<14; num++) {
			heart[num]  = new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu2/" + num + "H.png"));
		}
		
		// CLUB
		for(int num=1; num<14; num++) {
			club[num]  = new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu2/" + num + "C.png"));
		}
		
		// DIAMOND
		for(int num=1; num<14; num++) {
			diamond[num]  = new ImageIcon(getClass().getClassLoader().getResource("res/gambar/kartu2/" + num + "D.png"));
		}					
	}
}