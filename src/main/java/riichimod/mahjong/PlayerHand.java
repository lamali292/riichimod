package riichimod.mahjong;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.BobEffect;
import riichimod.mahjong.rules.utils.TileGroup;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.yakus.Yaku;
import riichimod.mahjong.rules.scoring.RiichiScoringParametersImpl;
import riichimod.mahjong.rules.utils.Seat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerHand extends Hand {
    public List<TileGroup> melds; // called tiles, they are not removed from hand
    public List<MeldSlot> meldSlots;
    private Seat tableWind;
    private Seat seatWind;

    public PlayerHand() {
        super();
        meldSlots = new ArrayList<>();
        melds = new ArrayList<>();
        pos = new Vector2(177F,650f);
    }

    public PlayerHand(List<Tile> tile) {
        this();
        getTiles().addAll(tile);
    }

    public int getSize() {
        return getTiles().size();
    }

    public void addNewMeldSlot() {
        BobEffect bob = new BobEffect();
        Vector2 vec = new Vector2((pos.x + 75F*getMeldedTilesCount()+ 23F*melds.size()) * Settings.xScale, (pos.y+150F) * Settings.yScale);
        meldSlots.add(new MeldSlot(this, meldSlots.size(), vec, bob));
    }

    @Override
    public int getMaxHandSize() {
        return maxHandSize - melds.size() * 3;
    }

    public void addMeld(TileGroup tileGroup, RiichiDeck deck) {
        addNewMeldSlot();
        melds.add(tileGroup);
        tileGroup.getTileKinds().stream().map(Tile::new).forEach(t->getTiles().remove(t));
        if (tileGroup.isQuad()) {
            draw(deck, 1);
        }
        clearAndSort();
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
        return melds;
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
        return melds.isEmpty();
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
