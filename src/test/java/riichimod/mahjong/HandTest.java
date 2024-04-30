package riichimod.mahjong;

import org.junit.jupiter.api.Test;
import riichimod.mahjong.utils.TileGroup;
import riichimod.mahjong.utils.MahjongTileKind;

import java.util.ArrayList;
import java.util.List;

class HandTest {

    @Test
    void getAllTileGroupsTest() {
        System.out.println(groupsOf(0,0,1,1,2,2,9,9,10,10,11,11,30,30));
        System.out.println(groupsOf(0,0,0,0,0,0,0,0,1,1,1,1));
        System.out.println(groupsOf(0,0,0,1,1,2,2,2,9,9,10,10,11,11,30));
        System.out.println(groupsOf(0,1,2,3,4,5,6,7,8,9,10,11));
    }

    private List<TileGroup> groupsOf(int... tileIDs) {
        List<MahjongTileKind> tiles = genMahjongTiles(tileIDs);
        return RiichiCalculator.getAllTileGroups(tiles);
    }

    private List<MahjongTileKind> genMahjongTiles(int... indices) {
        List<MahjongTileKind> tiles = new ArrayList<>();
        for (int index : indices) {
            tiles.add(MahjongTileKind.getKindFromIndex(index));
        }
        return tiles;
    }
}