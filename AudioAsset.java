import java.net.URL;

public class AudioAsset {
	
	URL cardSound01 = getClass().getClassLoader().getResource("res/musik/card_sound.wav");
	URL youwon = getClass().getClassLoader().getResource("res/musik/game_win.wav");
	URL youlost = getClass().getClassLoader().getResource("res/musik/game_lost.wav");
	URL draw = getClass().getClassLoader().getResource("res/musik/game_draw.wav");
	// URL isee = getClass().getClassLoader().getResource("Ryan_isee.wav");
	// URL ok = getClass().getClassLoader().getResource("Ryan_ok.wav");
	// URL hitanother = getClass().getClassLoader().getResource("Ryan_hitanother.wav");
	URL bgm = getClass().getClassLoader().getResource("res/musik/BGM.wav");
}