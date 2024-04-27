package riichimod;

import org.junit.jupiter.api.Test;
import riichimod.mahjong.Hand;
import riichimod.mahjong.RiichiCalculator;
import riichimod.mahjong.Tile;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.yakus.Yaku;
import riichimod.mahjong.rules.scoring.RiichiScoringParametersImpl;
import riichimod.mahjong.rules.utils.Seat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RiichiCalculatorTest {

    @Test
    void calcTileGroupsTest() {
        List<MahjongTileKind> tiles = genMahjongTiles(0,0,1,1,2,2,9,9,10,10,11,11,30,30);
        List<List<TileGroup>> groups = RiichiCalculator.calcTileGroups(tiles);
        for (List<TileGroup> group : groups) {
            System.out.println(group);
        }
    }

    @Test
    public void calcYakuTest() {
        List<MahjongTileKind> mtiles = genMahjongTiles(0,0,1,1,2,2,9,9,10,10,11,11,30,30);
        List<Tile> tiles = mtiles.stream().map(Tile::new).collect(Collectors.toList());
        Hand hand = new Hand(tiles);
        RiichiScoringParametersImpl para = new RiichiScoringParametersImpl(Seat.EAST);
        List<Yaku> yakus = RiichiCalculator.getYakus(hand, para);
        if (!yakus.isEmpty()) {
            System.out.println(yakus.stream().map((y) -> y.getClass().getSimpleName()).collect(Collectors.toList()));
        }else {
            System.out.println("No Yaku!");
        }

    }

    @Test
    void calcNumberGroupsTest() {
        List<MahjongTileKind> tiles = genMahjongTiles(0,0,1,1,2,2);
        List<List<TileGroup>> groups = RiichiCalculator.calcNumberGroups(tiles);
        for (List<TileGroup> group : groups) {
            System.out.println(group);
        }
    }

    @Test
    void getListWithoutGroupTest() {
        List<MahjongTileKind> assertTiles = genMahjongTiles(0,2,2,3);
        List<MahjongTileKind> tiles = genMahjongTiles(0,0,1,2,2,2,3);
        TileGroup group = new TileGroup(0,1,2);
        List<MahjongTileKind> newList = RiichiCalculator.getListWithoutGroup(tiles, group);
        assertEquals(assertTiles, newList);
    }

    private List<MahjongTileKind> genMahjongTiles(int... indices) {
        List<MahjongTileKind> tiles = new ArrayList<>();
        for (int index : indices) {
            tiles.add(MahjongTileKind.getKindFromIndex(index));
        }
        return tiles;
    }
}