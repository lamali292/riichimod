package riichimod.mahjong.rules.yakus.tilebased;

import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.utils.Tile;
import riichimod.mahjong.utils.MahjongTileKind;

public class Ryuuiisou extends TileBasedYaku
{
    public Ryuuiisou(PlayerHand hand)
    {
        super(hand);
    }

    public boolean isGreen(MahjongTileKind t) {
        return t.is(MahjongTileKind.BAMBOOS_2, //
                MahjongTileKind.BAMBOOS_3, //
                MahjongTileKind.BAMBOOS_4, //
                MahjongTileKind.BAMBOOS_6, //
                MahjongTileKind.BAMBOOS_8, //
                MahjongTileKind.GREEN);
    }


    @Override
    public boolean isValid() {
        return hand.getTiles().stream().map(Tile::getTileKind).allMatch(this::isGreen);
    }

    @Override
    public int getHanValue()
    {
        return 13;
    }
}
