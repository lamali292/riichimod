package riichimod.game.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riichimod.RiichiHelper;
import riichimod.game.character.RiichiCharacter;
import riichimod.game.util.CardStats;
import riichimod.mahjong.*;
import riichimod.mahjong.hand.MeldHolder;
import riichimod.mahjong.hand.MonsterHand;
import riichimod.mahjong.slot.MeldSlot;
import riichimod.mahjong.slot.Slot;
import riichimod.mahjong.utils.Tile;
import riichimod.mahjong.utils.TileGroup;
import riichimod.mahjong.utils.MahjongTileKind;
import riichimod.select.SelectAction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestCard extends BaseCard {
    public static final String ID = makeID(TestCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RiichiCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public TestCard() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
        RiichiHelper.updateMonsterHands();
        RiichiHelper.enemyHands.forEach(t->t.draw(RiichiHelper.deck, 1+RiichiHelper.drawPower));
        RiichiHelper.enemyHands.forEach(MonsterHand::calculatePossibleGroups);
        addToBot(new SelectAction(1, RiichiHelper.enemyHands, true, this::generateGroups2));
    }

    public void generateGroups2() {
        for (MonsterHand monsterHand : RiichiHelper.enemyHands) {
            if (monsterHand.getSelected().isEmpty()) {
                monsterHand.clear();
                monsterHand.resetSelected();
                continue;
            }
            Slot slot = monsterHand.getSelected().get(0);
            MahjongTileKind extraTile = ((Tile) slot.getHoldable()).getTileKind();
            MeldHolder meld = monsterHand.possibleGroups.get(slot);
            if (meld.slots.isEmpty()) {
                monsterHand.clear();
                monsterHand.resetSelected();
                continue;
            }
            addToTop(new SelectAction(1, meld, false, () -> {
                RiichiHelper.hand.add(new Tile(extraTile));
                MeldSlot meldSlot = (MeldSlot) meld.getSelected().get(0);
                RiichiHelper.hand.addMeld((TileGroup) meldSlot.getHoldable(), RiichiHelper.deck);
                for(MahjongTileKind kinds : ((TileGroup) meldSlot.getHoldable()).getTileKinds()) {
                    int d = kinds.isNumeral() ? kinds.getTileNumber() : 10;
                    addToTop(new DamageAction(monsterHand.getMonster(), new DamageInfo(AbstractDungeon.player, d, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }
                monsterHand.clear();
                monsterHand.resetSelected();
                addToTop(new SelectAction(1, RiichiHelper.hand, () -> RiichiHelper.hand.discardSelected()));

            }));
        }
    }


    public void generateGroups() {
        for (MonsterHand monsterHand : RiichiHelper.enemyHands) {
            for (Slot slot : monsterHand.getSelected()) {
                action(monsterHand, RiichiCalculator::getAllTileGroups, slot);
            }
            monsterHand.resetSelected();
            monsterHand.clear();
        }
    }

    public void action(MonsterHand monsterHand,Function<List<MahjongTileKind>, List<TileGroup>> func, Slot slot) {
        MahjongTileKind extraTile = ((Tile) slot.getHoldable()).getTileKind();
        List<MahjongTileKind> tileKinds = RiichiHelper.hand.getTiles().stream().map(Tile::getTileKind).collect(Collectors.toList());
        tileKinds.add(extraTile);
        List<TileGroup> groupList = func.apply(tileKinds).stream().filter(t->t.contains(extraTile)).collect(Collectors.toList());
        ArrayList<AbstractCard> cardGroups = groupList.stream().map(SelectCard::new).collect(Collectors.toCollection(ArrayList::new));
        if (cardGroups.isEmpty()) return;
        addToTop(new SelectCardsCenteredAction(cardGroups, 1, "", false, t->true, (cards) -> {
            for (AbstractCard card : cards) {
                RiichiHelper.hand.add(new Tile(extraTile));
                RiichiHelper.hand.addMeld(((SelectCard) card).tileGroup, RiichiHelper.deck);
                for(MahjongTileKind kinds : ((SelectCard) card).tileGroup.getTileKinds()) {
                    int d = kinds.isNumeral() ? kinds.getTileNumber() : 10;
                    addToTop(new DamageAction(monsterHand.getMonster(), new DamageInfo(AbstractDungeon.player, d, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }
                addToTop(new SelectAction(1, RiichiHelper.hand, () -> RiichiHelper.hand.discardSelected()));
            }
        }));
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new TestCard();
    }
}
