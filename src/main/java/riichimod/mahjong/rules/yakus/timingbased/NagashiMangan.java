package riichimod.mahjong.rules.yakus.timingbased;

import riichimod.mahjong.rules.scoring.RiichiScoringParameters;

public class NagashiMangan extends TimingBasedYaku
{
    public NagashiMangan(RiichiScoringParameters parameters)
    {
        super(parameters);
    }

    @Override
    public boolean isValid()
    {
        return parameters.doesPlayerWinOnNagashiMangan();
    }

    @Override
    public int getHanValue()
    {
        return 5;
    }
}
