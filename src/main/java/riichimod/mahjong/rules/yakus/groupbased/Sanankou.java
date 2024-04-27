package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.Hand;
import riichimod.mahjong.Tile;
import riichimod.mahjong.rules.utils.TileGroupUtils;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;

import java.util.List;

public class Sanankou extends GroupBasedYaku
{
    public Sanankou(Hand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        List<TileGroup> melds = hand.getMelds();
        int ankouCount = 0;

        for (TileGroup group : groups) {
            if (group.isCompleteExclusiveGroup()) {
                if (!melds.contains(group)) {
                    ankouCount++;
                }
            }
        }

        return ankouCount == 3;
    }

    @Override
    public int getHanValue()
    {
        return 2;
    }
}