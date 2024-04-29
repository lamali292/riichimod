package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.rules.utils.TileGroup;

import java.util.List;

public class Sankantsu extends GroupBasedYaku
{
    public Sankantsu(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        int quadCount = 0;
        for (TileGroup group : groups)
        {
            if (group.isQuad())
            {
                quadCount++;
            }
        }
        return quadCount == 3;
    }

    @Override
    public int getHanValue()
    {
        return 2;
    }
}