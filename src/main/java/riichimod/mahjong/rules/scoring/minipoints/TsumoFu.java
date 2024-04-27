package riichimod.mahjong.rules.scoring.minipoints;

import java.util.List;

import riichimod.mahjong.Hand;
import riichimod.mahjong.rules.scoring.RiichiScoringParameters;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;
import riichimod.mahjong.rules.yakus.groupbased.Pinfu;

public class TsumoFu implements Fu
{
    private Hand hand;
    private List<TileGroup> groups;
    private RiichiScoringParameters parameters;

    public TsumoFu(Hand hand, List<TileGroup> groups, RiichiScoringParameters parameters)
    {
        this.hand = hand;
        this.groups = groups;
        this.parameters = parameters;
    }

    @Override
    public boolean isValid()
    {
        if (!parameters.doesPlayerWinOnTsumo())
        {
            return false;
        }

        if (isChiitoitsu())
        {
            return false;
        }

        return !isPinfu();
    }

    private boolean isChiitoitsu()
    {
        return groups.size() == 7;
    }

    private boolean isPinfu()
    {
        Pinfu pinfu = new Pinfu(hand, groups);
        return pinfu.isValid();
    }

    @Override
    public int getFuValue()
    {
        return 2;
    }
}
