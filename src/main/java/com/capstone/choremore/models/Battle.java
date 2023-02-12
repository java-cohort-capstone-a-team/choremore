package com.capstone.choremore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "battles")
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Avatar op1;

    @OneToOne
    private Avatar op2;

    private Boolean winner;

    private Boolean tied;

    public Battle(Battle copy) {

        id = copy.id;
        op1 = copy.op1;
        op2 = copy.op2;
        winner = copy.winner;

    }

}
