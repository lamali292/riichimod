package riichimod.mahjong.utils;

public enum TileFamily
{
    CHARACTERS, //
    CIRCLES, //
    BAMBOOS, //
    HONOURS, //
    NONE;

    public boolean isNumeral()
    {
        return this == CHARACTERS || this == CIRCLES || this == BAMBOOS;
    }
}
