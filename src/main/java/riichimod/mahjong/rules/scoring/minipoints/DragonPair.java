package riichimod.mahjong.rules.scoring.minipoints;

import riichimod.mahjong.rules.shanten.parsing.TileGroup;

import java.util.List;

public class DragonPair implements Fu
{
    private List<TileGroup> groups;

    public DragonPair(List<TileGroup> groups)
    {
        this.groups = groups;
    }

    @Override
    public boolean isValid()
    {
        if (groups.size() == 7)
        {
            return false;
        }

        for (TileGroup group : groups)
        {
            if (group.isPair() && group.getTileKindAt(0).isDragon())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getFuValue()
    {
        return 2;
    }
}
