import java.sql.*;
import java.util.ArrayList;

public class DatabaseOperations {
    private static final String URL = "jdbc:mysql://localhost:3306/blackjack";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // menambahkan player baru ke database. Hanya perlu nama, sedangkan atribut lain (Win, Draw, Lose) bernilai 0
    public static void addPlayer(String nama) {
        String query = "INSERT INTO players (nama, win, draw, lose) VALUES (?, 0, 0, 0)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            // Set parameter untuk nama player
            preparedStatement.setString(1, nama); // Nama player
            
            // Eksekusi query
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Player berhasil ditambahkan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // mengekstrak jumlah player di database
    public static int getPlayerCount() {
        String query = "SELECT COUNT(*) AS total FROM players";
        int playerCount = 0;
    
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            // Ambil hasil jumlah player dari ResultSet
            if (resultSet.next()) {
                playerCount = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return playerCount;
    }

    //Update record Win, Draw, dan Lose player setelah bermain
    public static void updatePlayer(Player player) {
        String query = "UPDATE players SET win = ?, draw = ?, lose = ? WHERE nama = ?";
    
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            // Set parameter untuk kolom win, draw, lose, dan nama
            preparedStatement.setInt(1, player.getWin());     // Set win
            preparedStatement.setInt(2, player.getDraw());    // Set draw
            preparedStatement.setInt(3, player.getLose());    // Set lose
            preparedStatement.setString(4, player.getName()); // Set nama (untuk mencari record)

            // Eksekusi query
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Player berhasil diperbarui.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ekstrak seluruh record player di database, untuk menampilkannya dalam frame load game
    public static Player[] getPlayers() {
        String query = "SELECT nama, win, draw, lose FROM players";
        ArrayList<Player> playerList = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            
            // Ambil data player dari ResultSet dan masukkan ke dalam list
            while (resultSet.next()) {
                String nama = resultSet.getString("nama");
                int win = resultSet.getInt("win");
                int draw = resultSet.getInt("draw");
                int lose = resultSet.getInt("lose");

                // Buat objek Player dan tambahkan ke dalam list
                Player player = new Player(nama, win, draw, lose);
                playerList.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Mengembalikan array dari list player
        return playerList.toArray(new Player[0]);
    }

    // Mengekstrak player yang akan digunakan dalam permainan
    public static Player getPlayer(String name) {
        String query = "SELECT nama, win, draw, lose FROM players WHERE nama = ?";
        Player player = null;
    
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            // Set parameter untuk nama player
            preparedStatement.setString(1, name);
    
            // Eksekusi query dan ambil hasilnya
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nama = resultSet.getString("nama");
                    int win = resultSet.getInt("win");
                    int draw = resultSet.getInt("draw");
                    int lose = resultSet.getInt("lose");
    
                    // Buat objek Player dan set nilai-nilai yang diambil dari database
                    player = new Player(nama, win, draw, lose);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return player; // Jika tidak ditemukan, player akan tetap null
    }

    // Menghapus record player dari database
    public static void deletePlayer(String playerName) {
        String query = "DELETE FROM players WHERE nama = ?";
    
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            // Set parameter untuk nama player
            preparedStatement.setString(1, playerName);
    
            // Eksekusi query
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Player berhasil dihapus.");
            } else {
                System.out.println("Player dengan nama tersebut tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    //Mengecek keberadaan player dengan nama tertentu di database
    public static boolean playerExist(String name) {
        String query = "SELECT COUNT(*) FROM players WHERE nama = ?";
        boolean exists = false;
    
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            // Set parameter untuk nama pemain
            preparedStatement.setString(1, name);
    
            // Eksekusi query dan periksa hasilnya
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Jika jumlah lebih besar dari 0, maka pemain ada
                    exists = resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return exists;
    }    
}