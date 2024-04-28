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
import riichimod.mahjong.Hand;
import riichimod.mahjong.MonsterHand;
import riichimod.mahjong.RiichiCalculator;
import riichimod.mahjong.Tile;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;
import riichimod.mahjong.rules.utils.MahjongTileKind;
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
        //action(RiichiCalculator::getAllTileGroups);
        addMissingMonsterHands();
        RiichiHelper.enemyHands.forEach(t->t.draw(RiichiHelper.deck, 1));
        //generateGroups();
        addToBot(new SelectAction(1, RiichiHelper.enemyHands, true, this::generateGroups));
    }

    public void generateGroups() {
        /*
        List<Tile> tiles = RiichiHelper.enemyHands.stream().map(Hand::getSelectedTiles).flatMap(List::stream).collect(Collectors.toList());
        for (Tile tile : tiles) {
            action(RiichiCalculator::getAllTileGroups, tile.getTileKind());
        }
        RiichiHelper.enemyHands.forEach(Hand::clearWithTiles);
        RiichiHelper.enemyHands.forEach(Hand::resetSelected);
        */
        for (MonsterHand monsterHand : RiichiHelper.enemyHands) {
            for (Tile tile : monsterHand.getSelectedTiles()) {
                action(monsterHand.getMonster(), RiichiCalculator::getAllTileGroups, tile.getTileKind());
            }
            monsterHand.clearWithTiles();
            monsterHand.resetSelected();
        }



    }

    public void action(AbstractMonster m,Function<List<MahjongTileKind>, List<TileGroup>> func, MahjongTileKind extraTile) {
        List<MahjongTileKind> tileKinds = RiichiHelper.hand.getTiles().stream().map(Tile::getTileKind).collect(Collectors.toList());
        tileKinds.add(extraTile);
        List<TileGroup> groupList = func.apply(tileKinds).stream().filter(t->t.contains(extraTile)).collect(Collectors.toList());
        ArrayList<AbstractCard> cardGroups = groupList.stream().map(SelectCard::new).collect(Collectors.toCollection(ArrayList::new));
        if (cardGroups.isEmpty()) return;
        addToTop(new SelectCardsCenteredAction(cardGroups, 1, "", false, t->true, (cards) -> {
            for (AbstractCard card : cards) {
                RiichiHelper.hand.add(new Tile(extraTile));
                RiichiHelper.hand.addNewSlot();
                RiichiHelper.hand.addMeld(((SelectCard) card).tileGroup, RiichiHelper.deck);
                addToTop(new DamageAction(m, new DamageInfo(AbstractDungeon.player, 10, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                addToTop(new SelectAction(1, RiichiHelper.hand, () -> {
                    RiichiHelper.hand.discardSelected();
                }));
            }
        }));

    }


    public void addMissingMonsterHands(){
        List<AbstractMonster> monstersWithHand = RiichiHelper.enemyHands.stream().map(MonsterHand::getMonster).collect(Collectors.toList());
        List<AbstractMonster> missingMonsters = AbstractDungeon.getMonsters().monsters.stream().filter(t->!monstersWithHand.contains(t)).collect(Collectors.toList());
        for (AbstractMonster monster : missingMonsters) {
            MonsterHand hand = new MonsterHand(monster);
            RiichiHelper.enemyHands.add(hand);
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new TestCard();
    }
}
