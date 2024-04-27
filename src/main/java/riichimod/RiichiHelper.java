package riichimod;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riichimod.game.character.RiichiCharacter;
import riichimod.mahjong.MonsterHand;
import riichimod.mahjong.Hand;
import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.RiichiDeck;
import riichimod.select.SelectScreen;

import java.util.ArrayList;
import java.util.List;

public class RiichiHelper {
    public static SelectScreen riichiSelectScreen;
    public static boolean doStuff = false;
    public static boolean handSelection = false;
    public static RiichiDeck deck;
    public static PlayerHand hand;
    public static List<MonsterHand> enemyHands;
    public static int drawPower = 0;

    public static void init() {
        if (AbstractDungeon.player instanceof RiichiCharacter) {
            RiichiDeck.init();
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
}
