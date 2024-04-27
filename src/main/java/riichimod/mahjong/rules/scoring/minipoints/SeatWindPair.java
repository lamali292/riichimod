package riichimod.mahjong.rules.scoring.minipoints;

import riichimod.mahjong.Hand;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;
import riichimod.mahjong.rules.utils.Seat;

import java.util.List;

public class SeatWindPair implements Fu
{
    private Hand hand;
    private List<TileGroup> groups;

    public SeatWindPair(Hand hand, List<TileGroup> groups)
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
            MahjongTileKind seatWindTile = Seat.getTileKindFromSeat(hand.getSeatWind());
            if (group.isPair() && group.getTileKindAt(0).equals(seatWindTile))
            {
                return true;
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
