package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.utils.TileGroup;

import java.util.List;

public class Toitoi extends GroupBasedYaku
{
    public Toitoi(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        int pairAmount = 0;
        for (TileGroup group : groups)
        {
            if (group.isPair())
            {
                pairAmount++;
                if (pairAmount > 1)
                {
                    return false;
                }
            }
            else if (!group.isCompleteExclusiveGroup())
            {
                return false;
            }
        }
        return pairAmount == 1;
    }

    @Override
    public int getHanValue()
    {
        return 2;
    }
}
