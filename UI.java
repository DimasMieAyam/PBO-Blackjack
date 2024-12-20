import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class UI extends JFrame{
	
	// Title Screen UI
	PaintPanel titlePanel;
	JButton startB,exitB,themeB;
	
	// Table UI
	JPanel table;
	JPanel dealerPanel;
	JPanel playerPanel;
	JLabel playerCardLabel[] = new JLabel[11];
	JLabel dealerCardLabel[] = new JLabel[11];
	
	// Message UI
    JTextAreaPlus messageText;
	JPanel scorePanel;
	JLabel playerScore, dealerScore;
	JPanel buttonPanel = new JPanel();
	JButton button[] = new JButton[6];
	Game game;
	
	int cardWidth = 150;
	int cardHeight = 213;

	Color bgColor = new Color(0,81,0); //warna background, berubah sesuai theme
	String iconImagePath = "res/gambar/kartu1/1C.png";
	
	
	public UI(Game game) {
		
		this.game = game;
		
		this.setTitle("SUPER BlACKJACK");
		this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource(iconImagePath)).getImage()); //icon game, pojok kiri atas frame
		this.setSize(1200,700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.getContentPane().setBackground(bgColor);
		
		createTitleScreen();
		createTableUI();	
		createOtherUI();
		this.setVisible(true);
	}
	public void createTitleScreen() { //UI title screen
			
		titlePanel = new PaintPanel(this);
		titlePanel.setBounds(0,0,1200,600);
		titlePanel.setOpaque(false);
		this.add(titlePanel);
		
		startB = new JButton("START");
		startB.setBounds(500,450,200,50);
		startB.setBorder(null);
		startB.setBackground(null);
		startB.setFocusPainted(false);
		startB.setForeground(Color.white);
		startB.setFont(new Font("Book Antiqua", Font.PLAIN,30));
		startB.setVisible(false);
		startB.addActionListener(game.aHandler);
		startB.setActionCommand("start");
		startB.setContentAreaFilled(false);
		this.add(startB);

		themeB = new JButton("THEME");
		themeB.setBounds(500,500,200,50);
		themeB.setBorder(null);
		themeB.setBackground(null);
		themeB.setFocusPainted(false);
		themeB.setForeground(Color.white);
		themeB.setFont(new Font("Book Antiqua", Font.PLAIN,30));
		themeB.setVisible(false);
		themeB.addActionListener(game.aHandler);
		themeB.setActionCommand("theme");
		themeB.setContentAreaFilled(false);
		this.add(themeB);
		
		exitB = new JButton("EXIT");
		exitB.setBounds(500,550,200,50);
		exitB.setBorder(null);
		exitB.setBackground(null);
		exitB.setFocusPainted(false);
		exitB.setForeground(Color.white);
		exitB.setFont(new Font("Book Antiqua", Font.PLAIN,30));
		exitB.setVisible(false);
		exitB.addActionListener(game.aHandler);
		exitB.setActionCommand("exit");
		exitB.setContentAreaFilled(false);
		this.add(exitB);
		
		titlePanel.timer.start();
	}
	public void createTableUI() { //UI table (latar hijau/biru di game screen)
		
		table = new JPanel();
		table.setBackground(bgColor);//background table game 
		table.setBounds(50,50,850,600);
		table.setLayout(null);
		table.setVisible(false);
		
		dealerPanel = new JPanel();
		dealerPanel.setBounds(100,120,cardWidth*5,cardHeight);
		dealerPanel.setBackground(null);
		dealerPanel.setOpaque(false);
		dealerPanel.setLayout(new GridLayout(1,11));
		dealerPanel.setVisible(false);
		this.add(dealerPanel);
				
		playerPanel = new JPanel();
		playerPanel.setBounds(100,370,cardWidth*5,cardHeight);
		playerPanel.setOpaque(false);
		playerPanel.setLayout(new GridLayout(1,11));
		playerPanel.setVisible(false);
		this.add(playerPanel);

		for(int i = 1; i < 6; i++) {
			playerCardLabel[i] = new JLabel();
			playerCardLabel[i].setVisible(false);
			playerPanel.add(playerCardLabel[i]);
		}
		for(int i = 1; i < 6; i++) {
			dealerCardLabel[i] = new JLabel();
			dealerCardLabel[i].setVisible(false);
			dealerPanel.add(dealerCardLabel[i]);
		}	
		
		dealerScore = new JLabel();
		dealerScore.setBounds(50,10,200,50);
		dealerScore.setForeground(Color.white);
		dealerScore.setFont(new Font("Times New Roman", Font.PLAIN, 42));
		table.add(dealerScore);
		
		playerScore = new JLabel();
		playerScore.setBounds(50,540,200,50);
		playerScore.setForeground(Color.white);
		playerScore.setFont(new Font("Times New Roman", Font.PLAIN, 42));
		table.add(playerScore);
	
		this.add(table);
	}
	
	public void createOtherUI() { //UI latar belakang game screen, tempat menampilkan tombol dan teks
		
		messageText = new JTextAreaPlus();
		messageText.setBounds(230,650,720,100); // y sebelumnya 680
		messageText.setBackground(null);
		messageText.setForeground(Color.white);//warna teks di bawah
		messageText.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		messageText.setEditable(false);
		this.add(messageText);
		
		buttonPanel = new JPanel();
		buttonPanel.setBounds(920,340,200,300);
		buttonPanel.setBackground(null);
		buttonPanel.setLayout(new GridLayout(6,1));
		this.add(buttonPanel);
		
		for(int i = 1; i < 6; i++) {
			button[i] = new JButton();
			button[i].setBackground(null);
			button[i].setForeground(Color.white); //tombol hit stand
			button[i].setFocusPainted(false);
			button[i].setBorder(null);
			button[i].setFont(new Font("Times New Roman", Font.PLAIN, 42));
			button[i].addActionListener(game.aHandler);
			button[i].setActionCommand(""+i);
			button[i].setVisible(false);
			buttonPanel.add(button[i]);
		}		
	}
}