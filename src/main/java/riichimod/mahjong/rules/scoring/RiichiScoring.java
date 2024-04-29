package riichimod.mahjong.rules.scoring;

import java.util.List;
import java.util.stream.Collectors;

import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.rules.utils.TileGroupUtils;
import riichimod.mahjong.rules.utils.TileGroup;
import riichimod.mahjong.rules.yakus.Yaku;
import riichimod.mahjong.rules.yakus.Yakus;

public class RiichiScoring {
    public int getScore(List<TileGroup> tileGroups, RiichiScoringParameters parameters)
    {
        PlayerHand hand = new PlayerHand(TileGroupUtils.getTilesFromTileGroups(tileGroups));
        List<Yaku> yakus = Yakus.getStandardYakus(hand, tileGroups, parameters);
        int hanTotal = yakus.stream().filter(Yaku::isValid).mapToInt(Yaku::getHanValue).sum();

        RiichiScoreCalculator scoreCalculator = new RiichiScoreCalculator();
        return scoreCalculator.getRonScore(hanTotal, 30, parameters.isPlayerDealer());
    }

    public List<Yaku> getValidYakus(PlayerHand hand, List<TileGroup> tileGroups, RiichiScoringParameters parameters)
    {
        List<Yaku> yakus = Yakus.getStandardYakus(hand, tileGroups, parameters);
        return yakus.stream().filter(Yaku::isValid).collect(Collectors.toList());
    }
}
