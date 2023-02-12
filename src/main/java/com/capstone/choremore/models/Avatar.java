package com.capstone.choremore.models;

import com.sun.mail.iap.ByteArray;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "avatars")
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private String classType;

    @Lob
    @Column(name = "path_to_image", length = Integer.MAX_VALUE)
    private byte[] image;

    @Column(length = 100)
    private long level = 1;

    @Column(length = 100)
    private long hp = 1;

    @Column(length = 100)
    private long strength = 1;

    @Column(length = 100)
    private long defense = 1;

    @Column(length = 100)
    private long exp = 9;

    @Column(length = 100)
    private long build_points = 9;

    @ManyToOne
    private User parent;

    @OneToOne
    private User child;

    @Transient
    String imageString;

    public Avatar(Avatar copy) {

        id = copy.id;
        classType = copy.classType;
        image = copy.image;
        level = copy.level;
        hp = copy.hp;
        strength = copy.strength;
        defense = copy.defense;
        exp = copy.exp;
        build_points = copy.build_points;
        parent = copy.parent;
        child = copy.child;

    }

}