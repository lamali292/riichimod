package riichimod.mahjong.rules.yakus.timingbased;

import riichimod.mahjong.rules.scoring.RiichiScoringParameters;

public class Riichi extends TimingBasedYaku
{
    public Riichi(RiichiScoringParameters parameters)
    {
        super(parameters);
    }

    @Override
    public boolean isValid()
    {
        return parameters.hasPlayerDeclaredRiichi();
    }

    @Override
    public int getHanValue()
    {
        return 1;
    }
}
