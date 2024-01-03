package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "chat")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Chat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "PARTICIPANTL_1")
    private Integer participantId1;
    @Column(name = "PARTICIPANTL_2")
    private Integer participantId2;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTICIPANTL_2",insertable = false,updatable = false)
    private User participaint2;
    @OneToMany(mappedBy = "chat")
    private List<Message> messages;
    public Chat(Integer id,Integer participantId1,Integer participantId2,List<Message> messages){
        this.id = id;
        this.participantId1 = participantId1;
        this.participantId2 = participantId2;
        this.messages = messages;
    }
}

