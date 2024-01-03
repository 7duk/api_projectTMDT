package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDate;

/**
 * This would be JPA managed entity or Mongo document, but for this example it's just a POJO
 */

@Table(name = "user")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;
    @Column(name="ADDRESS")
    private String address;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "GENDER")
    private Integer gender;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "SALT")
    private String salt;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ROLE_ID")
    private Integer roleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID",insertable=false, updatable=false)
    private Role role;
    @Column(name = "IS_DELETE")
    private Boolean isDelete;
    @Column(name = "STATUS_ACCOUNT")
    private Integer statusAccount;
    @Column(name = "IMAGE")
    private String image;
    @Column(name = "PROVIDER")
    @Enumerated(EnumType.STRING)
    private Provider provider;
//    @JsonIgnore
//    @OneToMany(mappedBy = "odder", fetch = FetchType.LAZY)
//    @JoinColumn(name = "CUSTOMER_ID",insertable = false,updatable = false)
}
