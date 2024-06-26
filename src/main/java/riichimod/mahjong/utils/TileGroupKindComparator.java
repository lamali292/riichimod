package riichimod.mahjong.utils;

import java.util.Comparator;
import java.util.Objects;

/**
 * This comparator sorts tiles by tile kind. The shortest group is always sorted
 * first if they are equal.
 */
public class TileGroupKindComparator implements Comparator<TileGroup>
{
    @Override
    public int compare(TileGroup first, TileGroup second)
    {
        int min = Math.min(first.getSize(), second.getSize());
        for (int i = 0; i < min; i++)
        {
            if (!Objects.equals(first.getIndices().get(i), second.getIndices().get(i)))
            {
                return first.getIndices().get(i) - second.getIndices().get(i);
            }
        }
        return first.getSize() - second.getSize();
    }
}
