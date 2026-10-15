import java.util.Arrays;
import java.util.Comparator;

public class Player
{
    protected String name;
    protected LinkedChain<Tile> hand;
    protected boolean hasInitialMeld;
    
    public Player(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name is empty");
        }
        this.name = name.trim(); //this is so that any space after / before the given Name is truncated
        hand = new LinkedChain<>(); //empty hand
        hasInitialMeld = false; //no valid sets yet!
    }

    public String getName() {
        return name;
    }
    
    public LinkedChain<Tile> getHand(){
        return hand;
    }

    public void setHand(LinkedChain<Tile> h) {
        hand = h;
    }

    //return end game
    public void endTurn(Game g){
        //later
    }
    
    public void addTile(Tile t){
        hand.add(t);
    }
    
    public boolean playSet(Set s){
        //later
    }
    
    public void sortHand() {
        int size = hand.size();
        Tile[] tiles = new Tile[size];
        for (int i = 0; i < size; i++) {
            tiles[i] = hand.remove();
        }
        Arrays.sort(tiles, Comparator.comparing(Tile::getColor).thenComparingInt(Tile::getNumber));
        for (Tile t : tiles) {
            hand.add(t);
        }
    }
    
    public int getHandValue(){
        Tile[] tiles = hand.toArray(new Tile[hand.size()]);
        int sum = 0;
        for(Tile tile : tiles) {
            sum += tile.getNumber();
        }
        return sum;
    }
    
    public void displayHand(){
        //later
    }

}