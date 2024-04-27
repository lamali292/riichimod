package riichimod.mahjong.rules.yakus.timingbased;

import riichimod.mahjong.rules.scoring.RiichiScoringParameters;

public class MenzenTsumo extends TimingBasedYaku
{
    public MenzenTsumo(RiichiScoringParameters parameters)
    {
        super(parameters);
    }

    @Override
    public boolean isValid()
    {
        return parameters.doesPlayerWinOnMenzenTsumo();
    }

    @Override
    public int getHanValue()
    {
        return 1;
    }
}
