package edu.sg.nus.iss.day35.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sg.nus.iss.day35.models.GameSummary;
import edu.sg.nus.iss.day35.repositories.GamesRepository;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

@Controller
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class GamesController {

    @Autowired
    private GamesRepository gamesRepo;

    @GetMapping(path = "/games")
    @ResponseBody
    public ResponseEntity<String> getGames(
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int skip) {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        List<GameSummary> games = gamesRepo.getGames(filter, skip, limit);
        games.stream()
                .map(game -> Json.createObjectBuilder()
                        .add("gameId", game.gameId())
                        .add("name", game.name())
                        .build())
                .forEach(json -> arrBuilder.add(json));

        return ResponseEntity.ok(arrBuilder.build().toString());

    }

}
