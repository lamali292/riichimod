package riichimod.mahjong.rules.scoring.minipoints;

import riichimod.mahjong.Hand;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;
import riichimod.mahjong.rules.utils.Seat;

import java.util.List;

public class TableWindPair implements Fu
{
    private Hand hand;
    private List<TileGroup> groups;

    public TableWindPair(Hand hand, List<TileGroup> groups)
    {
        this.hand = hand;
        this.groups = groups;
    }

    @Override
    public boolean isValid()
    {
        if (groups.size() == 7)
        {
            return false;
        }

        for (TileGroup group : groups)
        {
            if (group.isPair() && group.getTileKindAt(0).isWind())
            {
                MahjongTileKind windTile = group.getTileKindAt(0);
                Seat wind = Seat.getSeatFromTileKind(windTile);
                if (hand.isTableWind(wind))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getFuValue()
    {
        return 2;
    }

}
