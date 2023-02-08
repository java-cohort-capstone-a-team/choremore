package com.capstone.choremore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "chores")
public class Chore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String body;

    @Column(length = 100)
    private String status = "Incomplete";

    @Column(nullable = false, length = 100)
    private long value;

    @ManyToOne
    private User parent;

    @OneToOne
    private User child;

    public Chore(Chore copy) {
        id = copy.id;
        title = copy.title;
        body = copy.body;
        status = copy.status;
        value = copy.value;
        parent = copy.parent;
        child = copy.child;
    }

}
