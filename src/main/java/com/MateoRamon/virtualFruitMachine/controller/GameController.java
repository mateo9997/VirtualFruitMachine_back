package com.MateoRamon.virtualFruitMachine.controller;

import com.MateoRamon.virtualFruitMachine.model.Player;
import com.MateoRamon.virtualFruitMachine.model.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @PostMapping("/play")
    public ResponseEntity<Result> play(@RequestBody Player player) {
        // Implement game logic and return the result
    }
}
