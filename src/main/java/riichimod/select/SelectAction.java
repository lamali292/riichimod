package riichimod.select;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import riichimod.RiichiHelper;

import java.util.List;

public class SelectAction extends AbstractGameAction {
    private final Runnable runnable;
    private final List<? extends SelectableHolder> selectables;

    public SelectAction(int amount, List<? extends SelectableHolder> selectables, Runnable runnable) {
        this.amount = amount;
        this.selectables = selectables;
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        this.runnable = runnable;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (selectables.stream().map(SelectableHolder::getSelectables).allMatch(List::isEmpty) || this.runnable == null) {
                this.isDone = true;
                return;
            }
            RiichiHelper.riichiSelectScreen = new SelectScreen(selectables);
            RiichiHelper.riichiSelectScreen.open(this.amount);
            this.tickDuration();
        }
        if (selectables.stream().mapToInt(SelectableHolder::selected).sum() >= this.amount) {
            this.runnable.run();
            this.isDone = true;
        }
        this.tickDuration();
    }

}
