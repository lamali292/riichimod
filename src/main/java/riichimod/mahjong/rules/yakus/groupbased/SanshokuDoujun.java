package riichimod.mahjong.rules.yakus.groupbased;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.rules.utils.TileFamily;
import riichimod.mahjong.rules.utils.TileGroup;

public class SanshokuDoujun extends GroupBasedYaku
{
    public SanshokuDoujun(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        ArrayList<TileGroup> runsCatalog = new ArrayList<>();

        for (TileGroup group : groups)
        {
            if (group.isRun())
            {
                runsCatalog.add(group);
            }
        }

        while (runsCatalog.size() >= 3)
        {
            // pop first run
            TileGroup firstRun = runsCatalog.remove(0);
            HashSet<TileFamily> families = new HashSet<>();
            families.add(firstRun.getTileKindAt(0).getFamily());

            // test against other runs
            for (TileGroup group : runsCatalog)
            {
                if (group.getTileNumbers().equals(firstRun.getTileNumbers()))
                {
                    families.add(group.getTileKindAt(0).getFamily());
                }
            }

            if (families.size() == 3)
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getHanValue()
    {
        return hand.isClosed() ? 2 : 1;
    }
}
