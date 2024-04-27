package riichimod.mahjong.rules.yakus.timingbased;

import riichimod.mahjong.rules.scoring.RiichiScoringParameters;

public class Chankan extends TimingBasedYaku
{
    public Chankan(RiichiScoringParameters parameters)
    {
        super(parameters);
    }

    @Override
    public boolean isValid()
    {
        return parameters.doesPlayerWinOnChankan();
    }

    @Override
    public int getHanValue()
    {
        return 1;
    }
}
