import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;



public class AccountSelection extends JFrame{

    static Color mainColor = new Color(0, 81, 0);

    public AccountSelection(Game game) {
        setTitle("Pilihan akun");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Panel utama dengan CardLayout
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);


        // Panel utama untuk pilihan akun (New Account, History)
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(mainColor); // Set warna hijau
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Jarak antar tombol
        gbc.gridx = 0;

        // Tombol New Account
        JButton btnNewAccount = new JButton("New Game");
        btnNewAccount.setPreferredSize(new Dimension(200, 50));
        btnNewAccount.setFont(new Font("Arial", Font.BOLD, 18)); // Perbesar teks tombol
        btnNewAccount.setForeground(mainColor);
        btnNewAccount.setBackground(Color.white); // Set background tombol
        gbc.gridy = 0;
        mainPanel.add(btnNewAccount, gbc);

        // Tombol Load game
        JButton btnLoadGame = new JButton("Load game");
        btnLoadGame.setPreferredSize(new Dimension(200, 50));
        btnLoadGame.setPreferredSize(new Dimension(200, 50));
        btnLoadGame.setFont(new Font("Arial", Font.BOLD, 18)); // Perbesar teks tombol
        btnLoadGame.setForeground(mainColor);
        btnLoadGame.setBackground(Color.white); // Set background tombol
        gbc.gridy = 1;
        mainPanel.add(btnLoadGame, gbc);

        // ActionListener untuk tombol New Account
        btnNewAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(AccountSelection.this, "Enter your name:", "New Account", JOptionPane.PLAIN_MESSAGE);
                if (name != null && !name.trim().isEmpty() && !DatabaseOperations.playerExist(name)) {
                    DatabaseOperations.addPlayer(name);
                    int option = JOptionPane.showOptionDialog(AccountSelection.this, "Account \"" + name + "\" created successfully!", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] {"OK"}, "OK");
                    if(option == 0) {
                        Thread startGame = new Thread(() -> {
                            game.currPlayer = new Player(DatabaseOperations.getPlayer(name));
                            game.titleToGame();
                        });

                        Thread closeWindow = new Thread(() -> {
                            AccountSelection.this.dispose();
                        });

                        startGame.start();
                        closeWindow.start();
                    }
                } else if(DatabaseOperations.playerExist(name)) {
                    JOptionPane.showMessageDialog(AccountSelection.this, "Player with the name \"" + name + "\" already exists", "Error", JOptionPane.ERROR_MESSAGE);
                } else if(name != null && name.equals("")) {
                    JOptionPane.showMessageDialog(AccountSelection.this, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(AccountSelection.this, "Action canceled", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener untuk tombol History
        btnLoadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Menampilkan panel history dengan CardLayout
                cardLayout.show(cardPanel, "history");
            }
        });
        

    JPanel loadGamePanel = new JPanel();
    loadGamePanel.setBackground(mainColor); // Set warna hijau
    loadGamePanel.setLayout(new BoxLayout(loadGamePanel, BoxLayout.Y_AXIS));

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Layout untuk tombol di kiri
    buttonPanel.setBackground(mainColor); // Samakan dengan background utama

    JButton backButton = new JButton();
    backButton.setPreferredSize(new Dimension(300, 50));
    backButton.setText("BACK");
    backButton.setForeground(Color.BLACK);
    backButton.setBackground(Color.WHITE);
    backButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Menampilkan panel history dengan CardLayout
            cardLayout.show(cardPanel, "main");
        }
    });

    // Tambahkan tombol ke dalam panel tambahan
    buttonPanel.add(backButton);

    // Tambahkan panel tombol ke loadGamePanel
    loadGamePanel.add(buttonPanel);

    for (int i = 0; i < DatabaseOperations.getPlayerCount(); i++) {
        Player[] players = DatabaseOperations.getPlayers();

        String playerName = players[i].getName();

        JPanel userPanel = new JPanel(new GridLayout(2, 2)); // Menambahkan layout grid ke panel
        userPanel.setPreferredSize(new Dimension(350, 100)); // Memperbesar ukuran panel
        userPanel.setBackground(Color.WHITE); // Set warna kuning agak hijau
        userPanel.add(new JLabel("Name: " + players[i].getName()));
        userPanel.add(new JLabel("Win: " + players[i].getWin()));
        userPanel.add(new JLabel("Draw: " + players[i].getDraw()));
        userPanel.add(new JLabel("Lose: " + players[i].getLose()));

        // Sesuaikan warna teks
        for (Component component : userPanel.getComponents()) {
            if (component instanceof JLabel) {
                ((JLabel) component).setForeground(new Color(0, 0, 0)); // Set warna teks hitam
            }
        }

        JButton userButton = new JButton();
        userButton.setPreferredSize(new Dimension(300, 100)); // Memperbesar ukuran tombol
        userButton.add(userPanel);
        userButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread startGame = new Thread(() -> {
                    game.currPlayer = new Player(DatabaseOperations.getPlayer(playerName));
                    game.titleToGame();
                });

                Thread closeWindow = new Thread(() -> {
                    AccountSelection.this.dispose();
                });

                startGame.start();
                closeWindow.start();
            }
        });

        JButton delButton = new JButton();
        delButton.setPreferredSize(new Dimension(50, 50));
        delButton.setText("DELETE");
        delButton.setForeground(Color.WHITE);
        delButton.setBackground(Color.RED);
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread performDelete = new Thread(() -> {
                    DatabaseOperations.deletePlayer(playerName);
                });

                Thread closeWindow = new Thread(() -> {
                    AccountSelection.this.dispose();
                });

                performDelete.start();
                closeWindow.start();
            }
        });

        // Tambahkan jarak pada setiap tombol history secara vertikal dan horizontal
        JPanel panelJarak = new JPanel();
        panelJarak.setLayout(new BorderLayout());
        panelJarak.add(userButton, BorderLayout.CENTER);
        panelJarak.add(delButton, BorderLayout.SOUTH);
        panelJarak.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Jarak atas, kiri, bawah, kanan
        panelJarak.setOpaque(false); // Set panel menjadi transparan

        loadGamePanel.add(panelJarak); // Menambahkan panelJarak ke loadGamePanel
    }
        // Membungkus loadGamePanel dengan JScrollPane agar bisa digulir
        JScrollPane scrollPane = new JScrollPane(loadGamePanel);
        scrollPane.setPreferredSize(new Dimension(380, 200)); // Ukuran scroll pane, bisa disesuaikan

        // Menambahkan panel utama dan panel history ke cardPanel
        cardPanel.add(mainPanel, "main");
        cardPanel.add(scrollPane, "history"); // Menambahkan scrollPane ke cardPanel

        // Background dan layout utama
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBackground(mainColor); // Set warna hijau
        backgroundLabel.setOpaque(true); // Pastikan label dapat menampilkan warna
        backgroundLabel.setLayout(new BorderLayout());
        backgroundLabel.add(cardPanel, BorderLayout.CENTER);

        // Tambahkan background ke frame utama
        setContentPane(backgroundLabel);
        setVisible(true);
    }
}
