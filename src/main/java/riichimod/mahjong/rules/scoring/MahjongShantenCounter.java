package riichimod.mahjong.rules.scoring;

import riichimod.mahjong.rules.shanten.parsing.TileGroup;
import riichimod.mahjong.rules.shanten.parsing.TileGroupLengthComparator;
import riichimod.mahjong.rules.shanten.WaitShapeEngine;
import riichimod.mahjong.rules.utils.MahjongTileKind;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MahjongShantenCounter
{
    private final List<TileGroup> tileGroups;

    public MahjongShantenCounter(List<TileGroup> tileGroups)
    {
        this.tileGroups = tileGroups;
    }

    /**
     * This method counts all shanten for a specific hand combination. It does not
     * count all combinations that are possible for the current hand. See
     * {@link WaitShapeEngine} for hand parsing.
     *
     * @return shanten count. 0 is tenpai, -1 is mahjong-complete
     */
    public int countShanten()
    {
        int kokushiShantenCount = countKokushiShanten();
        int sevenPairsShantenCount = countSevenPairsShanten();
        int fourGroupsOnePairShantenCount = countFourGroupsOnePairShanten();

        int lowestShantenCount = Math.min(kokushiShantenCount, sevenPairsShantenCount);
        lowestShantenCount = Math.min(lowestShantenCount, fourGroupsOnePairShantenCount);
        return lowestShantenCount;
    }

    private int countKokushiShanten()
    {
        List<MahjongTileKind> terminalAndHonourCatalog = new ArrayList<>(MahjongTileKind.getAllTerminalsAndHonours());
        boolean pairFound = false;

        for (TileGroup group : tileGroups)
        {
            if (!pairFound && group.getIndices().size() > 1)
            {
                if (group.isPair() || group.isTriplet()) // check also for triplets, they can also influence shanten
                {
                    MahjongTileKind kind = group.getTileKindAt(0);
                    boolean isRemoved = terminalAndHonourCatalog.remove(kind);
                    if (isRemoved)
                    {
                        pairFound = true;
                        continue;
                    }
                }
            }

            Optional<Integer> currentTerminalOrHonour = group.getIndices().stream().filter(index -> {
                MahjongTileKind kind = MahjongTileKind.getKindFromIndex(index);
                return kind.isTerminal() || kind.isHonour();
            }).findFirst();

            if (currentTerminalOrHonour.isPresent())
            {
                MahjongTileKind kind = MahjongTileKind.getKindFromIndex(currentTerminalOrHonour.get());
                terminalAndHonourCatalog.remove(kind);
            }
        }

        int shantenCount = terminalAndHonourCatalog.size();
        if (pairFound)
        {
            shantenCount--;
        }
        return shantenCount;
    }

    private int countSevenPairsShanten()
    {
        int amountOfPairs = 0;

        for (TileGroup group : tileGroups)
        {
            if (group.isPair() || group.isTriplet())
            {
                amountOfPairs++;
            }
        }

        return 6 - amountOfPairs;
    }

    private int countFourGroupsOnePairShanten()
    {
        int shanten = 8;
        boolean pairFound = false;

        tileGroups.sort(new TileGroupLengthComparator());
        for (int i = 0; i < Math.min(tileGroups.size(), 5); i++)
        {
            TileGroup currentGroup = tileGroups.get(i);
            if (!pairFound && currentGroup.isPair())
            {
                pairFound = true;
            }

            int currentGroupSize = Math.min(currentGroup.getSize(), 3);
            shanten = shanten - (currentGroupSize - 1);
        }

        if (!pairFound)
        {
            shanten++;
        }

        return shanten;
    }
}
