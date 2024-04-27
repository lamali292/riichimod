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
import riichimod.select.Selectable;

public abstract class Slot implements Selectable  {
    public final Vector2 pos;
    public final BobEffect bob;
    public final int id;
    public final Hand hand;
    public final Hitbox hb;

    public boolean selected = false;

    public Slot(Hand hand, int id, Vector2 pos, BobEffect bob, Hitbox hb) {
        this.hand = hand;
        this.id = id;
        this.pos = pos;
        this.bob = bob;
        this.hb = hb;
    }

    public void renderTile(SpriteBatch sb, Tile tile, float tileX, float tileY) {
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
