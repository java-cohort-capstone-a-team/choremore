package com.capstone.choremore.controller;


import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.EmailService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserRepo userDao;

    @Autowired
    private EmailService emailDao;

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
