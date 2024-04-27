package riichimod.mahjong.rules.scoring.minipoints;

import riichimod.mahjong.Hand;

public class OpenPinfu implements Fu
{
    private Hand hand;
    private int initialFu;

    public OpenPinfu(Hand hand, int initialFu)
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
