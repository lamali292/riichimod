package riichimod.mahjong.rules.shanten.parsing;

import riichimod.mahjong.Tile;
import riichimod.mahjong.rules.utils.MahjongTileKind;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * All parsing methods (for runs, pairs, triplets, etc.) for tiles are here.
 */
public class TileParser
{
    public List<TileGroup> parseFamilyTiles(List<Tile> tiles)
    {
        List<TileGroup> tileGroups = new ArrayList<>();

        MahjongTileKind previousTileKind = null;

        while (!tiles.isEmpty())
        {
            MahjongTileKind currentTileKind = tiles.get(0).getTileKind();

            if (currentTileKind == previousTileKind)
            {
                // this tile index was already analysed. Continue
                tiles.remove(0);
                continue;
            }

            // check for pairs/triplets
            if (!includedInAnExclusiveGroup(currentTileKind, tileGroups))
            {
                parsePairsAndTriplets(tiles, currentTileKind).ifPresent(tileGroups::add);
            }

            // check for runs
            for (int i = 1; i < tiles.size(); i++)
            {
                TileGroup runBasedGroup = new TileGroup();
                for (int j = i; j < tiles.size(); j++)
                {
                    runBasedGroup.addAll(indexOf(tiles.get(0)), indexOf(tiles.get(i)), indexOf(tiles.get(j)));

                    if (runBasedGroup.isRun() && !tileGroups.contains(runBasedGroup))
                    {
                        int occurrence = 4;
                        for (int index : runBasedGroup.getIndices())
                        {
                            int currentTileOccurrence = getOccurrencesOfTileIndex(index, tiles);
                            occurrence = Math.min(occurrence, currentTileOccurrence);
                        }

                        for (int k = 0; k < occurrence; k++)
                        {
                            tileGroups.add(runBasedGroup);
                        }

                        runBasedGroup = new TileGroup();

                        int firstTileOccurrence = getOccurrencesOfTileIndex(tiles.get(0).getTileKind().getIndex(), tiles);
                        if (firstTileOccurrence > occurrence) // there might be more protogroups that we could add from that
                        {
                            int secondTileOccurrence = getOccurrencesOfTileIndex(tiles.get(i).getTileKind().getIndex(), tiles);
                            int thirdTileOccurrence = getOccurrencesOfTileIndex(tiles.get(j).getTileKind().getIndex(), tiles);

                            if (secondTileOccurrence > occurrence)
                            {
                                runBasedGroup.addAll(indexOf(tiles.get(0)), indexOf(tiles.get(i)));
                                int protogroupOccurrence = Math.min(firstTileOccurrence - occurrence, secondTileOccurrence - occurrence);
                                for (int k = 0; k < protogroupOccurrence; k++)
                                {
                                    tileGroups.add(runBasedGroup);
                                }
                            }
                            else if (thirdTileOccurrence > occurrence)
                            {
                                runBasedGroup.addAll(indexOf(tiles.get(0)), indexOf(tiles.get(j)));
                                int protogroupOccurrence = Math.min(firstTileOccurrence - occurrence, thirdTileOccurrence - occurrence);
                                for (int k = 0; k < protogroupOccurrence; k++)
                                {
                                    tileGroups.add(runBasedGroup);
                                }
                            }
                        }
                        break;
                    }
                    else
                    {
                        runBasedGroup = new TileGroup();
                    }
                }

                TileGroup analyzedSubGroup = new TileGroup(indexOf(tiles.get(0)), indexOf(tiles.get(i)));
                if (runBasedGroup.getIndices().isEmpty() && analyzedSubGroup.isProtogroup())
                {
                    if (!includedInARunGroup(indexOf(tiles.get(0)), indexOf(tiles.get(i)), tileGroups))
                    {
                        runBasedGroup.addAll(indexOf(tiles.get(0)), indexOf(tiles.get(i)));

                        int occurrence = 4;
                        for (int index : runBasedGroup.getIndices())
                        {
                            int currentTileOccurrence = getOccurrencesOfTileIndex(index, tiles);
                            occurrence = Math.min(occurrence, currentTileOccurrence);
                        }

                        for (int j = 0; j < occurrence; j++)
                        {
                            tileGroups.add(runBasedGroup);
                        }
                        break;
                    }
                }
            }

            // create a group for that tile
            if (!includedInAGroup(currentTileKind, tileGroups))
            {
                tileGroups.add(parseLoneTiles(currentTileKind));
            }

            tiles.remove(0);

            previousTileKind = currentTileKind;
        }
        return tileGroups;
    }

    private int getOccurrencesOfTileIndex(int index, List<Tile> tiles)
    {
        return (int) tiles.stream().filter(tile -> tile.getTileKind().getIndex() == index).count();
    }

    public List<TileGroup> parseHonourTiles(List<Tile> tiles)
    {
        List<TileGroup> tileGroups = new ArrayList<>();

        while (!tiles.isEmpty())
        {
            MahjongTileKind tileKind = tiles.get(0).getTileKind();

            parsePairsAndTriplets(tiles, tileKind).ifPresent(tileGroups::add);

            if (!includedInAGroup(tileKind, tileGroups))
            {
                tileGroups.add(parseLoneTiles(tileKind));
            }

            tiles.removeIf(tile -> tile.getTileKind() == tileKind);
        }

        return tileGroups;
    }

    protected Optional<TileGroup> parsePairsAndTriplets(List<Tile> tiles, MahjongTileKind tileKind)
    {
        TileGroup tileGroup = new TileGroup(tiles.stream() //
                .filter(tile -> tile.getTileKind() == tileKind) //
                .map(tile -> tile.getTileKind().getIndex()) //
                .collect(Collectors.toList()));

        if (tileGroup.isLoneTile())
        {
            return Optional.empty();
        }
        return Optional.of(tileGroup);
    }

    protected TileGroup parseLoneTiles(MahjongTileKind currentTileKind)
    {
        return new TileGroup(currentTileKind.getIndex());
    }

    protected boolean includedInAnExclusiveGroup(MahjongTileKind currentTileKind, List<TileGroup> tileGroups)
    {
        return tileGroups.stream().anyMatch(group -> group.getTileKindAt(0) == currentTileKind && group.isExclusiveGroup());
    }

    protected boolean includedInAGroup(MahjongTileKind currentTileKind, List<TileGroup> tileGroups)
    {
        return tileGroups.stream().anyMatch(group -> group.getIndices().contains(currentTileKind.getIndex()));
    }

    protected boolean includedInARunGroup(int first, int second, List<TileGroup> tileGroups)
    {
        return tileGroups.stream().filter(TileGroup::isComplete).anyMatch(group -> group.getIndices().contains(first) && group.getIndices().contains(second));
    }

    protected int indexOf(Tile tile)
    {
        return tile.getTileKind().getIndex();
    }
}
