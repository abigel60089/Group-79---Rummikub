public abstract class Set
{

    protected LinkedChain<Tile> tiles;

    public Set()
    {
        tiles = new LinkedChain<Tile>();
    }


    public abstract boolean isValid();


    public abstract boolean canAdd(Tile t);


    public abstract boolean addTile(Tile t);


    public boolean isEmpty()
    {
        return tiles.isEmpty();
    }


    public int size()
    {
        return tiles.size();
    }


    public Tile get(int i)
    {
        return tiles.get(i);
    }


    public abstract int getPoints();


    // arbitrary format, we can change it later ig
    public String toString()
    {
        if (tiles.isEmpty())
        {
            return "";
        }
        String str = "";
        for (int i = 0; i < tiles.size(); i++)
        {
            str += "| " + tiles.get(i) + " ";
        }
        return str + "|";
    }
}
