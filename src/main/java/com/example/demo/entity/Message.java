package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "message")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "CONTENT_MESS")
  private String message;
  @Column(name = "CREATE_AT")
  private LocalDateTime time;
  @Column(name = "SENDER_ID")
  private Integer senderId;
  @Column(name = "CHAT_ID")
  private Integer chatId;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SENDER_ID", insertable=false, updatable=false)
  private User sender;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CHAT_ID",insertable=false, updatable=false)
  private Chat chat;
  public  Message (String message,LocalDateTime time,Integer senderId,Integer chatId){
    this.message= message;
    this.time = time;
    this.senderId = senderId;
    this.chatId = chatId;
  }
}
