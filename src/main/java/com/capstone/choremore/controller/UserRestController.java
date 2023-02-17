package com.capstone.choremore.controller;

import org.soulwing.maven.uuid.UuidMojo;
import com.capstone.choremore.models.User;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.EmailService;
import com.capstone.choremore.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserRepo userDao;

    @Autowired
    private AvatarRepo avatarDao;

    @Autowired
    private UserService userServ;

    @Autowired
    private EmailService emailDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static String makeNewPassword(Integer length){

        char[] possibleCharacters = new char[]{

                'A', 'b', 'C', 'd', 'E', 'f', 'G', 'h', 'I', 'j', 'K',
                'l', 'M', 'n', 'O', 'p', 'Q', 'r', 'S', 't', 'U', 'v',
                'W', 'x', 'Y', 'z','0','1','2','3','4','5','6','7','8','9'

        };

        StringBuilder randomSecCode = new StringBuilder();

        for(int i = 0; i < length; i++){

            Character randomChar = possibleCharacters[(int) Math.floor(Math.random() * possibleCharacters.length)];
            randomSecCode.append(randomChar);

        }

        return randomSecCode.toString();

    }

    @GetMapping("/check-username")
    public Map<String, Boolean> checkUsername(@RequestParam String username) {

        Map<String, Boolean> response = new HashMap<>();
        boolean exists = userDao.existsByUsername(username);

        response.put("exists", exists);

        return response;

    }

    @GetMapping("/check-email")
    public Map<String, Boolean> checkEmail(@RequestParam String email) {

        Map<String, Boolean> response = new HashMap<>();
        boolean exists = userDao.existsByEmail(email);

        response.put("exists", exists);

        return response;

    }

    @Setter
    @Getter
    @AllArgsConstructor
    private static class EmailVarReq {

        String email;

    }

    @Setter
    @Getter
    @AllArgsConstructor
    private static class EmailVarRes {

        Boolean status;
        int code;

    }

    @RequestMapping(path = "/verify-email", produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public EmailVarRes verifyEmail(@RequestBody EmailVarReq email) {

        int theCode = email.email.length();

        emailDao.prepareAndSend(email.email, "Choremore Verification Code", "Your verification code is: " + theCode);

        Map<String, String> response = new HashMap<>();

        response.put("status", "success");

        return new EmailVarRes(true, theCode);

    }

    @RequestMapping(path = "/sendAllRecords", produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public String sendAllRecords(@RequestBody EmailVarReq email) {

        try {

            User user = userDao.findByEmail(email.email);
            long id = user.getId();

            List<User> allUsers = userDao.findAll();
            List<User> myChildren = new ArrayList<>();

            for (User usr : allUsers) {

                if (usr.getRoles().equals("ROLE_CHILD") && usr.getAvatar().getParent().getId() == id) {

                    myChildren.add(usr);

                }

            }

            Map<String, String> childrensRecords = new HashMap<>();

            myChildren.forEach(child -> {

                String newPassword = makeNewPassword(10);

                child.setPassword(passwordEncoder.encode(newPassword));

                childrensRecords.put(child.getUsername(), newPassword);

            });

            StringBuilder sb = new StringBuilder();

            childrensRecords.forEach((k, v) -> sb.append(k + " -" + v));

            emailDao.prepareAndSend(email.email, "Choremore Records", "Here is your username and password: " + user.getUsername() + " " + user.getPassword() + "and your children's usernames and new passwords are: " + sb.toString());

            Map<String, String> response = new HashMap<>();

            response.put("status", "success");

            return "success";

        } catch (Exception e) {

            return "error";

        }

    }

//    @GetMapping("/check-username")
//    @ResponseBody
//    public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestParam String username) {
//        Map<String, Boolean> response = new HashMap<>();
//        boolean exists = userDao.existsByUsername(username);
//        response.put("exists", exists);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/check-email")
//    @ResponseBody
//    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
//        Map<String, Boolean> response = new HashMap<>();
//        boolean exists = userDao.existsByEmail(email);
//        response.put("exists", exists);
//        return ResponseEntity.ok(response);
//    }

}
