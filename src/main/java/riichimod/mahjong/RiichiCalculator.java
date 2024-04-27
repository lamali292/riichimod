package riichimod.mahjong;

import riichimod.mahjong.rules.shanten.parsing.TileGroup;
import riichimod.mahjong.rules.utils.MahjongTileKind;
import riichimod.mahjong.rules.yakus.Yaku;
import riichimod.mahjong.rules.scoring.RiichiScoring;
import riichimod.mahjong.rules.scoring.RiichiScoringParametersImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RiichiCalculator {
    public static List<List<TileGroup>> calcTileGroups(List<MahjongTileKind> tileList) {
        Collections.sort(tileList);
        List<List<TileGroup>> allGroups = new ArrayList<>();
        List<MahjongTileKind> bam = tileList.stream().filter(MahjongTileKind::isBamboos).collect(Collectors.toList());
        List<MahjongTileKind> cir = tileList.stream().filter(MahjongTileKind::isCircles).collect(Collectors.toList());
        List<MahjongTileKind> cha = tileList.stream().filter(MahjongTileKind::isCharacters).collect(Collectors.toList());
        List<MahjongTileKind> hon = tileList.stream().filter(MahjongTileKind::isHonour).collect(Collectors.toList());
        List<List<TileGroup>> bamGroups = calcNumberGroups(bam);
        List<List<TileGroup>> cirGroups = calcNumberGroups(cir);
        List<List<TileGroup>> chaGroups = calcNumberGroups(cha);
        if (bamGroups.isEmpty()) bamGroups.add(new ArrayList<>());
        if (cirGroups.isEmpty()) cirGroups.add(new ArrayList<>());
        if (chaGroups.isEmpty()) chaGroups.add(new ArrayList<>());
        List<TileGroup> honGroup = calcHonorGroup(hon);
        for (List<TileGroup> bamGroup : bamGroups) {
            for (List<TileGroup> cirGroup : cirGroups) {
                for (List<TileGroup> chaGroup : chaGroups) {
                    ArrayList<TileGroup> all = new ArrayList<>();
                    all.addAll(bamGroup);
                    all.addAll(cirGroup);
                    all.addAll(chaGroup);
                    all.addAll(honGroup);
                    allGroups.add(all);
                }
            }
        }
        return allGroups;
    }

    public static List<TileGroup> calcHonorGroup(List<MahjongTileKind> hon) {
        List<TileGroup> groups = new ArrayList<>();
        for (MahjongTileKind tile : MahjongTileKind.getAllHonours()) {
            int id = tile.getIndex();
            if (hon.contains(tile)) {
                int sameTileSize = (int) hon.stream().filter(tile::equals).count();
                if (sameTileSize==2) groups.add(new TileGroup(id,id));
                else if (sameTileSize==3) groups.add(new TileGroup(id,id,id));
                else if (sameTileSize==4) groups.add(new TileGroup(id,id,id,id));
            }
        }
        return groups;
    }

    public static List<List<TileGroup>> calcNumberGroups(List<MahjongTileKind> tileList) {
        List<List<TileGroup>> numGroups = new ArrayList<>();
        List<TileGroup> groups = calcGroupFromFirstTile(tileList);
        for (TileGroup tileGroup : groups) {
            List<MahjongTileKind> newList = getListWithoutGroup(tileList, tileGroup);
            List<List<TileGroup>> newGroups = new ArrayList<>();
            if (newList.isEmpty()) {
                List<TileGroup> newGroup = new ArrayList<>();
                newGroup.add(tileGroup);
                newGroups.add(newGroup);
            } else {
                newGroups = calcNumberGroups(newList);
                for (List<TileGroup> newGroup : newGroups) {
                    newGroup.add(tileGroup);
                }
            }
            numGroups.addAll(newGroups);
        }
        return numGroups;
    }

    public static List<MahjongTileKind> getListWithoutGroup(List<MahjongTileKind> tileList, TileGroup group) {
        List<MahjongTileKind> numGroups = new ArrayList<>(tileList);
        for (MahjongTileKind tile : group.getTileKinds()) {
            numGroups.remove(tile);
        }
        return numGroups;
    }

    public static List<TileGroup> calcGroupFromFirstTile(List<MahjongTileKind> tileList) {
        List<TileGroup> groups = new ArrayList<>();
        if (tileList.isEmpty()) return groups;
        MahjongTileKind firstTile = tileList.get(0);
        int id = firstTile.getIndex();
        // pair, triplet, quadruplet
        int sameTileSize = (int) tileList.stream().filter(firstTile::equals).count();
        if (sameTileSize>=2) groups.add(new TileGroup(id,id));
        if (sameTileSize>=3) groups.add(new TileGroup(id,id,id));
        if (sameTileSize>=4) groups.add(new TileGroup(id,id,id,id));
        // sequence
        int seq = (int) tileList.stream().filter((tile) -> isInConSeq(firstTile,tile)).distinct().count();
        if (seq == 3) groups.add(new TileGroup(id,id+1,id+2));
        return groups;
    }

    public static boolean isInConSeq(MahjongTileKind firstTile,MahjongTileKind secondTile) {
        return secondTile.getTileNumber()>=firstTile.getTileNumber() && secondTile.getTileNumber()<=firstTile.getTileNumber()+2;
    }

    public static List<Yaku> getYakus(PlayerHand hand, RiichiScoringParametersImpl scoringParameters){
        RiichiScoring scoring = new RiichiScoring();
        List<List<Yaku>> yakuss = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        // System.out.println(hand.getTiles().stream().map(Tile::getTileKind).collect(Collectors.toList()));
        List<TileGroup> melded = hand.getMelds();
        List<List<TileGroup>> handCombinations = calcTileGroups(hand.getUnmeldedTiles().stream().map(Tile::getTileKind).collect(Collectors.toList()));
        for (List<TileGroup> tileGroups : handCombinations) {
            tileGroups.addAll(melded);
            List<Yaku> yakus;
            if (isValidShape(tileGroups)){
                yakus = scoring.getValidYakus(hand, tileGroups, scoringParameters);
                if (!yakus.isEmpty()) {
                    scores.add(scoring.getScore(tileGroups,scoringParameters));
                    yakuss.add(yakus);
                }
            }

        }
        if (!scores.isEmpty()) {
            int maxscore = 0;
            int maxid = -1;
            for (int i = 0; i < scores.size(); i++) {
                if (scores.get(i) > maxscore) {
                    maxscore = scores.get(i);
                    maxid = i;
                }
            }
            return yakuss.get(maxid);
        }
        return new ArrayList<>();
    }

    public static boolean isValidShape(List<TileGroup> tileGroups) {
        return isFourGroupsOnePairShape(tileGroups) || isSevenPairsShape(tileGroups) || isThirteenOrphansShape(tileGroups);
    }

    public static boolean isThirteenOrphansShape(List<TileGroup> tileGroups) {
        return tileGroups.size() == 13
                && tileGroups.stream().map(TileGroup::getSize).filter(t->t==1).count() == 12
                && tileGroups.stream().map(TileGroup::getSize).filter(t->t==2).count() == 1;
    }
    public static boolean isSevenPairsShape(List<TileGroup> tileGroups) {
        return tileGroups.size() == 7
                && tileGroups.stream().map(TileGroup::getSize).filter(t->t==2).count() == 7;
    }

    public static boolean isFourGroupsOnePairShape(List<TileGroup> tileGroups) {
        return tileGroups.size() == 5
                && tileGroups.stream().map(TileGroup::getSize).filter(t->t==3||t==4).count() == 4
                && tileGroups.stream().map(TileGroup::getSize).filter(t->t==2).count() == 1;
    }

    public static List<TileGroup> getAllTileGroups(List<MahjongTileKind> tileKinds) {
        return Stream.of(getSequences(tileKinds), getTriplets(tileKinds), getQuadruplets(tileKinds)).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public static List<TileGroup> getSequences(List<MahjongTileKind> tileKinds) {
        return tileKinds.stream().filter(MahjongTileKind::isNumeral).filter(t->t.getTileNumber()<=7).distinct().map(MahjongTileKind::getIndex)
                .filter(
                        t->tileKinds.stream().map(MahjongTileKind::getIndex).distinct().filter(g->(g==t+1 || g == t+2)).count() == 2
                ).map(t->new TileGroup(t,t+1,t+2)).collect(Collectors.toList());
    }

    public static List<TileGroup> getTriplets(List<MahjongTileKind> tileKinds) {
        return tileKinds.stream().distinct().filter(t->tileKinds.stream().filter(t::equals).count() >= 3).map(MahjongTileKind::getIndex).map(t->new TileGroup(t,t,t)).collect(Collectors.toList());
    }

    public static List<TileGroup> getQuadruplets(List<MahjongTileKind> tileKinds) {
        return tileKinds.stream().distinct().filter(t->tileKinds.stream().filter(t::equals).count() >= 4).map(MahjongTileKind::getIndex).map(t->new TileGroup(t,t,t,t)).collect(Collectors.toList());
    }

}
