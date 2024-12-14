import java.awt.Color;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;

public class Game{
	
	// CLASS
	ActionHandler aHandler = new ActionHandler(this);
	UI ui = new UI(this);
	Cards cards = new Cards();
	Random random = new Random();
	AudioAsset aa = new AudioAsset();
	SE se = new SE();
	Music music = new Music();
	Player currPlayer;
	
	// KARTU
	int pickedCardNum;	
	int playerHas = 0;
	int dealerHas = 0;
	int playerCardNum[] = new int[6];
	int dealerCardNum[] = new int[6];	
	int playerCardValue[] = new int[6];
	int dealerCardValue[] = new int[6];	
	int playerTotalValue;
	int dealerTotalValue;	
	
	// LAIN-LAIN
	String situation = "";
	ImageIcon dealerSecondCard;
	Color bgColor = new Color(0,81,0); //berubah sesuai theme
	

	public static void main(String[] args) {
		
		new Game();
	}	
	public void titleToGame() { //transisi dari title screen ke game screen

		Thread updateGUI = new Thread(() -> { //menyembunyikan elemen title screen dan menampilkan elemen game screen
			ui.titlePanel.setVisible(false);
			ui.startB.setVisible(false);
			ui.themeB.setVisible(false);
			ui.exitB.setVisible(false);
			ui.table.setVisible(true);
			ui.dealerPanel.setVisible(true);
			ui.playerPanel.setVisible(true);
			ui.messageText.setVisible(true);
			ui.buttonPanel.setVisible(true);
			ui.getContentPane().setBackground(Color.black); //mengatur warna background table
		});
		
		Thread operateMusic = new Thread(() -> {
			playMusic(aa.bgm); //memainkan lagu
		});
		
		Thread startGame = new Thread(() -> {
			resetEverything(); //memulai round, menghapus round sebelumnya jika ada
		});

		updateGUI.start();
		operateMusic.start();
		startGame.start();
	}
	public void gameToTitle() { //transisi dari game screen ke title screen

		Thread updateGUI = new Thread(() -> { //menyembunyikan elemen game screen dan menampilkan elemen title screen
			ui.titlePanel.setVisible(true);
			ui.table.setVisible(false);
			ui.dealerPanel.setVisible(false);
			ui.playerPanel.setVisible(false);
			ui.messageText.setVisible(false);
			ui.buttonPanel.setVisible(false);
			ui.getContentPane().setBackground(bgColor);
			
			//memunculkan title screen secara perlahan
			ui.titlePanel.alphaValue = 0f;
			ui.titlePanel.timer.start();
		});

		Thread startGame = new Thread(() -> {
			removeButtons(); //menyembuyikan tombol-tombol game screen
		});

		Thread operateMusic = new Thread(() -> {
			stopMusic(aa.bgm); //menghentikan pemutaran lagu
		});

		updateGUI.start();
		operateMusic.start();
		startGame.start();
	}
	public void startGame() { //memulai round
		
		//draw 2 kartu pertama untuk dealer dan player, secara berurutan

		// Dealer Draw
		dealerDraw();
		try {
			Thread.sleep(500); // Tunggu 0.5 detik
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Player Draw
		playerDraw();
		try {
			Thread.sleep(500); // Tunggu 0.5 detik
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		// Dealer Draw dan sembunyikan kartu kedua dealer
		dealerDraw();
		ui.dealerCardLabel[2].setIcon(cards.front);
		ui.dealerScore.setText("Dealer: ?");
		try {
			Thread.sleep(500); // Tunggu 0.5 detik
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		// Player Turn
		playerTurn();		
	}
	public void dealerDraw() { //dealer mengambil kartu
		// System.out.println("dealer draw");

		playSE(aa.cardSound01); //memainkan sound effect kartu 
		dealerHas++;
		
		ImageIcon pickedCard = pickRandomCard(); // pickRandomCard: mengembalikan ImageIcon
		if(dealerHas==2) {
			dealerSecondCard = pickedCard; // simpan kartu kedua dealer untuk diperlihatkan nanti
		}
		
		dealerCardNum[dealerHas] = pickedCardNum; // simpan kartu yang didapat		
		dealerCardValue[dealerHas] = checkCardValue();	// simpan nilai kartu yang didapat
		
		ui.dealerCardLabel[dealerHas].setVisible(true);	
		ui.dealerCardLabel[dealerHas].setIcon(pickedCard);	// menampilkan kartu
		
		dealerTotalValue = dealerTotalValue(); //nilai total kartu dealer
		ui.dealerScore.setText("Dealer: " + dealerTotalValue);	
	}
	public void playerDraw() { //player mengambil kartu
		// System.out.println("player draw");

		playSE(aa.cardSound01); //memainkan sound effect kartu
		playerHas++;
		
		ImageIcon pickedCard = pickRandomCard();
				
		playerCardNum[playerHas] = pickedCardNum; // simpan kartu yang didapat
		playerCardValue[playerHas] = checkCardValue();	// simpan nilai kartu yang didapat
		
		ui.playerCardLabel[playerHas].setVisible(true);
		ui.playerCardLabel[playerHas].setIcon(pickedCard); // menampilkan kartu
		
		playerTotalValue = playerTotalValue();	//nilai total kartu player	
		ui.playerScore.setText("You: " + playerTotalValue);
	}	

	public void playerTurn() { //giliran player, bisa memilih hit atau stand
		
		situation = "playerTurn";

		playerDraw(); // ambil kartu
								
		if(playerTotalValue > 21) { // kalau nilai total > 21, giliran player selesai
			dealerOpen();
		}
		else if(playerTotalValue == 21 && playerHas == 2) { //mendapat "natural", 2 kartu pertama berupa AS dan 10/J/Q/K
			playerNatural();
		}
		else {					
			if(playerHas > 1 && playerHas < 5) { // jika nilai total < 21 dan kartu yang diambil < 5, masih boleh hit
				ui.messageText.setTextPlus("Do you want to hit one more card?");
				ui.button[1].setVisible(true);
				ui.button[1].setText("Hit");
				ui.button[2].setVisible(true);
				ui.button[2].setText("Stand");	
			}
			if(playerHas == 5) { // jika kartu yang diambil sudah 5, giliran player selesai
				dealerOpen();
			}
		}		
	}
	public void playerNatural() {
		
		situation = "playerNatural";
		
		ui.messageText.setTextPlus("You got natural. Let's open the dealer's card.");
		ui.button[1].setVisible(true);
		ui.button[1].setText("Continue");
	}
	public void dealerOpen() {
		// System.out.println("dealer open");
		
		playSE(aa.cardSound01);
		ui.dealerCardLabel[2].setIcon(dealerSecondCard); // tampilkan kartu kedua dealer	
		ui.dealerScore.setText("Dealer: " + dealerTotalValue);	
		
		if(playerHas == 2 && playerTotalValue == 21) { // jika player mendapat "natural"
			checkResult();
		}
		else if(dealerTotalValue < 17 && playerTotalValue <= 21) { // jika nilai total dealer < 17 dan total nilai player < 22, dealer ambil kartu
			dealerTurnContinue();
		}
		else {
			checkResult();
		}	
	}
	public void dealerTurn() { // giliran dealer
						
		if(dealerTotalValue < 17) { // jika total nilai kartu dealer < 17, dealer tetap ambil kartu
			
			dealerDraw();
			
			if(dealerHas == 5 || dealerTotalValue >= 17) { //jika kartu yang diambil dealer sudah 5, atau nilai total > 16, giliran dealer selesai
				checkResult();
			}
			else {
				dealerTurnContinue();
			}
		}	
		else {
			checkResult();
		}		
	}	
	public void dealerTurnContinue() {
		
		situation = "dealerTurnContinue";
		
		ui.messageText.setTextPlus("The dealer is going to hit another card.");
		ui.button[1].setVisible(true);
		ui.button[1].setText("Continue");
	}
	public void checkResult() { //cek hasil round
		
		situation = "checkResult";		
		
		if(playerTotalValue > 21) { //jika total nilai kartu player > 21, player kalah
			playSE(aa.youlost);
			ui.messageText.setTextPlus("You lost!");	
			currPlayer.addLose();
			gameFinished();
		}
		else {		
			if(playerTotalValue == 21 && dealerHas == 2) { // kasus saat player mendapat "natural"
				if(dealerTotalValue == 21) { //jika dealer juga menadapat total nilai 21, hasil seri
					playSE(aa.draw);
					ui.messageText.setTextPlus("Draw!");
					currPlayer.addDraw();	
					gameFinished();
				}
				else { //player menang
					playSE(aa.youwon);
					ui.messageText.setTextPlus("You won!");
					currPlayer.addWin();	
					gameFinished();
				}
			}
			else { 
				if(dealerTotalValue < 22 && dealerTotalValue > playerTotalValue) { //total nilai kartu dealer > player, dan < 22, player kalah
					playSE(aa.youlost);
					ui.messageText.setTextPlus("You lost!");
					currPlayer.addLose();	
					gameFinished();
				}
				else if(dealerTotalValue == playerTotalValue) { // total nilai kartu kedua pihak sama, hasil seri
					playSE(aa.draw);
					ui.messageText.setTextPlus("Draw!");
					currPlayer.addDraw();	
					gameFinished();
				}
				else { ////total nilai kartu dealer < player, dan < 22, player menang
					playSE(aa.youwon);
					ui.messageText.setTextPlus("You won!");
					currPlayer.addWin();	
					gameFinished();
				}						
			}
		}
	}
	public void gameFinished() { //round selesai

		situation = "gameFinished";		
		ui.button[1].setVisible(true);
		ui.button[1].setText("Play Again");
		ui.button[2].setVisible(true);		
		ui.button[2].setText("Quit");
	}
	public void resetEverything() { //reset semua kartu yang sudah diambil oleh dealer dan player, mengosongkan table

		for(int i=1; i < 6; i++ ){
			ui.playerCardLabel[i].setVisible(false);
			ui.dealerCardLabel[i].setVisible(false);			
		}					
		for(int i=1; i < 6; i++) {
			playerCardNum[i]=0;
			playerCardValue[i]=0;
			dealerCardNum[i]=0;
			dealerCardValue[i]=0;
		}
		playerHas=0;		
		dealerHas=0;
		
		removeButtons();
		Thread startGame = new Thread(() -> {
			startGame();
		});			
		startGame.start();
	}
	public int playerTotalValue() { //menghitung total nilai kartu player
		
		playerTotalValue = playerCardValue[1] + playerCardValue[2] + playerCardValue[3] + playerCardValue[4] + playerCardValue[5];

		int jokerCount = 0;
		for (int i = 1; i < 6; i++) {
			if (playerCardNum[i] == 14) { // Jika kartu adalah joker
				jokerCount++;
			}
		}
		
		if(playerTotalValue < 21 && jokerCount == 2) { //jika ada 2 joker, sesuaikan nilai joker kedua agar total menjadi 21
			adjustPlayerJokerValue(jokerCount);
		}

		if(playerTotalValue > 21) { //jika total nilai > 21, kurangi nilai kartu AS menjadi 1
			adjustPlayerAceValue();
		}

		playerTotalValue = playerCardValue[1] + playerCardValue[2] + playerCardValue[3] + playerCardValue[4] + playerCardValue[5];	
		return playerTotalValue;
	}
	public int dealerTotalValue() { //menghitung total nilai kartu dealer
		
		dealerTotalValue = dealerCardValue[1] + dealerCardValue[2] + dealerCardValue[3] + dealerCardValue[4] + dealerCardValue[5];
		
		int jokerCount = 0;
		for (int i = 1; i < 6; i++) {
			if (dealerCardNum[i] == 14) { // Jika kartu adalah joker
				jokerCount++;
			}
		}

		if(dealerTotalValue < 21 && jokerCount == 2) { //jika ada 2 joker, sesuaikan nilai joker kedua agar total menjadi 21
			adjustDealerJokerValue(jokerCount);
		}

		if(dealerTotalValue > 21) { //jika total nilai > 21, kurangi nilai kartu AS menjadi 1
			adjustDealerAceValue();
		}

		dealerTotalValue = dealerCardValue[1] + dealerCardValue[2] + dealerCardValue[3] + dealerCardValue[4] + dealerCardValue[5];
		return dealerTotalValue;
	}
	public void adjustPlayerAceValue() {
		
		for(int i=1; i<6; i++) {
			if(playerCardNum[i]==1) {
				playerCardValue[i]=1;	
				playerTotalValue = playerCardValue[1] + playerCardValue[2] + playerCardValue[3] + playerCardValue[4] + playerCardValue[5];
				if(playerTotalValue < 21) {
					break;
				}
			}
		}
	}
	public void adjustPlayerJokerValue(int jokerCount) {
		int totalValueWithoutJoker = 0; // Total nilai kartu selain joker
	
		// Hitung total nilai kartu non-joker
		for (int i = 1; i < 6; i++) {
			if (playerCardNum[i] != 14) { // Hanya hitung kartu bukan joker
				totalValueWithoutJoker += playerCardValue[i];
			}
		}
	
		if (jokerCount == 2) {
			// Jika ada 2 joker, sesuaikan nilai joker kedua agar total menjadi 21
			int[] jokerIndexes = findAllJokerIndexes("player");
			playerCardValue[jokerIndexes[0]] = 0; // Joker pertama tetap 0
			int joker2Value = 21 - totalValueWithoutJoker;
			playerCardValue[jokerIndexes[1]] = Math.max(0, joker2Value); // Pastikan nilai tidak negatif
		}
	}
	public void adjustDealerAceValue() {
		
		for(int i=1; i<6; i++) {
			if(dealerCardNum[i]==1) {
				dealerCardValue[i]=1;	
				dealerTotalValue = dealerCardValue[1] + dealerCardValue[2] + dealerCardValue[3] + dealerCardValue[4] + dealerCardValue[5];
				if(dealerTotalValue < 21) {
					break;
				}
			}
		}
	}
	public void adjustDealerJokerValue(int jokerCount) {
		int totalValueWithoutJoker = 0; // Total nilai kartu selain joker
	
		// Hitung total nilai kartu non-joker
		for (int i = 1; i < 6; i++) {
			if (dealerCardNum[i] != 14) { // Hanya hitung kartu bukan joker
				totalValueWithoutJoker += dealerCardValue[i];
			}
		}
	
		if (jokerCount == 2) {
			// Jika ada 2 joker, sesuaikan nilai joker kedua agar total menjadi 21
			int[] jokerIndexes = findAllJokerIndexes("dealer");
			dealerCardValue[jokerIndexes[0]] = 0; // Joker pertama tetap 0
			int joker2Value = 21 - totalValueWithoutJoker;
			dealerCardValue[jokerIndexes[1]] = Math.max(0, joker2Value); // Pastikan nilai tidak negatif
		}
	}
	// Fungsi untuk menemukan semua indeks joker
	private int[] findAllJokerIndexes(String player) { //mencari lokasi 2 joker
		int[] indexes = new int[2];
		int count = 0;
		if(player.equals("player")) {
			for (int i = 1; i < 6; i++) {
				if (playerCardNum[i] == 14 && count < 2) {
					indexes[count++] = i;
				}
			}
		}
		else {
			for (int i = 1; i < 6; i++) {
				if (dealerCardNum[i] == 14 && count < 2) {
					indexes[count++] = i;
				}
			}
		}
		return indexes;
	}

	public ImageIcon pickRandomCard() { //method untuk mengambil kartu random
		
		ImageIcon pickedCard = null;
		
		pickedCardNum = random.nextInt(14)+1;
		int pickedMark = random.nextInt(4)+1;
		
		switch(pickedMark) {
		case 1: pickedCard = cards.spade[pickedCardNum]; break;
		case 2: pickedCard = cards.heart[pickedCardNum]; break;
		case 3: pickedCard = cards.club[pickedCardNum]; break;
		case 4: pickedCard = cards.diamond[pickedCardNum]; break;
		}			
		return pickedCard;				
	}
	public int checkCardValue() { //menyesuaikan nilai kartu
		//nilai kartu secara default mengikuti indeks array kartu (1 - 14)

		int cardValue = pickedCardNum;
		if(pickedCardNum==1) {  //menyesuaikan nilai default kartu AS menjadi 11
			cardValue=11;
		}
		if(pickedCardNum>10) { //menyesuaikan nilai J/Q/K menjadi 10
			cardValue=10;
		}
		if(pickedCardNum==14) { // menyesuaikan nilai default kartu JOKER menjadi 0
			cardValue=0;
		}
		return cardValue;		
	}
	public void removeButtons() {
		
		ui.button[1].setVisible(false);
		ui.button[2].setVisible(false);
		ui.button[3].setVisible(false);
		ui.button[4].setVisible(false);
		ui.button[5].setVisible(false);
	}
	public void playSE(URL url) {
		
		se.setFile(url);
		se.play(url);
	}
	public void playMusic(URL url) {
		
		music.setFile(url);
		music.play(url);
		music.loop(url);
	}
	public void stopMusic(URL url) {
		
		if(music.clip != null) music.stop(url);
	}

	public void changeTheme() { //menampilkan frame theme
		new ThemeLayout(this);
	}

	public void choosePlayer() { //menampilkan frame player selection
		new AccountSelection(this);
	}
}