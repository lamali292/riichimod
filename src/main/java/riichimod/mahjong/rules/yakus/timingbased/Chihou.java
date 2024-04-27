package riichimod.mahjong.rules.yakus.timingbased;

import riichimod.mahjong.rules.scoring.RiichiScoringParameters;

public class Chihou extends TimingBasedYaku
{
    public Chihou(RiichiScoringParameters parameters)
    {
        super(parameters);
    }

    @Override
    public boolean isValid()
    {
        return parameters.doesPlayerWinOnChihou();
    }

    @Override
    public int getHanValue()
    {
        return 13;
    }
}
