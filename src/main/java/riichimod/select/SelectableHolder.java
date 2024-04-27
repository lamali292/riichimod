package riichimod.select;

import java.util.List;

public interface SelectableHolder {
    int selected();
    List<? extends Selectable> getSelectables();
    boolean isSelected(int id);
    void deselect(int id);
    void select(int id);
}
