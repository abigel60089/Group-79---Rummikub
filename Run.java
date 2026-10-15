public class Run extends Set
{
    private final LinkedChain<Tile> tiles;

    public Run()
    {
        super();
        tiles = new LinkedChain<>();
    }

    @Override
    public boolean isValid()
    {
        if (tiles.size() < 3 || tiles.size() > 14)
        {
            return false;
        }
        for (int i = 0; i < tiles.size(); i++)
        {
            Tile previous = tiles.get(i-1);
            Tile current = tiles.get(i);

            boolean sameColor = previous.getColor().equals(current.getColor());
            boolean consecutiveNumbers = previous.getNumber() + 1 == current.getNumber();

            if(!sameColor || !consecutiveNumbers)
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

        if(!tiles.isEmpty() && t.getNumber() < tiles.get(0).getNumber())
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