package riichimod.mahjong.rules.yakus.groupbased.yakuhai;

import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.utils.MahjongTileKind;
import riichimod.mahjong.utils.TileGroup;

import java.util.List;

public class RedDragonYakuhai extends DragonYakuhai
{
    public RedDragonYakuhai(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    protected MahjongTileKind getYakuhaiTile()
    {
        return MahjongTileKind.RED;
    }
}
