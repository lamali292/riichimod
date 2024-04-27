package riichimod.game.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

public class StealCard extends BaseCard {
    public static final String ID = makeID(StealCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RiichiCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public StealCard() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
    }

    public StealCard(String id, CardStats inf) {
        super(id, inf);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
        action(RiichiCalculator::getAllTileGroups);

        List<AbstractMonster> monstersWithHand = RiichiHelper.enemyHands.stream().map(MonsterHand::getMonster).collect(Collectors.toList());
        List<AbstractMonster> missingMonsters = AbstractDungeon.getMonsters().monsters.stream().filter(t->!monstersWithHand.contains(t)).collect(Collectors.toList());
        for (AbstractMonster monster : missingMonsters) {
            MonsterHand hand = new MonsterHand(monster);
            RiichiHelper.enemyHands.add(hand);
        }
        RiichiHelper.enemyHands.forEach(t->t.draw(RiichiHelper.deck, 1));

        addToBot(new SelectAction(2, RiichiHelper.enemyHands, () -> {
            RiichiHelper.enemyHands.forEach(Hand::discardSelected);
        }));

    }

    public void action(Function<List<MahjongTileKind>, List<TileGroup>> func) {
        List<MahjongTileKind> tileKinds = RiichiHelper.hand.getUnmeldedTiles().stream().map(Tile::getTileKind).collect(Collectors.toList());
        List<TileGroup> groupList = func.apply(tileKinds);
        ArrayList<AbstractCard> cardGroups = groupList.stream().map(SelectCard::new).collect(Collectors.toCollection(ArrayList::new));
        addToBot(new SelectCardsCenteredAction(cardGroups, 1, "", true, t->true, (cards) -> {
            for (AbstractCard card : cards) {
                RiichiHelper.hand.addMeld(((SelectCard) card).tileGroup, RiichiHelper.deck);
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new StealCard();
    }
}
