package riichimod.select;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import riichimod.RiichiHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectAction extends AbstractGameAction {
    private final Runnable runnable;
    private final List<? extends SelectableHolder> selectables;
    private final boolean anyNumber;

    public SelectAction(int amount, List<? extends SelectableHolder> selectables, boolean anyNumber, Runnable runnable) {
        this.amount = amount;
        this.anyNumber = anyNumber;
        this.selectables = selectables;
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        this.runnable = runnable;
    }

    public SelectAction(int amount, SelectableHolder selectable, boolean anyNumber, Runnable runnable) {
        this(amount,  Stream.of(selectable).collect(Collectors.toList()), anyNumber, runnable);
    }

    public SelectAction(int amount, SelectableHolder selectable, Runnable runnable) {
        this(amount,  selectable, false, runnable);
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (selectables.stream().map(SelectableHolder::getSelectables).allMatch(List::isEmpty) || this.runnable == null) {
                this.isDone = true;
                return;
            }
            RiichiHelper.riichiSelectScreen = new SelectScreen(selectables, anyNumber);
            RiichiHelper.riichiSelectScreen.open(this.amount);
            this.tickDuration();
        }
        if (RiichiHelper.riichiSelectScreen.skipped || selectables.stream().mapToInt(SelectableHolder::selected).sum() >= this.amount) {
            this.runnable.run();
            this.isDone = true;
        }
        this.tickDuration();
    }

}
