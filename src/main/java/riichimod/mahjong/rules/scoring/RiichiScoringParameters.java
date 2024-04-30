package riichimod.mahjong.rules.scoring;

public interface RiichiScoringParameters
{
    boolean hasPlayerDeclaredRiichi();

    boolean isPlayerDealer();

    boolean doesPlayerWinOnIppatsu();

    boolean doesPlayerWinOnTenhou();

    boolean doesPlayerWinOnChihou();

    boolean doesPlayerWinOnRenhou();

    boolean doesPlayerWinOnMenzenTsumo();

    boolean doesPlayerWinOnRinshanKaihou();

    boolean doesPlayerWinOnChankan();

    boolean doesPlayerWinOnHaitei();

    boolean doesPlayerWinOnHoutei();

    boolean doesPlayerWinOnNagashiMangan();

    boolean doesPlayerWinOnRon();

    boolean doesPlayerWinOnTsumo();
}
