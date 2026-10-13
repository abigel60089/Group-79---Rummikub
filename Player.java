public class Player
{
    //~ Fields ................................................................
    
    

    //~ Constructors ..........................................................
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

    
    //~Public  Methods ........................................................
    public String getName() {
        return name;
    }
    
    public void endTurn(Game g){

    }
    
    public void drawTile(Tile t){

    }
    
    public boolean playSet(Set s){

    }
    
    public void sortHand(){

    }
    
    public void getHandValue(){

    }
    
    public void displayHand(){

    }

}
