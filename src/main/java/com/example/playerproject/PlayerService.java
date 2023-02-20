package com.example.playerproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlayerService {

    private final PlayerDAO playerDAO;

    @Autowired
    public PlayerService(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public void addPlayer(Player player) {
        playerDAO.addPlayer(player);
    }

    public void addLevelToPlayer(int id, int playerId, PlayerLevel playerLevel,  Game game) {

        playerDAO.addGameLevelToPlayer(id, playerId, game, playerLevel);
    }

    public List<Player> getMatchingPlayers(String countryCode, PlayerLevel level) {
        return playerDAO.getMatchingPlayers(countryCode, level);
    }

    public List<Player> getPlayerByRanking(Game game, PlayerLevel level) {
        return playerDAO.getPlayerByRanking(game, level);
    }

    public List<Player> getAllPlayers() {
        return playerDAO.getAllPlayers();
    }
}
