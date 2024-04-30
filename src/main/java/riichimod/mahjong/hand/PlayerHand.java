package riichimod.mahjong.hand;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.BobEffect;
import riichimod.mahjong.slot.MeldSlot;
import riichimod.mahjong.RiichiCalculator;
import riichimod.mahjong.RiichiDeck;
import riichimod.mahjong.utils.Tile;
import riichimod.mahjong.utils.TileGroup;
import riichimod.mahjong.utils.MahjongTileKind;
import riichimod.mahjong.rules.yakus.Yaku;
import riichimod.mahjong.rules.scoring.RiichiScoringParametersImpl;
import riichimod.mahjong.utils.Seat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerHand extends Hand { // called tiles, they are not removed from hand
    public final List<MeldSlot> meldSlots;
    private Seat tableWind;
    private Seat seatWind;

    public PlayerHand() {
        super();
        meldSlots = new ArrayList<>();
        pos = new Vector2(177F,650f);
    }

    public PlayerHand(List<Tile> tile) {
        this();
        getTiles().addAll(tile);
    }

    public int getSize() {
        return getTiles().size();
    }

    public void addNewMeldSlot(TileGroup tileGroup) {
        BobEffect bob = new BobEffect();
        Vector2 vec = new Vector2((pos.x + 75F*getMeldedTilesCount()+ 23F*meldSlots.size()) * Settings.xScale, (pos.y+150F) * Settings.yScale);
        meldSlots.add(new MeldSlot(this, tileGroup, vec, bob));
    }

    @Override
    public int getMaxHandSize() {
        return maxHandSize - meldSlots.size() * 3;
    }

    public void addMeld(TileGroup tileGroup, RiichiDeck deck) {
        addNewMeldSlot(tileGroup);
        discard(tileGroup.getTileKinds().stream().map(Tile::new).collect(Collectors.toList()));
        if (tileGroup.isQuad()) {
            draw(deck, 1);
        }
        //clearAndSort();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        meldSlots.forEach((s)->s.render(sb));
    }

    @Override
    public void update() {
        super.update();
        meldSlots.forEach(MeldSlot::update);
    }


    public List<TileGroup> getMelds()  {
        return meldSlots.stream().map(t->(TileGroup) t.getHoldable()).collect(Collectors.toList());
    }

    public List<Tile> getOpenTiles() {
        return getTiles();
    }

    public int getMeldedTilesCount() {
        return getMelds().stream().mapToInt(TileGroup::getSize).sum();
    }

    public boolean isOpen() {
        return !isClosed();
    }

    public boolean isClosed() {
        return meldSlots.isEmpty();
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

    public boolean isTableWind(Seat seatFromTileKind) {
        return tableWind == seatFromTileKind;
    }

    public boolean isSeatWind(Seat seatFromTileKind) {
        return seatWind == seatFromTileKind;
    }

    public MahjongTileKind getWinningTile() {
        return null;
    }

    public Seat getSeatWind() {
        return seatWind;
    }
}
