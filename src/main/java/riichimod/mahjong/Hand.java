package riichimod.mahjong;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.BobEffect;
import riichimod.select.Selectable;
import riichimod.select.SelectableHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Hand implements SelectableHolder {

    public Vector2 pos = new Vector2();
    public int maxHandSize = 13;
    int selected = 0;

    private ArrayList<Tile> tiles;
    private final List<TileSlot> slots;
    public static final int cardHeight = 80;
    public static final int cardWidth = 60;


    public Hand() {
        slots = new ArrayList<>();
        tiles = new ArrayList<>();
    }

    public int getSize() {
        return tiles.size();
    }

    public void add(Tile tile) {
        tiles.add(tile);
    }

    public void draw(RiichiDeck deck, int nr) {
        for (int i = 0; i < nr; i++) {
            add(deck.draw());
        }
        genMissingSlots();
    }


    public void render(SpriteBatch sb) {
        slots.forEach((s)->s.render(sb));
    }

    public void update() {
        slots.forEach(TileSlot::update);
    }

    @Override
    public int selected() {
        return selected;
    }

    @Override
    public List<? extends Selectable> getSelectables() {
        return slots;
    }

    @Override
    public void select(int id) {
        if (!slots.get(id).selected) {
            slots.get(id).selected = true;
            selected++;
        }
    }

    @Override
    public boolean isSelected(int id) {
        return slots.get(id).selected;
    }

    @Override
    public void deselect(int id) {
        if (slots.get(id).selected) {
            slots.get(id).selected = false;
            selected--;
        }
    }

    public void addNewSlot() {
        int size = slots.size();
        float overMax = (size >= getMaxHandSize()) ? 23F : 0F;
        Vector2 vec = new Vector2((pos.x + overMax + 75F*size) * Settings.xScale, pos.y * Settings.yScale);
        BobEffect bob = new BobEffect();
        Hitbox hb = new Hitbox(vec.x+8F* Settings.xScale, vec.y + bob.y +55F* Settings.yScale, cardWidth * Settings.scale, cardHeight * Settings.scale);
        slots.add(new TileSlot(this,slots.size(),vec,bob,hb));
    }

    public void genMissingSlots() {
        for (int i = slots.size(); i < getSize(); i++) {
            addNewSlot();
        }
    }
    public int getMaxHandSize() {
        return maxHandSize;
    }

    public List<Tile> getSelectedTiles() {
        return slots.stream().filter(s->s.selected).map(Slot::getID).map(tiles::get).collect(Collectors.toList());
    }

    public void resetSelected() {
        slots.forEach(s->s.selected = false);
        selected = 0;
    }

    public void discardSelected() {
        List<Tile> discards = getSelectedTiles();
        resetSelected();
        discard(discards);
        clearAndSort();
    }

    public void discard(List<Tile> discards) {
        discards.stream().filter(tiles::contains).forEach(tiles::remove);
    }

    public void sort() {
        Collections.sort(tiles);
    }

    public void clearAndSort() {
        clear();
        sort();
    }

    public void clear() {
        slots.subList(getSize(), slots.size()).clear();
        /*
        int msize = getMaxHandSize();
        if (slots.size() > msize) {
            slots.subList(msize, slots.size()).clear();
        }
         */
    }

    public void clearWithTiles() {
        int msize = getMaxHandSize();
        if (slots.size() > msize) {
            tiles.subList(msize, tiles.size()).clear();
            slots.subList(msize, slots.size()).clear();
        }
    }


    public List<Tile> getTiles()    {
        return tiles;
    }

    public void setTiles(List<Tile> tile)    {
        tiles = new ArrayList<>(tile);
    }




}
