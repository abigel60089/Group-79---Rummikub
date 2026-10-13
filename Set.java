public abstract class Set
{
    //~ Fields ................................................................
    private LinkedChain<Tile> tiles;
    
    //~ Constructors ..........................................................
    public Set() {
        tiles = new LinkedChain<Tile>();
    }
    //~Public  Methods ........................................................
    public abstract boolean isValid();
    
    public abstract boolean canAdd(Tile t);
}
