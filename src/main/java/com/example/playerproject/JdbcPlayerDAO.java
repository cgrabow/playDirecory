package com.example.playerproject;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcPlayerDAO implements PlayerDAO {
    public JdbcTemplate jdbcTemplate;

    public JdbcPlayerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void savePlayer(Player player) {
        String insertPlayerQuery =
                "INSERT INTO players (id, name, geography) " +
                "VALUES (?, ?, ?); ";
        jdbcTemplate.update(insertPlayerQuery, player.getPlayerId(), player.getName(), player.getCountryCode());
    }

    @Override
    public void addGameLevelToPlayer(Level level, Player player) {
        String insertGameLevelQuery =
                "INSERT INTO playerLevels (id, playerId, level, game)" +
                "VALUES (?, ?, ?, ?); ";
        jdbcTemplate.update(insertGameLevelQuery, level.getId(), player.getPlayerId(), level.getPlayerLevel(), level.getGame());
    }

    @Override
    public List<Player> getMatchingPlayers(String countryCode, Level level) {
        String getMatchingPlayersQuery =
                "SELECT id, name, geography " +
                " FROM players " +
                " JOIN playerLevels ON p.id = l.playerId " +
                " WHERE p.geography = ? AND l.level = ?; ";
         return jdbcTemplate.query(getMatchingPlayersQuery, (rs, rowNum) -> Player.builder()
                         .playerId(String.valueOf(rs.getString("id")))
                         .name(String.valueOf(rs.getString("name")))
                         .countryCode(String.valueOf(rs.getString("geography")))
                         .build(),
                         countryCode, level);
    }

    @Override
    public List<Player> getPlayerByRanking() {
        return null;
    }
}
