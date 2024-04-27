package riichimod.mahjong.rules.yakus.tilebased;

import riichimod.mahjong.Hand;
import riichimod.mahjong.Tile;
import riichimod.mahjong.rules.utils.MahjongTileKind;

public class Chinroutou extends TileBasedYaku
{
    public Chinroutou(Hand hand)
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
