package riichimod.mahjong.rules.scoring.minipoints;

import riichimod.mahjong.PlayerHand;

public class OpenPinfu implements Fu
{
    private PlayerHand hand;
    private int initialFu;

    public OpenPinfu(PlayerHand hand, int initialFu)
    {
        this.hand = hand;
        this.initialFu = initialFu;
    }

    @Override
    public boolean isValid()
    {
        return initialFu == 20 && hand.isOpen();
    }

    @Override
    public int getFuValue()
    {
        return 2;
    }
}
