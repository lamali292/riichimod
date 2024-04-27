package riichimod.mahjong.rules.scoring.minipoints;

import java.util.List;

import riichimod.mahjong.Hand;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.utils.TileGroupUtils;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;

/**
 * By exclusive groups, we mean minkou (open pon), ankou (closed pon), minkan
 * (open kan) and ankan (closed kan).
 */
public class ExclusiveGroups implements Fu
{
    private Hand hand;
    private List<TileGroup> tileGroups;

    public ExclusiveGroups(Hand hand, List<TileGroup> tileGroups)
    {
        this.hand = hand;
        this.tileGroups = tileGroups;
    }

    @Override
    public int getFuValue()
    {
        int totalFuValue = 0;
        for (TileGroup tileGroup : tileGroups)
        {
            if (tileGroup.isCompleteExclusiveGroup())
            {
                int groupValue = 2;

                if (isClosed(tileGroup))
                {
                    groupValue *= 2;
                }

                if (isNonSimple(tileGroup))
                {
                    groupValue *= 2;
                }

                if (tileGroup.isQuad())
                {
                    groupValue *= 4;
                }

                totalFuValue += groupValue;
            }
        }
        return totalFuValue;
    }

    private boolean isNonSimple(TileGroup tileGroup)
    {
        return MahjongTileKind.isTerminalOrHonour(tileGroup.getIndices().get(0));
    }

    private boolean isClosed(TileGroup tileGroup)
    {
        boolean hasThisGroupOpen = hand.getMelds() //
                .stream() //
                .anyMatch(tileList -> TileGroupUtils.getTilesFromTileGroup(tileGroup).equals(tileList));
        return !hasThisGroupOpen;
    }

    @Override
    public boolean isValid()
    {
        return true;
    }
}
