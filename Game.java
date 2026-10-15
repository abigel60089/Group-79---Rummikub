public class Game
{
    private LinkedChain<Tile> drawPile = new LinkedChain<Tile>();
    private LinkedChain<Set> table = new LinkedChain<Set>();
    private Player player;
    private Computer computer;
    //private ? scanner
    private boolean gameOver = false;
    
    public Game() {
        createTiles();
        shuffleDrawPile();
        //we need the player's name before creating the object
        player = null;
        computer = null;
    }
    
    private void createTiles() {
        String[] colors = {"black", "red", "yellow", "blue"};
        for(String color : colors) {
            for(int num=1; num < 14; num++) {
                drawPile.add(new Tile(num, color));
                drawPile.add(new Tile(num, color));
            }
        }
        drawPile.add(new Tile(14, "black"));
        drawPile.add(new Tile(14, "black"));
    }
    
    public void shuffleDrawPile() {
        //idk
    }
}
