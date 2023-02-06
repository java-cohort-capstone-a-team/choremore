package com.capstone.choremore.serviceImplementation;

import com.capstone.choremore.models.User;
import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserRepo userDao;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public UserServiceImp(UserRepo userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user) {

        String hash = passwordEncoder.encode(user.getPassword());

        user.setPassword(hash);
        userDao.save(user);

    }

    public User getUserById(long id) {
        return userDao.getOne(id);
    }

    public List<User> getUsersByChildRole() {

        return userDao.findAll().stream().filter(user -> user.getRoles().contains("ROLE_CHILD")).collect(Collectors.toList());

    }

    public void authWithoutPassword(User user){

        try {
            List<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles());
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), passwordEncoder.encode(user.getPassword()));
            authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
