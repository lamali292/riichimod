package riichimod.game.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riichimod.game.character.RiichiCharacter;
import riichimod.game.util.CardStats;

public class StrikeCard extends BaseCard {
    public static final String ID = makeID(StrikeCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RiichiCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;


    public StrikeCard() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new StrikeCard();
    }
}
