package riichimod.mahjong;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.BobEffect;
import riichimod.mahjong.rules.utils.TileGroup;
import riichimod.select.Selectable;
import riichimod.select.SelectableHolder;

import java.util.stream.Collectors;

public abstract class Slot implements Selectable  {
    public final Vector2 pos;
    public final BobEffect bob;
    public final int id;
    public final SelectableHolder holder;
    public final Hitbox hb;
    public boolean selected = false;

    public Slot(SelectableHolder holder, int id, Vector2 pos, BobEffect bob, Hitbox hb) {
        this.holder = holder;
        this.id = id;
        this.pos = pos;
        this.bob = bob;
        this.hb = hb;
    }

    public static void renderTile(SpriteBatch sb, Tile tile, float tileX, float tileY, boolean selected) {
        float pad = 4F;
        Texture t = selected ? RiichiDeck.back : RiichiDeck.front;
        sb.draw(t, tileX, tileY, 0, 0, Hand.cardWidth * Settings.scale, Hand.cardHeight * Settings.scale, 1, 1, 0, 0, 0, Hand.cardWidth , Hand.cardHeight , false, false);
        sb.draw(tile.getTexture(), tileX, tileY, 0, 0, Hand.cardWidth * Settings.scale, Hand.cardHeight  * Settings.scale, 1, 1, 0, 0, 0, Hand.cardWidth , Hand.cardHeight , false, false);
        if (tile.getTileKind().isNumeral()) {
            String msg = tile.getTileKind().getTileNumber()+"";
            BitmapFont font = FontHelper.buttonLabelFont;
            font.getData().setScale(0.5F);
            FontHelper.renderSmartText(sb, font, msg, tileX+RiichiDeck.front.getWidth()-font.getSpaceWidth()*2-pad, tileY+RiichiDeck.front.getHeight()-pad, Float.MAX_VALUE, font.getLineHeight(), Color.RED);
            font.getData().setScale(1F);
        }
    }

    public static void renderTileGroup(SpriteBatch sb, TileGroup group, Vector2 pos, BobEffect bob, boolean selected) {
        Texture tex;
        if (group.getSize() == 4) {
            tex = RiichiDeck.meldSlot4;
        } else {
            tex = RiichiDeck.meldSlot3;
        }
        sb.draw(tex, pos.x, pos.y + bob.y, 0, 0, tex.getWidth() * Settings.scale, tex.getHeight() * Settings.scale, 1, 1, 0, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
        int m = 0;
        for (Tile tile : group.getTileKinds().stream().map(Tile::new).collect(Collectors.toList())) {
            renderTile(sb, tile, pos.x + (8F + m*75F)* Settings.xScale, pos.y + bob.y + 55F*Settings.yScale, selected);
            m++;
        }
    }

    public void update() {
        bob.update();
    }

    @Override
    public Hitbox getHitbox() {
        return hb;
    }

    @Override
    public int getID() {
        return id;
    }
}
