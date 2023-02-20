package com.example.playerproject;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PlayerDAO {

    void addPlayer(Player player);

    void addGameLevelToPlayer(int id, int playerId, PlayerLevel playerLevel,  Game game);

    List<Player> getMatchingPlayers(String countryCode, Level level);

    List<Player> getPlayerByRanking(Game game, PlayerLevel playerLevel);

    List<Player> getAllPlayers();
}
