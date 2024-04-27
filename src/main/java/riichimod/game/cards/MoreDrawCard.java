package riichimod.game.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riichimod.game.character.RiichiCharacter;
import riichimod.game.powers.DrawPower;
import riichimod.game.util.CardStats;
import riichimod.mahjong.RiichiCalculator;

public class MoreDrawCard extends BaseCard {
    public static final String ID = makeID(MoreDrawCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RiichiCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.RARE,
            AbstractCard.CardTarget.NONE,
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public MoreDrawCard() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DrawPower(1));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MoreDrawCard();
    }
}
