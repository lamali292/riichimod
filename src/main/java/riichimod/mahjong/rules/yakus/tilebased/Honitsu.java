package riichimod.mahjong.rules.yakus.tilebased;

import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.utils.Tile;
import riichimod.mahjong.utils.TileFamily;
import riichimod.mahjong.utils.MahjongTileKind;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class Honitsu extends TileBasedYaku
{
    public Honitsu(PlayerHand hand)
    {
        super(hand);
    }

    @Override
    public boolean isValid() {
        Supplier<Stream<TileFamily>> supplier = () -> hand.getTiles().stream().map(Tile::getTileKind).map(MahjongTileKind::getFamily);
        return supplier.get().anyMatch(TileFamily.HONOURS::equals) && supplier.get().anyMatch(TileFamily::isNumeral) && supplier.get().distinct().count() == 2;
    }

    @Override
    public int getHanValue()
    {
        return hand.isOpen() ? 2 : 3;
    }
}
