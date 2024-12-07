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
	
	// CARD STUFF
	int pickedCardNum;	
	int playerHas = 0;
	int dealerHas = 0;
	int playerCardNum[] = new int[6];
	int dealerCardNum[] = new int[6];	
	int playerCardValue[] = new int[6];
	int dealerCardValue[] = new int[6];	
	int playerTotalValue;
	int dealerTotalValue;	
	
	// OTHERS
	String situation = "";
	ImageIcon dealerSecondCard;
	

	public static void main(String[] args) {
		
		new Game();
	}	
	public void titleToGame() {
		
		ui.titlePanel.setVisible(false);
		ui.startB.setVisible(false);
		ui.themeB.setVisible(false);
		ui.exitB.setVisible(false);
		ui.table.setVisible(true);
		ui.dealerPanel.setVisible(true);
		ui.playerPanel.setVisible(true);
		ui.messageText.setVisible(true);
		ui.buttonPanel.setVisible(true);
		ui.getContentPane().setBackground(Color.black);//mengatur warna background table
		
		playMusic(aa.bgm);
		
		startGame();
	}
	public void gameToTitle() {
				
		ui.titlePanel.setVisible(true);
		ui.table.setVisible(false);
		ui.dealerPanel.setVisible(false);
		ui.playerPanel.setVisible(false);
		ui.messageText.setVisible(false);
		ui.buttonPanel.setVisible(false);
		ui.getContentPane().setBackground(new Color(0,81,0));
		
		ui.titlePanel.alphaValue = 0f;
		ui.titlePanel.timer.start();
		
		stopMusic(aa.bgm);
	}
	public void startGame() {
				
		dealerDraw();
		playerDraw();
		dealerDraw();				
		ui.dealerCardLabel[2].setIcon(cards.front);
		ui.dealerScore.setText("Dealer: ?");
		playerTurn();		
	}
	public void dealerDraw() {

		playSE(aa.cardSound01);
		dealerHas++;
		
		ImageIcon pickedCard = pickRandomCard(); // pickRandomCard: return an ImageIcon
		if(dealerHas==2) {
			dealerSecondCard = pickedCard; // Save the second card for revealing it later
		}
		
		dealerCardNum[dealerHas] = pickedCardNum; // Register what card the dealer got		
		dealerCardValue[dealerHas] = checkCardValue();	// Register the card value. If it's from Jack to King, it returns 10
		
		ui.dealerCardLabel[dealerHas].setVisible(true);	
		ui.dealerCardLabel[dealerHas].setIcon(pickedCard);	// Display the card
		
		dealerTotalValue = dealerTotalValue();
		ui.dealerScore.setText("Dealer: " + dealerTotalValue);	
	}
	public void playerDraw() {

		playSE(aa.cardSound01);
		playerHas++;
		
		ImageIcon pickedCard = pickRandomCard();
				
		playerCardNum[playerHas] = pickedCardNum;		
		playerCardValue[playerHas] = checkCardValue();	
		
		ui.playerCardLabel[playerHas].setVisible(true);
		ui.playerCardLabel[playerHas].setIcon(pickedCard);
		
		playerTotalValue = playerTotalValue();	// Count total player card value	
		ui.playerScore.setText("You: " + playerTotalValue);
	}	
	public void playerTurn() {
		
		situation = "playerTurn";

		playerDraw(); // Hit a card
								
		if(playerTotalValue > 21) { // If the total value surpassed 21, the game is over
			dealerOpen();
		}
		else if(playerTotalValue == 21 && playerHas == 2) {
			playerNatural();
		}
		else {					
			if(playerHas > 1 && playerHas < 5) { // If the total value is less than 22 and has 4 or less cards, you can still hit
				ui.messageText.setTextPlus("Do you want to hit one more card?");
				ui.button[1].setVisible(true);
				ui.button[1].setText("Hit");
				ui.button[2].setVisible(true);
				ui.button[2].setText("Stand");	
			}
			if(playerHas == 5) { // If the player has 5 cards, the player's turn is over
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
		
		playSE(aa.cardSound01);
		ui.dealerCardLabel[2].setIcon(dealerSecondCard); // Reveal the second card	
		ui.dealerScore.setText("Dealer: " + dealerTotalValue);	
		
		if(playerHas == 2 && playerTotalValue == 21) { // If player got natural
			checkResult();
		}
		else if(dealerTotalValue < 17 && playerTotalValue <= 21) {
			dealerTurnContinue();
		}
		else {
			checkResult();
		}	
	}
	public void dealerTurn() {
						
		if(dealerTotalValue < 17) { // If it's less than 17, the dealer must hit one more card
			
			dealerDraw();
			
			if(dealerHas == 5 || dealerTotalValue >= 17) { // If the player has 5 cards, the player's turn is over
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
	public void checkResult() {
		
		situation = "checkResult";		
		
		if(playerTotalValue > 21) {
			playSE(aa.youlost);
			ui.messageText.setTextPlus("You lost!");	
			gameFinished();
		}
		else {		
			if(playerTotalValue == 21 && dealerHas == 2) { // If player is natural
				if(dealerTotalValue == 21) {
					playSE(aa.draw);
					ui.messageText.setTextPlus("Draw!");	
					gameFinished();
				}
				else {
					playSE(aa.youwon);
					ui.messageText.setTextPlus("You won!");	
					gameFinished();
				}
			}
			else { 
				if(dealerTotalValue < 22 && dealerTotalValue > playerTotalValue) {
					playSE(aa.youlost);
					ui.messageText.setTextPlus("You lost!");	
					gameFinished();
				}
				else if(dealerTotalValue == playerTotalValue) {
					playSE(aa.draw);
					ui.messageText.setTextPlus("Draw!");	
					gameFinished();
				}
				else {
					playSE(aa.youwon);
					ui.messageText.setTextPlus("You won!");	
					gameFinished();
				}						
			}
		}
	}
	public void gameFinished() {

		situation = "gameFinished";		
		ui.button[1].setVisible(true);
		ui.button[1].setText("Play Again");
		ui.button[2].setVisible(true);		
		ui.button[2].setText("Quit");
	}
	public void resetEverything() {

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
		startGame();
	}
	public int playerTotalValue() {
		
		playerTotalValue = playerCardValue[1] + playerCardValue[2] + playerCardValue[3] + playerCardValue[4] + playerCardValue[5];

		int jokerCount = 0;
		for (int i = 1; i < 6; i++) {
			if (playerCardNum[i] == 14) { // Jika kartu adalah joker
				jokerCount++;
			}
		}
		
		if(playerTotalValue < 21 && jokerCount == 2) {
			adjustPlayerJokerValue(jokerCount);
		}

		if(playerTotalValue > 21) {
			adjustPlayerAceValue();
		}

		playerTotalValue = playerCardValue[1] + playerCardValue[2] + playerCardValue[3] + playerCardValue[4] + playerCardValue[5];	
		return playerTotalValue;
	}
	public int dealerTotalValue() {
		
		dealerTotalValue = dealerCardValue[1] + dealerCardValue[2] + dealerCardValue[3] + dealerCardValue[4] + dealerCardValue[5];
		
		int jokerCount = 0;
		for (int i = 1; i < 6; i++) {
			if (dealerCardNum[i] == 14) { // Jika kartu adalah joker
				jokerCount++;
			}
		}

		if(dealerTotalValue < 21 && jokerCount == 2) {
			adjustDealerJokerValue(jokerCount);
		}

		if(dealerTotalValue > 21) {
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
	private int[] findAllJokerIndexes(String player) {
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

	public ImageIcon pickRandomCard() {
		
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
	public int checkCardValue() {
		
		int cardValue = pickedCardNum;
		if(pickedCardNum==1) {
			cardValue=11;
		}
		if(pickedCardNum>10) {
			cardValue=10;
		}
		if(pickedCardNum==14) {
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
		
		music.stop(url);
	}
}