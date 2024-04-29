package riichimod.mahjong.rules.utils;

import riichimod.mahjong.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class MPSZNotation
{
    public TileGroup getTileGroupFrom(String tileText)
    {
        TileGroup tileGroup = new TileGroup();
        for (MahjongTileKind tileKind : getTileKindsFrom(tileText))
        {
            tileGroup.add(tileKind.getIndex());
        }
        return tileGroup;
    }

    public List<TileGroup> getTileGroupsFrom(String... tileTexts)
    {
        ArrayList<TileGroup> tileGroups = new ArrayList<TileGroup>();
        for (String tileText : tileTexts)
        {
            tileGroups.add(getTileGroupFrom(tileText));
        }
        return tileGroups;
    }

    /**
     * This method returns a hand made of the tiles in input. The strings in input
     * must use the Tenhou notation in one block, eg. 345m345p345s1155z.
     *
     * @param tileText : all the tiles that make up the hand
     */
    public List<Tile> getTilesFrom(String tileText)
    {
        List<Tile> hand = new ArrayList<>();

        for (MahjongTileKind tileKind : getTileKindsFrom(tileText))
        {
            Tile tile = new Tile(tileKind);
            hand.add(tile);
        }

        return hand;
    }

    public String getHandAsMPSZNotation(List<Tile> hand)
    {
        StringBuilder mpszStringBuilder = new StringBuilder();
        hand.stream().map(Tile::getTileKind).sorted(new MahjongTileKindComparator()).forEach(tileKind -> {
            mpszStringBuilder.append(tileKind.abbreviation);
        });

        String resultString = mpszStringBuilder.toString();

        BiFunction<String, String, String> replaceLettersInString = (originalString, letterToReplace) -> {
            while (originalString.indexOf(letterToReplace) != originalString.lastIndexOf(letterToReplace))
            {
                originalString = originalString.replaceFirst(letterToReplace, "");
            }
            return originalString;
        };

        resultString = replaceLettersInString.apply(resultString, "m");
        resultString = replaceLettersInString.apply(resultString, "p");
        resultString = replaceLettersInString.apply(resultString, "s");
        resultString = replaceLettersInString.apply(resultString, "z");

        return resultString;
    }

    private List<MahjongTileKind> getTileKindsFrom(String tileText)
    {
        ArrayList<MahjongTileKind> mahjongTileKinds = new ArrayList<MahjongTileKind>();
        String remainingText = tileText;
        StringBuilder currentSuitNumbers = new StringBuilder();

        while (!remainingText.isEmpty())
        {
            char firstCharacter = remainingText.charAt(0);
            if (Character.isDigit(firstCharacter))
            {
                currentSuitNumbers.append(firstCharacter);
            }
            else if (Character.isAlphabetic(firstCharacter))
            {
                for (int i = 0; i < currentSuitNumbers.length(); i++)
                {
                    MahjongTileKind kind = MahjongTileKind.getMahjongTileByAbbreviation(currentSuitNumbers.substring(i, i + 1) + firstCharacter );
                    mahjongTileKinds.add(kind);
                }
                currentSuitNumbers = new StringBuilder();
            }
            else
            {
                throw new IllegalArgumentException("Invalid input");
            }

            remainingText = remainingText.substring(1);
        }
        return mahjongTileKinds;
    }
}
