package riichimod.mahjong.rules.yakus.tilebased;

import riichimod.mahjong.hand.PlayerHand;

public class Tsuuiisou extends TileBasedYaku
{
    public Tsuuiisou(PlayerHand hand)
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
