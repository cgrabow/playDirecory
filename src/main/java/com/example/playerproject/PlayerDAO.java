package com.example.playerproject;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PlayerDAO {

    String addPlayer(Player player);

    String addGameLevelToPlayer(int id, int playerId, Game game, PlayerLevel playerLevel);

    List<Player> getMatchingPlayers(String countryCode, PlayerLevel level);

    List<Player> getPlayerByRanking(Game game, PlayerLevel playerLevel);

    List<Player> getAllPlayers();
}
