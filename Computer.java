import java.util.*;

public class Computer
    extends Player
{
    private LinkedChain<Set> currMoves = new LinkedChain<Set>();

    public Computer(String name)
    {
        super(name);
    }


    @Override
    public void takeTurn(Game g)
    {
        if (g == null)
        {
            throw new IllegalArgumentException("Game cannot be null");
        }
        findMoves();
        if (!currMoves.isEmpty())
        {
            LinkedChain<Set> plays = new LinkedChain<Set>();
            Set[] moves = currMoves.toArray(new Set[currMoves.size()]);
            Arrays.sort(moves, Comparator.comparingInt(Set::getPoints));
            Tile[] handArray = hand.toArray(new Tile[hand.size()]);
            boolean[] used = new boolean[hand.size()];
            Tile needed = null;
            boolean[] justUsed;
            boolean wasFound = false;
            boolean complete = true;
            for(Set set : moves) {
                justUsed = new boolean[hand.size()];
                for(int j=0; j < set.size(); j++) {
                    needed = set.get(j);
                    for(int i=0; i < hand.size(); i++) {
                        if (!used[i] && needed.equals(handArray[i]))
                        {
                            used[i] = true;
                            justUsed[i] = true;
                            wasFound = true;
                            break;
                        }
                    }
                    if(!wasFound) {
                        for(int i=0; i < hand.size(); i++) {
                            if(justUsed[i]) {
                                used[i] = false;
                            }
                        }
                        complete = false;
                        break;
                    }
                }
                if(complete) {
                    plays.add(set);
                }
                complete = true;
            }
            int totalPoints = 0;
            for(int i=0; i < plays.size(); i++) {
                totalPoints += plays.get(i).getPoints();
            }
            if (hasInitialMeld || (totalPoints >= INITIAL_MELD_POINTS))
            {
                for(int i=0; i < plays.size(); i++) {
                    g.addSetToTable(plays.get(i));
                }
                for(int i = hand.size() - 1; i >= 0; i--) {
                    if(used[i]) {
                        hand.remove(hand.get(i));
                    }
                }
                hasInitialMeld = true;
                playedThisTurn = true;
            }
        }
        endTurn(g);
        currMoves.clear();
        sortHand();
        System.out.println(name + " has " + hand.size() + " tiles.");
    }


    private void findMoves()
    {
        ArrayList<String> colors = new ArrayList<String>();
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        String col = "";
        int num = 0;
        for (int i = 0; i < hand.size(); i++)
        {
            col = hand.get(i).getColor();
            if (!colors.contains(col))
            {
                colors.add(col);
                findRuns(col);
            }
            num = hand.get(i).getNumber();
            if (!numbers.contains(num))
            {
                numbers.add(num);
                findGroups(num);
            }
        }
    }


    private void findGroups(int num)
    {
        LinkedChain<Group> groups = new LinkedChain<Group>();
        groups.add(new Group());
        Tile[] handArray = hand.toArray(new Tile[hand.size()]);
        boolean wasAdded = false;
        for (Tile tile : handArray)
        {
            if ((tile.getNumber() == num) || (tile.getNumber() == 14))
            {
                for (int i = 0; i < groups.size(); i++)
                {
                    if (groups.get(i).addTile(tile))
                    {
                        wasAdded = true;
                        break;
                    }
                }
                if (!wasAdded)
                {
                    groups.add(new Group());
                    groups.get(0).addTile(tile);
                }
                wasAdded = false;
            }
        }
        Group[] groupArray = groups.toArray(new Group[groups.size()]);
        for (Group group : groupArray)
        {
            if (group.isValid())
            {
                currMoves.add(group);
            }
        }
    }


    private void findRuns(String col)
    {
        LinkedChain<Run> runs = new LinkedChain<Run>();
        runs.add(new Run());
        Tile[] handArray = hand.toArray(new Tile[hand.size()]);
        boolean wasAdded = false;
        for (Tile tile : handArray)
        {
            if (tile.getColor().equals(col) || (tile.getNumber() == 14))
            {
                for (int i = 0; i < runs.size(); i++)
                {
                    if (runs.get(i).addTile(tile))
                    {
                        wasAdded = true;
                        break;
                    }
                }
                if (!wasAdded)
                {
                    runs.add(new Run());
                    runs.get(0).addTile(tile);
                }
                wasAdded = false;
            }
        }
        Run[] runArray = runs.toArray(new Run[runs.size()]);
        for (Run run : runArray)
        {
            if (run.isValid())
            {
                currMoves.add(run);
            }
        }
    }
}
