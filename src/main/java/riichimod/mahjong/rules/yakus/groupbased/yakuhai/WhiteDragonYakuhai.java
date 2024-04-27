package riichimod.mahjong.rules.yakus.groupbased.yakuhai;

import riichimod.mahjong.Hand;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;

import java.util.List;

public class WhiteDragonYakuhai extends DragonYakuhai
{
    public WhiteDragonYakuhai(Hand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    protected MahjongTileKind getYakuhaiTile()
    {
        return MahjongTileKind.WHITE;
    }
}
