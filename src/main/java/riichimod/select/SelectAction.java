package riichimod.select;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import riichimod.RiichiHelper;

public class SelectAction extends AbstractGameAction {
    private final Runnable runnable;
    private final SelectableHolder selectable;

    public SelectAction(int amount, SelectableHolder selectable, Runnable runnable) {
        this.amount = amount;
        this.selectable = selectable;
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        this.runnable = runnable;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (selectable.getSelectables().isEmpty() || this.runnable == null) {
                this.isDone = true;
                return;
            }
            RiichiHelper.riichiSelectScreen = new SelectScreen(selectable);
            RiichiHelper.riichiSelectScreen.open(this.amount);
            this.tickDuration();
        }
        if (selectable.selected() >= this.amount) {
            this.runnable.run();
            this.isDone = true;
        }
        this.tickDuration();
    }

}
