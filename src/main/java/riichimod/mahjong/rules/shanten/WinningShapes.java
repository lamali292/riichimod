package riichimod.mahjong.rules.shanten;

import riichimod.mahjong.Tile;

import java.util.List;

/**
 * Winning shapes is meant as a way to validate if a hand has a winning shape or
 * not. It may differ from one type of mahjong ruleset to another.
 */
public interface WinningShapes
{
    public boolean hasWinningShape(List<Tile> hand);

    /**
     * Alias for hasWinningShape
     */
    public default boolean isMahjong(List<Tile> hand)
    {
        return hasWinningShape(hand);
    }
}
