package com.example.playerproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playerproject")
public class PlayerController {
    @Autowired
    private PlayerService service;

    @GetMapping("/players")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getAllPlayers() {
        return service.getAllPlayers();
    }

    @GetMapping("/levels")
    @ResponseStatus(HttpStatus.OK)
    public List<Level> getAllLevels() {
        return service.getAllLevels();
    }

    @PostMapping("/players")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlayer(@RequestBody Player player) {
        service.addPlayer(player);
    }

    @PostMapping("/levels/{playerId}/{id}/{playerLevel}/{game}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addLevelToPlayer(@PathVariable("id") int id, @PathVariable("playerId") int playerId,
                                 @PathVariable("playerLevel") PlayerLevel playerLevel,
                                 @PathVariable("game") Game game) {
        service.addLevelToPlayer(id, playerId, playerLevel, game);
    }

    @GetMapping("/players/{countryCode}/{level}")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getMatchingPlayers(@PathVariable("countryCode") String countryCode,
                                           @PathVariable("level") PlayerLevel level) {
        return service.getMatchingPlayers(countryCode, level);
    }

    @GetMapping("/players/{game}/{playerLevel}")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getPlayersByRanking(@PathVariable("game") Game game,
                                            @PathVariable("playerLevel") PlayerLevel playerLevel) {
        return service.getPlayerByRanking(game, playerLevel);
    }
}
