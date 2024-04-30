package riichimod.mahjong.rules.yakus.groupbased.yakuhai;

import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.utils.MahjongTileKind;
import riichimod.mahjong.utils.TileGroup;
import riichimod.mahjong.rules.yakus.groupbased.GroupBasedYaku;

import java.util.List;

public abstract class DragonYakuhai extends GroupBasedYaku
{
    public DragonYakuhai(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    protected abstract MahjongTileKind getYakuhaiTile();

    @Override
    public boolean isValid()
    {
        for (TileGroup group : groups)
        {
            if (group.isCompleteExclusiveGroup() && getYakuhaiTile() == group.getTileKindAt(0))
            {
                return true;
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
