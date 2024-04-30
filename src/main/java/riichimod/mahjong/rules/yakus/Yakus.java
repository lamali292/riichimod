package riichimod.mahjong.rules.yakus;

import java.util.ArrayList;
import java.util.List;

import riichimod.mahjong.hand.PlayerHand;
import riichimod.mahjong.utils.TileGroup;
import riichimod.mahjong.rules.scoring.RiichiScoringParameters;
import riichimod.mahjong.rules.yakus.groupbased.*;
import riichimod.mahjong.rules.yakus.groupbased.yakuhai.*;
import riichimod.mahjong.rules.yakus.tilebased.*;
import riichimod.mahjong.rules.yakus.timingbased.*;

public class Yakus
{
    // Factory method for the standard yakus
    public static List<Yaku> getStandardYakus(PlayerHand hand, List<TileGroup> tileGroups, RiichiScoringParameters parameters)
    {
        ArrayList<Yaku> yakuList = new ArrayList<>();

        // group-based yakus
        yakuList.add(new GreenDragonYakuhai(hand, tileGroups));
        yakuList.add(new RedDragonYakuhai(hand, tileGroups));
        yakuList.add(new SeatWindYakuhai(hand, tileGroups));
        yakuList.add(new TableWindYakuhai(hand, tileGroups));
        yakuList.add(new WhiteDragonYakuhai(hand, tileGroups));
        yakuList.add(new Chanta(hand, tileGroups));
        yakuList.add(new Chiitoitsu(hand, tileGroups));
        yakuList.add(new ChuurenPoutou(hand, tileGroups));
        yakuList.add(new Daisangen(hand, tileGroups));
        yakuList.add(new Iipeikou(hand, tileGroups));
        yakuList.add(new Ittsu(hand, tileGroups));
        yakuList.add(new Junchan(hand, tileGroups));
        yakuList.add(new KokushiMusou(hand, tileGroups));
        yakuList.add(new Pinfu(hand, tileGroups));
        yakuList.add(new Ryanpeikou(hand, tileGroups));
        yakuList.add(new Sanankou(hand, tileGroups));
        yakuList.add(new Sankantsu(hand, tileGroups));
        yakuList.add(new SanshokuDoujun(hand, tileGroups));
        yakuList.add(new SanshokuDoukou(hand, tileGroups));
        yakuList.add(new Shousangen(hand, tileGroups));
        yakuList.add(new Suuankou(hand, tileGroups));
        yakuList.add(new Suukantsu(hand, tileGroups));
        yakuList.add(new Toitoi(hand, tileGroups));

        // tile-based yakus
        yakuList.add(new Chinitsu(hand));
        yakuList.add(new Chinroutou(hand));
        yakuList.add(new Honitsu(hand));
        yakuList.add(new Honroutou(hand));
        yakuList.add(new Ryuuiisou(hand));
        yakuList.add(new Tanyao(hand));
        yakuList.add(new Tsuuiisou(hand));

        // timing-based yakus
        yakuList.add(new Chankan(parameters));
        yakuList.add(new Chihou(parameters));
        yakuList.add(new Haitei(parameters));
        yakuList.add(new Houtei(parameters));
        yakuList.add(new Ippatsu(parameters));
        yakuList.add(new MenzenTsumo(parameters));
        yakuList.add(new NagashiMangan(parameters));
        yakuList.add(new Renhou(parameters));
        yakuList.add(new Riichi(parameters));
        yakuList.add(new RinshanKaihou(parameters));
        yakuList.add(new Tenhou(parameters));

        return yakuList;
    }
}
