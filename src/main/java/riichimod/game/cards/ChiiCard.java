package riichimod.game.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riichimod.RiichiHelper;
import riichimod.game.character.RiichiCharacter;
import riichimod.game.util.CardStats;
import riichimod.mahjong.RiichiCalculator;
import riichimod.mahjong.Tile;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;
import riichimod.mahjong.rules.utils.MahjongTileKind;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChiiCard extends StealCard {
    public static final String ID = makeID(ChiiCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RiichiCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public ChiiCard() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
        action(RiichiCalculator::getSequences);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ChiiCard();
    }
}
