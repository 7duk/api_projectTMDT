package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "comment")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "CONTENT_CMT")
    private String content;
    @Column(name = "CREATE_AT")
    private LocalDateTime time;
    @Column(name = "RATING")
    private Integer rating;
    @Column(name = "IS_CHECK")
    private Integer isCheck;
    @Column(name = "USER_ID")
    private Integer userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID",insertable = false,updatable = false)
    private User user;
    @Column(name = "ITEM_ID")
    private Integer itemId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID",insertable = false,updatable = false)
    private Item item;
    @OneToMany(mappedBy = "comment",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ReplyComment> replyComments;
}
