package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.utils.TileGroup;

import java.util.ArrayList;
import java.util.List;

public class Chiitoitsu extends GroupBasedYaku
{
    public Chiitoitsu(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid()
    {
        ArrayList<MahjongTileKind> knownTileKinds = new ArrayList<>();
        for (TileGroup group : groups)
        {
            if (!group.isPair())
            {
                return false;
            }
            else
            {
                MahjongTileKind tileKind = group.getTileKindAt(0);
                if (knownTileKinds.contains(tileKind))
                {
                    return false;
                }
                knownTileKinds.add(tileKind);
            }
        }
        return true;
    }

    @Override
    public int getHanValue()
    {
        return 2;
    }
}
