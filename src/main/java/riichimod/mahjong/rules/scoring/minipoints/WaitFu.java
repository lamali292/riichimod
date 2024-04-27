package riichimod.mahjong.rules.scoring.minipoints;

import riichimod.mahjong.Hand;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;

import java.util.List;

public class WaitFu implements Fu
{
    private Hand hand;
    private List<TileGroup> tileGroups;
    private TileGroup winningGroup;

    public WaitFu(Hand hand, List<TileGroup> tileGroups, TileGroup winningGroup)
    {
        this.hand = hand;
        this.tileGroups = tileGroups;
        this.winningGroup = winningGroup;
    }

    @Override
    public boolean isValid()
    {
        if (isChiitoitsu() || isKokushi())
        {
            return false;
        }

        TileGroup tileGroupBeforeWinning = getWinningTileGroupWithoutWinningTile();

        if (tileGroupBeforeWinning.getSize() == 1) // tanki wait
        {
            return true;
        }

        return tileGroupBeforeWinning.isEndBlock() || tileGroupBeforeWinning.isInsideBlock();
    }

    private boolean isKokushi()
    {
        return tileGroups.size() == 13;
    }

    private boolean isChiitoitsu()
    {
        return tileGroups.size() == 7;
    }

    private TileGroup getWinningTileGroupWithoutWinningTile()
    {
        MahjongTileKind winningTile = hand.getWinningTile();

        List<Integer> groupIndices = winningGroup.getIndices();
        groupIndices.remove(Integer.valueOf(winningTile.getIndex()));

        return new TileGroup(groupIndices);
    }

    @Override
    public int getFuValue()
    {
        return 2;
    }

}
