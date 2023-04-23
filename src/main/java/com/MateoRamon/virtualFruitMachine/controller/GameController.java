package com.MateoRamon.virtualFruitMachine.controller;

import com.MateoRamon.virtualFruitMachine.model.Player;
import com.MateoRamon.virtualFruitMachine.model.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/game/")
public class GameController {
    private final String[] colors = {"blue", "red", "green", "yellow"};
    private final int costPerPlay = 1;
    private final int jackpotReward = 10;

    @PostMapping("/play")
    public ResponseEntity<Result> play(@RequestBody Player player) {
        if (player.getMoney() < costPerPlay) {
            return ResponseEntity.badRequest().body(null);
        }

        player.setMoney(player.getMoney() - costPerPlay);

        String[] slots = generateSlots();
        boolean isJackpot = checkJackpot(slots);

        if (isJackpot) {
            player.setMoney(player.getMoney() + jackpotReward);
        }

        Result result = new Result(isJackpot, player.getMoney(), slots);
        return ResponseEntity.ok(result);
    }

    private String[] generateSlots() {
        String[] slots = new String[3];
        Random random = new Random();
        for (int i = 0; i < slots.length; i++) {
            slots[i] = colors[random.nextInt(colors.length)];
        }
        return slots;
    }

    private boolean checkJackpot(String[] slots) {
        for (int i = 1; i < slots.length; i++) {
            if (!slots[i].equals(slots[i - 1])) {
                return false;
            }
        }
        return true;
    }
}

