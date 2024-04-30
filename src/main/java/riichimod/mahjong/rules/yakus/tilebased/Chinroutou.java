package riichimod.mahjong.rules.yakus.tilebased;

import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.utils.Tile;
import riichimod.mahjong.utils.MahjongTileKind;

public class Chinroutou extends TileBasedYaku
{
    public Chinroutou(PlayerHand hand)
    {
        super(hand);
    }

    @Override
    public boolean isValid() {
        return hand.getTiles().stream().map(Tile::getTileKind).allMatch(MahjongTileKind::isTerminal);
    }

    @Override
    public int getHanValue()
    {
        return 13;
    }
}
