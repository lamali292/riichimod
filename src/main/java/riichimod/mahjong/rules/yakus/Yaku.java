package riichimod.mahjong.rules.yakus;

public interface Yaku
{
    boolean isValid();

    int getHanValue();

    default boolean isYakuman()
    {
        return getHanValue() == 13;
    }

    default boolean isDoubleYakuman()
    {
        return getHanValue() == 26;
    }
}
