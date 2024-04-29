package riichimod.mahjong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.BobEffect;
import riichimod.mahjong.rules.utils.TileGroup;
import riichimod.select.SelectableHolder;

import java.util.stream.Collectors;

public class MeldSlot extends Slot {
    public MeldSlot(SelectableHolder holder, int id, Vector2 pos, BobEffect bob) {
        super(holder, id, pos, bob, null);
    }

    public void render(SpriteBatch sb) {
        TileGroup tileGroup = (TileGroup) holder.getSelectables().get(id);
        renderTileGroup(sb, tileGroup, pos, bob, selected);
    }
}
