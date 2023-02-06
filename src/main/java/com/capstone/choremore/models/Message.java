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
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String body;

//    @Column(name = "date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date date;

    @ManyToOne
    private User child;

    public Message(Message copy) {
        id = copy.id;
        title = copy.title;
        body = copy.body;
        child = copy.child;
    }

}