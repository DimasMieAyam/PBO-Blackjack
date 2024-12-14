import java.net.URL;
import javax.sound.sampled.Clip;

//class untuk memainkan sound effect
public class SE extends Audio{
	
	// Clip clip;
	
	// public void setFile(URL name) {
		
	// 	try {
	// 		AudioInputStream sound = AudioSystem.getAudioInputStream(name);
	// 		clip = AudioSystem.getClip();
	// 		clip.open(sound);
	// 	}
	// 	catch(Exception e) {
			
	// 	}
	// }
	
	public void play(URL name) {
		System.out.println("Special Effect Playing");
		
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void loop(URL name) {
		System.out.println("Special Effect Looping");
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop(URL name) {
		System.out.println("Special Effect Stopping");

		clip.stop();
	}
}