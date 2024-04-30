package riichimod.mahjong.utils;

import com.badlogic.gdx.graphics.Texture;
import riichimod.mahjong.RiichiDeck;
import riichimod.select.Holdable;

public class Tile implements Comparable<Tile>, Holdable {

    private MahjongTileKind tileKind;
    private boolean red; // red/special tile

    public Tile(MahjongTileKind tileKind) {
        this.tileKind = tileKind;
    }

    public Texture getTexture() {
        return RiichiDeck.textures.get(tileKind.getIndex());
    }

    public int getId() {
        return tileKind.getIndex();
    }

    public MahjongTileKind getTileKind()
    {
        return tileKind;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red)
    {
        this.red = red;
    }

    @Override
    public int compareTo(Tile o) {
        return this.tileKind.getIndex() - o.tileKind.getIndex();
    }


    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Tile)) {
            return false;
        }
        Tile otherTile = (Tile) other;
        return tileKind == otherTile.tileKind && red == otherTile.red;
    }

    @Override
    public int hashCode()
    {
        int prime = 13;
        prime = 31 * prime + tileKind.hashCode();
        prime = 31 * prime + (red ? 1 : 0);
        return prime;
    }
}
