package riichimod.mahjong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.BobEffect;
import riichimod.mahjong.rules.shanten.parsing.TileGroup;

import java.util.stream.Collectors;

public class MeldSlot extends Slot {
    public MeldSlot(Hand hand, int id, Vector2 pos, BobEffect bob) {
        super(hand, id, pos, bob, null);
    }

    public void render(SpriteBatch sb) {
        TileGroup tileGroup = hand.melds.get(id);
        Texture tex;
        if (tileGroup.getSize() == 4) {
            tex = RiichiDeck.meldSlot4;
        } else {
            tex = RiichiDeck.meldSlot3;
        }
        sb.draw(tex, pos.x, pos.y + bob.y, 0, 0, tex.getWidth() * Settings.scale, tex.getHeight() * Settings.scale, 1, 1, 0, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
        //if (id >= hand.getMelds().size()) return;
        int m = 0;
        for (Tile tile : tileGroup.getTileKinds().stream().map(Tile::new).collect(Collectors.toList())) {
            renderTile(sb, tile, pos.x + (8F + m*75F)* Settings.xScale, pos.y + bob.y + 55F*Settings.yScale);
            m++;
        }
    }
}
