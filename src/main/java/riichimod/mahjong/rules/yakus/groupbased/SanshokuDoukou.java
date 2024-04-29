package riichimod.mahjong.rules.yakus.groupbased;

import riichimod.Counter;
import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.rules.utils.TileGroup;
import riichimod.mahjong.rules.utils.MahjongTileKind;

import java.util.List;

public class SanshokuDoukou extends GroupBasedYaku {

    public SanshokuDoukou(PlayerHand hand, List<TileGroup> groups)
    {
        super(hand, groups);
    }

    @Override
    public boolean isValid() {
        Counter<Integer> counter = new Counter<>();
        groups.stream()
                .filter(TileGroup::isCompleteExclusiveGroup)
                .map(TileGroup::getFirstTileKind)
                .filter(MahjongTileKind::isNumeral)
                .map(MahjongTileKind::getTileNumber)
                .forEach(counter::inc);
        return counter.stream().anyMatch(t -> t >= 3);
    }

    @Override
    public int getHanValue() {
        return 2;
    }
}
