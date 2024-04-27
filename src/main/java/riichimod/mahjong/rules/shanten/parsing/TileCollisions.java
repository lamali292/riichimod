package riichimod.mahjong.rules.shanten.parsing;

import one.util.streamex.StreamEx;

import java.util.List;

public class TileCollisions
{
    public List<List<TileGroup>> createCollisionList(List<TileGroup> tileGroups)
    {
        return StreamEx.of(tileGroups) //
                .sorted(new TileGroupKindComparator()) //
                .groupRuns(TileGroup::collidesWith) //
                .filter(list -> list.size() >= 2) //
                .toList();
    }
}
