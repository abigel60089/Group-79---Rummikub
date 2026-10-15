public abstract class Set
{

    private LinkedChain<Tile> tiles;

    public Set()
    {
        tiles = new LinkedChain<Tile>();
    }


    public abstract boolean isValid();


    public abstract boolean canAdd(Tile t);
}
