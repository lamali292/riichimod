package riichimod.mahjong.rules.scoring;

import java.util.HashMap;
import java.util.Map;

import riichimod.mahjong.utils.Seat;

public class RiichiScoreCalculator
{
    private boolean kiriageMangan;

    public RiichiScoreCalculator()
    {
        kiriageMangan = false;
    }

    public void setKiriageMangan(boolean kiriageMangan)
    {
        this.kiriageMangan = kiriageMangan;
    }

    public Map<Seat, Integer> getTsumoScore(int han, int fu, Seat winner)
    {
        return getTsumoScore(han, fu, 0, winner);
    }

    public Map<Seat, Integer> getTsumoScore(int han, int fu, int yakuman, Seat winner)
    {
        Map<Seat, Integer> seatPayments = new HashMap<>();

        int baseScore = yakuman > 0 //
                ? getYakumanBaseScore(yakuman) //
                : getBaseScore(han, fu);

        if (winner == Seat.EAST)
        {
            int paidScore = roundToUpperHundred(baseScore / 2);
            seatPayments.put(Seat.SOUTH, paidScore);
            seatPayments.put(Seat.WEST, paidScore);
            seatPayments.put(Seat.NORTH, paidScore);
        }
        else
        {
            int dealerScore = roundToUpperHundred(baseScore / 2);
            int nonDealerScore = roundToUpperHundred(baseScore / 4);

            seatPayments.put(Seat.EAST, dealerScore);
            seatPayments.put(Seat.SOUTH, nonDealerScore);
            seatPayments.put(Seat.WEST, nonDealerScore);
            seatPayments.put(Seat.NORTH, nonDealerScore);
            seatPayments.remove(winner);
        }

        return seatPayments;
    }

    public int getRonScore(int han, int fu, boolean dealer)
    {
        return getRonScore(han, fu, 0, dealer);
    }

    public int getRonScore(int han, int fu, int yakuman, boolean dealer)
    {
        int baseScore = yakuman > 0 //
                ? getYakumanBaseScore(yakuman) //
                : getBaseScore(han, fu);

        if (dealer)
        {
            baseScore = (int) (baseScore * 1.5);
        }
        return roundToUpperHundred(baseScore);
    }

    private int getBaseScore(int han, int fu)
    {
        if (han < 1 || fu < 20)
        {
            return 0;
        }

        ScoringTier scoringTier = getScoringTier(han);

        return getBaseScore(han, fu, scoringTier);
    }

    /**
     * The base score is the score someone gets on ron, as non-dealer, without
     * rounding to the upper hundred
     */
    private int getBaseScore(int han, int fu, ScoringTier scoringTier)
    {
        switch (scoringTier)
        {
        case MANGAN:
            return 8000;
        case HANEMAN:
            return 12000;
        case BAIMAN:
            return 16000;
        case SANBAIMAN:
            return 24000;
        case YAKUMAN:
            return 32000;
        case DOUBLE_YAKUMAN:
            return 64000;
        case TRIPLE_YAKUMAN:
            return 96000;
        case QUADRUPLE_YAKUMAN:
            return 128000;
        case QUINTUPLE_YAKUMAN:
            return 160000;
        case SEXTUPLE_YAKUMAN:
            return 192000;
        case NORMAL:
        default:
            return calculateScore(han, fu);
        }
    }

    private int getYakumanBaseScore(int yakuman)
    {
        ScoringTier scoringTier = getYakumanScoringTier(yakuman);

        return getBaseScore(0, 0, scoringTier);
    }

    private int calculateScore(int han, int fu)
    {
        if (kiriageMangan && han * fu >= 120)
        {
            return 8000;
        }

        int score = (int) (32 * fu * Math.pow(2, han - 1));
        return Math.min(score, 8000);
    }

    private ScoringTier getScoringTier(int han)
    {
        if (han == 5)
        {
            return ScoringTier.MANGAN;
        }
        if (han == 6 || han == 7)
        {
            return ScoringTier.HANEMAN;
        }
        if (han == 8 || han == 9 || han == 10)
        {
            return ScoringTier.BAIMAN;
        }
        if (han == 11 || han == 12)
        {
            return ScoringTier.SANBAIMAN;
        }
        if (han >= 13)
        {
            return ScoringTier.YAKUMAN;
        }

        return ScoringTier.NORMAL;
    }

    private ScoringTier getYakumanScoringTier(int yakuman)
    {
        if (yakuman == 1)
        {
            return ScoringTier.YAKUMAN;
        }
        if (yakuman == 2)
        {
            return ScoringTier.DOUBLE_YAKUMAN;
        }
        if (yakuman == 3)
        {
            return ScoringTier.TRIPLE_YAKUMAN;
        }
        if (yakuman == 4)
        {
            return ScoringTier.QUADRUPLE_YAKUMAN;
        }
        if (yakuman == 5)
        {
            return ScoringTier.QUINTUPLE_YAKUMAN;
        }
        if (yakuman == 6)
        {
            return ScoringTier.SEXTUPLE_YAKUMAN;
        }

        throw new IllegalArgumentException("No yakumans or too many yakumans");
    }

    private static int roundToUpperHundred(int score)
    {
        if (score % 100 == 0)
        {
            return score;
        }
        return (score / 100 + 1) * 100;
    }
}
