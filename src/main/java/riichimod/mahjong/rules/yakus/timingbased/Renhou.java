package riichimod.mahjong.rules.yakus.timingbased;

import riichimod.mahjong.rules.scoring.RiichiScoringParameters;

public class Renhou extends TimingBasedYaku
{
    // Default value for renhou is mangan
    private int value = 5;

    public Renhou(RiichiScoringParameters parameters)
    {
        super(parameters);
    }

    public void setHanValue(int hanValue)
    {
        value = hanValue;
    }

    @Override
    public boolean isValid()
    {
        return parameters.doesPlayerWinOnRenhou();
    }

    @Override
    public int getHanValue()
    {
        return value;
    }
}
