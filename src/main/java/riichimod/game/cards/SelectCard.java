package riichimod.game.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riichimod.game.character.RiichiCharacter;
import riichimod.mahjong.RiichiDeck;
import riichimod.mahjong.utils.Tile;
import riichimod.mahjong.utils.TileGroup;
import riichimod.game.util.CardStats;

import java.util.stream.Collectors;

public class SelectCard extends BaseCard {
    public enum Type {
        TILE,
        TILEGROUP
    }

    public static final String ID = makeID(SelectCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RiichiCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    public Tile tile;
    public TileGroup tileGroup;
    public Type type;

    public SelectCard() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
    }

    @SpireOverride
    protected void renderPortrait(SpriteBatch sb) {
        if (type == Type.TILE) {
            Texture tex = tile.getTexture();
            sb.draw(tex, current_x, current_y, 0, 0, tex.getWidth(), tex.getHeight(), drawScale * Settings.scale, drawScale * Settings.scale, 0, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
        } else if (type == Type.TILEGROUP) {
            int i = 0;
            for (Tile t : tileGroup.getTileKinds().stream().map(Tile::new).collect(Collectors.toList())) {
                Texture tex = t.getTexture();
                Vector2 vec = getPos(tileGroup.getSize(),i,tex.getWidth(), tex.getHeight());
                drawAt(sb,RiichiDeck.front, vec.x, vec.y);
                drawAt(sb,tex, vec.x, vec.y);
                i++;
            }
        }
        //SpireSuper.call(sb);

    }

    public void drawAt(SpriteBatch sb, Texture tex, float x, float y) {
        sb.draw(tex, x , y, 0, 0, tex.getWidth(), tex.getHeight(), drawScale * Settings.scale, drawScale * Settings.scale, 0, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }

    public Vector2 getPos(int size, int id, float width, float height) {
        float ow = portrait.getRegionWidth();
        float oh = portrait.getRegionHeight();
        float pad_x = Math.max(0,2*(ow-width*size)/(size+1));
        Vector2 vec = new Vector2();
        vec.x = current_x+(id *(ow-pad_x-width)/(size-1)- (ow-pad_x)/2F)*drawScale;
        vec.y = current_y+ (oh-height)/2*drawScale;
        return vec;
    }

    public SelectCard(Tile tile) {
        super(ID, info);
        this.tile = tile;
        type = Type.TILE;
    }

    public SelectCard(TileGroup tileGroup) {
        super(ID, info);
        this.tileGroup = tileGroup;
        type = Type.TILEGROUP;
        if (tileGroup.isTriplet()){
            this.name = "Triplet";
        }else if (tileGroup.isRun()) {
            this.name = "Sequence";
        } else if (tileGroup.isQuad()) {
            this.name = "Quadruplet";
        }
        rawDescription = tileGroup.getTileKinds().stream().map(Enum::toString).collect(Collectors.joining(" NL "));
        initializeDescription();
        //description = tileGroup.getTileKinds().stream().map(Enum::toString).map(t->new DescriptionLine(t,portrait.originalWidth)).collect(Collectors.toCollection(ArrayList::new));
        //initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SelectCard(tile);
    }
}
