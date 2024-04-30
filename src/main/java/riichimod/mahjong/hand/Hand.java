package riichimod.mahjong.hand;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.BobEffect;
import riichimod.mahjong.RiichiDeck;
import riichimod.mahjong.slot.Slot;
import riichimod.mahjong.utils.Tile;
import riichimod.mahjong.slot.TileSlot;
import riichimod.select.Renderable;
import riichimod.select.Selectable;
import riichimod.select.SelectableHolder;
import riichimod.select.Updatable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Hand implements SelectableHolder, Updatable, Renderable {

    public Vector2 pos = new Vector2();
    public int maxHandSize = 13;
    int selected = 0;

    public final List<TileSlot> slots;
    public static final int cardHeight = 80;
    public static final int cardWidth = 60;


    public Hand() {
        slots = new ArrayList<>();
    }

    public int getSize() {
        return slots.size();
    }

    public void add(Tile tile) {
        addNewSlot(tile);
    }

    public void draw(RiichiDeck deck, int nr) {
        for (int i = 0; i < nr; i++) {
            add(deck.draw());
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        slots.forEach((s)->s.render(sb));
    }

    @Override
    public void update() {
        slots.forEach(Slot::update);
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
        if (!slots.get(id).isSelected()) {
            slots.get(id).setSelected(true);
            selected++;
        }
    }

    @Override
    public boolean isSelected(int id) {
        return slots.get(id).isSelected();
    }

    @Override
    public void deselect(int id) {
        if (slots.get(id).isSelected()) {
            slots.get(id).setSelected(false);
            selected--;
        }
    }

    public int getID(Selectable selectable) {
        return slots.indexOf((TileSlot)selectable);
    }

    public void addNewSlot(Tile tile) {
        int size = slots.size();
        float overMax = (size >= getMaxHandSize()) ? 23F : 0F;
        Vector2 vec = new Vector2((pos.x + overMax + 75F*size) * Settings.xScale, pos.y * Settings.yScale);
        BobEffect bob = new BobEffect();
        Hitbox hb = new Hitbox(vec.x+8F* Settings.xScale, vec.y + bob.y +55F* Settings.yScale, cardWidth * Settings.scale, cardHeight * Settings.scale);
        slots.add(new TileSlot(this,tile,vec,bob,hb));
    }

    public int getMaxHandSize() {
        return maxHandSize;
    }

    public List<Tile> getSelectedTiles() {
        return slots.stream().filter(Selectable::isSelected).map(s->(Tile)s.getHoldable()).collect(Collectors.toList());
    }

    @Override
    public List<Slot> getSelected() {
        return slots.stream().filter(Selectable::isSelected).collect(Collectors.toList());
    }

    public void resetSelected() {
        slots.forEach(s->s.setSelected(false));
        selected = 0;
    }

    public void discardSelected() {
        List<Tile> discards = getSelectedTiles();
        resetSelected();
        discard(discards);
        clearAndSort();
    }

    public void discard(List<Tile> discards) {
        List<Tile> tiles = getTiles();
        discards.stream().filter(tiles::contains).forEach(tiles::remove);
        setTiles(tiles);
    }

    public void sort() {
        setTiles(getTiles().stream().sorted().collect(Collectors.toList()));
    }

    public void clearAndSort() {
        clear();
        sort();
    }

    public void clear() {
        int msize = getMaxHandSize();
        if (slots.size() > msize) {
            slots.subList(msize, slots.size()).clear();
        }
    }

    public List<Tile> getTiles()    {
        return slots.stream().map(s->(Tile)s.getHoldable()).collect(Collectors.toList());
    }

    public void setTiles(List<Tile> tiles) {
        for (int i = 0; i < tiles.size(); i++) {
            if (i >= slots.size()) addNewSlot(tiles.get(i));
            else slots.get(i).setHoldable(tiles.get(i));
        }
        if (tiles.size() < slots.size()) {
            slots.subList(tiles.size(), slots.size()).clear();
        }

    }




}
