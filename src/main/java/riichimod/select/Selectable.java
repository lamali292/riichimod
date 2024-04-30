package riichimod.select;

import com.megacrit.cardcrawl.helpers.Hitbox;

public interface Selectable {
    Hitbox getHitbox();
    Holdable getHoldable();
    boolean isSelected();
    void setSelected(boolean selected);
    void setHoldable(Holdable holdable);
}
