package riichimod.mahjong.rules.yakus.tilebased;

import riichimod.mahjong.hand.PlayerHand;

public class Tanyao extends TileBasedYaku
{
    // should tanyao be open, default is true
    private boolean kuitan = true;

    public Tanyao(PlayerHand hand)
    {
        super(hand);
    }

    @Override
    public boolean isValid()
    {
        if (!kuitan && hand.isOpen()) return false;
        return hand.getTiles().stream().allMatch(tile -> tile.getTileKind().isNumeral() && !tile.getTileKind().isTerminal());
    }

    @Override
    public int getHanValue()
    {
        return 1;
    }

    public void setKuitan(boolean kuitan)
    {
        this.kuitan = kuitan;
    }
}
