package com.example.playerproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foo")
public class PlayerController {
    @Autowired
    private PlayerService service;

    @GetMapping(value = "/players")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getAllPlayers() {
        return service.getAllPlayers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlayer(@RequestBody Player player) {
        service.addPlayer(player);
    }

    @PostMapping(value = "/{playerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addLevelToPlayer(@RequestBody Level level, @PathVariable("playerId") String playerId) {
        service.addLevelToPlayer(level, playerId);
    }

    @GetMapping(value = "/{countryCode}/{level}")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getMatchingPlayers(@PathVariable("countryCode") String countryCode, @PathVariable("level") Level level) {
        return service.getMatchingPlayers(countryCode, level);
    }

    @GetMapping(value = "/{game}/{playerLevel}")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getPlayersByRanking(@PathVariable("game") Game game, @PathVariable("playerLevel") PlayerLevel playerLevel) {
        return service.getPlayerByRanking(game, playerLevel);
    }

}
