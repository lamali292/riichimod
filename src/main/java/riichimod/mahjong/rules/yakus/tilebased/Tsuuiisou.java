package riichimod.mahjong.rules.yakus.tilebased;

import riichimod.mahjong.Hand;

public class Tsuuiisou extends TileBasedYaku
{
    public Tsuuiisou(Hand hand)
    {
        super(hand);
    }

    @Override
    public boolean isValid()
    {
        return hand.getTiles().stream().allMatch(tile -> tile.getTileKind().isHonour());
    }

    @Override
    public int getHanValue()
    {
        return 13;
    }
}
