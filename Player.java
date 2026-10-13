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
        this.hand = new LinkedChain<>(); //hand
        this.hasInitialMeld = false; //no valid sets yet!
    }

    public String getName() {
        return name;
    }
    
    public void setHand(LinkedChain<Tile> h) {
        hand = h;
    }
    
    public void endTurn(Game g){
        //later
    }
    
    public void addTile(Tile t){
        hand.add(t);
    }
    
    public boolean playSet(Set s){
        //later
    }
    
    public void sortHand(){
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

    }

}
