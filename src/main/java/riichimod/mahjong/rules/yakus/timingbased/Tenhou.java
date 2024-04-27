package riichimod.mahjong.rules.yakus.timingbased;

import riichimod.mahjong.rules.scoring.RiichiScoringParameters;

public class Tenhou extends TimingBasedYaku
{
    public Tenhou(RiichiScoringParameters parameters)
    {
        super(parameters);
    }

    @Override
    public boolean isValid()
    {
        return parameters.doesPlayerWinOnTenhou();
    }

    @Override
    public int getHanValue()
    {
        return 13;
    }
}
