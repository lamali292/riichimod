package riichimod.select;

import java.util.List;

public interface SelectableHolder {
    int selected();
    List<? extends Selectable> getSelectables();
    List<? extends Selectable> getSelected();
    boolean isSelected(int id);
    void deselect(int id);
    void select(int id);
    int getID(Selectable selectable);
}
