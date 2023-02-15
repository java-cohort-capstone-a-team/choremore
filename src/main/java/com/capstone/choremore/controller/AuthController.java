package com.capstone.choremore.controller;

import com.capstone.choremore.models.User;
import com.capstone.choremore.models.UserWithRoles;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.capstone.choremore.repositories.UserRepo;

@Controller
@NoArgsConstructor
public class AuthController {

    @Autowired
    private UserRepo userDao;

    @GetMapping("/login")
    private String showLoginForm() {

        return "login";

    }

    @GetMapping("/")
    private String routeUser() {

        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(String.class)) {

            UserWithRoles principal = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userDao.getById(principal.getId());
            var role = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles());

            if (role.contains(new SimpleGrantedAuthority("ROLE_PARENT"))) {

                return "redirect:/profile";

            } else if (role.contains(new SimpleGrantedAuthority("ROLE_CHILD"))) {

                if (user.getAvatar().getImage() == null) {

                    return "avatars/avatar-form";

                }

                return "redirect:/child-profile";

            }

        }

        return "redirect:/login";

    }

}
