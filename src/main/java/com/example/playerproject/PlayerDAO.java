package com.example.playerproject;

import java.util.List;

public interface PlayerDAO {

    void savePlayer(Player player);

    void addGameLevelToPlayer(Level level, Player player);

    List<Player> getMatchingPlayers(String countryCode, Level level);

    List<Player> getPlayerByRanking();
}
