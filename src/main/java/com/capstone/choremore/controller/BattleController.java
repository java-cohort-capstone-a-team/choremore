package com.capstone.choremore.controller;

import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.Battle;
import com.capstone.choremore.models.Message;
import com.capstone.choremore.models.User;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.repositories.BattleReop;
import com.capstone.choremore.services.AvatarService;
import com.capstone.choremore.services.BattleService;
import com.capstone.choremore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class BattleController {

//    @Autowired
//    private BattleService battleServ;

    @Autowired
    AvatarRepo avatarDao;

    @Autowired
    BattleReop battleDao;

    @Autowired
    UserService userServ;

    @Autowired
    AvatarService avatarServ;

    @GetMapping("/battle-won/{battleId}")
    public String showWin(@PathVariable long battleId, Model model) throws UnsupportedEncodingException {

        Avatar myAv = avatarServ.getCurrentAvatar();
        Battle myBattle = battleDao.getReferenceById(battleId);
        Avatar enemyAv = myBattle.getOp2();


        model.addAttribute("avatar", myAv);
        model.addAttribute("enemy", enemyAv);

        String base64Encoded = avatarServ.getAvatarImg(myAv);
        String enemyBase64Encoded = avatarServ.getAvatarImg(enemyAv);

        model.addAttribute("contentImage", base64Encoded);
        model.addAttribute("enemyImage", enemyBase64Encoded);

        model.addAttribute("user", userServ.getCurrentUser());
        model.addAttribute("battle", myBattle);

        return "battles/battle-won";

    }

    @GetMapping("/battle-lost/{battleId}")
    public String showLoss(@PathVariable long battleId, Model model) throws UnsupportedEncodingException {

        Avatar myAv = avatarServ.getCurrentAvatar();
        Battle myBattle = battleDao.getReferenceById(battleId);
        Avatar enemyAv = myBattle.getOp2();


        model.addAttribute("avatar", myAv);
        model.addAttribute("enemy", enemyAv);

        String base64Encoded = avatarServ.getAvatarImg(myAv);
        String enemyBase64Encoded = avatarServ.getAvatarImg(enemyAv);

        model.addAttribute("contentImage", base64Encoded);
        model.addAttribute("enemyImage", enemyBase64Encoded);

        model.addAttribute("user", userServ.getCurrentUser());
        model.addAttribute("battle", myBattle);

        return "battles/battle-lost";

    }

    @GetMapping("/battle-tied/{battleId}")
    public String showTied(@PathVariable long battleId, Model model) throws UnsupportedEncodingException {

        Avatar myAv = avatarServ.getCurrentAvatar();
        Battle myBattle = battleDao.getReferenceById(battleId);
        Avatar enemyAv = myBattle.getOp2();


        model.addAttribute("avatar", myAv);
        model.addAttribute("enemy", enemyAv);

        String base64Encoded = avatarServ.getAvatarImg(myAv);
        String enemyBase64Encoded = avatarServ.getAvatarImg(enemyAv);

        model.addAttribute("contentImage", base64Encoded);
        model.addAttribute("enemyImage", enemyBase64Encoded);

        model.addAttribute("user", userServ.getCurrentUser());
        model.addAttribute("battle", myBattle);

        return "battles/battle-tied";

    }

    @PostMapping("/battle")
    public String battle(@RequestParam(name = "enemy") long opId) {

        Battle battle = new Battle();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Avatar me = avatarDao.findAvatarByChildId(user.getId());

        Avatar enemy = avatarDao.getReferenceById(opId);

        long myScore = me.getHp() + me.getStrength() + me.getDefense();
        long enemyScore = enemy.getHp() + enemy.getStrength() + enemy.getDefense();

        if (myScore > enemyScore) {

            long newExp = me.getExp() + 2;

            if (newExp >= 10) {

                long newLevel = me.getLevel() + 1;

                me.setLevel(newLevel);
                me.setExp(newExp - 10);

            } else {

                me.setExp(newExp);

            }

            me.setBuild_points(me.getBuild_points() + 2);

            battle.setOp1(me);
            battle.setOp2(enemy);
            battle.setWinner(false);
            battle.setTied(false);

            avatarDao.save(me);
            battleDao.save(battle);
            long battleId = battle.getId();

            return "redirect:/battle-won/" + battleId;

        } else if (myScore < enemyScore) {

            long newExp = enemy.getExp() + 2;

            if (newExp >= 10) {

                long newLevel = enemy.getLevel() + 1;

                enemy.setLevel(newLevel);
                enemy.setExp(newExp - 10);

            } else {

                enemy.setExp(newExp);

            }

            enemy.setBuild_points(enemy.getBuild_points() + 2);

            battle.setOp1(me);
            battle.setOp2(enemy);
            battle.setWinner(true);
            battle.setTied(false);

            avatarDao.save(enemy);
            battleDao.save(battle);
            long battleId = battle.getId();

            return "redirect:/battle-lost/" + battleId;

        } else {

            long newExp = me.getExp() + 1;

            if (newExp >= 10) {

                long newLevel = me.getLevel() + 1;

                me.setLevel(newLevel);
                me.setExp(newExp - 10);

            } else {

                me.setExp(newExp);

            }

            long newExp1 = enemy.getExp() + 1;

            if (newExp1 >= 10) {

                long newLevel = enemy.getLevel() + 1;

                enemy.setLevel(newLevel);
                enemy.setExp(newExp1 - 10);

            } else {

                enemy.setExp(newExp1);

            }

            me.setBuild_points(me.getBuild_points() + 1);
            enemy.setBuild_points(enemy.getBuild_points() + 1);

            battle.setOp1(me);
            battle.setOp2(enemy);
            battle.setWinner(null);
            battle.setTied(true);

            avatarDao.save(me);
            avatarDao.save(enemy);
            battleDao.save(battle);
            long battleId = battle.getId();

            return "redirect:/battle-tied/" + battleId;

        }

//        return "redirect:/battle-arena";

    }

}
