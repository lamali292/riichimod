package riichimod.mahjong;

import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riichimod.RiichiHelper;

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
