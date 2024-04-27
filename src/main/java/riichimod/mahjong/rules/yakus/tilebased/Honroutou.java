package riichimod.mahjong.rules.yakus.tilebased;

import riichimod.mahjong.Hand;
import riichimod.mahjong.Tile;
import riichimod.mahjong.rules.utils.MahjongTileKind;

public class Honroutou extends TileBasedYaku
{
    public Honroutou(Hand hand)
    {
        super(hand);
    }

    /**
     * Honroutou requires the presence of both honours and terminals.
     */
    @Override
    public boolean isValid() {
        return hand.getTiles().stream().map(Tile::getTileKind).allMatch(t->(t.isTerminal()||t.isHonour()));
    }

    @Override
    public int getHanValue()
    {
        return 2;
    }
}
