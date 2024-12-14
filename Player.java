public class Player {
    private String name;
    private int win, draw, lose;

    public Player(String name, int win, int draw, int lose) {
        this.name = name;
        this.win = win;
        this.draw = draw;
        this.lose = lose;
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
}
