package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.Hand;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;

import java.util.List;

public class Chanta extends GroupBasedYaku
{
    public Chanta(Hand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        boolean honourFound = false;
        boolean nonTerminalFound = false;

        for (TileGroup group : groups)
        {
            if (group.contains(MahjongTileKind::isHonour))
            {
                honourFound = true;
            }
            else
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
        }

        return honourFound && nonTerminalFound;
    }

    @Override
    public int getHanValue()
    {
        return hand.isClosed() ? 2 : 1;
    }
}
