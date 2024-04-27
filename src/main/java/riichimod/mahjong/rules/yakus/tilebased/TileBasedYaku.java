package riichimod.mahjong.rules.yakus.tilebased;

import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.rules.yakus.Yaku;

public abstract class TileBasedYaku implements Yaku
{
    protected PlayerHand hand;

    public TileBasedYaku(PlayerHand hand)
    {
        this.hand = hand;
    }
}
