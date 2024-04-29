package riichimod.mahjong.rules.yakus.groupbased.yakuhai;

import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.utils.Seat;
import riichimod.mahjong.rules.utils.TileGroup;
import riichimod.mahjong.rules.yakus.groupbased.GroupBasedYaku;

import java.util.List;

public class TableWindYakuhai extends GroupBasedYaku
{
    public TableWindYakuhai(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        for (TileGroup group : groups)
        {
            if (group.isCompleteExclusiveGroup())
            {
                MahjongTileKind currentTile = group.getTileKindAt(0);
                if (currentTile.isWind() && hand.isTableWind(Seat.getSeatFromTileKind(currentTile)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getHanValue()
    {
        return 1;
    }
}
