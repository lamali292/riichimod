package riichimod.mahjong.rules.yakus.tilebased;

import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.Tile;
import riichimod.mahjong.rules.utils.TileFamily;
import riichimod.mahjong.rules.utils.MahjongTileKind;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class Chinitsu extends TileBasedYaku
{
    public Chinitsu(PlayerHand hand)
    {
        super(hand);
    }

    @Override
    public boolean isValid() {
        Supplier<Stream<TileFamily>> supplier = () -> hand.getTiles().stream().map(Tile::getTileKind).map(MahjongTileKind::getFamily);
        return supplier.get().noneMatch(TileFamily.HONOURS::equals) && supplier.get().distinct().count() == 1;
    }

    @Override
    public int getHanValue()
    {
        return hand.isOpen() ? 5 : 6;
    }
}
