package riichimod.mahjong.rules.yakus.timingbased;

import riichimod.mahjong.rules.scoring.RiichiScoringParameters;

public class Houtei extends TimingBasedYaku
{
    public Houtei(RiichiScoringParameters parameters)
    {
        super(parameters);
    }

    @Override
    public boolean isValid()
    {
        return parameters.doesPlayerWinOnHoutei();
    }

    @Override
    public int getHanValue()
    {
        return 1;
    }
}
