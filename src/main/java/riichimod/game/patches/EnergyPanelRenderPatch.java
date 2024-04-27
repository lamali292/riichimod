package riichimod.game.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import riichimod.RiichiHelper;

@SpirePatch(
        clz = EnergyPanel.class,
        method = "render",
        paramtypes = {"com.badlogic.gdx.graphics.g2d.SpriteBatch"}
)
public class EnergyPanelRenderPatch {
    public static void Prefix(EnergyPanel __instance, SpriteBatch sb) {
        //New patch here. This patch hooks into the time where EnergyPanel renders.
        //Since this only renders in combat specifically, and renders in a unique way in relation to the foreground, this is a useful patch.
        // Maybe other things should render here.
        if (RiichiHelper.doStuff) {
            RiichiHelper.render(sb);
        }
    }
}
