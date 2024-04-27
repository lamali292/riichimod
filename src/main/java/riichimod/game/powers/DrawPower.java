package riichimod.game.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riichimod.RiichiHelper;
import riichimod.game.cards.KanCard;
import static riichimod.RiichiMod.makeID;

public class DrawPower extends BasePower{
    public static final String ID = makeID(KanCard.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;
    public DrawPower(int amount) {
        super(ID, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
        RiichiHelper.drawPower += amount;
    }
}
