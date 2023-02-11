package com.capstone.choremore;


import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.UserDetailsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailsLoader usersLoader;

    @Autowired
    UserRepo userDao;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {

        this.usersLoader = usersLoader;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(new UserDetailsLoader(userDao));
//        provider.setUserDetailsService(usersLoader);
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

//                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/", "/login", "/sign-up", "/css/**")
                .permitAll()
                .and().authorizeHttpRequests().requestMatchers("/create-avatar", "/avatar-creation", "/profile", "/chore-manager", "/avatar-manager", "/deletechore", "/editchore", "/createchore", "/approved", "/deletechild", "/approvedPro", "/deletechorePro", "/editchorePro").hasAuthority("ROLE_PARENT")
                .and().authorizeHttpRequests().requestMatchers("/child-profile", "/chores-view", "/message-board", "/createmsg", "/skill-builder", "/hpplus", "/strengthplus", "/deletemsg", "/defenseplus", "/changestatus", "/editmsg", "/editmsg", "/editmsgPro", "/deletemsgPro", "/changestatusPro", "/deletechild", "/avatar-form", "/avatarbuilder").hasAuthority("ROLE_CHILD")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        return http.build();
    }

}
