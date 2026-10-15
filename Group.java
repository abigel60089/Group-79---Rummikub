public class Group
    extends Set
{

    private final LinkedChain<Tile> tiles;

    public Group()
    {
        super();
        tiles = new LinkedChain<>();
    }


    @Override
    public boolean isValid()
    {
        if ((tiles.size() != 3))
        {
            return false;
        }

        for (int i = 0; i < tiles.size(); i++)
        {
            for (int j = i + 1; j < tiles.size(); j++)
            {
                Tile t1 = tiles.get(i);
                Tile t2 = tiles.get(j);
                if (t1.getNumber() != t2.getNumber()
                    || t1.getColor().equals(t2.getColor()))
                {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean canAdd(Tile t)
    {
        if (tiles.size() >=3)
        {
            return false;
        }
        
        if (tiles.isEmpty())
        {
            return true;
        }

        if (tiles.size() >= 4)
        {
            return false;
        }

        for (int i = 0; i < tiles.size(); i++)
        {
            Tile currentTile = tiles.get(i);

            if (currentTile.getNumber() != t.getNumber()
                || currentTile.getColor().equals(t.getColor()))
            {
                return false;
            }

        }

        return true;

    }

} // end of group sub-class
