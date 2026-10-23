// -------------------------------------------------------------------------
/**
 * Abstract class that creates a new Set object that holds tiles.
 */
public abstract class Set
{

    /**
     * Set of tiles
     */
    protected LinkedChain<Tile> tiles;

    // ----------------------------------------------------------
    /**
     * Create a new Set object.
     */
    public Set()
    {
        tiles = new LinkedChain<Tile>();
    }


    // ----------------------------------------------------------
    /**
     * Checks if a set fits the requirements to be a valid group or run.
     * 
     * @return true if the set is a valid group/run and false otherwise
     */
    public abstract boolean isValid();


    // ----------------------------------------------------------
    /**
     * Checks if a tile fits the requirements to be added to a group or run.
     * 
     * @param t
     *            a tile
     * @return true if tile can be added and false otherwise
     */
    public abstract boolean canAdd(Tile t);


    // ----------------------------------------------------------
    /**
     * Adds a tile to a set if it can be added.
     * 
     * @param t
     *            a tile
     * @return true if tile is added and false otherwise
     */
    public abstract boolean addTile(Tile t);


    // ----------------------------------------------------------
    /**
     * Calculates points a set is worth for player's first turn to check
     * 30-point requirement.
     * 
     * @return point value of the set
     */
    public abstract int getPoints();


    // ----------------------------------------------------------
    /**
     * Checks whether a set has any tiles.
     * 
     * @return true if set is empty and false if not
     */
    public boolean isEmpty()
    {
        return tiles.isEmpty();
    }


    // ----------------------------------------------------------
    /**
     * Returns number of tiles in set.
     * 
     * @return size of the set
     */
    public int size()
    {
        return tiles.size();
    }


    // ----------------------------------------------------------
    /**
     * Returns a tile from set.
     * 
     * @param i
     *            index of tile
     * @return a tile
     */
    public Tile get(int i)
    {
        return tiles.get(i);
    }


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
