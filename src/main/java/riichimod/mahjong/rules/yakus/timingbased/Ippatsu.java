package riichimod.mahjong.rules.yakus.timingbased;

import riichimod.mahjong.rules.scoring.RiichiScoringParameters;

public class Ippatsu extends TimingBasedYaku
{
    public Ippatsu(RiichiScoringParameters parameters)
    {
        super(parameters);
    }

    @Override
    public boolean isValid()
    {
        return parameters.doesPlayerWinOnIppatsu();
    }

    @Override
    public int getHanValue()
    {
        return 1;
    }
}
