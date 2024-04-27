package riichimod.mahjong.rules.yakus.groupbased.yakuhai;

import riichimod.mahjong.Hand;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;
import riichimod.mahjong.rules.yakus.groupbased.GroupBasedYaku;

import java.util.List;

public abstract class DragonYakuhai extends GroupBasedYaku
{
    public DragonYakuhai(Hand hand, List<TileGroup> groups)
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
