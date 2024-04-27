package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.Hand;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;
import riichimod.mahjong.rules.yakus.Yaku;

import java.util.List;

public abstract class GroupBasedYaku implements Yaku
{
    protected Hand hand;
    protected List<TileGroup> groups;

    public GroupBasedYaku(Hand hand, List<TileGroup> groups)
    {
        this.hand = hand;
        this.groups = groups;
    }
}
