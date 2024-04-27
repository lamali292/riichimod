package riichimod.mahjong.rules.yakus.timingbased;

import riichimod.mahjong.rules.scoring.RiichiScoringParameters;

public class Haitei extends TimingBasedYaku
{
    public Haitei(RiichiScoringParameters parameters)
    {
        super(parameters);
    }

    @Override
    public boolean isValid()
    {
        return parameters.doesPlayerWinOnHaitei();
    }

    @Override
    public int getHanValue()
    {
        return 1;
    }
}
