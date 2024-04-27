package riichimod.mahjong.rules.shanten;

import riichimod.mahjong.Tile;
import riichimod.mahjong.rules.utils.MPSZNotation;

import java.util.List;

public class ShantenCalculation
{
    /**
     * Count shanten with a hand in mpsz notation.
     */
    public static int countShanten(String hand)
    {
        MPSZNotation mpsz = new MPSZNotation();
        List<Tile> asHand = mpsz.getTilesFrom(hand);
        WaitShapeEngine engine = new WaitShapeEngine(asHand);
        return engine.getShanten();
    }
}
