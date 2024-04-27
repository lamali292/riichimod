package riichimod.game.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riichimod.game.character.RiichiCharacter;
import riichimod.game.util.CardStats;

public class RonCard extends BaseCard {
    public static final String ID = makeID(RonCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RiichiCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public RonCard() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RonCard();
    }
}
