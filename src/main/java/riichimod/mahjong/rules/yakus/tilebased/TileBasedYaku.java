package riichimod.mahjong.rules.yakus.tilebased;

import riichimod.mahjong.Hand;
import riichimod.mahjong.rules.yakus.Yaku;

public abstract class TileBasedYaku implements Yaku
{
    protected Hand hand;

    public TileBasedYaku(Hand hand)
    {
        this.hand = hand;
    }
}
