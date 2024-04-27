package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.Hand;
import riichimod.mahjong.Tile;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.utils.TileGroupUtils;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;

import java.util.List;

public class Suuankou extends GroupBasedYaku
{
    public Suuankou(Hand hand, List<TileGroup> groups)
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

        return ankouCount == 4;
    }

    @Override
    public int getHanValue()
    {
        MahjongTileKind winningTile = hand.getWinningTile();
        for (TileGroup group : groups)
        {
            if (group.isPair() && group.getTileKindAt(0) == winningTile)
            {
                return 26;
            }
        }
        return 13;
    }
}