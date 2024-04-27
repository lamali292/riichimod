package riichimod.mahjong.rules.shanten;

import riichimod.mahjong.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RiichiWinningShapes
{
    /*
     * Hand has 4 groups and 1 pair OR 7 pairs OR 13 orphans
     */
    public boolean hasWinningShape(List<Tile> hand)
    {
        if (hand.size() < 14 || hand.size() > 18) {
            return false;
        }

        if (isThirteenOrphans(hand)) {
            return true;
        }

        if (isSevenPairs(hand)) {
            return true;
        }

        return isFourGroupsOnePair(hand);
    }

    public boolean isThirteenOrphans(List<Tile> hand)
    {
        ArrayList<List<Tile>> tileGroups = copyHand(new ArrayList<>(hand));
        if (tileGroups.size() == 13) {
            List<Integer> sizes = tileGroups.stream().mapToInt(List::size).boxed().collect(Collectors.toList());
            return sizes.stream().filter(size -> size == 1).count() == 12 && sizes.stream().filter(size -> size == 2).count() == 1;
        }

        return false;
    }

    public boolean isSevenPairs(List<Tile> hand)
    {
        ArrayList<List<Tile>> tileGroups = copyHand(new ArrayList<>(hand));
        if (tileGroups.size() == 7) {
            List<Integer> sizes = tileGroups.stream().mapToInt(List::size).boxed().collect(Collectors.toList());
            return sizes.stream().filter(size -> size == 2).count() == 7;
        }

        return false;
    }

    public boolean isFourGroupsOnePair(List<Tile> hand)
    {
        ArrayList<List<Tile>> tileGroups = copyHand(new ArrayList<>(hand));
        if (tileGroups.size() == 5) {
            List<Integer> sizes = tileGroups.stream().mapToInt(List::size).boxed().collect(Collectors.toList());
            return sizes.stream().filter(size -> size >= 3 && size < 5).count() == 4 && sizes.stream().filter(size -> size == 2).count() == 1;
        }

        return false;
    }

    private ArrayList<List<Tile>> copyHand(ArrayList<Tile> handCopy) {
        ArrayList<List<Tile>> tileGroups = new ArrayList<>();
        while (!handCopy.isEmpty()) {
            boolean tileWasAdded = false;
            Tile tile = handCopy.remove(0);
            for (List<Tile> group : tileGroups)
            {
                if (group.stream().allMatch(otherTile -> otherTile.getTileKind() == tile.getTileKind()))
                {
                    group.add(tile);
                    tileWasAdded = true;
                }
            }

            if (!tileWasAdded)
            {
                ArrayList<Tile> newGroup = new ArrayList<>();
                newGroup.add(tile);
                tileGroups.add(newGroup);
            }
        }
        return tileGroups;
    }
}
