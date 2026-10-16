public class Group
    extends Set
{
    private static final int JOKER = 14;

    public Group()
    {
        super();
    }


    private boolean isJoker(Tile t)
    {
        return t.getNumber() == JOKER;
    }


    @Override
    public boolean isValid()
    {
        if ((tiles.size() < 3) || (tiles.size() > 4))
        {
            return false;
        }

        int realTiles = 0;
        for (int i = 0; i < tiles.size(); i++)
        {
            Tile t1 = tiles.get(i);
            if (isJoker(t1))
            {
                continue;
            }
            realTiles++;

            for (int j = i + 1; j < tiles.size(); j++)
            {
                Tile t2 = tiles.get(j);
                if (isJoker(t2))
                {
                    continue;
                }

                if (t1.getNumber() != t2.getNumber()
                    || t1.getColor().equals(t2.getColor()))
                {
                    return false;
                }
            }

        }

        return realTiles > 0;
    }


    @Override
    public boolean canAdd(Tile t)
    {
        if (tiles.size() >= 4)
        {
            return false;
        }

        if (tiles.isEmpty())
        {
            return true;
        }

        if (isJoker(t))
        {
            return true;
        }

        for (int i = 0; i < tiles.size(); i++)
        {
            Tile currentTile = tiles.get(i);

            if (isJoker(currentTile))
            {
                continue;
            }

            if (currentTile.getNumber() != t.getNumber()
                || currentTile.getColor().equals(t.getColor()))
            {
                return false;
            }

        }

        return true;

    }


    @Override
    public boolean addTile(Tile t)
    {
        if (!canAdd(t))
        {
            return false;
        }

        return tiles.add(t);
    }


    @Override
    public int getPoints()
    {
        if (tiles.isEmpty())
        {
            return 0;
        }
        for (int i = 0; i < tiles.size(); i++)
        {
            if (!isJoker(tiles.get(i)))
            {
                return tiles.get(i).getNumber() * tiles.size();
            }
        }
        return 0; // now every tile counts as the group's number, including the
                  // jokers
    }

} // end of group sub-class
