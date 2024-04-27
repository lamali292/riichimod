package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.shanten.parsing.TileFamily;
import riichimod.mahjong.rules.shanten.Pair;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

public class ChuurenPoutou extends GroupBasedYaku
{
    public ChuurenPoutou(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        if (!hand.getMelds().isEmpty())
        {
            return false;
        }

        TileFamily family = groups.get(0).getTileKindAt(0).getFamily();
        if (!family.isNumeral())
        {
            return false;
        }

        int[] amountOfEachNumber = new int[9];
        for (TileGroup group : groups)
        {
            for (MahjongTileKind kind : group.getTileKinds())
            {
                if (kind.getFamily() != family)
                {
                    return false;
                }

                int currentNumber = kind.getTileNumber();
                amountOfEachNumber[currentNumber - 1]++;
            }
        }

        BiPredicate<Integer, Integer> verifyAmountOfTilesPerNumber = (index, count) -> amountOfEachNumber[index] == count || amountOfEachNumber[index] == count + 1;

        List<Pair<Integer, Integer>> amountPerNumber = setAmountOfTilesRequiredPerNumber();
        for (Pair<Integer, Integer> amountForCurrentNumber : amountPerNumber)
        {
            if (!verifyAmountOfTilesPerNumber.test(amountForCurrentNumber.getFirst(), amountForCurrentNumber.getSecond()))
            {
                return false;
            }
        }

        return Arrays.stream(amountOfEachNumber).sum() == 14;
    }

    private List<Pair<Integer, Integer>> setAmountOfTilesRequiredPerNumber()
    {
        ArrayList<Pair<Integer, Integer>> list = new ArrayList<>();
        list.add(new Pair<>(0, 3));
        list.add(new Pair<>(1, 1));
        list.add(new Pair<>(2, 1));
        list.add(new Pair<>(3, 1));
        list.add(new Pair<>(4, 1));
        list.add(new Pair<>(5, 1));
        list.add(new Pair<>(6, 1));
        list.add(new Pair<>(7, 1));
        list.add(new Pair<>(8, 3));
        return list;
    }

    @Override
    public int getHanValue()
    {
        MahjongTileKind winningTile = hand.getWinningTile();
        if (winningTile != null && winningTile.isNumeral())
        {
            int tileCount = (int) groups.stream().map(TileGroup::getTileKinds).flatMap(List::stream).filter(kind -> kind.getTileNumber() == winningTile.getTileNumber()).count();
            if (winningTile.getTileNumber() == 1 || winningTile.getTileNumber() == 9)
            {
                return tileCount == 4 ? 26 : 13;
            }
            else
            {
                return tileCount == 2 ? 26 : 13;
            }
        }
        return 13;
    }
}