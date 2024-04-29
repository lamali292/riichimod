package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.rules.utils.TileGroup;
import riichimod.mahjong.rules.yakus.Yaku;

import java.util.List;

public abstract class GroupBasedYaku implements Yaku
{
    protected PlayerHand hand;
    protected List<TileGroup> groups;

    public GroupBasedYaku(PlayerHand hand, List<TileGroup> groups)
    {
        this.hand = hand;
        this.groups = groups;
    }
}
