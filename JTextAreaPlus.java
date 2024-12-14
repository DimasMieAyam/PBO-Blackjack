import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import javax.swing.Timer;

//menampilkan pesan teks per huruf 
public class JTextAreaPlus extends JTextArea implements ActionListener {
    
    private int i = 0;
    private char[] characterArray;
    private Timer timer;

    public JTextAreaPlus() {
        timer = new Timer(10, this);
    }

    public JTextAreaPlus(String text) {
        super(text);
        timer = new Timer(10, this);
    }

    public void setTextPlus(String text) {
        // Pastikan timer dihentikan sebelum memulai animasi baru
        if (timer.isRunning()) {
            timer.stop();
        }

        this.characterArray = text.toCharArray(); // Simpan array karakter
        this.i = 0; // Reset indeks
        setText(""); // Kosongkan teks area
        timer.start(); // Mulai timer
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (i < characterArray.length) {
            append(String.valueOf(characterArray[i])); // Tambahkan karakter berikutnya
            i++;
        } else {
            timer.stop(); // Hentikan timer saat animasi selesai
        }
    }
}
