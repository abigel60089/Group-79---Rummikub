// -------------------------------------------------------------------------
/**
 * Subclass of Set that creates a new Run object that holds tiles.
 */
public class Run
    extends Set
{
    private static final int JOKER = 14;

    // ----------------------------------------------------------
    /**
     * Create a new Run object.
     */
    public Run()
    {
        super();
    }


    /**
     * Checks if a tile is a joker.
     * 
     * @return true if a tile is a joker and false if not
     */
    private boolean isJoker(Tile t)
    {
        return t.getNumber() == JOKER;
    }


    /**
     * Returns index of tile if not a joker.
     * 
     * @return index of tile or -1 if tile is a joker
     */
    private int firstRealTile()
    {
        for (int i = 0; i < tiles.size(); i++)
        {
            if (!isJoker(tiles.get(i)))
            {
                return i;
            }
        }
        return -1;
    }


    @Override
    public boolean isValid()
    {
        if (tiles.size() < 3 || tiles.size() > 13)
        {
            return false;
        }

        int t1 = firstRealTile();
        if (t1 == -1)
        {
            return false;
        }

        Tile temp = tiles.get(t1);
        int start = temp.getNumber() - t1;
        int end = start + tiles.size() - 1;
        if (start < 1 || end > 13)
        {
            return false;
        }

        for (int i = 0; i < tiles.size(); i++)
        {
            Tile t = tiles.get(i);
            if (isJoker(t))
            {
                continue;
            }

            if (!t.getColor().equals(temp.getColor())
                || t.getNumber() != start + i)
            {
                return false;
            }
        }

        return true;
    }


    @Override
    public boolean canAdd(Tile t)
    {
        if (tiles.isEmpty())
        {
            return true;
        }

        if (tiles.size() >= 13)
        {
            return false;
        }

        int t1 = firstRealTile();
        if (t1 == -1)
        {
            return true;
        }

        Tile temp = tiles.get(t1);
        int start = temp.getNumber() - t1;
        int end = start + tiles.size() - 1;

        if (isJoker(t))
        {
            return start > 1 || end < 13;
        }

        return t.getColor().equals(temp.getColor())
            && (t.getNumber() == start - 1 || t.getNumber() == end + 1);

    }


    @Override
    public boolean addTile(Tile t)
    {
        if (!canAdd(t))
        {
            return false;
        }

        if (goesOnFront(t))
        {
            tiles.add(t);
        }

        else
        {
            tiles.addToEnd(t);
        }

        return true;
    }


    @Override
    public int getPoints()
    {
        if (tiles.isEmpty())
        {
            return 0;
        }
        int position = firstRealTile();
        if (position == -1)
        {
            return 0;
        }
        int first = tiles.get(position).getNumber() - position;
        int last = first + tiles.size() - 1;
        float sum = (tiles.size() / (float)2) * (first + last);
        return Math.round(sum);
    }


    /**
     * Checks if tile should go to front of run
     * 
     * @return true if tile is not joker and is before start number
     */
    private boolean goesOnFront(Tile t)
    {
        int t1 = firstRealTile();
        if (t1 == -1)
        {
            return false;
        }

        int start = tiles.get(t1).getNumber() - t1;
        int end = start + tiles.size() - 1;

        if (isJoker(t))
        {
            return end == 13;
        }

        return t.getNumber() == start - 1;
    }

}
