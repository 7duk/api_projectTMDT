package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "reply_comment")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyComment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "CONTENT_CMT")
    private String content;
    @Column(name = "CREATE_AT")
    private LocalDateTime time;
    @Column(name = "IS_CHECK")
    private Integer isCheck;
    @Column(name = "USER_ID")
    private Integer userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID",insertable = false,updatable = false)
    private User user;
    @Column(name = "COMMENT_ID")
    private Integer commentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID",insertable = false,updatable = false)
    @JsonIgnore
    private Comment comment;

}
