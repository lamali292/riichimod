package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.utils.TileGroup;
import riichimod.mahjong.rules.yakus.Yaku;

import java.util.List;

public abstract class GroupBasedYaku implements Yaku
{
    protected final PlayerHand hand;
    protected final List<TileGroup> groups;

    public GroupBasedYaku(PlayerHand hand, List<TileGroup> groups)
    {
        this.hand = hand;
        this.groups = groups;
    }
}
