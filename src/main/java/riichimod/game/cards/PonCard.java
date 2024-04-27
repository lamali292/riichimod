package riichimod.game.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riichimod.RiichiHelper;
import riichimod.game.character.RiichiCharacter;
import riichimod.mahjong.RiichiCalculator;
import riichimod.mahjong.Tile;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.game.util.CardStats;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PonCard extends StealCard {
    public static final String ID = makeID(PonCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RiichiCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public PonCard() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
        action(RiichiCalculator::getTriplets);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new PonCard();
    }
}
