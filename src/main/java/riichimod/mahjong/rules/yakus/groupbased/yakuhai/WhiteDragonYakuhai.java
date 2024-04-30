package riichimod.mahjong.rules.yakus.groupbased.yakuhai;

import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.utils.MahjongTileKind;
import riichimod.mahjong.utils.TileGroup;

import java.util.List;

public class WhiteDragonYakuhai extends DragonYakuhai
{
    public WhiteDragonYakuhai(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    protected MahjongTileKind getYakuhaiTile()
    {
        return MahjongTileKind.WHITE;
    }
}
