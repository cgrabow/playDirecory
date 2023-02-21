package com.example.playerproject;

import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
@Configuration
public class JdbcPlayerDAO implements PlayerDAO {
    private final JdbcTemplate jdbcTemplate;

    public JdbcPlayerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String addPlayer(Player player) {
        String insertPlayerQuery =
                "INSERT INTO players (id, name, geography) " +
                "VALUES (?, ?, ?); ";
        try {
            jdbcTemplate.update(insertPlayerQuery, player.getPlayerId(), player.getName(), player.getCountryCode());
            return "Success";
        }
        catch (DuplicateKeyException e) {
            return e.getMessage();}
    }

    @Override
    public String addGameLevelToPlayer(int id, int playerId, Game game, PlayerLevel playerLevel) {
        String insertGameLevelQuery =
                "INSERT INTO playerLevels (id, playerId, game, level)" +
                "VALUES (?, ?, ?, ?); ";
        try{
            jdbcTemplate.update(insertGameLevelQuery, id, playerId, game.name(), playerLevel.name());
            return "Success";
        }
        catch (DuplicateKeyException e) {
            return e.getMessage();}
    }

    @Override
    public List<Player> getMatchingPlayers(String countryCode, PlayerLevel playerLevel) {
        String getMatchingPlayersQuery =
                "SELECT p.id, p.name, p.geography " +
                " FROM players p " +
                " JOIN playerLevels l ON p.id = l.playerId " +
                " WHERE p.geography = ? AND l.level = ?; ";
         return jdbcTemplate.query(getMatchingPlayersQuery, (rs, rowNum) -> Player.builder()
                         .playerId(Integer.parseInt(String.valueOf(rs.getString("id"))))
                         .name(String.valueOf(rs.getString("name")))
                         .countryCode(String.valueOf(rs.getString("geography")))
                         .build(),
                         countryCode, playerLevel.name());
    }

    @Override
    public List<Player> getPlayerByRanking(Game game, PlayerLevel playerLevel) {
        String getPlayersByRankingQuery =
                "SELECT p.id, p.name, p.geography " +
                " FROM players p " +
                " JOIN playerLevels l ON p.id = l.playerId " +
                " WHERE l.level = ? AND l.game = ?; ";
        return jdbcTemplate.query(getPlayersByRankingQuery, (rs, rowNum) -> Player.builder()
                        .playerId(Integer.parseInt(String.valueOf(rs.getString("id"))))
                        .name(String.valueOf(rs.getString("name")))
                        .countryCode(String.valueOf(rs.getString("geography")))
                        .build(),
                        playerLevel.name(), game.name());
    }

    @Override
    public List<Player> getAllPlayers() {
        String getAllPlayers =
                "SELECT p.id, p.name, p.geography " +
                        " FROM players p ";
        return jdbcTemplate.query(getAllPlayers, (rs, rowNum) -> Player.builder()
                        .playerId(Integer.parseInt(String.valueOf(rs.getString("id"))))
                        .name(String.valueOf(rs.getString("name")))
                        .countryCode(String.valueOf(rs.getString("geography")))
                        .build());
    }

    @Override
    public List<Level> getAllLevels() {
        String getAllLevels =
                "SELECT l.id, l.playerId, l.game, l.level " +
                        " FROM playerLevels l ";
        return jdbcTemplate.query(getAllLevels, (rs, rowNum) -> Level.builder()
                .id(Integer.parseInt(String.valueOf(rs.getString("id"))))
                .playerId(Integer.parseInt(String.valueOf(rs.getString("playerId"))))
                .game(Game.valueOf(String.valueOf(rs.getString("game"))))
                .playerLevel(PlayerLevel.valueOf(String.valueOf(rs.getString("level"))))
                .build());
    }
}
