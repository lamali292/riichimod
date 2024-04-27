package riichimod.mahjong;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.BobEffect;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.yakus.Yaku;
import riichimod.mahjong.rules.scoring.RiichiScoringParametersImpl;
import riichimod.mahjong.rules.utils.Seat;
import riichimod.select.SelectableHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Hand implements SelectableHolder {
    public ArrayList<Tile> tiles;
    public List<HandSlot> slots;
    public List<TileGroup> melds; // called tiles, they are not removed from hand
    public List<MeldSlot> meldSlots;
    public int selected = 0;

    public int maxHandSize = 13;

    public static final int cardHeight = 80;
    public static final int cardWidth = 60;

    private List<Tile> bonus; // flowers, peis, etc.
    private List<Seat> tableWinds;
    private Seat seatWind;

    private MahjongTileKind winningTile;

    public Hand() {
        slots = new ArrayList<>();
        meldSlots = new ArrayList<>();
        tiles = new ArrayList<>();
        melds = new ArrayList<>();
        bonus = new ArrayList<>();

        tableWinds = new ArrayList<>();
    }

    public Hand(List<Tile> tile) {
        this();
        tiles.addAll(tile);
    }

    public void addNewSlot() {
        int size = slots.size();
        float overMax = (size >= getMaxHandSize()) ? 23F : 0F;
        Vector2 vec = new Vector2((177F + overMax + 75F*size) * Settings.xScale, 650f * Settings.yScale);
        BobEffect bob = new BobEffect();
        Hitbox hb = new Hitbox(vec.x+8F* Settings.xScale, vec.y + bob.y +55F* Settings.yScale, cardWidth * Settings.scale, cardHeight * Settings.scale);
        slots.add(new HandSlot(this,slots.size(),vec,bob,hb));
    }

    public void addNewMeldSlot() {
        BobEffect bob = new BobEffect();
        Vector2 vec = new Vector2((177F + 75F*getMeldedTilesCount()+ 23F*melds.size()) * Settings.xScale, 800f * Settings.yScale);
        meldSlots.add(new MeldSlot(this, meldSlots.size(), vec, bob));
    }

    public void add(Tile tile) {
        tiles.add(tile);
    }

    public void genSlots() {
        for (int i = slots.size(); i < getUnmeldedTilesCount(); i++) {
            addNewSlot();
        }
    }

    public void sort() {
        Collections.sort(tiles);
    }

    public void sortAndClear() {
        sort();
        int msize = getMaxHandSize();
        if (slots.size() > msize) {
            slots.subList(msize, slots.size()).clear();
        }
    }

    public int getMaxHandSize() {
        return maxHandSize - melds.size() * 3;
    }

    public void draw(RiichiDeck deck, int nr) {
        for (int i = 0; i < nr; i++) {
            add(deck.draw());
        }
        genSlots();
    }

    public void addMeld(TileGroup tileGroup, RiichiDeck deck) {
        addNewMeldSlot();
        addMeld(tileGroup);
        if (tileGroup.isQuad()) {
            draw(deck, 1);
        }
        sortAndClear();
    }

    public void render(SpriteBatch sb) {
        slots.forEach((s)->s.render(sb));
        meldSlots.forEach((s)->s.render(sb));
    }

    public void update() {
        slots.forEach(HandSlot::update);
        meldSlots.forEach(MeldSlot::update);
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

    public void discardSelected() {
        List<Tile> unmelded = getUnmeldedTiles();
        List<Tile> discards = slots.stream().filter(s->s.selected).map(Slot::getID).map(unmelded::get).collect(Collectors.toList());
        slots.stream().filter(s->s.selected).forEach(s->s.selected = false);
        discard(discards);
        selected = 0;
        sortAndClear();
    }

    public void discard(List<Tile> discards) {
        List<Tile> unmelded = getUnmeldedTiles();
        discards.stream().filter(unmelded::contains).forEach(tiles::remove);
    }

    public List<Tile> getTiles()    {
        return tiles;
    }

    public void setTiles(List<Tile> tile)    {
        tiles = new ArrayList<>(tile);
    }

    public List<TileGroup> getMelds()  {
        return melds;
    }

    public void addMeld(TileGroup meld) {
        melds.add(meld);
    }

    public List<Tile> getUnmeldedTiles() {
        List<Tile> unmeldedTiles = new ArrayList<>(getTiles());
        List<Tile> meldedTiles = getMelds().stream().map(TileGroup::getTileKinds).flatMap(List::stream).map(Tile::new).collect(Collectors.toList());
        meldedTiles.forEach(unmeldedTiles::remove);
        return unmeldedTiles;
    }

    public int getUnmeldedTilesCount() {
        return tiles.size() - getMeldedTilesCount();
    }

    public int getMeldedTilesCount() {
        return getMelds().stream().mapToInt(TileGroup::getSize).sum();
    }

    public MahjongTileKind getWinningTile()
    {
        return winningTile;
    }

    public void setWinningTile(MahjongTileKind winningTile) {
        this.winningTile = winningTile;
    }

    public boolean isOpen() {
        return !isClosed();
    }

    public boolean isClosed() {
        return melds.isEmpty();
    }

    public void addTableWind(Seat seat) {
        tableWinds.add(seat);
    }

    public Seat getSeatWind() {
        return seatWind;
    }

    public void setSeatWind(Seat seat) {
        seatWind = seat;
    }

    public boolean isTableWind(Seat seat) {
        return tableWinds.contains(seat);
    }

    public boolean isSeatWind(Seat seat) {
        return seatWind == seat;
    }

    public void calcYaku() {
        RiichiScoringParametersImpl para = new RiichiScoringParametersImpl(Seat.EAST);
        List<Yaku> yakus = RiichiCalculator.getYakus(this, para);
        if (!yakus.isEmpty()) {
            System.out.println(yakus.stream().map((y) -> y.getClass().getSimpleName()).collect(Collectors.toList()));
        } else {
            System.out.println("No Yaku!");
        }
    }


    @Override
    public int selected() {
        return selected;
    }

    @Override
    public List<HandSlot> getSelectables() {
        return slots;
    }
}
