package riichimod.mahjong.rules.scoring.minipoints;

import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;
import riichimod.mahjong.rules.scoring.RiichiScoringParameters;
import riichimod.mahjong.rules.yakus.groupbased.Chiitoitsu;

import java.util.List;

public class BaseMinipoints implements Fu
{
    private PlayerHand hand;
    private List<TileGroup> groups;
    private RiichiScoringParameters parameters;

    public BaseMinipoints(PlayerHand hand, List<TileGroup> groups, RiichiScoringParameters parameters)
    {
        this.hand = hand;
        this.groups = groups;
        this.parameters = parameters;
    }

    @Override
    public boolean isValid()
    {
        return true;
    }

    @Override
    public int getFuValue()
    {
        if (isSevenPairs())
        {
            return 25;
        }
        else if (isClosedRon())
        {
            return 30;
        }
        return 20;
    }

    private boolean isClosedRon()
    {
        return hand.isClosed() && parameters.doesPlayerWinOnRon();
    }

    private boolean isSevenPairs()
    {
        Chiitoitsu chiitoitsu = new Chiitoitsu(hand, groups);
        return chiitoitsu.isValid();
    }
}
