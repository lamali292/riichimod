package riichimod.mahjong.rules.yakus.timingbased;

import riichimod.mahjong.rules.scoring.RiichiScoringParameters;
import riichimod.mahjong.rules.yakus.Yaku;

public abstract class TimingBasedYaku implements Yaku
{
    protected final RiichiScoringParameters parameters;

    public TimingBasedYaku(RiichiScoringParameters parameters)
    {
        this.parameters = parameters;
    }
}
