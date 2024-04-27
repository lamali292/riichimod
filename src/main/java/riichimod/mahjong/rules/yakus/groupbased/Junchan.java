package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;

import java.util.List;

public class Junchan extends GroupBasedYaku
{
    public Junchan(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        boolean nonTerminalFound = false;

        for (TileGroup group : groups)
        {
            if (group.contains(MahjongTileKind::isTerminal))
            {
                if (group.isRun())
                {
                    nonTerminalFound = true;
                }
            }
            else
            {
                return false;
            }
        }

        return nonTerminalFound;
    }

    @Override
    public int getHanValue()
    {
        return hand.isClosed() ? 3 : 2;
    }
}