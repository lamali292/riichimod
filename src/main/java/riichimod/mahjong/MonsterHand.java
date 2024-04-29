package riichimod.mahjong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riichimod.RiichiHelper;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.utils.TileGroup;

import java.util.List;
import java.util.stream.Collectors;

public class MonsterHand extends Hand {
    private final AbstractMonster monster;

    public MonsterHand(AbstractMonster monster) {
        super();
        this.monster = monster;
        pos = new Vector2(monster.drawX,monster.drawY);
        //draw(RiichiHelper.deck, 1);
    }

    public AbstractMonster getMonster() {
        return monster;
    }

    public void removeThis() {
        RiichiHelper.enemyHands.remove(this);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        for (Slot slot : slots) {
            if (slot.getHitbox().hovered) {
                List<TileGroup> groups = RiichiCalculator.getGroupsInHand(RiichiHelper.hand.getOpenTiles(), tiles.get(slot.getID()));
                new MeldHolder(groups, slot.pos, slot.bob).render(sb);
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
