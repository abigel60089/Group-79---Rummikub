public class Run
    extends Set
{
    private final LinkedChain<Tile> tiles;
    //private static final int Joker = 14;

    public Run()
    {
        super();
        tiles = new LinkedChain<>();
    }

    @Override
    public boolean isValid()
    {
        if (tiles.size() < 3 || tiles.size() > 13)
        {
            return false;
        }
        for (int i = 1; i < tiles.size(); i++)
        {
            Tile previous = tiles.get(i - 1);
            Tile current = tiles.get(i);

            boolean sameColor = previous.getColor().equals(current.getColor());
            boolean consecutiveNumbers =
                previous.getNumber() + 1 == current.getNumber();

            if (!sameColor || !consecutiveNumbers)
            {
                return false;
            }
        }

        return true;
    }


    public boolean canAdd(Tile t)
    {
        if (tiles.isEmpty())
        {
            return true;
        }
        Tile firstTile = tiles.get(0);
        Tile lastTile = tiles.get(tiles.size() - 1);

        if (!t.getColor().equals(firstTile.getColor())
            || !t.getColor().equals(lastTile.getColor()))
        {
            return false;
        }

        return t.getNumber() == firstTile.getNumber() - 1 || t.getNumber() == lastTile.getNumber() + 1;

    }

    public boolean addTile(Tile t)
    {
        if (!canAdd(t))
        {
            return false;
        }

        if (!tiles.isEmpty() && t.getNumber() < tiles.get(0).getNumber())
        {
            tiles.add(t);
        }

        else
        {
            tiles.addToEnd(t);
        }

        return true;

    }

} // end of isValid method
