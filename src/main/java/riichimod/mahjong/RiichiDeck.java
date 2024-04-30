package riichimod.mahjong;

import com.badlogic.gdx.graphics.Texture;
import riichimod.mahjong.utils.MahjongTileKind;
import riichimod.game.util.TextureLoader;
import riichimod.mahjong.utils.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class RiichiDeck {
    public static Texture front;
    public static Texture back;
    public static Texture sequenceSlot;
    public static Texture meldSlot3;
    public static Texture meldSlot4;
    public static ArrayList<Tile> tiles;
    public static ArrayList<Texture> textures;
    public static void init() {
        front = TextureLoader.getTexture("riichimod/images/tiles/Front.png");
        back = TextureLoader.getTexture("riichimod/images/tiles/Back.png");
        sequenceSlot = TextureLoader.getTexture("riichimod/images/ui/sequenceSlot.png");
        meldSlot3 = TextureLoader.getTexture("riichimod/images/ui/meldSlot.png");
        meldSlot4 = TextureLoader.getTexture("riichimod/images/ui/meldSlotKan.png");
        tiles = new ArrayList<>();
        textures = new ArrayList<>();
        for (MahjongTileKind kind : MahjongTileKind.values()) {
            textures.add(new Texture("riichimod/images/tiles/"+kind.abbreviation+".png"));
            tiles.add(new Tile(kind));
        }
    }

    public Stack<Tile> deck;
    public void reshuffle() {
        deck = new Stack<>();
        for (int i = 0; i < 4; i++) {
            deck.addAll(tiles);
        }
        Collections.shuffle(deck);
    }

    public Tile draw() {
        return deck.pop();
    }

    public RiichiDeck() {
        reshuffle();
    }
}
