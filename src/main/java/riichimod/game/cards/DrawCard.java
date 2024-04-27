package riichimod.game.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riichimod.RiichiHelper;
import riichimod.game.character.RiichiCharacter;
import riichimod.select.SelectAction;
import riichimod.game.util.CardStats;
import riichimod.select.SelectableHolder;

import java.util.ArrayList;
import java.util.List;


public class DrawCard extends BaseCard {
    public static final String ID = makeID(DrawCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RiichiCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            0
    );

    public DrawCard() {
        super(ID, info);
        setMagic(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));

        RiichiHelper.hand.draw(RiichiHelper.deck, magicNumber+RiichiHelper.drawPower);
        RiichiHelper.hand.calcYaku();
        addToBot(new SelectAction(magicNumber + RiichiHelper.drawPower, RiichiHelper.hand, () -> {
            RiichiHelper.hand.discardSelected();
        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DrawCard();
    }
}
