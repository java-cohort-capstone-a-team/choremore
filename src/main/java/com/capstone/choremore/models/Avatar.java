package com.capstone.choremore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "avatars")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Column(unique = true, nullable = false, length = 100)
//    private String username;
//
//    @Column(nullable = false, length = 100)
//    private String password;

    @Column(length = 100)
    private String classType;

    @Column(length = 100)
    private String image;

    @Column(length = 100)
    private long level = 1;

    @Column(length = 100)
    private long hp = 1;

    @Column(length = 100)
    private long strength = 1;

    @Column(length = 100)
    private long defense = 1;

    @Column(length = 100)
    private long exp = 10;

//    @Column(nullable = false, length = 100)
//    private String roles = "ROLE_AVATAR";

    @ManyToOne
    private User parent;

    @OneToOne
    private User child;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "avatar")
//    List<Message> messages;

    public Avatar(Avatar copy) {
        id = copy.id;
        classType = copy.classType;
        level = copy.level;
        hp = copy.hp;
        strength = copy.strength;
        defense = copy.defense;
        exp = copy.exp;
        parent = copy.parent;
        child = copy.child;
    }
}