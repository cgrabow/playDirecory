package com.example.playerproject;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
@Configuration
public class JdbcPlayerDAO implements PlayerDAO {
    public JdbcTemplate jdbcTemplate;

    public JdbcPlayerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addPlayer(Player player) {
        String insertPlayerQuery =
                "INSERT INTO players (id, name, geography) " +
                "VALUES (?, ?, ?); ";
        jdbcTemplate.update(insertPlayerQuery, player.getPlayerId(), player.getName(), player.getCountryCode());
    }

    @Override
    public void addGameLevelToPlayer(int id, int playerId, PlayerLevel playerLevel,  Game game) {
        String insertGameLevelQuery =
                "INSERT INTO playerLevels (id, playerId, level, game)" +
                "VALUES (?, ?, ?, ?); ";
        jdbcTemplate.update(insertGameLevelQuery, id, playerId, playerLevel, game);
    }

    @Override
    public List<Player> getMatchingPlayers(String countryCode, Level level) {
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
                         countryCode, level);
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
                        playerLevel, game);
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
}
