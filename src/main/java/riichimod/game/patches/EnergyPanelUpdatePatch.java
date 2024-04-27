package riichimod.game.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import riichimod.RiichiHelper;

@SpirePatch(
        clz = EnergyPanel.class,
        method = "update"
)
public class EnergyPanelUpdatePatch {
    public static void Prefix(EnergyPanel __instance) {
        if (RiichiHelper.doStuff) {
            RiichiHelper.update();
        }
    }
}