package riichimod.mahjong.rules.yakus.tilebased;

import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.utils.Tile;

public class Honroutou extends TileBasedYaku
{
    public Honroutou(PlayerHand hand)
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
