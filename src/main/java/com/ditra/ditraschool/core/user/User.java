package com.ditra.ditraschool.core.user;


import com.ditra.ditraschool.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql=" UPDATE user SET deleted =true WHERE id = ?")
public class User extends Auditable<String> implements Serializable  {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private String fullName;

    @JsonIgnore
    private String password;

    private String email;

    @JsonIgnore
    private String token;

    private String role;

    @JsonIgnore
    private Boolean suspended;

    @JsonIgnore
    private Boolean deleted;

    public User(String fullName, String password, String email) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        role = "USER";
        suspended = false;
        deleted = false;
    }


}