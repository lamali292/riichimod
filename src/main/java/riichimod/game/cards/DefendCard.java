package riichimod.game.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riichimod.game.character.RiichiCharacter;
import riichimod.game.util.CardStats;

public class DefendCard extends BaseCard {
    public static final String ID = makeID(DefendCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RiichiCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.NONE,
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;

    public DefendCard() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DefendCard();
    }
}
