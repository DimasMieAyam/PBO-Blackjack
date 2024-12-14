import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

abstract class Audio {
    
    Clip clip;

    public void setFile(URL name) {
        try {
			AudioInputStream sound = AudioSystem.getAudioInputStream(name);
			clip = AudioSystem.getClip();
			clip.open(sound);
		}
		catch(Exception e) {
			
		}
    }
    abstract public void play(URL name);
    abstract public void loop(URL name);
    abstract public void stop(URL name);
}
