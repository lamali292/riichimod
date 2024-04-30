package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.utils.TileGroup;

import java.util.ArrayList;
import java.util.List;

public class Ryanpeikou extends GroupBasedYaku
{
    public Ryanpeikou(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        if (hand.isClosed())
        {
            ArrayList<TileGroup> sequencesFound = new ArrayList<>();
            ArrayList<TileGroup> matchesFound = new ArrayList<>(); // a match found is any sequence that is present at least twice

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

            return matchesFound.size() == 2;
        }

        return false;
    }

    @Override
    public int getHanValue()
    {
        return 3;
    }
}
