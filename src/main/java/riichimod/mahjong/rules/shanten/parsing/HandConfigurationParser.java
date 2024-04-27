package riichimod.mahjong.rules.shanten.parsing;

import one.util.streamex.StreamEx;
import riichimod.mahjong.PlayerHand;
import riichimod.mahjong.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class HandConfigurationParser
{
    private final PlayerHand hand;
    private final List<Tile> unmeldedTiles;

    public HandConfigurationParser(PlayerHand hand)
    {
        this.hand = hand;
        unmeldedTiles = hand.getUnmeldedTiles();
    }

    private List<TileGroup> getMeldedTileGroups() {
        return hand.getMelds();
    }

    /**
     * This method computes all hand configurations that are possible for the
     * current hand using the known tile groups that are supplied as a parameter.
     * All melded tile groups are added automatically to all hand configurations
     * because they can't be changed.
     *
     * @param tileGroups possible tile groups that can be created with the hand
     */
    public List<List<TileGroup>> getHandConfigurations(List<TileGroup> tileGroups)
    {
        // first, create the collision list for all groups that collide with each other.
        // All groups that have no collisions are not added in the list
        List<List<TileGroup>> collisionList = new TileCollisions().createCollisionList(tileGroups);

        // then dissect these collision groups to get the different possible pairings
        List<List<List<TileGroup>>> listOfPossiblePairings = new ArrayList<>();
        PossiblePairings possiblePairings = new PossiblePairings(unmeldedTiles);
        for (List<TileGroup> collidingGroups : collisionList)
        {
            if (collidingGroups == null) continue;
            // create the pairings for the current group
            listOfPossiblePairings.add(possiblePairings.createFrom(collidingGroups));
        }

        // create hand configurations from possible pairing lists
        List<List<TileGroup>> handConfigurations = createHandConfigurations(listOfPossiblePairings);

        // add tile groups that are not within a collision list to the result, then add
        // melded tiles to the result
        handConfigurations.forEach(list -> {
            list.addAll(getTileGroupsWithoutCollisions(tileGroups, collisionList));
            list.addAll(getMeldedTileGroups());
        });

        return handConfigurations;
    }

    private List<TileGroup> getTileGroupsWithoutCollisions(List<TileGroup> tileGroups, List<List<TileGroup>> collisionList)
    {
        return tileGroups.stream() //
                .filter(tileGroup -> collisionList.stream().noneMatch(list -> list.contains(tileGroup))).collect(Collectors.toList()); //;
    }

    /**
     * Hand configs are built in a similar fashion than combinations are, as they
     * are basically a combination of combinations.
     */
    private List<List<TileGroup>> createHandConfigurations(List<List<List<TileGroup>>> possiblePairingsList)
    {
        return StreamEx.cartesianProduct(possiblePairingsList) //
                .map(list -> list.stream().flatMap(Collection::stream).collect(Collectors.toList())) //
                .toList();
    }
}
