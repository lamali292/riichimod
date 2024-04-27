package riichimod.select;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import riichimod.RiichiHelper;

public class SelectScreen {

    private final SelectableHolder holder;
    private int numCards = 0;

    
    public SelectScreen(SelectableHolder holder) {
        this.holder = holder;
    }
    public int getHovered() {
        //System.out.println(RiichiHelper.hand.slots.stream().map(t->t.pos.x).collect(Collectors.toList()));
        for (Selectable selectable : holder.getSelectables()) {
            selectable.getHitbox().update();
            if (selectable.getHitbox().hovered) {
                return selectable.getID();
            }
        }
        return -1;
    }

    public void update() {
        int id = getHovered();
        if (id == -1) return;
        Hitbox hb = holder.getSelectables().get(id).getHitbox();
        if (InputHelper.justClickedLeft)
            hb.clickStarted = true;
        if (hb.clicked) {
            hb.clicked = false;
            if (!holder.isSelected(id)) {
                holder.select(id);
            } else {
                holder.deselect(id);
            }
            CardCrawlGame.sound.play("CARD_SELECT");
            if (holder.selected() >= numCards) {
                close();
            }
        }
    }

    public void close() {
        RiichiHelper.handSelection = false;
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.isScreenUp = false;
    }

    public void open(int numCards) {
        this.numCards = numCards;
        RiichiHelper.handSelection = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NO_INTERACT;
        AbstractDungeon.isScreenUp = true;
    }


}
