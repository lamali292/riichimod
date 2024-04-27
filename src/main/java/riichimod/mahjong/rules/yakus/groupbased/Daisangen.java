package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;

import java.util.List;

public class Daisangen extends GroupBasedYaku
{
    public Daisangen(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        boolean hasGreen = false;
        boolean hasRed = false;
        boolean hasWhite = false;

        for (TileGroup group : groups)
        {
            if (group.isExclusiveGroup())
            {
                MahjongTileKind tileKind = group.getTileKindAt(0);
                if (tileKind.isDragon() && group.isCompleteExclusiveGroup())
                {
                    switch (tileKind)
                    {
                    case GREEN:
                        hasGreen = true;
                        break;
                    case RED:
                        hasRed = true;
                        break;
                    case WHITE:
                        hasWhite = true;
                        break;
                    default:
                        break;
                    }
                }
            }
        }

        return hasGreen && hasRed && hasWhite;
    }

    @Override
    public int getHanValue()
    {
        return 13;
    }
}
