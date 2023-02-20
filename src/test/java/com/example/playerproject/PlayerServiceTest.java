package com.example.playerproject;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@FlywayTest
@SpringBootTest
class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    @Mock
    private PlayerDAO playerDAO;

    @BeforeEach
    public void setUp() {
        playerService = new PlayerService(playerDAO);
    }

    @Test
    void addPlayer() {

        final var player = new Player(10, "Alice", "USA");

        // Act
        playerService.addPlayer(player);

        // Assert
        verify(playerDAO, times(1)).addPlayer(any(Player.class));
        verifyNoMoreInteractions(playerDAO);
    }

    @Test
    void addLevelToPlayer() {
        final var player = new Player(11, "Molly", "NOR");
        final var level = new Level(32, player.getPlayerId(), Game.BASEBALL, PlayerLevel.INVINCIBLE);

        // Act
        playerService.addLevelToPlayer(level.getId(), level.getPlayerId(), level.getPlayerLevel(), level.getGame());

        // Assert
        verify(playerDAO, times(1)).addGameLevelToPlayer(any(int.class), any(int.class),
                any(Game.class), any(PlayerLevel.class));
        verifyNoMoreInteractions(playerDAO);
    }

    @Test
    void getMatchingPlayers() {
        final var expectedPlayer = new Player(13, "Lilly", "DEU");
        List<Player> playerList = new ArrayList<>();
        playerList.add(expectedPlayer);

        when(playerDAO.getMatchingPlayers(any(String.class), any(PlayerLevel.class))).thenReturn(playerList);

        // Act
        final var actual = playerDAO.getMatchingPlayers("DEU", PlayerLevel.PRO);

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(playerList);
        verify(playerDAO, times(1)).getMatchingPlayers(any(String.class), any(PlayerLevel.class));
        verifyNoMoreInteractions(playerDAO);
    }

    @Test
    void getPlayerByRanking() {
        final var expectedPlayer = new Player(14, "Sara", "GBR");
        List<Player> playerList = new ArrayList<>();
        playerList.add(expectedPlayer);

        when(playerDAO.getPlayerByRanking(any(Game.class), any(PlayerLevel.class))).thenReturn(playerList);

        // Act
        final var actual = playerDAO.getPlayerByRanking(Game.BOWLING, PlayerLevel.PRO);

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(playerList);
        verify(playerDAO, times(1)).getPlayerByRanking(any(Game.class), any(PlayerLevel.class));
        verifyNoMoreInteractions(playerDAO);
    }

    @Test
    void getAllPlayers() {
        final var expectedPlayer = new Player(13, "Peter", "SWE");
        List<Player> playerList = new ArrayList<>();
        playerList.add(expectedPlayer);

        when(playerDAO.getAllPlayers()).thenReturn(playerList);

        // Act
        final var actual = playerDAO.getAllPlayers();

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(playerList);
        verify(playerDAO, times(1)).getAllPlayers();
        verifyNoMoreInteractions(playerDAO);
    }
}