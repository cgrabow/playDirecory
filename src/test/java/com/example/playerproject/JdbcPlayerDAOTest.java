package com.example.playerproject;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@FlywayTest
@SpringBootTest
public class JdbcPlayerDAOTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcPlayerDAO jdbcPlayerDAO;

    private static Level gameLevelMapper(ResultSet rs) throws SQLException {
        rs.next();
        return Level.builder()
                .id(Integer.parseInt(String.valueOf(rs.getString("id"))))
                .playerId(Integer.parseInt(String.valueOf(rs.getString("playerId"))))
                .game(Game.valueOf(String.valueOf(rs.getString("game"))))
                .playerLevel(PlayerLevel.valueOf(String.valueOf(rs.getString("level"))))
                .build();
    }

    private static Player playerMapper(ResultSet rs) throws SQLException {
        rs.next();
        return Player.builder()
                .playerId(Integer.parseInt(String.valueOf(rs.getString("id"))))
                .name(String.valueOf(rs.getString("name")))
                .countryCode(String.valueOf(rs.getString("geography")))
                .build();
    }

    @BeforeEach
    public void setUp() {
        jdbcPlayerDAO = new JdbcPlayerDAO(jdbcTemplate);
    }

    @Test
    public void testAddPlayer_success() {
        Player player = new Player(10, "Alice", "USA");

        var result = jdbcPlayerDAO.addPlayer(player);

        var expected = jdbcTemplate.query("SELECT id, name, geography FROM players WHERE id = ?; ",
                JdbcPlayerDAOTest::playerMapper,
                player.getPlayerId());

        assertEquals("Success", result);
        assertEquals(expected, player);
    }

    @Test
    public void testAddPlayer_identical_id_fail() {
        Player player = new Player(20, "Alice", "USA");
        Player player2 = new Player(20, "Alice", "USA");

        jdbcPlayerDAO.addPlayer(player);
        String result = jdbcPlayerDAO.addPlayer(player2);

        assertTrue(result.contains("Unique index or primary key violation") );

    }

    @Test
    public void addGameLevelToPlayer_success() {
        var level = new Level(40, 10, Game.TENNIS, PlayerLevel.PRO);

        var result = jdbcPlayerDAO.addGameLevelToPlayer(level.getId(), level.getPlayerId(), level.getGame(), level.getPlayerLevel());

        var resultQuery = jdbcTemplate.query("SELECT id, playerId, level, game FROM playerLevels WHERE id = ?; ",
                JdbcPlayerDAOTest::gameLevelMapper,
                level.getId());

        assertEquals("Success", result);
        assertEquals(level, resultQuery);
    }

    @Test
    public void addGameLevelToPlayer_identical_id_fail() {
        Level level = new Level(40, 10, Game.TENNIS, PlayerLevel.PRO);
        Level level2 = new Level(40, 10, Game.TENNIS, PlayerLevel.PRO);

        jdbcPlayerDAO.addGameLevelToPlayer(level.getId(), level.getPlayerId(), level.getGame(), level.getPlayerLevel());
        String result = jdbcPlayerDAO.addGameLevelToPlayer(level2.getId(), level2.getPlayerId(), level2.getGame(),
                level2.getPlayerLevel());


        assertTrue(result.contains("Unique index or primary key violation") );
    }

    @Test
    public void getMatchingPlayers_success() {
        List<Player> playerList = new ArrayList<>();
        Player player1 = new Player(21, "Sille", "DNK");
        jdbcPlayerDAO.addPlayer(player1);
        playerList.add(player1);
        Player player2 = new Player(22, "Daniel", "DNK");
        jdbcPlayerDAO.addPlayer(player2);
        playerList.add(player2);
        Player player3 = new Player(23, "Julie", "DNK");
        jdbcPlayerDAO.addPlayer(player3);
        playerList.add(player3);
        Player player4 = new Player(24, "Sune", "DNK");
        jdbcPlayerDAO.addPlayer(player4);
        playerList.add(player4);

        jdbcPlayerDAO.addGameLevelToPlayer(31, player1.getPlayerId(), Game.TENNIS, PlayerLevel.PRO);
        jdbcPlayerDAO.addGameLevelToPlayer(32, player2.getPlayerId(), Game.BASEBALL, PlayerLevel.PRO);
        jdbcPlayerDAO.addGameLevelToPlayer(33, player3.getPlayerId(), Game.BOWLING, PlayerLevel.PRO);
        jdbcPlayerDAO.addGameLevelToPlayer(34, player4.getPlayerId(), Game.GOLF, PlayerLevel.PRO);

        var result = jdbcPlayerDAO.getMatchingPlayers("DNK", PlayerLevel.PRO);

        assertEquals(result, playerList);
    }

    @Test
    public void getPlayerByRanking_success() {
        List<Player> playerList = new ArrayList<>();
        Player player1 = new Player(31, "Sille", "DNK");
        jdbcPlayerDAO.addPlayer(player1);
        playerList.add(player1);
        Player player2 = new Player(32, "Daniel", "DNK");
        jdbcPlayerDAO.addPlayer(player2);
        playerList.add(player2);
        Player player3 = new Player(33, "Julie", "DNK");
        jdbcPlayerDAO.addPlayer(player3);
        playerList.add(player3);
        Player player4 = new Player(34, "Sune", "DNK");
        jdbcPlayerDAO.addPlayer(player4);
        playerList.add(player4);

        jdbcPlayerDAO.addGameLevelToPlayer(41, player1.getPlayerId(), Game.TENNIS, PlayerLevel.N00B);
        jdbcPlayerDAO.addGameLevelToPlayer(42, player2.getPlayerId(), Game.TENNIS, PlayerLevel.N00B);
        jdbcPlayerDAO.addGameLevelToPlayer(43, player3.getPlayerId(), Game.TENNIS, PlayerLevel.PRO);
        jdbcPlayerDAO.addGameLevelToPlayer(44, player4.getPlayerId(), Game.TENNIS, PlayerLevel.N00B);

        var result = jdbcPlayerDAO.getPlayerByRanking(Game.TENNIS, PlayerLevel.N00B);

        playerList.remove(player3);
        assertEquals(result, playerList);
    }

    @Test
    public void getAllPlayers_success() {
        var result = jdbcPlayerDAO.getAllPlayers();

        List<Player> expected = jdbcTemplate.query("SELECT * FROM players;",
                (rs, rowNum) -> Player.builder()
                        .playerId(Integer.parseInt(String.valueOf(rs.getString("id"))))
                        .name(String.valueOf(rs.getString("name")))
                        .countryCode(String.valueOf(rs.getString("geography")))
                        .build());

        assertEquals(result, expected);
    }
}