package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.utils.MahjongTileKind;
import riichimod.mahjong.utils.TileGroup;

import java.util.List;

public class Shousangen extends GroupBasedYaku
{
    public Shousangen(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        boolean hasGreen = false;
        boolean hasRed = false;
        boolean hasWhite = false;
        boolean pairIsDragon = false;

        for (TileGroup group : groups)
        {
            if (group.isExclusiveGroup())
            {
                MahjongTileKind tileKind = group.getTileKindAt(0);
                if (tileKind.isDragon())
                {
                    if (group.isPair())
                    {
                        if (pairIsDragon)
                        {
                            return false;
                        }
                        pairIsDragon = true;
                    }
                    else if (!group.isCompleteExclusiveGroup())
                    {
                        return false;
                    }

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

        return hasGreen && hasRed && hasWhite && pairIsDragon;
    }

    @Override
    public int getHanValue()
    {
        return 2;
    }
}
