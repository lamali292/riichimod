package riichimod.mahjong.rules.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import riichimod.mahjong.Tile;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;

public class TileGroupUtils
{
    public static List<TileGroup> tileGroupsOf(String... groups)
    {
        ArrayList<TileGroup> tileGroupList = new ArrayList<TileGroup>();

        for (String group : groups)
        {
            List<Tile> tiles = new MPSZNotation().getTilesFrom(group);
            List<Integer> tileIndices = tiles.stream().map(tile -> tile.getTileKind().getIndex()).collect(Collectors.toList());
            TileGroup tileGroup = new TileGroup(tileIndices);
            tileGroupList.add(tileGroup);
        }

        return tileGroupList;
    }

    public static List<Tile> getTilesFromMPSZNotation(String... groups)
    {
        return getTilesFromTileGroups(tileGroupsOf(groups));
    }

    public static List<Tile> getTilesFromTileGroup(TileGroup group)
    {
        ArrayList<TileGroup> a = new ArrayList<>();
        a.add(group);
        return getTilesFromTileGroups(a);
    }

    public static List<Tile> getTilesFromTileGroups(List<TileGroup> groups)
    {
        ArrayList<Tile> tiles = new ArrayList();

        for (TileGroup group : groups)
        {
            for (int index : group.getIndices())
            {
                Tile tile = new Tile(MahjongTileKind.getKindFromIndex(index));
                tiles.add(tile);
            }
        }
        // order by tile kind index ascending
        tiles.sort(Comparator.comparingInt(tile -> tile.getTileKind().getIndex()));

        return tiles;
    }

    public static TileGroup getTileGroupFromTiles(List<Tile> tiles)
    {
        TileGroup group = new TileGroup();
        tiles.stream().map(tile -> tile.getTileKind().getIndex()).forEach(group::add);
        return group;
    }
}
