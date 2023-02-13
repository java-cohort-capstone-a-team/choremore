package com.capstone.choremore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.buf.StringUtils;
import javax.management.relation.Role;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private String first_name;

    @Column(length = 100)
    private String last_name;

    @Column(length = 100)
    private Date dob;

    @Column(unique=true, nullable = false, length = 100)
    private String username;

    @Column(length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 100)
    private String roles = "ROLE_PARENT";

    @OneToOne
    @JoinColumn(name = "avatar_id", nullable = true, referencedColumnName = "id")
    private Avatar avatar;

    @OneToMany(cascade = { CascadeType.ALL, CascadeType.MERGE }, mappedBy = "parent")
    List<Avatar> avatars;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "child")
    List<Message> messages;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    List<Chore> chores;

    public User(User copy) {

        id = copy.id;
        first_name = copy.first_name;
        last_name = copy.last_name;
        dob = copy.dob;
        username = copy.username;
        email = copy.email;
        password = copy.password;
        roles = copy.roles;
        avatar = copy.avatar;
        avatars = copy.avatars;
        messages = copy.messages;
        chores = copy.chores;

    }

    public User(long id, String first_name, String last_name, Date dob, String username, String email, String password, List<Role> roles, Avatar avatar, List<Avatar> avatars, List<Message> messages, List<Chore> chores, Chore chore) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.username = username;
        this.email = email;
        this.password = password;
        List<String> rolesStrList = new ArrayList<>();
        roles.forEach(role -> {
            rolesStrList.add(role.getRoleName());
        });
        this.roles = StringUtils.join(rolesStrList, ',');
        this.avatar = avatar;
        this.avatars = avatars;
        this.messages = messages;
        this.chores = chores;
    }

}
