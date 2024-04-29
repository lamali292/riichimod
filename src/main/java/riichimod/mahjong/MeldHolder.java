package riichimod.mahjong;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.vfx.BobEffect;
import riichimod.mahjong.rules.utils.TileGroup;
import riichimod.select.Selectable;
import riichimod.select.SelectableHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MeldHolder implements SelectableHolder {
    public List<MeldSlot> slots;
    public int selected = 0;
    public MeldHolder(List<TileGroup> groups, Vector2 pos, BobEffect bob) {
        slots = new ArrayList<>();
        int m = 0;
        for (TileGroup group : groups) {
            Vector2 tmp = new Vector2(pos.x,pos.y+100F*(m+1));
            slots.add(new MeldSlot(this, m, tmp, bob));
            m++;
        }
    }

    public void render(SpriteBatch sb) {
        slots.forEach(s->s.render(sb));
    }


    @Override
    public int selected() {
        return selected;
    }

    @Override
    public List<? extends Selectable> getSelectables() {
        return slots;
    }

    @Override
    public void select(int id) {
        if (!slots.get(id).selected) {
            slots.get(id).selected = true;
            selected++;
        }
    }

    @Override
    public boolean isSelected(int id) {
        return slots.get(id).selected;
    }

    @Override
    public void deselect(int id) {
        if (slots.get(id).selected) {
            slots.get(id).selected = false;
            selected--;
        }
    }
}
