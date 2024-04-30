package riichimod;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riichimod.game.character.RiichiCharacter;
import riichimod.mahjong.hand.MonsterHand;
import riichimod.mahjong.hand.Hand;
import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.RiichiDeck;
import riichimod.select.SelectScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RiichiHelper {
    public static SelectScreen riichiSelectScreen;
    public static boolean doStuff = false;
    public static boolean handSelection = false;
    public static RiichiDeck deck;
    public static PlayerHand hand;
    public static List<MonsterHand> enemyHands;
    public static int drawPower;

    public static void init() {
        if (AbstractDungeon.player instanceof RiichiCharacter) {
            RiichiDeck.init();
            drawPower = 0;
            doStuff = true;
            enemyHands = new ArrayList<>();
            deck = new RiichiDeck();
            hand = new PlayerHand();
            hand.draw(deck, 13);
            hand.sort();
        }

    }

    public static void render(SpriteBatch sb) {
        if (doStuff) {
            hand.render(sb);
            enemyHands.forEach(g->g.render(sb));
            if (handSelection && riichiSelectScreen != null) riichiSelectScreen.render(sb);
        }
    }

    public static void update() {
        if (!doStuff && AbstractDungeon.player instanceof RiichiCharacter) {
            doStuff = true;
        } else if (AbstractDungeon.player == null) {
            doStuff = false;
        }
        if (doStuff && hand != null) {
            hand.update();
            enemyHands.forEach(Hand::update);
            if (handSelection && riichiSelectScreen != null) riichiSelectScreen.update();
        }
    }

    public static void updateMonsterHands() {
        removeDeadMonsterHands();
        addMissingMonsterHands();
    }

    public static void removeDeadMonsterHands() {
        List<MonsterHand> hands = RiichiHelper.enemyHands.stream().map(MonsterHand::getMonster)
                .filter(AbstractCreature::isDeadOrEscaped)
                .map(MonsterHand::getHand)
                .filter(Objects::nonNull).collect(Collectors.toList());
        hands.forEach(MonsterHand::removeThis);
    }

    public static void addMissingMonsterHands(){
        List<AbstractMonster> monstersWithHand = RiichiHelper.enemyHands.stream().map(MonsterHand::getMonster).collect(Collectors.toList());
        AbstractDungeon.getMonsters().monsters.stream()
                .filter(t->!monstersWithHand.contains(t))
                .filter(t->!t.isDeadOrEscaped())
                .forEach(t->RiichiHelper.enemyHands.add(new MonsterHand(t)));
    }
}
