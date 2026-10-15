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
    
    //doesn't account for jokers, yet
    public int getPoints() {
        if (tiles.isEmpty()) {
            return 0;
        }
        int sum = 0;
        for(int i=0; i<tiles.size(); i++) {
            sum += tiles.get(i).getNumber();
        }
        return sum;
    }
    
    //arbitrary format, we can change it later ig
    public String toString() {
        if (tiles.isEmpty()) {
            return "";
        }
        String str = "";
        for(int i=0; i < tiles.size(); i++) {
            str += "| " + tiles.get(i) + " ";
        }
        return str + "|";
    }
}
