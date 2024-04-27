package riichimod.mahjong.rules.shanten.parsing;

import one.util.streamex.StreamEx;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.shanten.Permutations;
import riichimod.mahjong.Tile;
import riichimod.mahjong.rules.utils.MPSZNotation;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PossiblePairings
{
    private final List<Tile> referenceTiles;

    public PossiblePairings(List<Tile> referenceTiles)
    {
        this.referenceTiles = referenceTiles;
    }

    public PossiblePairings(String referenceTiles)
    {
        this(new MPSZNotation().getTilesFrom(referenceTiles));
    }

    /**
     * @param collidingGroups all groups that are part of one collision
     * @return the different ways the colliding groups can be arranged
     */
    public List<List<TileGroup>> createFrom(List<TileGroup> collidingGroups)
    {
        List<Integer> indicesForCollidingGroups = collidingGroups.stream().map(TileGroup::getIndices).flatMap(Collection::stream).distinct().collect(Collectors.toList());
        Map<Integer, List<List<TileGroup>>> possiblePairingsByElement = new TreeMap<>();
        int loopIndex = 0;
        while (loopIndex < referenceTiles.size())
        {
            MahjongTileKind currentKind = referenceTiles.get(loopIndex).getTileKind();
            int count = (int) referenceTiles.stream().filter(tile -> tile.getTileKind().equals(currentKind)).count();

            if (indicesForCollidingGroups.contains(currentKind.getIndex()))
            {
                List<List<TileGroup>> possiblePairingsOfCurrentElement = addPossiblePairing(currentKind, count, collidingGroups);
                possiblePairingsByElement.put(currentKind.getIndex(), possiblePairingsOfCurrentElement);
            }

            loopIndex = loopIndex + count;
        }

        List<List<List<TileGroup>>> differentCombinations = listDifferentCombinations(possiblePairingsByElement.values());

        List<List<TileGroup>> possiblePairings = new ArrayList<>();
        for (List<List<TileGroup>> combination : differentCombinations) // for each combination
        {
            if (combination == null) continue;
            Map<Integer, List<TileGroup>> combinationsByIndex = new TreeMap<>();
            StreamEx.of(indicesForCollidingGroups) //
                    .zipWith(combination.stream()) //
                    .forEach(oneCombination -> combinationsByIndex.put(oneCombination.getKey(), oneCombination.getValue()));

            // initialize tile keep flags
            List<List<Boolean>> keepTilesFlags = getFlagsForTilesToKeep(combinationsByIndex, collidingGroups);
            List<TileGroup> currentTileGroupPairing = getTileGroups(collidingGroups, keepTilesFlags);
            possiblePairings.add(currentTileGroupPairing);
        }

        return possiblePairings;
    }

    private static List<TileGroup> getTileGroups(List<TileGroup> collidingGroups, List<List<Boolean>> keepTilesFlags) {
        List<TileGroup> currentTileGroupPairing = new ArrayList<>();

        for (int i = 0; i < keepTilesFlags.size(); i++)
        {
            TileGroup currentGroup = collidingGroups.get(i);
            TileGroup dividedTileGroup = new TileGroup();
            List<Boolean> keepTilesFlagsForCurrentGroup = keepTilesFlags.get(i);

            for (int j = 0; j < currentGroup.getIndices().size(); j++)
            {
                if (keepTilesFlagsForCurrentGroup.get(j))
                {
                    dividedTileGroup.add(currentGroup.getIndices().get(j));
                }
            }

            if (!dividedTileGroup.isEmpty())
            {
                currentTileGroupPairing.add(dividedTileGroup);
            }
        }
        return currentTileGroupPairing;
    }

    public List<List<Boolean>> getFlagsForTilesToKeep(Map<Integer, List<TileGroup>> combination, List<TileGroup> collidingGroups)
    {
        HashMap<Integer, Integer> indicesLeft = new HashMap<Integer, Integer>();
        combination.keySet().forEach(index -> {
            indicesLeft.put(index, (int) referenceTiles.stream().filter(tile -> tile.getTileKind().getIndex() == index).count());
        });
        return collidingGroups.stream().map(
                tileGroup -> tileGroup.getIndices().stream().map(
                        index -> {
                            //System.out.println(index);
                            // keine Ahnung warum hier Null kommt
                            if (!indicesLeft.containsKey(index)) return false;
                            int amountOfIndexLeft = indicesLeft.get(index);
                            List<TileGroup> currentCombination = combination.get(index);
                            if (currentCombination.contains(tileGroup) && amountOfIndexLeft > currentCombination.size() - currentCombination.indexOf(tileGroup) - 1) {
                                indicesLeft.put(index, amountOfIndexLeft - 1);
                                return true;
                            }
                            return false;
                        }).collect(Collectors.toList())
                ).collect(Collectors.toList());
    }

    /**
     * This method creates a list of all the possible tile group lists of lists that
     * can be created with the current colliding tiles. The combinations must be
     * ordered by tile index.
     */
    public List<List<List<TileGroup>>> listDifferentCombinations(Collection<List<List<TileGroup>>> pairings)
    {
        return StreamEx.cartesianProduct(pairings).toList();
    }

    /**
     * Create the possible pairings on a specific tile kind. The occurrences is how
     * many of that tile kind need to be found and sorted between groups.
     *
     * @param tileKind           : the tile kind to search
     * @param occurrences        : how many of this tile need to be found
     * @param groupsToSelectFrom : the groups within which the tile is included
     */
    public List<List<TileGroup>> addPossiblePairing(MahjongTileKind tileKind, int occurrences, List<TileGroup> groupsToSelectFrom)
    {
        ArrayList<List<TileGroup>> pairingsOfTileKind = new ArrayList<>();

        Permutations.of(groupsToSelectFrom.stream() //
                .filter(tileGroup -> tileGroup.contains(tileKind)) //
                .toArray(TileGroup[]::new) //
        ) //
                .map(stream -> stream.collect(Collectors.toList())) //
                .forEach(list -> {
                    AtomicInteger countedOccurrences = new AtomicInteger(0);
                    ArrayList<TileGroup> pairing = new ArrayList<>();

                    for (TileGroup tileGroup : list) {
                        pairing.add(tileGroup);
                        int currentCount = countedOccurrences.addAndGet(tileGroup.countOfTile(tileKind));
                        if (currentCount >= occurrences) {
                            break;
                        }
                    }
                    /*list.stream().takeWhile(tileGroup -> {
                        pairing.add(tileGroup);
                        int currentCount = countedOccurrences.addAndGet(tileGroup.countOfTile(tileKind));
                        return currentCount < occurrences;
                    }).collect(Collectors.toList());*/
                    pairing.sort(new TileGroupKindComparator());
                    pairingsOfTileKind.add(pairing);
                });

        return pairingsOfTileKind.stream() //
                .distinct() //
                .collect(Collectors.toList());
    }
}
