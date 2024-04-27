package riichimod.mahjong.rules.shanten.parsing;

import riichimod.mahjong.rules.utils.MahjongTileKind;

import java.util.Comparator;

public class MahjongTileKindComparator implements Comparator<MahjongTileKind>
{
    @Override
    public int compare(MahjongTileKind firstTile, MahjongTileKind secondTile)
    {
        if (firstTile.equals(secondTile))
        {
            return 0;
        }
        return firstTile.ordinal() - secondTile.ordinal();
    }
}
