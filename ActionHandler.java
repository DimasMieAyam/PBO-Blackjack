import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// menangani berbagai event dalam game
public class ActionHandler implements ActionListener{
	
	Game game;
	
	public ActionHandler(Game game) {
		
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();
				
		game.removeButtons();
		
		//switch untuk menentukan aksi ketika tombol ditekan
		switch(command) {
		case "start":
			game.choosePlayer(); //membuka frame pilih pemain
			break;
		case "exit":
			System.exit(0); //tutup game
			break;
		case "theme":
			game.changeTheme(); //membuka frame pilih theme
			break;
		case "1": //tombol 1 dapat berarti hit, continue, atau play again
			if(game.situation.equals("playerTurn")) { //hit
				game.playerTurn();
			}
			else if(game.situation.equals("playerNatural")) { //continue
				game.dealerOpen();
			}
			else if(game.situation.equals("dealerTurnContinue")) { // continue
				game.dealerTurn();
			}
			else if(game.situation.equals("gameFinished")) { // play again
				game.resetEverything();
			}
			break;
		case "2": //tombol 2 dapat berarti quit atau stand
			if(game.situation.equals("playerTurn")) { //stand
				game.dealerOpen();
			}
			else if(game.situation.contentEquals("gameFinished")) { //quit, kembali ke title screen
				Thread threadDB = new Thread(() -> {
					DatabaseOperations.updatePlayer(game.currPlayer); //update data player ke database setelah bermain
				});

				threadDB.start();				
				game.gameToTitle();
			}
			break;
		}			
	}
}