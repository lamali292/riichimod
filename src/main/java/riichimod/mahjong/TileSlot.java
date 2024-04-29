package riichimod.mahjong;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.BobEffect;
import riichimod.select.SelectableHolder;

public class TileSlot extends Slot {

    public TileSlot(SelectableHolder holder, int id, Vector2 pos, BobEffect bob, Hitbox hb) {
        super(holder, id, pos, bob, hb);
    }

    public void render(SpriteBatch sb) {
        sb.draw(RiichiDeck.sequenceSlot, pos.x, pos.y + bob.y, 0, 0, RiichiDeck.sequenceSlot.getWidth() * Settings.scale, RiichiDeck.sequenceSlot.getHeight() * Settings.scale, 1, 1, 0, 0, 0, RiichiDeck.sequenceSlot.getWidth(), RiichiDeck.sequenceSlot.getHeight(), false, false);
        if (id >= holder.getSelectables().size()) return;
        Tile tile = (Tile) holder.getSelectables().get(id);
        if (tile != null) {
            renderTile(sb, tile, pos.x + 8F, pos.y + bob.y + 55F, selected);
        }
    }

}
