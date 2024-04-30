package riichimod.mahjong.hand;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.BobEffect;
import riichimod.mahjong.RiichiDeck;
import riichimod.mahjong.slot.MeldSlot;
import riichimod.mahjong.utils.TileGroup;
import riichimod.select.Renderable;
import riichimod.select.Selectable;
import riichimod.select.SelectableHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MeldHolder implements SelectableHolder, Renderable {
    public List<MeldSlot> slots;
    public int selected = 0;
    public MeldHolder(List<TileGroup> groups, Vector2 pos, BobEffect bob) {
        slots = new ArrayList<>();
        int m = 0;
        for (TileGroup group : groups) {
            int w = group.getSize() == 4 ? RiichiDeck.meldSlot4.getWidth() : RiichiDeck.meldSlot3.getWidth();
            int h = group.getSize() == 4 ? RiichiDeck.meldSlot4.getHeight() : RiichiDeck.meldSlot3.getHeight();
            Vector2 tmp = new Vector2(pos.x,pos.y+100F*(m+1));
            Hitbox hb = new Hitbox(tmp.x, tmp.y, w* Settings.scale, h* Settings.scale);
            slots.add(new MeldSlot(this, group, tmp, bob, hb));
            m++;
        }
    }

    @Override
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
    public List<? extends Selectable> getSelected() {
        return slots.stream().filter(Selectable::isSelected).collect(Collectors.toList());
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

    @Override
    public int getID(Selectable selectable) {
        return slots.indexOf((MeldSlot)selectable);
    }
}
