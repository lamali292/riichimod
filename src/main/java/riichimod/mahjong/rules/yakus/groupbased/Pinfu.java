package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.Hand;
import riichimod.mahjong.rules.utils.Seat;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;

import java.util.ArrayList;
import java.util.List;

public class Pinfu extends GroupBasedYaku
{
    public Pinfu(Hand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        ArrayList<TileGroup> groupsThatContainTheWinningTile = new ArrayList<TileGroup>();
        MahjongTileKind winningTile = hand.getWinningTile();
        TileGroup pair = null;

        for (TileGroup group : groups)
        {
            if (group.isPair())
            {
                if (pair != null)
                {
                    return false;
                }
                // else
                pair = group;
            }
            else if (group.isRun())
            {
                if (group.contains(tile -> tile.equals(winningTile)))
                {
                    groupsThatContainTheWinningTile.add(group);
                }
            }
            else
            {
                return false;
            }
        }

        boolean winningTileIsRyanmen = false;
        for (TileGroup candidateGroup : groupsThatContainTheWinningTile)
        {
            List<Integer> groupIndices = new ArrayList<>(candidateGroup.getIndices());
            groupIndices.remove(Integer.valueOf(winningTile.getIndex()));
            if (groupIndices.size() == 2)
            {
                winningTileIsRyanmen |= new TileGroup(groupIndices.get(0).intValue(), groupIndices.get(1).intValue()).isDoubleSidedBlock();
            }
        }
        if (!winningTileIsRyanmen)
        {
            return false;
        }

        // verify whether the pair gives minipoints
        if (pair == null)
        {
            return false;
        }
        MahjongTileKind pairKind = MahjongTileKind.getKindFromIndex(pair.getIndices().get(0));
        if (pairKind.isDragon())
        {
            return false;
        }
        if (pairKind.isWind())
        {
            Seat pairSeat = Seat.getSeatFromTileKind(pairKind);
            if (hand.isTableWind(pairSeat))
            {
                return false;
            }
            return !hand.isSeatWind(pairSeat);
        }

        return true;
    }

    @Override
    public int getHanValue()
    {
        return 1;
    }
}
