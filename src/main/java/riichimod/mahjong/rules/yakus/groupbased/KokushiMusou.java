package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.utils.MahjongTileKind;
import riichimod.mahjong.utils.TileGroup;

import java.util.ArrayList;
import java.util.List;

public class KokushiMusou extends GroupBasedYaku
{
    public KokushiMusou(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        ArrayList<MahjongTileKind> terminalAndHonourCatalog = new ArrayList<>(MahjongTileKind.getAllTerminalsAndHonours());
        boolean pairFound = false;

        for (TileGroup group : groups)
        {
            for (MahjongTileKind kind : group.getTileKinds())
            {
                if (kind.isNonTerminalNumeral())
                {
                    return false;
                }

                if (terminalAndHonourCatalog.contains(kind))
                {
                    terminalAndHonourCatalog.remove(kind);
                }
                else
                {
                    if (pairFound)
                    {
                        return false;
                    }
                    pairFound = true;
                }
            }
        }
        return terminalAndHonourCatalog.isEmpty() && pairFound;
    }

    @Override
    public int getHanValue()
    {
        MahjongTileKind winningTile = hand.getWinningTile();
        int tileCount = (int) groups.stream().map(TileGroup::getTileKinds).flatMap(List::stream).filter(kind -> kind == winningTile).count();
        return tileCount == 2 ? 26 : 13;
    }
}