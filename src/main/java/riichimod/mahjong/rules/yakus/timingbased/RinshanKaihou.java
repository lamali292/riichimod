package riichimod.mahjong.rules.yakus.timingbased;

import riichimod.mahjong.rules.scoring.RiichiScoringParameters;

public class RinshanKaihou extends TimingBasedYaku
{
    public RinshanKaihou(RiichiScoringParameters parameters)
    {
        super(parameters);
    }

    @Override
    public boolean isValid()
    {
        return parameters.doesPlayerWinOnRinshanKaihou();
    }

    @Override
    public int getHanValue()
    {
        return 1;
    }
}
