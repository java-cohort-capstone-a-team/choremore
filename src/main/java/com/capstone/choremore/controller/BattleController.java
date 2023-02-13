package com.capstone.choremore.controller;

import com.capstone.choremore.services.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BattleController {

    @Autowired
    private BattleService battleServ;

    @PostMapping("/battle")
    public String battle(@RequestParam(name = "enemy") long opId) {

        battleServ.postBattle(opId);

        return "redirect:/battle-arena";

    }

}
