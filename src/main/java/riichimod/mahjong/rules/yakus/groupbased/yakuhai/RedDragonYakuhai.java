package riichimod.mahjong.rules.yakus.groupbased.yakuhai;

import riichimod.mahjong.Hand;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;

import java.util.List;

public class RedDragonYakuhai extends DragonYakuhai
{
    public RedDragonYakuhai(Hand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    protected MahjongTileKind getYakuhaiTile()
    {
        return MahjongTileKind.RED;
    }
}
