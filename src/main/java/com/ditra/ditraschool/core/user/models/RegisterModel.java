package com.ditra.ditraschool.core.user.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterModel {
    String fullName;
    String password;
    String email;
}
