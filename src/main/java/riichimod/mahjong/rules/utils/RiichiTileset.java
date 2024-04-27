package riichimod.mahjong.rules.utils;

import java.util.ArrayList;
import java.util.List;

public class RiichiTileset
{
    public List<MahjongTileKind> getTileList()
    {
        ArrayList<MahjongTileKind> tileList = new ArrayList<>();
        tileList.addAll(MahjongTileKind.getAllCharacters());
        tileList.addAll(MahjongTileKind.getAllCircles());
        tileList.addAll(MahjongTileKind.getAllBamboos());
        tileList.addAll(MahjongTileKind.getAllHonours());
        return tileList;
    }
}
