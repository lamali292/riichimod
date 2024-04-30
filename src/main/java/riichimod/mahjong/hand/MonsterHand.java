package riichimod.mahjong.hand;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riichimod.RiichiHelper;
import riichimod.mahjong.RiichiCalculator;
import riichimod.mahjong.slot.Slot;
import riichimod.mahjong.utils.Tile;
import riichimod.mahjong.utils.TileGroup;

import java.util.HashMap;
import java.util.List;

public class MonsterHand extends Hand {
    private final AbstractMonster monster;

    public HashMap<Slot, MeldHolder> possibleGroups = new HashMap<>();

    public MonsterHand(AbstractMonster monster) {
        super();
        this.monster = monster;
        pos = new Vector2(monster.hb.x,monster.hb.y+monster.hb.height);
        //draw(RiichiHelper.deck, 1);
    }

    public AbstractMonster getMonster() {
        return monster;
    }

    public void removeThis() {
        RiichiHelper.enemyHands.remove(this);
    }

    public void calculatePossibleGroups() {
        possibleGroups = new HashMap<>();
        for (Slot slot : slots) {
            List<TileGroup> groups = RiichiCalculator.getGroupsInHand(RiichiHelper.hand.getOpenTiles(), (Tile) slot.getHoldable());
            possibleGroups.put(slot, new MeldHolder(groups, slot.pos, slot.bob));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        for (Slot slot : slots) {
            if ((slot.getHitbox().hovered || slot.isSelected()) && possibleGroups.containsKey(slot)) {
                possibleGroups.get(slot).render(sb);
            }
        }
    }

    @Override
    public int getMaxHandSize() {
        return 0;
    }

    public static MonsterHand getHand(AbstractMonster monster) {
        for (MonsterHand hand : RiichiHelper.enemyHands) {
            if (hand.getMonster() == monster) {
                return hand;
            }
        }
        return null;
    }

}
