package com.robbie.shoppingmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "email")
  private String email;

  @JsonIgnore
  @Column(name = "password_hash")
  private String passwordHash;

  @JsonIgnore
  @Column(name = "password_salt")
  private String passwordSalt;

  @Column(name = "created_date")
  private Timestamp createdDate;

  @Column(name = "last_modified_date")
  private Timestamp lastModifiedDate;
}
