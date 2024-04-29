package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.rules.utils.TileGroup;

import java.util.ArrayList;
import java.util.List;

public class Iipeikou extends GroupBasedYaku
{
    public Iipeikou(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        if (hand.isClosed())
        {
            List<TileGroup> sequencesFound = new ArrayList<>();
            List<TileGroup> matchesFound = new ArrayList<>(); // a match found is any sequence that is present at least twice

            for (TileGroup group : groups)
            {
                if (group.isRun())
                {
                    if (sequencesFound.contains(group) && !matchesFound.contains(group))
                    {
                        matchesFound.add(group);
                    }
                    else
                    {
                        sequencesFound.add(group);
                    }
                }
            }

            return matchesFound.size() == 1;
        }

        return false;
    }

    @Override
    public int getHanValue()
    {
        return 1;
    }
}
