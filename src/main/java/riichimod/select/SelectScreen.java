package riichimod.select;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton;
import riichimod.RiichiHelper;
import riichimod.mahjong.rules.shanten.Pair;

import java.util.List;

public class SelectScreen {

    public boolean skipped = false;
    GridSelectConfirmButton button;
    private final List<? extends SelectableHolder> holders;
    private final boolean anyNumber;
    private int numCards = 0;

    public SelectScreen(List<? extends SelectableHolder> holders) {
        this(holders, false);
    }

    public SelectScreen(List<? extends SelectableHolder> holders, boolean anyNumber) {
        this.holders = holders;
        this.anyNumber = anyNumber;
        if (anyNumber) {
            button = new GridSelectConfirmButton("Confirm");
            button.show();
            button.isDisabled = false;
        }

    }
    public Pair<SelectableHolder,Integer> getHovered() {
        for (SelectableHolder holder : holders) {
            for (Selectable selectable : holder.getSelectables()) {
                selectable.getHitbox().update();
                if (selectable.getHitbox().hovered) {
                    return new Pair<>(holder, selectable.getID());
                }
            }
        }
        return new Pair<>(null, null);
    }

    public void update() {
        if (anyNumber) {
            button.update();
            if (button.hb.clicked) {
                button.hb.clicked = false;
                skipped = true;
                close();
                return;
            }
        }

        Pair<SelectableHolder, Integer> ids = getHovered();
        if (ids.getFirst() == null || ids.getSecond() == null) return;
        SelectableHolder holder = ids.getFirst();
        int id = ids.getSecond();
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
            if (holders.stream().mapToInt(SelectableHolder::selected).sum() >= numCards) {
                close();
            }
        }
    }

    public void render(SpriteBatch sb) {
        if (anyNumber) {
            button.render(sb);
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
