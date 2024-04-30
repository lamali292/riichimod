package riichimod.mahjong.slot;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.BobEffect;
import riichimod.mahjong.utils.TileGroup;
import riichimod.select.SelectableHolder;

public class MeldSlot extends Slot {
    public MeldSlot(SelectableHolder holder, TileGroup tileGroup, Vector2 pos, BobEffect bob) {
        this(holder, tileGroup, pos, bob, null);
    }

    public MeldSlot(SelectableHolder holder, TileGroup tileGroup, Vector2 pos, BobEffect bob, Hitbox hb) {
        super(holder, tileGroup, pos, bob, hb);
    }

    @Override
    public void render(SpriteBatch sb) {
        TileGroup tileGroup = (TileGroup) holdable;
        renderTileGroup(sb, tileGroup, pos, bob, selected);
    }
}
