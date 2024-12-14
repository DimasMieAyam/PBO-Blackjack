public class Player { //wadah untuk data player dari database
    private String name;
    private int win, draw, lose;

    public Player(String name, int win, int draw, int lose) {
        this.name = name;
        this.win = win;
        this.draw = draw;
        this.lose = lose;
    }

    public Player(String name) {
        this.name = name;
        this.win = 0;
        this.draw = 0;
        this.lose = 0;
    }

    public Player(Player player) {
        this.name = player.getName();
        this.win = player.getWin();
        this.draw = player.getDraw();
        this.lose = player.getLose();
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getWin() {
        return win;
    }
    public void setWin(int win) {
        this.win = win;
    }
    public int getDraw() {
        return draw;
    }
    public void setDraw(int draw) {
        this.draw = draw;
    }
    public int getLose() {
        return lose;
    }
    public void setLose(int lose) {
        this.lose = lose;
    }

    public void addWin() {
        this.win = this.win + 1;
    }
    public void addLose() {
        this.lose = this.lose + 1;
    }
    public void addDraw() {
        this.draw = this.draw + 1;
    }
}
