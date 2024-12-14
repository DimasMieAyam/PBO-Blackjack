import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThemeLayout extends JFrame {

    static Color mainColor = new Color(0, 81, 0);
    static Color secondColor = new Color(0, 100, 0);

    public ThemeLayout(Game game) {
        initializeUI(game);
        setVisible(true);
    }

    private void initializeUI(Game game) {
        getContentPane().removeAll();

        // Frame utama
        setTitle("Pilihan Theme");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 250); // Ukuran lebih lebar dan tidak terlalu panjang
        setLayout(new GridLayout(2, 1)); // Menggunakan GridLayout untuk menampilkan bertumpuk
        setLocationRelativeTo(null);

        // Panel pertama
        JPanel panel1 = new JPanel(new BorderLayout(1, 1)); // Tambahkan margin antar komponen
        JLabel subTitle1 = new JLabel("DECK", JLabel.CENTER);
        subTitle1.setFont(new Font("Arial", Font.BOLD, 20)); // Perbesar teks subjudul
        subTitle1.setBorder(BorderFactory.createEmptyBorder(13, 0, 5, 0)); // Kurangi jarak subjudul dengan tombol
        subTitle1.setForeground(Color.WHITE);
        panel1.setBackground(mainColor); // Menambahkan warna hijau RGB 81 ke panel
        panel1.setBorder(BorderFactory.createLineBorder(secondColor, 1)); // Border putih dengan ketebalan 2
        panel1.add(subTitle1, BorderLayout.NORTH);

        JPanel buttonPanel1 = new JPanel(new GridLayout(1, 2, 0, 0)); // Tambahkan sedikit jarak horizontal antar tombol
        buttonPanel1.setBackground(mainColor); // Menambahkan warna hijau RGB 81 ke panel tombol

        JButton btn1Panel1 = new JButton("Transparan");
        btn1Panel1.setPreferredSize(new Dimension(100, 50)); // Perbesar tombol
        btn1Panel1.setFont(new Font("Arial", Font.BOLD, 18)); // Perbesar teks tombol
        btn1Panel1.setForeground(mainColor);
        btn1Panel1.setBackground(Color.white); // Set background tombol
        btn1Panel1.setOpaque(true);
        btn1Panel1.setBorder(BorderFactory.createLineBorder(mainColor, 10)); // Tambahkan border

        JButton btn2Panel1 = new JButton("Classic");
        btn2Panel1.setPreferredSize(new Dimension(100, 50)); // Perbesar tombol
        btn2Panel1.setFont(new Font("Arial", Font.BOLD, 18)); // Perbesar teks tombol
        btn2Panel1.setForeground(mainColor);
        btn2Panel1.setBackground(Color.WHITE); // Set background tombol
        btn2Panel1.setOpaque(true);
        btn2Panel1.setBorder(BorderFactory.createLineBorder(mainColor, 10)); // Tambahkan border

        btn1Panel1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.cards.cardPath = "res/gambar/kartu1/";
                    game.cards.loadCards();
                } catch (Exception ex) {
                    ex.printStackTrace(); // Menampilkan error jika terjadi masalah
                }
            }
        });
        btn2Panel1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.cards.cardPath = "res/gambar/kartu2/";
                    game.cards.loadCards();
                } catch (Exception ex) {
                    ex.printStackTrace(); // Menampilkan error jika terjadi masalah
                }
            }
        });

        buttonPanel1.add(btn1Panel1);
        buttonPanel1.add(btn2Panel1);
        panel1.add(buttonPanel1, BorderLayout.SOUTH);

        // Panel kedua
        JPanel panel2 = new JPanel(new BorderLayout(1, 1)); // Tambahkan margin antar komponen
        JLabel subTitle2 = new JLabel("BACKGROUND", JLabel.CENTER);
        subTitle2.setFont(new Font("Arial", Font.BOLD, 20)); // Perbesar teks subjudul
        subTitle2.setForeground(Color.WHITE);
        subTitle2.setBorder(BorderFactory.createEmptyBorder(13, 0, 5, 0)); // Kurangi jarak subjudul dengan tombol
        panel2.setBackground(mainColor); // Menambahkan warna hijau RGB 81 ke panel
        panel2.setBorder(BorderFactory.createLineBorder(secondColor, 1)); // Border putih dengan ketebalan 2
        panel2.add(subTitle2, BorderLayout.NORTH);

        JPanel buttonPanel2 = new JPanel(new GridLayout(1, 2, 0, 0)); // Tambahkan sedikit jarak horizontal antar tombol
        buttonPanel2.setBackground(mainColor); // Menambahkan warna hijau RGB 81 ke panel tombol

        JButton btn1Panel2 = new JButton("Green");
        btn1Panel2.setPreferredSize(new Dimension(100, 50)); // Perbesar tombol
        btn1Panel2.setFont(new Font("Arial", Font.BOLD, 18)); // Perbesar teks tombol
        btn1Panel2.setForeground(mainColor);
        btn1Panel2.setBackground(Color.WHITE); // Set background tombol
        btn1Panel2.setOpaque(true);
        btn1Panel2.setBorder(BorderFactory.createLineBorder(mainColor, 10)); // Tambahkan border

        JButton btn2Panel2 = new JButton("Blue");
        btn2Panel2.setPreferredSize(new Dimension(100, 50)); // Perbesar tombol
        btn2Panel2.setFont(new Font("Arial", Font.BOLD, 18)); // Perbesar teks tombol
        btn2Panel2.setForeground(mainColor);
        btn2Panel2.setBackground(Color.white); // Set background tombol
        btn2Panel2.setOpaque(true);
        btn2Panel2.setBorder(BorderFactory.createLineBorder(mainColor, 10)); // Tambahkan border

        btn1Panel2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ThemeLayout.mainColor = new Color(0, 81, 0);
                    ThemeLayout.secondColor = new Color(0, 100, 0);

                    Thread updateGUI = new Thread(() -> {
                        game.bgColor = new Color(0, 81, 0);
                        game.ui.bgColor = new Color(0, 81, 0);
                        game.ui.titlePanel.bgImagePath = "res/gambar/background3.jpg";
                        AccountSelection.mainColor = new Color(0, 81, 0);

                        game.ui.titlePanel.loadImage();

                        game.ui.getContentPane().setBackground(game.bgColor);

                        game.ui.table.setBackground(game.ui.bgColor);
                    });	

                    updateGUI.start();
                    reloadFrame(game);

                } catch (Exception ex) {
                    ex.printStackTrace(); // Menampilkan error jika terjadi masalah
                }
            }
        });
        btn2Panel2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ThemeLayout.mainColor = new Color(0, 0, 81);
                    ThemeLayout.secondColor = new Color(0, 0, 100);

                    Thread updateGUI = new Thread(() -> {
                        game.bgColor = new Color(0, 0, 81);
                        game.ui.bgColor = new Color(0, 0, 81);
                        game.ui.titlePanel.bgImagePath = "res/gambar/background4.jpg";
                        AccountSelection.mainColor = new Color(0, 0, 81);

                        game.ui.titlePanel.loadImage();

                        game.ui.getContentPane().setBackground(game.bgColor);

                        game.ui.table.setBackground(game.ui.bgColor);
                    });			

                    updateGUI.start();
                    reloadFrame(game);
                    
                } catch (Exception ex) {
                    ex.printStackTrace(); // Menampilkan error jika terjadi masalah
                }
            }
        });

        buttonPanel2.add(btn1Panel2);
        buttonPanel2.add(btn2Panel2);
        panel2.add(buttonPanel2, BorderLayout.SOUTH);

        // Tambahkan kedua panel ke frame
        add(panel1);
        add(panel2);

        validate();
        repaint();

        // Tampilkan frame
        // setVisible(true);
    }

    private void reloadFrame(Game game) {
        initializeUI(game);
    }
}
